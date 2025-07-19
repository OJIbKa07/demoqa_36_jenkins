package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {
    private SelenideElement monthSelect = $(".react-datepicker__month-select");
    private SelenideElement yearSelect = $(".react-datepicker__year-select");
    private SelenideElement daySelect;

    public void setDate(String day, String month, String year) {
        daySelect = $(".react-datepicker__day--0" + day);

        monthSelect.selectOption(month);
        yearSelect.selectOption(year);
        daySelect.click();
    }
}
