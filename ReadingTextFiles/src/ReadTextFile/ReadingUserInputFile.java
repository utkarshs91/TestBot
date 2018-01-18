package ReadTextFile;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;

import framework.BaseMethods;
import framework.Buttons;
import framework.NavigateAppianMenu;

public class ReadingUserInputFile {
	
	static String SheetName = null;
	static List<GenerateTestReport> resultList = new ArrayList<>();
	static int ExecutionCounter = 0;
	private static String[] columns = {"Test Case", "Result", "StackTrace"};


    public static void RunTestSuite(String TestDir) throws InterruptedException, IOException {


        // This will reference one line at a time
        String line = null;
        int lineCounter = 0;
        String NameOfTestFile = "Test";
        
       

        try {
        	
        	File dir = new File (TestDir);
        	
        	 
        	 SheetName = dir.getName();
             
        	for (File fileName : dir.listFiles()) {
        		
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            
           
            
            NameOfTestFile = fileName.getName();
            System.out.println(NameOfTestFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	lineCounter++;
                	
                	 System.out.println(line);
                     String[] cmdArray = line.split(Pattern.quote("|"));
                     System.out.println(Arrays.toString(cmdArray));
                     
                    if (cmdArray[0].trim().equalsIgnoreCase("Login in Appian Using")) {
                    	
                    BaseMethods.loginAppian(cmdArray[1].trim(), cmdArray[2].trim(), cmdArray[3].trim());
                    System.out.println("Logging in executed");
                    
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Navigate to")) {
                    	
                    	if (urlValidator(cmdArray[1].trim()) == true) {
                    		
                    		BaseMethods.driver.navigate().to(cmdArray[1].trim());
                    	}
                    	
                    	else {
                    		
                    		switch (cmdArray[1].trim().toUpperCase()) {
                    		
                    		case "RECORDS":
                    		
                    			NavigateAppianMenu.NavigateToRecords();
                    			break;
                    			
                    		case "NEWS":
                    			
                    			NavigateAppianMenu.NavigateToNews();
                    			break;
                    			
                    		case "TASKS":
                    			
                    			NavigateAppianMenu.NavigateToTasks();
                    			break;
                    			
                    		case "REPORTS":
                    			
                    			NavigateAppianMenu.NavigateToReports();
                    			break;
                    			
                    		case "ACTIONS":
                    			
                    			NavigateAppianMenu.NavigateToActions();
                    		}
                    		
                    		
                    	}
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Click Link")) {
                    	
                    	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", cmdArray[1].trim()))).click();
                    	BaseMethods.WaitTillWorking();
                    	
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Populate Textfield")) {
                    	
                    	String FieldLabel = cmdArray[1].trim();
                    	String TextToPopulate = cmdArray[2].trim();
                    	
                    	if ("".equalsIgnoreCase(FieldLabel)) {
                    		
                    		System.out.println("Please enter the field name");
                    	}
                    	
                    	else {
                    	
                    	if ("".equalsIgnoreCase(TextToPopulate)) {
                    		
                    		System.out.println("Please enter value to be populated");
                    	}
                    	
                    	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("TextField", FieldLabel))).sendKeys(TextToPopulate);
                    	BaseMethods.WaitTillWorking();
                    }
                    	
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Populate Paragraph")) {
                    	
                    	String FieldLabel = cmdArray[1].trim();
                    	String TextToPopulate = cmdArray[2].trim();
                    	
                    	if ("".equalsIgnoreCase(FieldLabel)) {
                    		
                    		System.out.println("Please enter the field name");
                    	}
                    	
                    	else {
                    	
                    	if ("".equalsIgnoreCase(TextToPopulate)) {
                    		
                    		System.out.println("Please enter value to be populated");
                    	}
                    	
                    	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Paragraph", FieldLabel))).sendKeys(TextToPopulate);
                    	BaseMethods.WaitTillWorking();
                    }
                    	
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Click Button")) {
                    	
                    	String BtnName = cmdArray[1];
                    	
                    	
                    	
                    	if ("".equalsIgnoreCase(BtnName)) {
                    		
                    		System.out.println("Please enter name of button");
                    		
                    	}
                    	
                    	else {
       	
                    	Buttons.ClickButton(BtnName);
                    	             
                    	
                    	}
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Populate Field With")) {
                    	
                    	
                    	try {
                    		String LocatorType = cmdArray[1].trim();
                    		String LocatorString = cmdArray[2].trim();
                    		String TextToEnter = cmdArray[4].trim();
                    		
                    		if (LocatorType.equalsIgnoreCase("Id")) {
                    			
                    			BaseMethods.driver.findElement(By.id(LocatorString)).sendKeys(TextToEnter);
                    		}
                    		
                    		if (LocatorType.equalsIgnoreCase("Xpath")) {
                    			
                    			BaseMethods.driver.findElement(By.xpath(LocatorString)).sendKeys(TextToEnter);
                    		}
                    		
                    		
                    	} catch (ArrayIndexOutOfBoundsException e) {
                    		
                    		System.out.println("Bad format please write in correct format");
                    		
                    	}
                    }
                    
                    else if (cmdArray[0].trim().equalsIgnoreCase("Open URL")) {
                    	
                    	String url = cmdArray[1].trim();
                    	
                    	Boolean IsValidURL = urlValidator(url);
                    	
                    	if (IsValidURL) {
                    		
                    	BaseMethods.driver.get(url);
                    	BaseMethods.driver.manage().window().maximize();
                    	
                    	}
                    	
                    	else {
                    		
                    		System.out.println("Invalid URL, please check the URL and try again");
                    	}
                    	
                    }
                    
                    
                    
                    
               
               
        
            }   

            // Always close files.
            bufferedReader.close(); 
            System.out.println("Test - "+NameOfTestFile+" passed");
            String status = "PASSED";
            String stackTrace = "N/A";
            
            resultList.add(new GenerateTestReport(NameOfTestFile, status, stackTrace));

            ExecutionCounter+=1;
            
            
        }
     }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        catch(Exception ex) {
        	
        	System.out.println("Test - "+NameOfTestFile+" FAILED");
        	
        	String status = "FAILED";
        	String stackTrace = ex.toString();	
        	resultList.add(new GenerateTestReport(NameOfTestFile, status, stackTrace));
        	ExecutionCounter+=1;
        }
        
        writeReport(resultList);
        
    }
    
    
    
    public static boolean urlValidator(String url)
    {
        /*validating url*/
        try {
            new URL(url).toURI();
            return true;
        }
        catch (URISyntaxException exception) {
            return false;
        }
 
        catch (MalformedURLException exception) {
            return false;
        }
    }
    
    
    public static void writeReport(List<GenerateTestReport> ListTestReport) throws IOException {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
		
		System.out.println("Generating Test Report");

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Test-Report");

        /*// Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight((short) 16);
        headerFont.setColor(IndexedColors.RED.getIndex());


        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        */

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
           // cell.setCellStyle(headerCellStyle);
        }


        // Create Other rows and cells with employees data
        int rowNum = 1;
        
        System.out.println(resultList);
        
        for(GenerateTestReport tr: resultList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(tr.getTestName());

            row.createCell(1)
                    .setCellValue(tr.getTestStatus());

            Cell stackTraceCell = row.createCell(2);
            stackTraceCell.setCellValue(tr.getStackTrace());

		// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("Test-Report.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        
    }
        
        System.out.println("Test Report Generated");
}
}