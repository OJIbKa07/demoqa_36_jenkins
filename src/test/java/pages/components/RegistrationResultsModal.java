package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationResultsModal {
    SelenideElement modalContent = $(".modal-content");
    public void verifyModalAppears() {
        modalContent.shouldHave(text("Thanks for submitting the form"));
    }

    public void verifyResult(String key, String value) {
        SelenideElement tableResponsive = $(".table-responsive");
        tableResponsive.$(byText(key))
                .parent().shouldHave(text(value));
    }

    public void verifyModalAppearsNegativ() {
        modalContent.shouldNot();
    }
}
