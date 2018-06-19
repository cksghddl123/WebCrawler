package firstSelenium;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginEx {

	WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\JavaWorkspace\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://www.naver.com");

	}

//	@After
//	public void tearDown() {
//	
//		driver.quit();
//	}

//	@Test
//	public void testNaver() throws IOException {
//		driver.get("http://www.naver.com");
//		driver.findElement(By.id("id")).clear();
//		driver.findElement(By.id("id")).sendKeys("cksghddl123");
//		driver.findElement(By.id("pw")).clear();
//		driver.findElement(By.id("pw")).sendKeys("asdf");
//		driver.findElement(By.xpath("//INPUT[@type='submit']")).click();
//	}
//	
//	@Test
//	public void testNaver2() throws IOException {
//		driver.get("http://www.naver.com");
//		driver.findElement(By.id("id")).clear();
//		driver.findElement(By.id("id")).sendKeys("cksghddl123");
//		driver.findElement(By.id("pw")).clear();
//		driver.findElement(By.id("pw")).sendKeys("kcha16415442");
//		driver.findElement(By.xpath("//INPUT[@type='submit']")).click();
//	}

	

}
