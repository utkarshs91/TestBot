package framework;

import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigateAppianMenu {
	
public static void NavigateToRecords() throws FileNotFoundException {
    	
    	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", "Records"))).click();
    	BaseMethods.WaitTillWorking();
    	
    }

public static void NavigateToNews() throws FileNotFoundException {
	
	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", "News"))).click();
	BaseMethods.WaitTillWorking();
}

public static void NavigateToTasks() throws FileNotFoundException {
	
	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", "Tasks"))).click();
	BaseMethods.WaitTillWorking();
}

public static void NavigateToReports() throws FileNotFoundException {
	
	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", "Reports"))).click();
	BaseMethods.WaitTillWorking();
	
}

public static void NavigateToActions() throws FileNotFoundException {
	
	BaseMethods.driver.findElement(By.xpath(BaseMethods.GenerateXpath("Link", "Actions"))).click();
	BaseMethods.WaitTillWorking();
}

}
