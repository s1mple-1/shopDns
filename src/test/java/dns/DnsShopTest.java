package dns;

import dns.pages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DnsShopTest extends BaseDnsTest {

    @Test
    void dnsShopTest() throws InterruptedException {
        //2. в поиске найти playstation
        SearchBar searchBar = new SearchBar();
        searchBar.search("playstation");

        //3. кликнуть по playstation 4 slim black
        ResultsPage resultsPage = new ResultsPage();
        resultsPage.chooseByProductName("PlayStation 4 Slim Black");

        /*4. запомнить цену
          5. Нажать Купить*/
        ProductPage productPage = new ProductPage();
        productPage.addProductToBasket();

        /*6. выполнить поиск Detroit
          7. запомнить цену
          8. нажать купить*/
        searchBar.search("Detroit");
        productPage.addProductToBasket();

        //9. перейри в корзину
        BasePage basePage = new BasePage();
        basePage.goToBasket();
        BasketPage basketPage = new BasketPage();

        //10. проверить цену каждого из товаров и сумму
        //
        //
        //
        basketPage.checkPrice();
        long totalBasketPrice = basePage.getTotalBasketPrice();

        //11. В корзине для playstation Доп.гарантия - выбрать 24 месяца
        basketPage.chooseWarranty();

        //12. дождаться изменения цены и запомнить цену с гарантией
        basePage.waitElementRefreshing(totalBasketPrice);
        long totalPriceWithWarranty = basePage.getTotalBasketPrice();
        long warrantyPrice = totalPriceWithWarranty - totalBasketPrice;

        //13. удалить из корзины Detroit
        Product deleteProduct = basketPage.deleteFromVirtualBasket("Detroit");
        basePage.waitElementRefreshing(totalPriceWithWarranty);

        //14. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
        long actual = basketPage.getTotalBasketPrice();
        long expected = totalPriceWithWarranty - deleteProduct.getPrice();
        Assertions.assertEquals(expected, actual, String.format("Ожидаемая стоимость корзины: %d. Фактическая: %d", expected, actual));

        //15. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна 3*(цена playstation+гарантия))

        //16. удалить (кнопка "удалить") Playstation из корзины

        //17. нажать вернуть удаленный товаров

        //18. проверить что 3 playstation снова в корзине и выбрана гарантия 24 месяца

    }

}
