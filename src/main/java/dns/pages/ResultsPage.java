package dns.pages;

import org.openqa.selenium.By;

public class ResultsPage extends BasePage {
    private String product = "//a[contains(@href, 'product') and contains(text(), '%s')]";

    public void chooseByProductName(String name) {
        By productLocator = By.xpath(String.format(product, name));
        webDriver.findElement(productLocator).click();
    }
}
