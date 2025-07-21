package tests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Selenide.webdriver;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;

@Tag("practice-form")
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

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    void successfulPracticeFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
            takeScreenshot();
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
            takeScreenshot();
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
            takeScreenshot();
        });
    }

    @Test
    void successfulMinFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
            takeScreenshot();
        });

        step("Заполняем обязательные поля формы и отправляем", () -> {
        practiceFormPages.removeAds()
                .setFirstName(userName)
                .setLastName(lastName)
                .setGender(gender)
                .setNumber(phoneNumber)
                .submitForm();
            takeScreenshot();
        });

        step("Проверяем окно с результатами", () -> {
        practiceFormPages.verifyResultsModalAppears()
                .verifyResult("Student Name", userName + " " + lastName)
                .verifyResult("Gender", gender)
                .verifyResult("Mobile", phoneNumber);
            takeScreenshot();
        });
    }

    @Test
    void negativeMinFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем форму", () -> {
            practiceFormPages.openPage();
            takeScreenshot();
        });

        step("Не заполняем все обязательные поля формы и отправляем", () -> {
        practiceFormPages.openPage()
                .removeAds()
                .setFirstName(userName)
                .setLastName(lastName)
                .setGender(gender)
                .setNumber("")
                .submitForm();
            takeScreenshot();
        });

        step("Проверяем окно с результатами", () -> {
        practiceFormPages.verifyResultsModalAppearsNeg();
            takeScreenshot();
        });
    }
}
