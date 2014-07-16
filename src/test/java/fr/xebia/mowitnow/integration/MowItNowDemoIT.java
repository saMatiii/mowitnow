package fr.xebia.mowitnow.integration;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.xebia.mowitnow.io.web.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MowItNowDemoIT {

  private WebDriver driver;

  private ConfigurableApplicationContext context;

  private final String instructions = "5 5" + "\n1 2 N" + "\nGAGAGAGAA" + "\n3 3 E"
      + "\nAADAADADDA";

  @Before
  public void setUp() throws Exception {
    // context = SpringApplication.run(Application.class, new String[] {});
    // driver = new HtmlUnitDriver();
    // ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
    driver = new FirefoxDriver();
    driver.get(url());
  }

  private String url() {
    return "http://localhost:" + System.getProperty("server.port", "8080");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    // SpringApplication.exit(context);
  }

  @Test
  public void demo() throws InterruptedException {
    driver.findElement(By.id("instructions")).clear();
    driver.findElement(By.id("instructions")).sendKeys(instructions);
    WebDriverWait wait = new WebDriverWait(driver, 20);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("start")));
    driver.findElement(By.id("start")).click();
    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("demo-result"))));
    String result = driver.findElement(By.id("demo-result")).getText();
    assertTrue("contenu incorrect : " + result, result.contains("La tondeuse 1 => (2,3,NORTH)"));
    assertTrue("contenu incorrect : " + result, result.contains("La tondeuse 2 => (5,1,EAST)"));
  }
}
