package com.gabrielspassos.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gabrielspassos.poc.config.BowlingConfig.MAX_SCORE_VALUE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrameDTO {

    private String playerName;
    private Integer firstScore;
    private Integer secondScore;
    private Integer thirdScore;
    private Integer finalScore;

    public List<Integer> getScores() {
        return Stream.of(this.getFirstScore(), this.getSecondScore(), this.getThirdScore())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Integer sumScores(Integer... scores) {
        return Stream.concat(Arrays.stream(scores), getScores().stream())
                .reduce(0, Integer::sum);
    }

    public boolean isStrikeFrame() {
        return isStrikeScore(this.getFirstScore())
                || isStrikeScore(this.getSecondScore())
                || isStrikeScore(this.getThirdScore());
    }

    public boolean isSpareFrame() {
        Integer sumOfScores = this.sumScores();
        return MAX_SCORE_VALUE.equals(sumOfScores) && !isStrikeFrame();
    }

    public boolean isStrikeScore(Integer score) {
        return MAX_SCORE_VALUE.equals(score);
    }

    public boolean isSecondScoreSpare() {
        Integer scoresSum = addScores(this.getFirstScore(), this.getSecondScore());
        return isSpareFrame() && MAX_SCORE_VALUE.equals(scoresSum);
    }

    public boolean isThirdScoreSpare() {
        Integer scoresSum = addScores(this.getFirstScore(), this.getSecondScore(), this.getThirdScore());
        return isSpareFrame() && MAX_SCORE_VALUE.equals(scoresSum);
    }

    private Integer addScores(Integer... scores) {
        return Arrays.stream(scores)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }
}
