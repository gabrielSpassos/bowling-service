package com.gabrielspassos.poc.builder;

import com.gabrielspassos.poc.dto.PlayDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;

public class PlayDTOBuilder {

    public static PlayDTO build(PlayOutput currentPlay) {
        Integer firstScore = currentPlay.getPlayerScore();
        return PlayDTO.builder()
                .playerName(currentPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(null)
                .thirdScore(null)
                .finalScore(null)
                .build();
    }

    public static PlayDTO build(PlayOutput currentPlay, PlayOutput nextPlay) {
        Integer firstScore = currentPlay.getPlayerScore();
        Integer secondScore = nextPlay.getPlayerScore();
        return PlayDTO.builder()
                .playerName(currentPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(secondScore)
                .thirdScore(null)
                .finalScore(null)
                .build();
    }

    public static PlayDTO build(PlayOutput firstPlay, PlayOutput secondPlay, PlayOutput thirdPlay) {
        Integer firstScore = firstPlay.getPlayerScore();
        Integer secondScore = secondPlay.getPlayerScore();
        Integer thirdScore = thirdPlay.getPlayerScore();

        return PlayDTO.builder()
                .playerName(firstPlay.getPlayerName())
                .firstScore(firstScore)
                .secondScore(secondScore)
                .thirdScore(thirdScore)
                .finalScore(null)
                .build();
    }
}
