package TATOC.TATOC;

import java.io.BufferedReader;
import java.io.FileReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;



public class GetLocators {
	
	
	WebDriver driver;
	  String pagename;
	  
		
		public GetLocators(WebDriver driver,String pagename){
			
			this.driver=driver;
			this.pagename=pagename;
			PageFactory.initElements(driver,this);
			
		}
		
		@SuppressWarnings("resource")
		public String[] readPage(String pagename,String element) throws Exception{
			BufferedReader br=null;
			String line1="";
			String path="src/main/resource/"+pagename+".txt";
			FileReader file=new FileReader(path);
			br=new BufferedReader(file);
			String line = br.readLine();
	        while (line != null) {
	            if (line.split(":", 3)[0].equalsIgnoreCase(element)) {
	                line1 = line;
	                break;
	            }
	            line = br.readLine();    
		}
	        
	        return line1.split(":", 3);

	}
		
		public By getLocatorValue(String locatorType, String locatorValue){

			switch (Locators.valueOf(locatorType)) {
			case id:
				return By.id(locatorValue);
			case x_path:
			
				return By.xpath(locatorValue);
			case name:
				return By.name(locatorValue);
			case classname:
				return By.className(locatorValue);
			case css:
				return By.cssSelector(locatorValue);
			default:
				return By.id(locatorValue);
			
			}
			
		}

	  public By getLocator(String element) throws Exception{
		String[] locatorTypeAndValue=readPage(this.pagename,element);
		return getLocatorValue(locatorTypeAndValue[1].trim(),locatorTypeAndValue[2].trim());
	}

	   public  WebElement element(String element) throws Exception {
			return driver.findElement(getLocator(element));
	}


}
