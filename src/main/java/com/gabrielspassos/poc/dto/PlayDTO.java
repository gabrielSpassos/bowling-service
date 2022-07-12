package com.gabrielspassos.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayDTO {

    private String playerName;
    private Integer firstScore;
    private Integer secondScore;
    private Integer thirdScore;
    private Integer finalScore;

}
