package dns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class InitWebDriver {
    private static WebDriver webDriver;

    static void initWebDriver() {
        Properties properties = TestProperties.getInstance().getProperties();

        String browser = properties.getProperty("browser", "chrome");

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver", "webdrivers/chromedriver.exe"));
                webDriver = new ChromeDriver();
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", properties.getProperty("webdriver"));
                webDriver = new OperaDriver();
                break;
        }
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.get(properties.getProperty("url", "https://www.dns-shop.ru"));//можно было сюда проперти вызвать
    }

    static void quitWebDriver() {
        webDriver.quit();
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }

}
