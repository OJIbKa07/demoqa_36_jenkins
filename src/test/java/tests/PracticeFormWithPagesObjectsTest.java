package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import helpers.Attach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static io.qameta.allure.Allure.step;

@Tag("demoqa")
public class PracticeFormWithPagesObjectsTest extends TestBase {
    PracticeFormPages practiceFormPages = new PracticeFormPages();

    String userName = "Olga";
    String lastName = "Palushina";
    String email = "lqokag@gmail.com";
    String gender = "Female";
    String phoneNumber = "8965412365";
    String[] birthday  = new String[] {"31", "July", "2003"};
    String subjects = "Maths";
    String hobbies = "Reading";
    String uploadFile = "MyPhoto.jpg";
    String address= "123 Main St.";
    String[] stateAndCity  = new String[] {"NCR", "Delhi"};

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @Test
    void successfulPracticeFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
        });
        step("Заполняем форму полностью и отправляем", () -> {
            practiceFormPages.removeAds()
                    .setFirstName(userName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setGender(gender)
                    .setNumber(phoneNumber)
                    .setBirthDate(birthday[0], birthday[1], birthday[2])
                    .setSubjects(subjects)
                    .setHobbies(hobbies)
                    .uploadPicture(uploadFile)
                    .setAddress(address)
                    .selectStateAndCity(stateAndCity[0], stateAndCity[1])
                    .submitForm();
        });

        step("Проверяем окно с результатами", () -> {
            practiceFormPages.verifyResultsModalAppears()
                    .verifyResult("Student Name", userName + " " + lastName)
                    .verifyResult("Student Email", email)
                    .verifyResult("Gender", gender)
                    .verifyResult("Mobile", phoneNumber)
                    .verifyResult("Date of Birth", birthday[0] + " " + birthday[1] + "," + birthday[2])
                    .verifyResult("Subjects", subjects)
                    .verifyResult("Hobbies", hobbies)
                    .verifyResult("Picture", uploadFile)
                    .verifyResult("Address", address)
                    .verifyResult("State and City", stateAndCity[0] + " " + stateAndCity[1]);
        });
    }

    @Test
    void successfulMinFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
        });

        step("Заполняем обязательные поля формы и отправляем", () -> {
        practiceFormPages.removeAds()
                .setFirstName(userName)
                .setLastName(lastName)
                .setGender(gender)
                .setNumber(phoneNumber)
                .submitForm();
        });

        step("Проверяем окно с результатами", () -> {
        practiceFormPages.verifyResultsModalAppears()
                .verifyResult("Student Name", userName + " " + lastName)
                .verifyResult("Gender", gender)
                .verifyResult("Mobile", phoneNumber);
        });
    }

    @Test
    void negativeMinFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
        });

        step("Не заполняем все обязательные поля формы и отправляем", () -> {
        practiceFormPages.openPage()
                .removeAds()
                .setFirstName(userName)
                .setLastName(lastName)
                .setGender(gender)
                .setNumber("")
                .submitForm();
        });

        step("Проверяем окно с результатами", () -> {
        practiceFormPages.verifyResultsModalAppearsNeg();
        });
    }
}
