package dns;

import dns.pages.BasePage;
import dns.pages.ProductPage;
import dns.pages.ResultsPage;
import dns.pages.SearchBar;
import org.junit.jupiter.api.Test;

public class DnsShopTest extends BaseDnsTest{

    @Test
    void dnsShopTest() throws InterruptedException {
        //1-2
        SearchBar searchBar = new SearchBar();
        searchBar.search("playstation");
        //3
        ResultsPage resultsPage = new ResultsPage();
        resultsPage.chooseByProductName("PlayStation 4 Slim Black");
        //4-7
        ProductPage productPage = new ProductPage();
        productPage.chooseWarranty(2);
        productPage.addProductToBasket();
        //8-10
        searchBar.search("Detroit");
        productPage.addProductToBasket();
        //11
        System.out.println(productPage.getTotalPrice());
        //12
        new BasePage().goToBasket();
        //13


    }

}
