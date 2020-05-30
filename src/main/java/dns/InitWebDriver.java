package dns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.concurrent.TimeUnit;

public class InitWebDriver {
    private static WebDriver webDriver;

    static void initWebDriver() {
        String browser = System.getProperty("browser", "chrome");

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver.exe");
                webDriver = new ChromeDriver();
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", "webdrivers/operadriver.exe");
                webDriver = new OperaDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "webdrivers/geckodriver.exe");
                webDriver = new FirefoxDriver();
                break;
        }
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.get("https://www.dns-shop.ru");
    }

    static void quitWebDriver() {
        webDriver.quit();
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }

}
