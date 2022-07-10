package com.gabrielspassos.poc.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayInput {

    private String playerName;
    private String playerScore;
}
