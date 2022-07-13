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
}
