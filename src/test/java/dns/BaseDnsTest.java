package dns;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class BaseDnsTest {

    @BeforeEach
    void startTest() {
        InitWebDriver.initWebDriver();
    }

    @AfterEach
    void quitTest() {
        InitWebDriver.quitWebDriver();
        BasketData.getBasketData().clear();
    }

}
