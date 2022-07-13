package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.stub.PlayOutputStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FrameServiceTest {

    @InjectMocks
    private FrameService frameService;

    @Test
    void shouldMapPlaysToFrame() {
        List<PlayOutput> plays = List.of(
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("John", 3, null),
                PlayOutputStub.create("John", 7, null),
                PlayOutputStub.create("Jeff", 7, null),
                PlayOutputStub.create("Jeff", 3, null),
                PlayOutputStub.create("John", 6, null),
                PlayOutputStub.create("John", 3, null),
                PlayOutputStub.create("Jeff", 9, null),
                PlayOutputStub.create("Jeff", 0, null),
                PlayOutputStub.create("John", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("John", 8, null),
                PlayOutputStub.create("John", 1, null),
                PlayOutputStub.create("Jeff", 0, null),
                PlayOutputStub.create("Jeff", 8, null),
                PlayOutputStub.create("John", 10, null),
                PlayOutputStub.create("Jeff", 8, null),
                PlayOutputStub.create("Jeff", 2, null),
                PlayOutputStub.create("John", 10, null),
                PlayOutputStub.create("Jeff", 0, null),
                PlayOutputStub.create("Jeff", 6, null),
                PlayOutputStub.create("John", 9, null),
                PlayOutputStub.create("John", 0, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("John", 7, null),
                PlayOutputStub.create("John", 3, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("John", 4, null),
                PlayOutputStub.create("John", 4, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 8, null),
                PlayOutputStub.create("Jeff", 1, null),
                PlayOutputStub.create("John", 10, null),
                PlayOutputStub.create("John", 9, null),
                PlayOutputStub.create("John", 0, null)
        );

        Map<Integer, List<FrameDTO>> frames = frameService.mapPlaysToFrames(plays);

        assertNotNull(frames);
        assertEquals(10, frames.size());
        boolean isAllFramesWithOnlyTwoFrames = frames.values()
                .stream()
                .allMatch(frameDTOS -> frameDTOS.size() == 2);
        assertTrue(isAllFramesWithOnlyTwoFrames);
    }

    @Test
    void shouldMapPerfectPlaysToFrame() {
        List<PlayOutput> plays = List.of(
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null),
                PlayOutputStub.create("Jeff", 10, null)
        );

        Map<Integer, List<FrameDTO>> frames = frameService.mapPlaysToFrames(plays);

        assertNotNull(frames);
        assertEquals(10, frames.size());
        boolean isAllFramesWithOnlyOneFrame = frames.values()
                .stream()
                .allMatch(frameDTOS -> frameDTOS.size() == 1);
        assertTrue(isAllFramesWithOnlyOneFrame);
    }

}