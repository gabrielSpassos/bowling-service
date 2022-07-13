package com.gabrielspassos.poc.builder;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;

public class FrameDTOBuilder {

    public static FrameDTO build(PlayOutput currentPlay) {
        Integer firstScore = currentPlay.getPlayerScore();
        return FrameDTO.builder()
                .playerName(currentPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(null)
                .thirdScore(null)
                .finalScore(null)
                .build();
    }

    public static FrameDTO build(PlayOutput currentPlay, PlayOutput nextPlay) {
        Integer firstScore = currentPlay.getPlayerScore();
        Integer secondScore = nextPlay.getPlayerScore();
        return FrameDTO.builder()
                .playerName(currentPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(secondScore)
                .thirdScore(null)
                .finalScore(null)
                .build();
    }

    public static FrameDTO build(PlayOutput firstPlay, PlayOutput secondPlay, PlayOutput thirdPlay) {
        Integer firstScore = firstPlay.getPlayerScore();
        Integer secondScore = secondPlay.getPlayerScore();
        Integer thirdScore = thirdPlay.getPlayerScore();

        return FrameDTO.builder()
                .playerName(firstPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(secondScore)
                .thirdScore(thirdScore)
                .finalScore(null)
                .build();
    }
}
