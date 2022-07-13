package com.gabrielspassos.poc.stub;

import com.gabrielspassos.poc.dto.FrameDTO;

public class FrameDTOStub {

    public static FrameDTO create(String name, Integer firstScore, Integer secondScore, Integer thirdScore, Integer finalScore) {
        FrameDTO frameDTO = new FrameDTO();
        frameDTO.setPlayerName(name);
        frameDTO.setFirstScore(firstScore);
        frameDTO.setSecondScore(secondScore);
        frameDTO.setThirdScore(thirdScore);
        frameDTO.setFinalScore(finalScore);
        return frameDTO;
    }
}
