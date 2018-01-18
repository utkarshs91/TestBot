package framework;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.BaseMethods;


public class DateTimeMethods {

	
	//Generic Method to generate the Date by giving Date field label and Date as input
	public static void populateDate (String dateFieldName, String date) 
	{	
		
		  String DateXpath = "(//label[text()='"+dateFieldName+"']/../..//input)[1]";
		  
		  String dateFieldId = BaseMethods.driver.findElement(By.xpath(DateXpath)).getAttribute("id");
		  
		  BaseMethods.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DateXpath)));
			
		  if (BaseMethods.driver instanceof JavascriptExecutor)
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').focus()");
		  }      
		  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(date);
		  if (BaseMethods.driver instanceof JavascriptExecutor) 
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').blur()");
		  }    

	 }
	
	public static void populateDateLongLabel (String dateFieldName, String date) 
	{	
		
		  String DateXpath = "(//label[contains(text(), '"+dateFieldName+"')]/../..//input)[1]";
		  
		  String dateFieldId = BaseMethods.driver.findElement(By.xpath(DateXpath)).getAttribute("id");
		  
		  BaseMethods.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DateXpath)));
			
		  if (BaseMethods.driver instanceof JavascriptExecutor)
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').focus()");
		  }      
		  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(date);
		  if (BaseMethods.driver instanceof JavascriptExecutor) 
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').blur()");
		  }    

	 }
	
	// Method to populate Date Field
	public static void populateBookingDateTimeDate (String dateFieldName, String date, String time) throws InterruptedException 
	{

		  String DateXpath = "(//span[text()='"+dateFieldName+"']/../..//input)[1]";
		  String TimeXpath = "(//span[text()='"+dateFieldName+"']/../..//input)[3]";
		  
		  String dateFieldId = BaseMethods.driver.findElement(By.xpath(DateXpath)).getAttribute("id");
		
		  String timeFieldId = BaseMethods.driver.findElement(By.xpath(TimeXpath)).getAttribute("id");

		  if (BaseMethods.driver instanceof JavascriptExecutor)
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').focus()");
		  }      
		  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(date);
		  
		  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(Keys.TAB);
		  
		  BaseMethods.waitForPageLoaded();
		  
		  BaseMethods.driver.findElement(By.id(timeFieldId)).sendKeys(time);
		 
		  if (BaseMethods.driver instanceof JavascriptExecutor) 
		  {
		   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').blur()");
		  }   
	}
		  
	//Generic Method to generate the Date by giving Date field label and Date as input
		public static void ImmsHistoryDateFill (String DiseaseLabel, String date) 
		{
				  
			  String DateXpath = "(//tr[contains(.,'"+DiseaseLabel+"')])//td[3]//input[1]";
			  
			  
			  String dateFieldId = BaseMethods.driver.findElement(By.xpath(DateXpath)).getAttribute("id");
			  
			  if (BaseMethods.driver instanceof JavascriptExecutor)
			  {
			   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').focus()");
			  }      
			  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(date);
			  if (BaseMethods.driver instanceof JavascriptExecutor) 
			  {
			   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').blur()");
			  }    

		 }
			
		
		public static String FutureDate(Integer numberOfDays)
		{
			    SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");            
			    Calendar c = Calendar.getInstance();        
			    c.add(Calendar.DATE, numberOfDays);  // number of days to add      
			    String date = (String)(formattedDate.format(c.getTime()));
			    return date;
		}
		
		
		 public static String GenerateBirthDate() 
		 {

		        GregorianCalendar gc = new GregorianCalendar();

		        int year = RandomGeneration.randBetween(1950, 1999);

		        gc.set(Calendar.YEAR, year);

		        int dayOfYear = RandomGeneration.randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

		        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		        
		        String DOB = gc.get(Calendar.DAY_OF_MONTH) + "/" + (gc.get(Calendar.MONTH) + 1) + "/" + gc.get(Calendar.YEAR);
		        	       
		        return DOB;
		 }
		 
		 public static String NextWorkingDate (String nextDateString) throws ParseException {
			 
			 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			 Date nextDate = df.parse(nextDateString);
			 
			 for (int i=1;i<=6;i++) {
				 
				 if (isWorkingDay(nextDate)) {
					 
					 break;
				 }
				 else {
					 
					 Calendar c = Calendar.getInstance();
					 c.setTime(nextDate);
					 c.add(Calendar.DATE, 1);
					 nextDateString = (String)(df.format(c.getTime()));
					 nextDate = df.parse(nextDateString);
				 }
				 
				 
			 }
			 
			 return nextDateString;
			 
		 }
		 
		 public static boolean isWorkingDay(Date date) {
			 
			 Calendar c = Calendar.getInstance();
			 c.setTime(date);
			 int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			 
			 if ((dayOfWeek==7) || (dayOfWeek==1)) {
				 return false;
			 }
			 
			 return true;
		 }
		 
		 public static void populateBatchExpiryDate(String date) throws FileNotFoundException {
			 
			 WebElement ExpiryDateField = BaseMethods.driver.findElement(By.xpath(BaseMethods.GetPropertiesFIleData().getProperty("BatchExpiryDate")));
			 BaseMethods.wait.until(ExpectedConditions.visibilityOf(ExpiryDateField));
			 String dateFieldId = ExpiryDateField.getAttribute("id");
			 
			 if (BaseMethods.driver instanceof JavascriptExecutor)
			  {
			   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').focus()");
			  }      
			  BaseMethods.driver.findElement(By.id(dateFieldId)).sendKeys(date);
			  if (BaseMethods.driver instanceof JavascriptExecutor) 
			  {
			   ((JavascriptExecutor) BaseMethods.driver).executeScript("document.getElementById('"+dateFieldId+"').blur()");
			  }    
			 
		 }
		 
	}

