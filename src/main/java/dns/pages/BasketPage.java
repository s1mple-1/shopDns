package dns.pages;

import dns.BasketData;
import dns.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Iterator;
import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(), '24  мес')]")
    private WebElement warranty;


    public boolean checkPrice() {
        String price = "//div[@data-cart-product-id][%d]//span[@class='price__current']";
        int count = 1;
        for (Product product : BasketData.getBasketData()) {
            String s = webDriver.findElement(By.xpath(String.format(price, count))).getText().replaceAll(" ", "");
            long expected = product.getPrice();
            long actual = Long.parseLong(s);
            count++;
        }
        return true;
    }


    public void chooseWarranty() {
        waitElementToClick(warranty).click();
    }


    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div[2]/div[1]/div[4]/div[2]/button[2]")
    private WebElement del;

    public Product deleteFromVirtualBasket(String name) {
        del.click();
        Iterator<Product> iterator = BasketData.getBasketData().iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().contains(name)) {
                BasketData.getBasketData().remove(product);
                return product;
            }
        }
        return null;
    }
}


//        long totalPrice = Long.parseLong(productPrice.getText().replaceAll(" ", ""));
