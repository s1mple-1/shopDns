package dns.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchBar extends BasePage{
    @FindBy(xpath = "//input[@placeholder='Поиск по сайту']")
    WebElement searchBar;

    public void search(String text) {
        waitElementToClick(searchBar).sendKeys(text);
        searchBar.sendKeys(Keys.ENTER);
    }
}
