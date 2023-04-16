package ui;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class YandexBasePage {

    @Test
    public void openMainPage() {
        open("https://dzen.ru/");
        $x("//div[text()='Видео']").shouldBe(Condition.visible);
    }
}
