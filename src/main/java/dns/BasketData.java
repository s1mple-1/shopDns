package dns;

import dns.pages.BasePage;

import java.util.ArrayList;

public class BasketData extends BasePage {
    private static ArrayList<Product> productList;

    private BasketData() {
    }

    public static ArrayList<Product> getBasketData() {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
    }
}
