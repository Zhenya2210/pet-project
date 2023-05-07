package ui;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class YandexBasePage {

    @Test
    public void openMainPage() {
        open("https://dzen.ru/");
        $x("//button[@class='dzen-search-arrow-common__button']").shouldBe(Condition.visible);
    }
}
