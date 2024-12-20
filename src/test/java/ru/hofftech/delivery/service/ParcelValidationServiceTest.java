package ru.hofftech.delivery.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.exception.InvalidParcelException;
import ru.hofftech.delivery.exception.ParcelNullOrEmptyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelValidationServiceTest {

    @Disabled
    @Test
    public void validateParcelString_validParcelString_shouldNotThrowException() {

        var parcelValidationService = new ParcelValidationService();

        for (var parcelString : ParcelValidationService.VALID_PARCEL_VARIANTS) {

            assertThatThrownBy(() -> parcelValidationService.validateParcelString(parcelString))
                    .doesNotThrowAnyException();
        }
    }

    @Test
    public void validateParcelString_invalidParcelString_returnsFalse() {

        var parcelValidationService = new ParcelValidationService();

        assertThatThrownBy(() -> parcelValidationService.validateParcelString("11"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("222"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("3"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("55\n555"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString("7\n77\n7777"))
                .isInstanceOf(InvalidParcelException.class);
        assertThatThrownBy(() -> parcelValidationService.validateParcelString(""))
                .isInstanceOf(ParcelNullOrEmptyException.class);
    }
}
