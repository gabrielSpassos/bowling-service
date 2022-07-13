package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.dto.input.PlayInput;

public class PlayInputStub {

    public static PlayInput create(String name, String score) {
        PlayInput playInput = new PlayInput();
        playInput.setPlayerName(name);
        playInput.setPlayerScore(score);
        return playInput;
    }
}
