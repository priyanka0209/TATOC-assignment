package TATOC.TATOC;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;



public class Basic {
	
	static WebDriver driver;
	static String pagename="Locators";
	static GetLocators obj;
	static String box1Class;
	static String box2Class;

	public static void setDriver(){
	String chromeDriverPath = "C:/Users/priyanka.sharma/Downloads/chromedriver_win32 (1)/chromedriver.exe";
	System.setProperty("webdriver.chrome.driver",chromeDriverPath);
	WebDriver driver=new ChromeDriver();
	}
	
	public static void main(String args[]) throws Exception{
		
		Basic.setDriver();
		obj=new GetLocators(driver,pagename);
		driver.get("http://10.0.1.86/tatoc/basic");
		obj.element("greenBox").click();
		Thread.sleep(1000);
		driver.switchTo().frame("main");
		WebElement box1=obj.element("Box1");
		System.out.println("Box1: class is "+box1.getAttribute("class"));
		box1Class=box1.getAttribute("class");
		box2Class="";
		
		while(!(box2Class.equals(box1Class))){
			
			obj.element("repaintBox_Btn").click();
			driver.switchTo().frame("child");
			WebElement box2=obj.element("Box2");
			System.out.println("Box2: class is "+box2.getAttribute("class"));
			 box2Class=box2.getAttribute("class");
			 driver.switchTo().defaultContent();
			 driver.switchTo().frame("main");	
		}
		
		obj.element("proceed").click();
		WebElement from=obj.element("dragMe");
		WebElement to=obj.element("dropbox");

		Actions builder=new Actions(driver);
	    Action dragNdrop= builder.clickAndHold(from).moveToElement(to).release(to).build();
	    dragNdrop.perform();
	    
	    obj.element("proceed").click();
	    obj.element("launchPopupWindow").click();
	  
	    String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
            
	    obj.element("enterName").click();
	    obj.element("enterName").sendKeys("Priyanka");
	    obj.element("submit").click();
	    
	    driver.switchTo().window(winHandleBefore);
	    obj.element("proceed").click();
		
	    obj.element("generateToken").click();
	    String token=obj.element("token").getText();
	    String tokenValue= token.split(": ")[1];
	    Cookie generatedToken=new Cookie("Token",tokenValue);
	    driver.manage().addCookie(generatedToken);
	    obj.element("proceed").click();
	    
	    
	    
	
	
		
	}
}
