package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseMethods
{
	public static String downloadFilepath = "C:/OHA_Automation/OHA_Downloads";
	
	String ServicelineName = null;
	
	public static WebDriver driver = GetDriver();
	
	public static WebDriverWait wait = new WebDriverWait(driver, 15);
	
	public static JavascriptExecutor jse = (JavascriptExecutor)driver;
	
	//public static SoftAssert sofas = new SoftAssert();

	public static WebDriver GetDriver()
	{
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--incognito");
		
		DesiredCapabilities Cap = DesiredCapabilities.chrome();
		Cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		Cap.setCapability(ChromeOptions.CAPABILITY, options);
		Cap.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		
		@SuppressWarnings("deprecation")
		WebDriver driver = new ChromeDriver(Cap);
		
		return driver;

	}


	//Read the Properties file
	public static  Properties ReadPropertiesFile() throws FileNotFoundException 
	{
		String prop = "src/Config.properties";	
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(new File (prop));
		
		try
			{
		    props.load(fis);
			}
		
		catch (IOException e)
			{
		    System.out.println("Something went wrong!");
		    
			}
		return props;
		}
		
	public static Properties GetPropertiesFIleData() throws FileNotFoundException 
		
		{
			Properties Prop = ReadPropertiesFile();
			return Prop;
		}
	
	//Generic method to login into Appian
		public static void loginAppian (String url, String username, String password, String pin) throws IOException, InterruptedException 
			{
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				
			if (pin.equalsIgnoreCase("")) {
				
				driver.navigate().to(url);
				driver.manage().window().maximize();
				driver.findElement(By.xpath(ReadPropertiesFile().getProperty("AppianUserName"))).sendKeys(username);
				driver.findElement(By.xpath(ReadPropertiesFile().getProperty("AppianPassword"))).sendKeys(password);
				driver.findElement(By.xpath(ReadPropertiesFile().getProperty("LoginBtn"))).click();
				Thread.sleep(5000);
				WaitTillWorking();
				System.out.println("Logged in as - " +username);
			}
			
			else {
			
				
				// Covert pin received in input to an array
				char[] pinVault = pin.toCharArray();
				
				driver.navigate().to(url);
				driver.manage().window().maximize();
				
				// Populate username		
				driver.findElement(By.id(ReadPropertiesFile().getProperty("OktaUserNameField"))).sendKeys(username);
				
				//Populate Password			
				driver.findElement(By.id(ReadPropertiesFile().getProperty("OktaPassField"))).sendKeys(password);
				
				// Click login button			
				driver.findElement(By.xpath(ReadPropertiesFile().getProperty("OktaLoginBtn"))).click();
				Thread.sleep(5000);
				
				// Get verification string as displayed on okta page			
				String verificationString = driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("VerificationString"))).getText();
				System.out.println("Verification String - "+verificationString);
				
				// Get first pin digit to be entered
				String firstDigit = verificationString.substring(20,21);
				
				// Get second pin digit to be entered
				String secondDigit = verificationString.substring(26, 27);
				
				// Convert both the digit to int datatype so that it can be passed in pinVault array
				int firstIndex = Integer.parseInt(firstDigit)-1;
				int secondIndex = Integer.parseInt(secondDigit)-1;		
				System.out.println("First Digit - "+firstDigit+"\nSecond Digit - "+secondDigit);
				System.out.println("Pin value at "+firstDigit+" is "+pinVault[firstIndex]+" and at "+secondDigit+" is "+pinVault[secondIndex]);
				
				// Convert the values of pinVault array back to string
				String pinInput = String.valueOf(pinVault[firstIndex])+String.valueOf(pinVault[secondIndex]);
				System.out.println("Pin Input - "+pinInput);
				
				// Populate pin input field
				driver.findElement(By.id(GetPropertiesFIleData().getProperty("PinInputField"))).sendKeys(pinInput);
				
				// Click submit button
				driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("PinSubmitBtn"))).click();
				Thread.sleep(8000);
			}
		}
	

	
	
	
	// Overloaded method for Login In Appian
	public static void loginAppian (String URL, String Username, String Password) throws IOException, InterruptedException 
		{
			loginAppian(URL, Username, Password, "");
		}
	
	// Generic method to login in Booking Bug
	
		public static void loginBB (String bbURL, String bbUser, String bbPass) throws FileNotFoundException {
			
			driver.get(bbURL);
			driver.findElement(By.id(GetPropertiesFIleData().getProperty("bbEmailField"))).sendKeys(bbUser);
			driver.findElement(By.id(GetPropertiesFIleData().getProperty("bbPassField"))).sendKeys(bbPass);
			driver.findElement(By.xpath(GenerateXpath("Button", "Log In"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GetPropertiesFIleData().getProperty("allSites"))));
			
		}
		
	
	//Generic Method which will generate the xpaths by giving Field Type and Label as its input
	public static String GenerateXpath (String FieldType, String FieldLabel)
		{
			if(FieldType.equalsIgnoreCase("Button"))
			{
				String BtnXpath = "//button[contains(text(), '"+FieldLabel+"')]";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BtnXpath)));
				return BtnXpath;
			}
			else if(FieldType.equalsIgnoreCase("Link"))
			{
				String LinkXpath = "(//a[contains(text(), '"+FieldLabel+"')])[1]";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LinkXpath)));
				return LinkXpath;
			}
			else if(FieldType.equalsIgnoreCase("TextField"))
			{
				String TextFieldXpath = "(//label[contains(text(),'"+FieldLabel+"')]/../..//input)[1]" ;
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TextFieldXpath)));
				return TextFieldXpath;
			}
			else if(FieldType.equalsIgnoreCase("Paragraph"))
			{
				String ParagraphXpath = "//label[contains(text() ,'"+FieldLabel+"')]/../..//textarea";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ParagraphXpath)));
				return ParagraphXpath;
			}
			else if(FieldType.equalsIgnoreCase("AdditionalDashboard"))
			{
				String ADxpath = "//span[text() = '"+FieldLabel+"']";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADxpath)));
				return ADxpath;
			}
			else if (FieldType.equalsIgnoreCase("Strong")) 
			{
			    String StrongXpath = "//strong[contains(text(), '"+FieldLabel+"')]";
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(StrongXpath)));
			    return StrongXpath;
			    
			}
			else if (FieldType.equalsIgnoreCase("ReadOnlyLabel")) 
			{
			    String StrongXpath = "(//span[contains(text(), '"+FieldLabel+"')]//../..//p)[1]";
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(StrongXpath)));
			    return StrongXpath;
			    
			}
			else if (FieldType.equalsIgnoreCase("SectionExpand")) {
				
				String ExpandXpath = "//h3[contains(text(), '"+FieldLabel+"')]/../..//a[@title='Expand']";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ExpandXpath)));
				return ExpandXpath;
				
			}
			
			else if (FieldType.equalsIgnoreCase("SelectionGrid")) {
				
				String SelectedXpath = "(//tr[td//text()[contains(., '"+FieldLabel+"')]]//td[1]//input)";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SelectedXpath)));
				return SelectedXpath;
			}
			
			
			else if (FieldType.equalsIgnoreCase("TableTextArea")) {
				
				String SelectedXpath = "(//td[contains(.,'"+FieldLabel+"')])/../..//textarea";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SelectedXpath)));
				return SelectedXpath;
			}
			
			else if (FieldType.equalsIgnoreCase("SelectServiceorServiceline")) {
				
				String ServiceOrServiceLineXpath = "//tr[td//text()='"+FieldLabel+"']//td[1]//input";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ServiceOrServiceLineXpath)));
				return ServiceOrServiceLineXpath;
			}
			
			else if(FieldType.equalsIgnoreCase("CheckBox"))
			{
				String Checkboxxpath = "//label[contains(text(),'"+FieldLabel+"')]/..//input";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Checkboxxpath)));
				return Checkboxxpath;
			}
			
			else if(FieldType.equalsIgnoreCase("PractitionerComment"))
			{
				String PractComments = "(//tr[contains(.,'"+FieldLabel+"')]//td[3])//input";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PractComments)));
				return PractComments;
			}
			
				else if(FieldType.equalsIgnoreCase("ImmsConfirmDueDate")) {
				
				String ImmsDueDateField = "//strong[contains(text(), '"+FieldLabel+"')]/../..//em";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ImmsDueDateField)));
				return ImmsDueDateField;
			}
			
			if(FieldType.equalsIgnoreCase("ExactButton"))
			{
				String BtnXpath = "//button[(text()= '"+FieldLabel+"')]";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BtnXpath)));
				return BtnXpath;
			}
			
			if (FieldType.equalsIgnoreCase("h2")) {
				
				String headingXpath = "//h2[text()='"+FieldLabel+"']";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headingXpath)));
				return headingXpath;
			}
			
			
			return FieldLabel;
		}
	
	//Xpath for Radio button and checkbox
	public static String GenerateComplexXpath (String FieldType, String FieldLabel, int index)
	{
		if (FieldType.equalsIgnoreCase("Radiobtn") || FieldType.equalsIgnoreCase("CheckBox"))  
		{
			String EleXpath = "(//label[contains(text(),'"+FieldLabel+"')]/../..//input)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EleXpath)));
			return EleXpath;
		}
		
		if (FieldType.equalsIgnoreCase("TableInput")) {
			
			String EleXpath = "(//tr[td//text()[contains(., '"+FieldLabel+"')]]//td["+index+"]//input)";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EleXpath)));
			return EleXpath;
		}
		if (FieldType.equalsIgnoreCase("RadioGrid")) {
			
			String EleXpath = "(//label[text()='"+FieldLabel+"']/..//input)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EleXpath)));
			return EleXpath;
		}
		if(FieldType.equalsIgnoreCase("SpanRadiobtn") || FieldType.equalsIgnoreCase("SpanCheckBox"))

		{
			String EleXpath = "(//span[contains(text(),'"+FieldLabel+"')]/../..//input)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EleXpath)));
			return EleXpath;
		}
		else if (FieldType.equalsIgnoreCase("DropDown")) {
			
			String DpDownXpath = "//span[contains(text(),'"+FieldLabel+"')]/../..//option["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DpDownXpath)));
			return DpDownXpath;
		}
		else if(FieldType.equalsIgnoreCase("DuplicateLink"))
		{
			String LinkXpath = "(//a[contains(text(),'"+FieldLabel+"')])["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LinkXpath)));
			return LinkXpath;
			
		}
		else if(FieldType.equalsIgnoreCase("HardCodeDropDown"))
		{
			String Checkboxxpath = "(//option[text()='"+FieldLabel+"'])["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Checkboxxpath)));
			return Checkboxxpath;
		}
		
		else if (FieldType.equalsIgnoreCase("div")) {
			
			String divXpath = "(//div[contains(text(), '"+FieldLabel+"')])["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(divXpath)));
			return divXpath;
		}
		else if (FieldType.equalsIgnoreCase("ExactLink")) {
			
			String linkXpath = "(//a[contains(text(), '"+FieldLabel+"')])["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkXpath)));
			return linkXpath;
		}
		else if (FieldType.equalsIgnoreCase("Textarea")) {
			
			String textAreaXpath = "(//label[contains(text(), '"+FieldLabel+"')]/../..//textarea)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textAreaXpath)));
			return textAreaXpath;
		}
		
		else if (FieldType.equalsIgnoreCase("IndexedButton")) {
			
			String BtnXpath = "//button[(text()= '\"+FieldLabel+\"')]["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BtnXpath)));
			return BtnXpath;
		}
		
		return FieldLabel;
	}
	
	//Xpath for Assessments web elements
	public static String AssessmentXpath (String FieldType, String FieldLabel, int index)
	{
		if (FieldType.equalsIgnoreCase("TextArea"))  
		{
			String TextAreaXpath = "((//td[contains(.,'"+FieldLabel+"')])/../..//textarea)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TextAreaXpath)));
			return TextAreaXpath;
		}
		
		if(FieldType.equalsIgnoreCase("RadioBtn"))
		{
			String RadioBtnXpath = "((//td[contains(.,'"+FieldLabel+"')])/../..//input)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RadioBtnXpath)));
			return RadioBtnXpath;
			
		}
		
		if(FieldType.equalsIgnoreCase("DropDown"))
		{
			String DpDownXpath = "((//td[contains(.,'"+FieldLabel+"')])/../..//option)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DpDownXpath)));
			return DpDownXpath;
			
		}
		
		return FieldLabel;
	}
	
	public static String GetTestLinkXpath(String Label)
	{
		String TestLinkXpath = "//h3[contains(text(),'Following test')]/../..//a[text()='"+Label+"']";
		
		return TestLinkXpath;
		
	}
	
	public static String GetAssessmentDataonDashboard(String FieldLabel)
	{
		String AssessmentXpath = "(//label[text()='"+FieldLabel+"']/../..//div)[4]";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AssessmentXpath)));
		return AssessmentXpath;
		
	}
	
	public static String getAssessmentScreenXpaths(String FieldType, String FieldLabel, int index)
	{
	
		
		if(FieldType.equalsIgnoreCase("RadioBtn"))
		{
			String AssessmentRadioBtnXpath = "(//tr[contains(.,'"+FieldLabel+"')][1]//td[2]//input)["+index+"]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AssessmentRadioBtnXpath)));
			return AssessmentRadioBtnXpath;
			
		}
		return FieldType;
		
	}
	
	//Generic Method to Logout from Appian
	public static  void AppianLogout() throws InterruptedException, IOException 
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ReadPropertiesFile().getProperty("LogoutDropdown"))));
		driver.findElement(By.xpath(ReadPropertiesFile().getProperty("LogoutDropdown"))).click();
		waitForPageLoaded();
		driver.findElement(By.xpath(ReadPropertiesFile().getProperty("SignOutLink"))).click();
		waitForPageLoaded();
		
		System.out.println("Successfully Logged Out");
		
	}

	//Method to Upload the Document 
	public static void UploadDoc(String UploadGridLabel, String Uploadfilepath) throws InterruptedException
	{ 
	
		driver.findElement(By.xpath("(((//span[contains(text(),'"+UploadGridLabel+"')]/../..//table)//tr)[2]/../..//input)[3]")).sendKeys(Uploadfilepath);
		WaitForTime(5000);
		
		driver.findElement(By.xpath("(((//span[contains(text(),'"+UploadGridLabel+"')]/../..//table)//tr)[2]/../..//option)[3]")).click();
		LongWait();

	}
	
	//Method to upload document without Grid Label
	public static String UploadDocWithoutGridLabel(int trIndex) throws InterruptedException
	{ 
		
		String UploadXpath = "(//tr)["+trIndex+"]/td[1]//input[3]";
		
		return UploadXpath;
	}
	
	// Generic method to open BB calendar view for selected date
	
	public static void BBLoadCalendarViewByDate(String userDate) throws FileNotFoundException, InterruptedException {
		
		// Get months difference between user input date and current system date
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		 try {
			
			// Parsing user input date in dd/MM/yyyy format 
		   Date userInputDate = df.parse(userDate);
		   
		   String userGivenDate = df.format(userInputDate);
		   
		  
		   
		    // Getting system's date today
		    Date currentDate = new Date();
		    
		    // formatting date today to dd/MM/yyyy format
		    df.format(currentDate);
			
			String DateToday = df.format(currentDate);
			
			System.out.println("Date today is - "+DateToday);
			
			System.out.println("User Input date is - "+userGivenDate);
			
			// Getting difference of months between the two dates
			Calendar start = Calendar.getInstance();
			start.setTime(currentDate);
			Calendar end = Calendar.getInstance();
			end.setTime(userInputDate);
			
			// Initializing month difference by zero
			int monthsBetween = 0;
           int dateDiff = end.get(Calendar.MONTH)-start.get(Calendar.MONTH); 
           
           
           
           System.out.println("Date difference - "+dateDiff);
           
// If date difference is less then zero
if(dateDiff<0) {
               int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);           
                dateDiff = (end.get(Calendar.DAY_OF_MONTH)+borrrow)-start.get(Calendar.DAY_OF_MONTH);
                monthsBetween--;
// If date difference is greater then zero
if(dateDiff>0) {
                    monthsBetween++;
                }
           }
           else {
        	   
        	   // If the date provided by user is in same month (this does not handles past date)
        	   
               monthsBetween=0;
           }      
           monthsBetween += end.get(Calendar.MONTH)-start.get(Calendar.MONTH);      
           monthsBetween  += (end.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12;
           
           System.out.println("Difference between months - "+monthsBetween);
           
           // Open date picker panel
           
           driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("openDatePicker"))).click();
           waitForPageLoaded();
           
          // Open user provided month panel in datepicker
           
           
           
           for(int i=1;i<=monthsBetween;i++) {
        	   
        	   
        	  WebElement openCalBtn = driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("pullDateButton")));
        	  
        	  // Executing Javascript to open calendar view
        	  clickElement(openCalBtn);
        	   waitForPageLoaded();
        	   System.out.println("Pulled calendar");
        	   
           }
           
           // Select user provided date 
           
           String day = userDate.substring(0,2);
           
           WebElement calDate = driver.findElement(By.xpath("//td/button/span[contains(text(), '"+day+"')]"));   
           clickElement(calDate);
           
           // Wait for 8 seconds
           WaitForTime(8000);
           
           // Open the day view
           driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("dayButton"))).click();
           
           // Wait for 8 seconds allowing calendar view to render
           WaitForTime(8000);
           
			
		
		} catch (ParseException e) {
			
			e.printStackTrace();
		}	
	
	
		
	}
	
	// Functionality to scroll at bottom of the page
	
	public static void scrollDown () {
		
		if (driver instanceof JavascriptExecutor) {
		    ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
		} else {
		    throw new IllegalStateException("This driver does not support JavaScript!");
		}
		
	}
	
	// Javascript method to perform click
	
	public static void clickElement(WebElement element) {
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
  	  	jse.executeScript("arguments[0].click();", element);
		
		
	}
	
	public static void openSessionCreationWindow() throws FileNotFoundException {
		
		Actions action = new Actions(driver);
		WebElement startPoint = driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("openSessionSource")));
		WebElement endPoint = driver.findElement(By.xpath(GetPropertiesFIleData().getProperty("openSessionTarget")));
		action.dragAndDrop(startPoint, endPoint);
		System.out.println("Opened session creation box");
	}
	

	
	
	// TestNG soft Assert
	/*public static void SoftAssertString(String actual, String expected)
	{
		sofas.assertEquals(actual, expected);
	}
	
	public static void SoftAssertStringContains(String actual, String ExpectedText) {
		
		sofas.assertTrue(actual.contains(ExpectedText));
	}*/
	
	
	//method for to wait until page loaded

    public static void waitForPageLoaded() throws InterruptedException 
    {
        Thread.sleep(3000);
	}
    public static void LongWait() throws InterruptedException 
    {
    	
        Thread.sleep(6000);
	}
    public static void WaitForTime(int Time) throws InterruptedException
    {
    	System.out.println("Waiting for " +Integer.parseInt(Integer.toString(Time).substring(0,Integer.toString(Time).length()-3)) +" seconds");
    	Thread.sleep(Time);
 	
    }
    
    public static void WaitTillWorking() throws FileNotFoundException {
    	
     wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(GetPropertiesFIleData().getProperty("Working"))));
     
    }
}