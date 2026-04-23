import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class cardDeliveryTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999/");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now()
                .plusDays(addDays)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeSuccess() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мандаринов Александр");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));

    }
}
