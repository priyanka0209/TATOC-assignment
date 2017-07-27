package TATOC.TATOC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import  com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

public class Advanced {
	
	static WebDriver driver;
	static String pagename="Locators";
	static GetLocators obj;
	static String cr_name="";
	static String cr_passkey="";
	
	public static void setDriver(){
		String chromeDriverPath = "C:/Users/priyanka.sharma/Downloads/chromedriver_win32 (1)/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",chromeDriverPath);
		 driver=new ChromeDriver();
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
	
	public static String generateToken(String session_id){
		
		JsonPath response=RestAssured.get("http://10.0.1.86/tatoc/advanced/rest/service/token/{Session ID}",session_id).then().extract().jsonPath();
		String token=response.getString("token");
		return token;
			
	}
	
	public static void register(String session_id,String token){
		
		RestAssured.given().parameters("id", session_id, "signature", token, "allow_access", "1").post("http://10.0.1.86/tatoc/advanced/rest/service/register");
	}
	
	@SuppressWarnings("resource")
	public static String readFile() throws IOException{
		
		
		String path="C:/Users/priyanka.sharma/Downloads/file_handle_test.dat";
		FileReader file=new FileReader(path);
		BufferedReader br=new BufferedReader(file);
		String sign="";
		String line=br.readLine();
		while(line!=null){
	
		
			if(line.split(": ")[0].equals("Signature")){
			 sign=line.split(":" )[1];
			 break;
			}
			line=br.readLine();
			
		}
		return sign;
	}
		
	
	
	public static void main(String args[]) throws Exception{
		
		Advanced.setDriver();
		obj=new GetLocators(driver,pagename);
		driver.get("http://10.0.1.86/tatoc/advanced");
		
		obj.element("menu2").click();
		obj.element("goNext").click();
		
		Advanced.executeQuery();
		obj.element("name").sendKeys(cr_name);
		obj.element("passkey").sendKeys(cr_passkey);
		obj.element("proceedAdvanced").click();
		
		driver.get("http://10.0.1.86/tatoc/advanced/rest/#");
		String str=obj.element("session_id").getText();
		String session_id=str.split(": ")[1];
		System.out.println(session_id);
		String token=Advanced.generateToken(session_id);
		Advanced.register(session_id, token);
		obj.element("proceed").click();
		
		Thread.sleep(1000);
		
		obj.element("downloadFile").click();
		Thread.sleep(3000);
		String signature=Advanced.readFile();
		obj.element("signature").sendKeys(signature);
		obj.element("proceed2").click();
		
		
		
	}

}
