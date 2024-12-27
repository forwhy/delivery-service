package ru.hofftech.delivery.service.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidationServiceTest {

    @Test
    public void validateInputStringConvertibleToInteger_inputValidInteger_shouldNotThrowException() {
        var inputValidationService = new InputValidationService();
        assertThatCode(() -> inputValidationService.validateInputStringConvertibleToInteger("10"))
                .doesNotThrowAnyException();
        assertThatCode(() -> inputValidationService.validateInputStringConvertibleToInteger("5"))
                .doesNotThrowAnyException();
        assertThatCode(() -> inputValidationService.validateInputStringConvertibleToInteger("-5"))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateInputStringConvertibleToInteger_inputEmpty_shouldThrowException() {
        var inputValidationService = new InputValidationService();
        assertThatThrownBy(() -> inputValidationService.validateInputStringConvertibleToInteger(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input value cannot be empty");
    }

    @Test
    public void validateInputStringConvertibleToInteger_inputInvalidInteger_shouldThrowException() {
        var inputValidationService = new InputValidationService();
        assertThatThrownBy(() -> inputValidationService.validateInputStringConvertibleToInteger("test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Integer value expected, but got %s".formatted("test"));
    }
}