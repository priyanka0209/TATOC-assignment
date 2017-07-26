package TATOC.TATOC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Advanced {
	
	static WebDriver driver;
	static String pagename="Locators";
	static GetLocators obj;
	static String cr_name="";
	static String cr_passkey="";
	
	public static WebDriver setDriver(){
		String chromeDriverPath = "C:/Users/priyanka.sharma/Downloads/chromedriver_win32 (1)/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",chromeDriverPath);
		WebDriver driver=new ChromeDriver();
		return driver;
		
		}
	
	public static  void executeQuery() throws  Exception{
		
		String username="tatocuser";
		String password="tatoc01";
		String url="jdbc:mysql://10.0.1.86:3306/tatoc";
		String Query1="select *  from identity;";
		String Query2="select *  from credentials;";
	    String symbol=obj.element("symbol").getText();
		String id=null;	
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection( url,username,password);
		Statement stmt = con.createStatement();		
		ResultSet rs=stmt.executeQuery(Query1);
		while(rs.next())
		{
			if(rs.getString(2).equalsIgnoreCase(symbol))
				id=rs.getString(1);  
		}
		
		ResultSet rs1=stmt.executeQuery(Query2);
		while(rs1.next()){
			
			if(rs1.getString(1).equals(id)){
				cr_name=rs1.getString(2);
				cr_passkey=rs1.getString(3);
			}	
		}
		
	}
	
	public static void main(String args[]) throws Exception{
		
		 driver=Advanced.setDriver();
		obj=new GetLocators(driver,pagename);
		driver.get("http://10.0.1.86/tatoc/advanced");
		
		obj.element("menu2").click();
		obj.element("goNext").click();
		Advanced.executeQuery();
		obj.element("name").sendKeys(cr_name);
		obj.element("passkey").sendKeys(cr_passkey);
		obj.element("proceedAdvanced").click();
		
	}

}
