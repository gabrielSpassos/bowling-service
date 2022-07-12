package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.builder.PlayDTOBuilder;
import com.gabrielspassos.poc.dto.PlayDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.gabrielspassos.poc.config.BowlingConfig.LAST_ROUND;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
public class FrameService {

    public Map<Integer, List<PlayDTO>> mapPlaysToFrames(List<PlayOutput> playOutputs) {
        Map<Integer, List<PlayDTO>> frames = new HashMap<>();
        Integer frame = 1;
        List<PlayDTO> plays = new ArrayList<>();

        for (int i = 0; i < playOutputs.size(); i++) {
            PlayOutput currentPlay = playOutputs.get(i);
            if (isTrue(currentPlay.getIsProcessed())) {
                continue;
            }

            boolean isLastFrame = LAST_ROUND.equals(frame);
            if (isLastFrame) {
                PlayOutput secondPlay = playOutputs.get(i + 1);
                PlayOutput thirdPlay = playOutputs.get(i + 2);
                PlayDTO play = processPlay(currentPlay, secondPlay, thirdPlay);
                plays.add(play);
                enhanceFramesWithPlays(frames, frame, playOutputs, plays);
            }

            PlayOutput nextPlay = playOutputs.get(i + 1);
            if (isTrue(nextPlay.getIsProcessed())) {
                continue;
            }

            PlayDTO play = processPlay(currentPlay, nextPlay);
            plays.add(play);
            boolean isFrameComplete = enhanceFramesWithPlays(frames, frame, playOutputs, plays);
            if (isFrameComplete) {
                frame++;
                plays = new ArrayList<>();
            }
        }

        return frames;
    }

    private PlayDTO processPlay(PlayOutput playOutput) {
        PlayDTO play = PlayDTOBuilder.build(playOutput);
        updatePlay(playOutput);
        return play;
    }

    private PlayDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();

        if (firstPlayPlayer.equals(secondPlayPlayer)) {
            PlayDTO play = PlayDTOBuilder.build(firstPlay, secondPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            return play;
        } else {
            return processPlay(firstPlay);
        }
    }

    private PlayDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay, PlayOutput thirdPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();
        String thirdPlayPlayer = thirdPlay.getPlayerName();

        if (firstPlayPlayer.equals(secondPlayPlayer) && secondPlayPlayer.equals(thirdPlayPlayer)) {
            PlayDTO play = PlayDTOBuilder.build(firstPlay, secondPlay, thirdPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            updatePlay(thirdPlay);
            return play;
        } else {
            return processPlay(firstPlay, secondPlay);
        }
    }

    private Boolean enhanceFramesWithPlays(Map<Integer, List<PlayDTO>> frames,
                                           Integer frame,
                                           List<? extends PlayOutput> playOutputs,
                                           List<PlayDTO> plays) {
        Boolean isFrameComplete = isFrameComplete(playOutputs, plays);

        if (isFrameComplete) {
            frames.put(frame, plays);
        }

        return isFrameComplete;
    }

    private Boolean isFrameComplete(List<? extends PlayOutput> playOutputs, List<PlayDTO> plays) {
        List<String> playersNames = playOutputs.stream()
                .map(PlayOutput::getPlayerName)
                .distinct()
                .collect(Collectors.toList());

        List<String> names = plays.stream().map(PlayDTO::getPlayerName).distinct().collect(Collectors.toList());
        return new HashSet<>(names).containsAll(playersNames);
    }

    private void updatePlay(PlayOutput playOutput) {
        playOutput.setIsProcessed(Boolean.TRUE);
    }
}
