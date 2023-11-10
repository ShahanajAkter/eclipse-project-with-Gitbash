package utility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public  WebDriver driver;
	public String user_dir=System.getProperty("user.dir");
	public Logger log= LogManager.getLogger(BaseClass.class);
	public ExtentReports reports= new ExtentReports();
	public ExtentSparkReporter repoter= new ExtentSparkReporter("Results/jenkins_extentReports.html");
	public ExtentTest test;
	public void ibrowser(String browsername, String url) {
		
		if (browsername.equalsIgnoreCase("chrome")) {
			
			WebDriverManager.chromedriver().setup();
			log.info("browser initialization");
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(url);
			//log.info("Logged into Website");

			driver.navigate().refresh();

		} else if (browsername.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(url);
			driver.navigate().refresh();
		}
	}

	 public  void  screenshots(String folder)  {
			
			File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destinatonFilepath = new File(user_dir + "\\pictures\\"+folder +"\\home" + System.currentTimeMillis() + ".jpg");
			try {
				FileHandler.copy(ss,destinatonFilepath) ;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			log.info("Capture Screenshots");
		}

	 public  String  screenshotsforExtentRe(String folder) {
			Date date_time=new Date();
	String	date=date_time.toString().replace(" ", "_").replace(":", "_");
			File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destinatonFilepath = new File(user_dir + "\\pictures\\"+folder +"\\home" +date + ".jpg");
			try {
				FileHandler.copy(ss,destinatonFilepath) ;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			log.info("Capture Screenshots for ER");
			String absoluatePath= destinatonFilepath.getAbsolutePath();
			return absoluatePath;
		}


		public  void explecit_wait(WebElement elementname) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// create the object of WebDriverWait
			// class with passing driver and giving the duration of wait for the element
			wait.until(ExpectedConditions.elementToBeClickable(elementname));
		}
		public  void javascript_click( WebElement element) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", element);
		}
		
		public void explecit_titlewait(String title) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// create the object of WebDriverWait
			// class with passing driver and giving the duration of wait for the element
			wait.until(ExpectedConditions.titleContains(title));
		}
		public void switchto_childwindow_handles(String parent_win) {
			Set<String> win_handles = driver.getWindowHandles();
			for (String sessid : win_handles) {
				if (sessid != parent_win) {
					driver.switchTo().window(sessid);
					System.out.println(sessid);

				}

			}
		}
		public void configureReport() {
			//add system information/environment information to report
			reports.attachReporter(repoter);
			reports.setSystemInfo("Machine", "TestPC1");
			reports.setSystemInfo("OS", "Windows11");
			reports.setSystemInfo("browser", "chrome");
			reports.setSystemInfo("username", "shanu");
			
			//configuration to change look of the reports
			repoter.config().setDocumentTitle("Shanu's report");
			repoter.config().setReportName("Quafox test report");
			repoter.config().setTheme(Theme.DARK);
		}
		
		
	}
