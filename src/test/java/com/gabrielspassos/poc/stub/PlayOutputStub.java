package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.dto.output.PlayOutput;

public class PlayOutputStub {

    public static PlayOutput create(String name, Integer score, Boolean isProcessed) {
        PlayOutput playOutput = new PlayOutput();
        playOutput.setPlayerName(name);
        playOutput.setPlayerScore(score);
        playOutput.setIsProcessed(isProcessed);
        return playOutput;
    }
}
