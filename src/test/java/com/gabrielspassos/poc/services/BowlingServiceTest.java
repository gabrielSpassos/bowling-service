package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.stub.FrameDTOStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BowlingServiceTest {

    @InjectMocks
    private BowlingService bowlingService;

    @Test
    void shouldCalculateFrameScores() {
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
        }};

        bowlingService.calculateFramesScore(frames);

        assertNotNull(frames);
        boolean isAllFramesWithFinalScore = frames.values()
                .stream()
                .flatMap(Collection::stream)
                .allMatch(frameDTO -> Objects.nonNull(frameDTO.getFinalScore()));
        assertTrue(isAllFramesWithFinalScore);
    }
}