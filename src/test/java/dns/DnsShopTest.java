package dns;

import dns.pages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class DnsShopTest extends BaseDnsTest {
    private static List<String[]> streamProduct() {
        return List.of(
                new String[]{"playstation", "PlayStation 4 Slim Black", "Detroit"},
                new String[]{"xbox", "Microsoft Xbox ONE X Black", "FIFA 17 (Xbox ONE)"});
    }


    @ParameterizedTest
    @MethodSource("streamProduct")
    void dnsShopTest(String nameShort, String nameFull, String game) {
        System.out.println(nameFull + nameShort + game);
        //2. в поиске найти playstation
        SearchBar searchBar = new SearchBar();
        searchBar.search(nameShort);

        //3. кликнуть по playstation 4 slim black
        ResultsPage resultsPage = new ResultsPage();
        resultsPage.chooseByProductName(nameFull);
        ProductPage productPage = new ProductPage();

        /*4. запомнить цену
          5. Нажать Купить*/
        productPage.addProductToBasket();

        /*6. выполнить поиск Detroit
          7. запомнить цену
          8. нажать купить*/
        searchBar.search(game);
        productPage.addProductToBasket();


        //9. перейти в корзину
        BasePage basePage = new BasePage();
        basePage.goToBasket();
        BasketPage basketPage = new BasketPage();

        //10. проверить цену каждого из товаров и сумму
        Assertions.assertTrue(basketPage.checkPrice(), "Цена добавленного товара не соотвествует его цене в корзине");
        long totalBasketPrice = basePage.getTotalBasketPrice();
        Assertions.assertEquals(basketPage.getVirtualBasketPrice(), totalBasketPrice,
                "Общая стоимость корзины не соотвествует стоимости добавленных товаров");

        //11. В корзине для playstation Доп.гарантия - выбрать 24 месяца
        basketPage.chooseTwoYearWarranty();

        //12. дождаться изменения цены и запомнить цену с гарантией
        basePage.waitElementRefreshing(totalBasketPrice);
        long totalPriceWithWarranty = basePage.getTotalBasketPrice();

        //13. удалить из корзины Detroit
        Product deleteProduct = basketPage.deleteFromBasket(game);
        basePage.waitElementRefreshing(totalPriceWithWarranty);

        //14. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
        Assertions.assertTrue(basketPage.checkProductDelete(game), "Продукт не удален");
        long actualPrice = basketPage.getTotalBasketPrice();
        long expectedPrice = totalPriceWithWarranty - deleteProduct.getPrice();
        Assertions.assertEquals(expectedPrice, actualPrice, String.format("Ожидаемая стоимость корзины: %d. Фактическая: %d", expectedPrice, actualPrice));

        //15. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна 3*(цена playstation+гарантия))
        basketPage.addMoreProduct(nameFull, 2, actualPrice);
        basePage.waitElementRefreshing(actualPrice);
        expectedPrice = actualPrice * 3L;
        actualPrice = basePage.getTotalBasketPrice();
        Assertions.assertEquals(expectedPrice, actualPrice,
                String.format("Ожидаемая стоимость корзины: %d. Фактическая: %d", expectedPrice, actualPrice));

        //16. удалить (кнопка "удалить") Playstation из корзины
        Product rollbackProduct = basketPage.deleteFromBasket(nameFull);
        //17. нажать вернуть удаленный товаров
        basketPage.rollbackDelete(rollbackProduct);
        //18. проверить что 3 playstation снова в корзине и выбрана гарантия 24 месяца
        Assertions.assertTrue(BasketPage.checkProductAfterRollback.isDisplayed(), "Товар не возвращен после удаления");
        Assertions.assertEquals(rollbackProduct.getCount(), basketPage.checkCountProductAfterRollBack(rollbackProduct), "Товар возвращен не в полном количестве");
        Assertions.assertTrue(BasketPage.checkWarrantyAfterRollback.isDisplayed(), "Дополнительная гарантия на 24 месяца не выбрана");
    }

}
