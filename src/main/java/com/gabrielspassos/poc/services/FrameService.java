package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.builder.FrameDTOBuilder;
import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.validator.FrameValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.gabrielspassos.poc.config.BowlingConfig.LAST_FRAME;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@AllArgsConstructor
public class FrameService {

    private FrameValidator frameValidator;

    public Map<Integer, List<FrameDTO>> mapPlaysToFrames(List<PlayOutput> playOutputs) {
        Map<Integer, List<FrameDTO>> frames = new HashMap<>();
        Integer frame = 1;
        List<FrameDTO> plays = new ArrayList<>();

        for (int i = 0; i < playOutputs.size(); i++) {
            PlayOutput currentPlay = getPlayByIndexIfExits(playOutputs, i);
            if (isTrue(currentPlay.getIsProcessed())) {
                continue;
            }

            boolean isLastFrame = LAST_FRAME.equals(frame);
            if (isLastFrame) {
                PlayOutput secondPlay = getPlayByIndexIfExits(playOutputs, i + 1);
                PlayOutput thirdPlay = getPlayByIndexIfExits(playOutputs, i + 2);
                FrameDTO play = processPlay(currentPlay, secondPlay, thirdPlay);
                plays.add(play);
                enhanceFramesWithPlays(frames, frame, playOutputs, plays);
                continue;
            }

            PlayOutput nextPlay = getPlayByIndexIfExits(playOutputs, i + 1);
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

        frameValidator.validate(frames);

        return frames;
    }

    private FrameDTO processPlay(PlayOutput playOutput) {
        FrameDTO frame = FrameDTOBuilder.build(playOutput);
        updatePlay(playOutput);
        return frame;
    }

    private FrameDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();
        FrameDTO firstFrame = FrameDTOBuilder.build(firstPlay);

        if (firstPlayPlayer.equals(secondPlayPlayer) && !firstFrame.isStrikeFrame()) {
            FrameDTO frame = FrameDTOBuilder.build(firstPlay, secondPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            return frame;
        } else {
            return processPlay(firstPlay);
        }
    }

    private FrameDTO processPlay(PlayOutput firstPlay, PlayOutput secondPlay, PlayOutput thirdPlay) {
        String firstPlayPlayer = firstPlay.getPlayerName();
        String secondPlayPlayer = secondPlay.getPlayerName();
        String thirdPlayPlayer = thirdPlay.getPlayerName();

        if (firstPlayPlayer.equals(secondPlayPlayer) && secondPlayPlayer.equals(thirdPlayPlayer)) {
            FrameDTO frame = FrameDTOBuilder.build(firstPlay, secondPlay, thirdPlay);
            updatePlay(firstPlay);
            updatePlay(secondPlay);
            updatePlay(thirdPlay);
            return frame;
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

    private PlayOutput getPlayByIndexIfExits(List<PlayOutput> plays, Integer index) {
        int lastIndex = plays.size();

        if (index >= lastIndex) {
            return new PlayOutput();
        }

        return plays.get(index);
    }
}
