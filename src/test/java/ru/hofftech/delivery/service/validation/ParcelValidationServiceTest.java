package ru.hofftech.delivery.service.validation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.exception.InvalidParcelException;
import ru.hofftech.delivery.exception.ParcelNullOrEmptyException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParcelValidationServiceTest {

    @Test
    public void validateParcelString_invalidParcelString_shouldNotThrowException() {
        var parcelValidationService = new ParcelValidationService();

        assertThatCode(() -> parcelValidationService.validateParcelString("1%n"))
                .doesNotThrowAnyException();
        assertThatCode(() -> parcelValidationService.validateParcelString("22%n"))
                .doesNotThrowAnyException();
        assertThatCode(() -> parcelValidationService.validateParcelString("333%n"))
                .doesNotThrowAnyException();
        assertThatCode(() -> parcelValidationService.validateParcelString("55555%n"))
                .doesNotThrowAnyException();
        assertThatCode(() -> parcelValidationService.validateParcelString("777%n7777%n"))
                .doesNotThrowAnyException();
        assertThatCode(() -> parcelValidationService.validateParcelString("666%n666%n"))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateParcelString_invalidParcelString_throwsException() {
        var parcelValidationService = new ParcelValidationService();

        assertThatThrownBy(() -> parcelValidationService.validateParcelString("11%n"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("222%n"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("3%n"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("55%n555%n"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("7%n77%n7777"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString(""))
                .isInstanceOf(ParcelNullOrEmptyException.class);
    }
}
