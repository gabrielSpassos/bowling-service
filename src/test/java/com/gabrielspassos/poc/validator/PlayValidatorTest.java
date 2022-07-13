package com.gabrielspassos.poc.validator;

import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.stub.PlayOutputStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.validator.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayValidatorTest {

    @InjectMocks
    private PlayValidator playValidator;

    @Test
    void shouldValidateSuccessfully() {
        PlayOutput playOutput = PlayOutputStub.create("Jeff", 10, null);

        assertDoesNotThrow(() -> playValidator.validate(playOutput));
    }

    @Test
    void shouldThrowErrorForInvalidPlayerName() {
        PlayOutput playOutput = PlayOutputStub.create("", 10, null);

        ValidationException validationException = assertThrows(ValidationException.class, () -> playValidator.validate(playOutput));

        assertEquals("Player name must be informed", validationException.getMessage());
    }

    @Test
    void shouldThrowErrorForInvalidPlayerScore() {
        PlayOutput playOutput = PlayOutputStub.create("Jeff", null, null);

        ValidationException validationException = assertThrows(ValidationException.class, () -> playValidator.validate(playOutput));

        assertEquals("Player score must be informed", validationException.getMessage());
    }

    @Test
    void shouldThrowErrorForInvalidMinPlayerScore() {
        PlayOutput playOutput = PlayOutputStub.create("Jeff", -1, null);

        ValidationException validationException = assertThrows(ValidationException.class, () -> playValidator.validate(playOutput));

        assertEquals("The minimal score is 0", validationException.getMessage());
    }

    @Test
    void shouldThrowErrorForInvalidMaxPlayerScore() {
        PlayOutput playOutput = PlayOutputStub.create("Jeff", 11, null);

        ValidationException validationException = assertThrows(ValidationException.class, () -> playValidator.validate(playOutput));

        assertEquals("The maximum score is 10", validationException.getMessage());
    }
}