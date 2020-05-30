package dns;

import dns.pages.BasePage;

import java.util.ArrayList;

public class BasketData extends BasePage {
    private static ArrayList<Product> productList;
    private long totalPrice;


    private BasketData() {
    }

    public static ArrayList<Product> getBasketData() {
        if (productList == null) {
            productList = new ArrayList<>();
            return productList;
        } else return productList;
    }
}
