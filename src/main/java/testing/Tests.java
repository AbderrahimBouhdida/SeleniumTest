package testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Tests {
    private Config conf = new Config();
    private int timeTaken = 0;
    private int TIMEOUT = 20 * 60;
    private WebDriver driver;

    @Before
    public void setup() {
        String pathToChromeDriver;
        if (System.getProperty("os.name").equals("Linux")){
            pathToChromeDriver = "lib/chromedriver";  //Comment this line for windows
            System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
            System.out.println("Running on linux");
        }
        else if (System.getProperty("os.name").contains("Windows")){
            pathToChromeDriver = "lib/chromedriver.exe";  //Comment this line for linux
            System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
            System.out.println("Running on Windows");
        }

        driver = new ChromeDriver();
        conf.loadConfig();
    }

    @Test
    public void testOne() throws InterruptedException {
        driver.navigate().to("https://esprit-tn.com/ESPONLINE/Online/default.aspx");

        FluentWait wait = new FluentWait(driver).withTimeout(60, SECONDS).pollingEvery(5, SECONDS).ignoring(NoSuchElementException.class);
        boolean passed = false;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ContentPlaceHolder1_TextBox3")));
        driver.findElement(By.id("ContentPlaceHolder1_TextBox3")).sendKeys(String.valueOf(conf.getUsername()));
        do {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ContentPlaceHolder1_Button3")));
            driver.findElement(By.id("ContentPlaceHolder1_Button3")).click();
            //WebDriver wait = new WebDriverWait(driver, 30);

            if (isAlertPresent()) {
                passed = false;
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("system down user");
                sleep();
            } else {
                passed = true;
            }
        } while (!passed);
        boolean pass = false;
        do {
            driver.findElement(By.id("ContentPlaceHolder1_TextBox7")).sendKeys(String.valueOf(conf.getPassword()));
            driver.findElement(By.id("ContentPlaceHolder1_ButtonEtudiant")).click();
            if (isAlertPresent()) {
                pass = false;
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("system down pass");
                sleep();
            } else {
                pass = true;
            }
        } while (!pass);

        driver.navigate().to("https://esprit-tn.com/ESPONLINE/Etudiants/Resultat.aspx");
        List<WebElement> res = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder1_GridView1']/tbody/tr"));
        int x = 0;
        int rowI = res.size();
        do {
            System.out.println("iteration n° " + x++);
            driver.navigate().refresh();
            int rowA = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder1_GridView1']/tbody/tr")).size();
            if (rowA == rowI) {
                System.out.println("no update");
                sleep();
                continue;
            }
            System.out.println("update found! : ");
            List<WebElement> resN = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder1_GridView1']/tbody/tr"));
            for (int i = 1, j = 1; i < rowI && j < rowI; i++, j++) {
                if (!res.get(i).getText().equals(resN.get(j).getText())) {
                    System.out.println(res.get(i).getText());
                    i--;
                    rowI++;
                }
            }
            res = resN;
            sleep();

        } while (true);
    }

    @After
    public void teardown() {
        driver.close();
    }

    public void sleep() throws InterruptedException {
        do {
            Thread.sleep(1000);
            timeTaken = timeTaken + 1;
        } while (timeTaken < TIMEOUT);
        timeTaken = 0;
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }
}
