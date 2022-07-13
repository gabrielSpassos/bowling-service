package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.builder.FrameDTOBuilder;
import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.gabrielspassos.poc.config.BowlingConfig.LAST_ROUND;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
public class FrameService {

    public Map<Integer, List<FrameDTO>> mapPlaysToFrames(List<PlayOutput> playOutputs) {
        Map<Integer, List<FrameDTO>> frames = new HashMap<>();
        Integer frame = 1;
        List<FrameDTO> plays = new ArrayList<>();

        for (int i = 0; i < playOutputs.size(); i++) {
            PlayOutput currentPlay = playOutputs.get(i);
            if (isTrue(currentPlay.getIsProcessed())) {
                continue;
            }

            boolean isLastFrame = LAST_ROUND.equals(frame);
            if (isLastFrame) {
                PlayOutput secondPlay = playOutputs.get(i + 1);
                PlayOutput thirdPlay = playOutputs.get(i + 2);
                FrameDTO play = processPlay(currentPlay, secondPlay, thirdPlay);
                plays.add(play);
                enhanceFramesWithPlays(frames, frame, playOutputs, plays);
            }

            PlayOutput nextPlay = playOutputs.get(i + 1);
            if (isTrue(nextPlay.getIsProcessed())) {
                continue;
            }

            FrameDTO play = processPlay(currentPlay, nextPlay);
            plays.add(play);
            boolean isFrameComplete = enhanceFramesWithPlays(frames, frame, playOutputs, plays);
            if (isFrameComplete) {
                frame++;
                plays = new ArrayList<>();
            }
        }

        return frames;
    }

    private FrameDTO processPlay(PlayOutput playOutput) {
        FrameDTO play = FrameDTOBuilder.build(playOutput);
        updatePlay(playOutput);
        return play;
    }

    private FrameDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();

        if (firstPlayPlayer.equals(secondPlayPlayer)) {
            FrameDTO play = FrameDTOBuilder.build(firstPlay, secondPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            return play;
        } else {
            return processPlay(firstPlay);
        }
    }

    private FrameDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay, PlayOutput thirdPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();
        String thirdPlayPlayer = thirdPlay.getPlayerName();

        if (firstPlayPlayer.equals(secondPlayPlayer) && secondPlayPlayer.equals(thirdPlayPlayer)) {
            FrameDTO play = FrameDTOBuilder.build(firstPlay, secondPlay, thirdPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            updatePlay(thirdPlay);
            return play;
        } else {
            return processPlay(firstPlay, secondPlay);
        }
    }

    private Boolean enhanceFramesWithPlays(Map<Integer, List<FrameDTO>> frames,
                                           Integer frame,
                                           List<? extends PlayOutput> playOutputs,
                                           List<FrameDTO> plays) {
        Boolean isFrameComplete = isFrameComplete(playOutputs, plays);

        if (isFrameComplete) {
            frames.put(frame, plays);
        }

        return isFrameComplete;
    }

    private Boolean isFrameComplete(List<? extends PlayOutput> playOutputs, List<FrameDTO> plays) {
        List<String> playersNames = playOutputs.stream()
                .map(PlayOutput::getPlayerName)
                .distinct()
                .collect(Collectors.toList());

        List<String> names = plays.stream().map(FrameDTO::getPlayerName).distinct().collect(Collectors.toList());
        return new HashSet<>(names).containsAll(playersNames);
    }

    private void updatePlay(PlayOutput playOutput) {
        playOutput.setIsProcessed(Boolean.TRUE);
    }
}
