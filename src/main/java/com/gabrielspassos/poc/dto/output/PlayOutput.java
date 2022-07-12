package com.gabrielspassos.poc.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayOutput {

    @NotEmpty(message = "Player name must be informed")
    private String playerName;

    @NotEmpty(message = "Player score must be informed")
    @Min(value = 0, message = "The minimal score is 0")
    @Max(value = 10, message = "The maximum score is 10")
    private Integer playerScore;

    private Boolean isProcessed;

}
