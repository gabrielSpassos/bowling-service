package com.gabrielspassos.poc.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FrameDTOTest {

    @Test
    void shouldReturnScores() {
        FrameDTO frameDTO1 = new FrameDTO("john", 1, null, null, null);
        FrameDTO frameDTO2 = new FrameDTO("john", 1, 2, null, null);
        FrameDTO frameDTO3 = new FrameDTO("john", 1, 2, 3, null);

        List<Integer> scores1 = frameDTO1.getScores();
        List<Integer> scores2 = frameDTO2.getScores();
        List<Integer> scores3 = frameDTO3.getScores();

        List<Integer> expected1 = List.of(1);
        List<Integer> expected2 = List.of(1, 2);
        List<Integer> expected3 = List.of(1, 2, 3);
        assertEquals(expected1, scores1);
        assertEquals(expected2, scores2);
        assertEquals(expected3, scores3);
    }

    @Test
    void shouldSumScores() {
        FrameDTO frameDTO1 = new FrameDTO("john", 1, null, null, null);
        FrameDTO frameDTO2 = new FrameDTO("john", 1, 2, null, null);
        FrameDTO frameDTO3 = new FrameDTO("john", 1, 2, 3, null);

        Integer score1 = frameDTO1.sumScores();
        Integer score2 = frameDTO2.sumScores();
        Integer score3 = frameDTO3.sumScores();

        assertEquals(1, score1);
        assertEquals(3, score2);
        assertEquals(6, score3);
    }

    @Test
    void shouldSumScoresWithNewScores() {
        FrameDTO frameDTO1 = new FrameDTO("john", 1, null, null, null);
        FrameDTO frameDTO2 = new FrameDTO("john", 1, 2, null, null);
        FrameDTO frameDTO3 = new FrameDTO("john", 1, 2, 3, null);
        FrameDTO frameDTO4 = new FrameDTO();

        Integer score1 = frameDTO1.sumScores(4);
        Integer score2 = frameDTO2.sumScores(5, 6);
        Integer score3 = frameDTO3.sumScores(7,8,9);
        Integer score4 = frameDTO4.sumScores();

        assertEquals(5, score1);
        assertEquals(14, score2);
        assertEquals(30, score3);
        assertEquals(0, score4);
    }
}