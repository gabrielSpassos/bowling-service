package com.gabrielspassos.poc.validator;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.stub.FrameDTOStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.validator.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FrameValidatorTest {

    @InjectMocks
    private FrameValidator frameValidator;

    @Test
    void shouldThrowErrorForInvalidFrameWithMultiplesPlayFromSamePlayer() {
        Map<Integer, List<FrameDTO>> frames1 = new HashMap<>() {{
            put(1, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("Jeff", 3, 7, null, null)
            ));
        }};

        Map<Integer, List<FrameDTO>> frames2 = new HashMap<>() {{
            put(2, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("Jose", 3, 7, null, null),
                    FrameDTOStub.create("Jeff", 7, null, null, null)
            ));
        }};

        ValidationException validationException1 = assertThrows(ValidationException.class,
                () -> frameValidator.validate(frames1));
        ValidationException validationException2 = assertThrows(ValidationException.class,
                () -> frameValidator.validate(frames2));
        assertEquals("Invalid frame, player with more than one play in this frame", validationException1.getMessage());
        assertEquals("Invalid frame, player with more than one play in this frame", validationException2.getMessage());
    }

    @Test
    void shouldThrowErrorForInvalidFrameSize() {
        Map<Integer, List<FrameDTO>> frames = new HashMap<>() {{
            put(1, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("John", 3, 7, null, null)
            ));
            put(2, List.of(
                    FrameDTOStub.create("Jeff", 7, 3, null, null),
                    FrameDTOStub.create("John", 6, 3, null, null)
            ));
            put(3, List.of(
                    FrameDTOStub.create("Jeff", 9, 0, null, null),
                    FrameDTOStub.create("John", 10, null, null, null)
            ));
            put(4, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("John", 8, 1, null, null)
            ));
            put(5, List.of(
                    FrameDTOStub.create("Jeff", 0, 8, null, null),
                    FrameDTOStub.create("John", 10, null, null, null)
            ));
            put(6, List.of(
                    FrameDTOStub.create("Jeff", 8, 2, null, null),
                    FrameDTOStub.create("John", 10, null, null, null)
            ));
            put(7, List.of(
                    FrameDTOStub.create("Jeff", 0, 6, null, null),
                    FrameDTOStub.create("John", 9, 0, null, null)
            ));
            put(8, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("John", 7, 3, null, null)
            ));
            put(9, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("John", 4, 4, null, null)
            ));
            put(10, List.of(
                    FrameDTOStub.create("Jeff", 10, 8, 1, null),
                    FrameDTOStub.create("John", 10, 9, 0, null)
            ));
            put(11, List.of(
                    FrameDTOStub.create("Jeff", 10, 8, 1, null),
                    FrameDTOStub.create("John", 10, 9, 0, null)
            ));
        }};

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> frameValidator.validate(frames));
        assertEquals("There is more then 10 frames", validationException.getMessage());
    }

    @Test
    void shouldNotThrowError() {
        Map<Integer, List<FrameDTO>> frames1 = new HashMap<>() {{
            put(1, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null),
                    FrameDTOStub.create("Jose", 3, 7, null, null)
            ));
        }};

        Map<Integer, List<FrameDTO>> frames2 = new HashMap<>() {{
            put(2, List.of(
                    FrameDTOStub.create("Jeff", 10, null, null, null)
            ));
        }};


        assertDoesNotThrow(() -> frameValidator.validate(frames1));
        assertDoesNotThrow(() -> frameValidator.validate(frames2));
    }

}