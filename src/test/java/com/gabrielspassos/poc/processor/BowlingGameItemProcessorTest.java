package com.gabrielspassos.poc.processor;

import com.gabrielspassos.poc.dto.input.PlayInput;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.stub.PlayInputStub;
import com.gabrielspassos.poc.validator.PlayValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BowlingGameItemProcessorTest {

    @InjectMocks
    private BowlingGameItemProcessor bowlingGameItemProcessor;

    @Mock
    private PlayValidator playValidator;

    @Test
    void shouldProcessPlayInput() {
        PlayInput playInput = PlayInputStub.create("Jeff", "10");

        PlayOutput playOutput = bowlingGameItemProcessor.process(playInput);

        assertNotNull(playOutput);
        assertEquals("Jeff", playOutput.getPlayerName());
        assertEquals(10, playOutput.getPlayerScore());
        assertNull(playOutput.getIsProcessed());

        verify(playValidator).validate(any());
    }

    @Test
    void shouldProcessPlayInputWithFault() {
        PlayInput playInput = PlayInputStub.create("Jeff", "F");

        PlayOutput playOutput = bowlingGameItemProcessor.process(playInput);

        assertNotNull(playOutput);
        assertEquals("Jeff", playOutput.getPlayerName());
        assertEquals(0, playOutput.getPlayerScore());
        assertNull(playOutput.getIsProcessed());

        verify(playValidator).validate(any());
    }

    @Test
    void shouldThrowErrorForInvalidFaultScore() {
        PlayInput playInput = PlayInputStub.create("Jeff", "fault");

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bowlingGameItemProcessor.process(playInput));

        assertEquals("Invalid fault score", illegalArgumentException.getMessage());
        verify(playValidator, never()).validate(any());
    }
}