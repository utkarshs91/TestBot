package framework;
import java.io.FileNotFoundException;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class Buttons {

	public static void ClickButton(String BtnLabel) throws FileNotFoundException
	{
		BaseMethods.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BaseMethods.GenerateXpath("Button", BtnLabel))));
		
		BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Button", BtnLabel))).click();
		
		BaseMethods.WaitTillWorking();
		
	}
	
	public static void ClickExactBtn (String BtnLabel) throws InterruptedException, FileNotFoundException {
		
		BaseMethods.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BaseMethods.GenerateXpath("ExactButton", BtnLabel))));
		BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("ExactButton", BtnLabel))).click();
		BaseMethods.WaitTillWorking();
		
	}
	
	public static void ClickIndexedBtn (String BtnLabel, String Index) throws InterruptedException, FileNotFoundException {
		
		int BtnIndex = Integer.parseInt(Index);
		
		BaseMethods.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BaseMethods.GenerateComplexXpath("IndexedButton", BtnLabel, BtnIndex))));
		BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateComplexXpath("IndexedButton", BtnLabel, BtnIndex))).click();
		BaseMethods.WaitTillWorking();
	}
	
}
