package TATOC.TATOC;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class BasicExtended {
	
	static WebDriver driver;
	
	public  void setDriver(){
		String chromeDriverPath = "C:/Users/priyanka.sharma/Downloads/chromedriver_win32 (1)/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",chromeDriverPath);
		 driver=new ChromeDriver();
		}

	public static void main(String args[]) throws Exception{
		
		BasicExtended be=new BasicExtended();
		be.setDriver();
		driver.get("http://10.0.1.86/tatoc/basic");
		
	    JavascriptExecutor js = (JavascriptExecutor) driver;  
	    
	    //Click green box
	    js.executeScript("document.getElementsByClassName('greenbox')[0].click();");
	    
	   
	    //RepaintBox2
	    driver.switchTo().frame("main");
	   String box1Class= js.executeScript("return document.getElementById('answer').getAttribute('class');").toString();
	   
	    String box2Class="";
	    
	    while(!(box2Class.equals(box1Class))){
	    	
	    	js.executeScript("document.getElementsByTagName('a')[0].click();");
	    	driver.switchTo().frame("child");
	    	box2Class=js.executeScript("return document.getElementById('answer').getAttribute('class');").toString();
	    	driver.switchTo().defaultContent();
			 driver.switchTo().frame("main");	
			 
	    }
	    //Proceed
	    js.executeScript("document.getElementsByTagName('a')[1].click();");
	    
	    
	    //Drag n Drop
	    WebElement from=(WebElement) js.executeScript("return document.getElementById('dragbox');");
	    WebElement to=(WebElement) js.executeScript("return document.getElementById('dropbox');");
	    Actions builder=new Actions(driver);
	    Action dragNdrop= builder.clickAndHold(from).moveToElement(to).release(to).build();
	    dragNdrop.perform();
	    
	    //Proceed
	    js.executeScript("document.getElementsByTagName('a')[0].click();");
	    
	    
	    
	    //Launch Popup Window
	    js.executeScript("document.getElementsByTagName('a')[0].click();");
	    //Switch to new window
	    String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
	   //Enter name and submit
        js.executeScript("document.getElementById('name').setAttribute('value','Priyanka');");
        js.executeScript("document.getElementById('submit').click();");
        
        //switch to old window
        driver.switchTo().window(winHandleBefore);
        //Proceed
	    js.executeScript("document.getElementsByTagName('a')[1].click();");
	    
	    
	    //Generate Token
	    js.executeScript("document.getElementsByTagName('a')[0].click();");
	    
	    //Extract Token
	    String token=js.executeScript("return document.getElementById('token').textContent;").toString();
	    String tokenValue=token.split(": ")[1];
	    
	    //add cookie
	    Cookie generatedToken=new Cookie("Token",tokenValue);
	    driver.manage().addCookie(generatedToken);
	    
	  //Proceed
	    js.executeScript("document.getElementsByTagName('a')[1].click();");
	    
	
	}
}
