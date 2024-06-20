package Tech_Integration.Automation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WebClass
{
	

	WebDriver driver;
	public String baseUrl = "https://w3.healthians.co.in";
	//String driverPath = "C:\\Users\\Healthians\\Downloads\\chromedriver_win32\\chromedriver.exe";
	String driverPath = "C:\\Users\\Healthians\\Downloads\\chromedriver-win32\\chromedriver.exe";
	
	public String getOTPFromDB(String query){
	    String dbHost = "gateway.echl.in";
	    String dbPort = "10301";
	    String dbName = "d10";
	    String dbUser = "chesta";
	    String dbPass = "RQuE5jW19Lt&Pg2e$&R$wZ^s";

	    System.out.print("dbHost "+dbHost);
	    System.out.print("dbPort "+dbPort);
	    System.out.print("dbName "+dbName);
	    System.out.print("dbUser "+dbUser);

	    String DB_URL = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;
	    String DB_USER = dbUser;
	    String DB_PASSWORD = dbPass;
	    ResultSet rs=null;
	    String otp="";
	    
	    try {  
	            Class.forName("com.mysql.jdbc.Driver");  
	            Connection con=DriverManager.getConnection(  
	            DB_URL,DB_USER,DB_PASSWORD);  
	            //here sonoo is database name, root is username and password  
	            Statement stmt=con.createStatement();  
	            rs=stmt.executeQuery("Select * from user_otp where mobile_no='8650674289'");  
	            while(rs.next()) {
	            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(4));
	            otp = rs.getString(4);
	    }
	    //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	    con.close();  
	    }  catch(Exception e){ 

	        System.out.println(e);
	    }  
	    


	    return otp;

	}
	
	@SuppressWarnings("deprecation")
	@BeforeMethod(description = "launching Chrome browser")
	public void launchBrowser() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(description="NewCustomer")
	public void Newuser() throws InterruptedException
	{
		
		
		WebElement ele6= driver.findElement(By.xpath("(//*[@class='cityCurrnt'])[1]"));
		  ele6.click();
		  
		  @SuppressWarnings("deprecation")
		WebDriverWait wait1=new WebDriverWait(driver,90);
			WebElement element1=wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='mobile_number']")));
			element1.sendKeys("8650674289");
			
			Thread.sleep(3000);
			  WebElement ele1= driver.findElement(By.xpath("//*[@id='submit_login']"));
			  ele1.click();
			  
			  
			  String otp = getOTPFromDB("Select otp_code from user_otp where mobile_no='8650674289'");
			  System.out.print("otp is "+otp);
			  Thread.sleep(2000);
			  
			  
			  Instant start = Instant.now();
	          Duration timeout = Duration.ofSeconds(30);

	            while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
	                otp = getOTPFromDB("Select otp_code from user_otp where mobile_no='8650674289'");

	                if (otp != null) {
	                    break;
	                }

	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
				  //if (otp.length()==6)
			       if (otp != null) 
				  {
					  driver.findElement(By.xpath("//*[@class='form-control pincode-input-text first']")).sendKeys(String.valueOf(otp.charAt(0)));
					  driver.findElement(By.xpath("(//*[@class='form-control pincode-input-text mid'])[1]")).sendKeys(String.valueOf(otp.charAt(1)));
					  driver.findElement(By.xpath("(//*[@class='form-control pincode-input-text mid'])[2]")).sendKeys(String.valueOf(otp.charAt(2)));
					  driver.findElement(By.xpath("(//*[@class='form-control pincode-input-text mid'])[3]")).sendKeys(String.valueOf(otp.charAt(3)));
					  driver.findElement(By.xpath("(//*[@class='form-control pincode-input-text mid'])[4]")).sendKeys(String.valueOf(otp.charAt(4)));
					  driver.findElement(By.xpath("//*[@class='form-control pincode-input-text last']")).sendKeys(String.valueOf(otp.charAt(5)));
				  
	                System.out.println("Success: OTP received and entered.");
	            } else {
	               
	            	WebElement resendOtpButton = driver.findElement(By.xpath("//*[@id='resend_code']"));
	                resendOtpButton.click();
	                System.out.println("Failed: OTP not received from the database.");
	            }

				  
				  
				  JavascriptExecutor js= (JavascriptExecutor)driver;
				  js.executeScript("scroll(0,200);");
				  Thread.sleep(2000);
				  
				  WebElement ele2= driver.findElement(By.xpath("//*[@id='login_button']"));
				  ele2.click();
				  
				  WebElement ele3=driver.findElement(By.xpath("//*[@class='currentUser']"));
				  ele3.click();
				  Thread.sleep(2000);
				  WebElement ele4=driver.findElement(By.id("sign_out_menu"));
				  ele4.click();
						  
}
}
	

    
	



















				  


