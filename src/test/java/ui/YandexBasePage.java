package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class YandexBasePage {

    @BeforeAll
    public static void setUpTimeOut() {
        Configuration.timeout = 10_000;
    }

    @Test
    public void openMainPage() {
        open("https://dzen.ru/");
        $x("//button[@class='dzen-search-arrow-common__button']").shouldBe(Condition.visible);
    }
}
