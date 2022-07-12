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
public class PlaysService {

    public Map<Integer, List<PlayDTO>> mapPlaysToRounds(List<PlayOutput> playOutputs) {
        Map<Integer, List<PlayDTO>> rounds = new HashMap<>();
        Integer round = 1;
        List<PlayDTO> plays = new ArrayList<>();

        for (int i = 0; i < playOutputs.size(); i++) {
            PlayOutput currentPlay = playOutputs.get(i);
            if (isTrue(currentPlay.getIsProcessed())) {
                continue;
            }

            boolean isLastRound = LAST_ROUND.equals(round);
            if (isLastRound) {
                PlayOutput secondPlay = playOutputs.get(i + 1);
                PlayOutput thirdPlay = playOutputs.get(i + 2);
                PlayDTO play = processPlay(currentPlay, secondPlay, thirdPlay);
                plays.add(play);
                enhanceRoundsWithPlays(rounds, round, playOutputs, plays);
            }

            PlayOutput nextPlay = playOutputs.get(i + 1);
            if (isTrue(nextPlay.getIsProcessed())) {
                continue;
            }

            PlayDTO play = processPlay(currentPlay, nextPlay);
            plays.add(play);
            boolean isRoundComplete = enhanceRoundsWithPlays(rounds, round, playOutputs, plays);
            if (isRoundComplete) {
                round++;
                plays = new ArrayList<>();
            }
        }

        return rounds;
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

    private Boolean enhanceRoundsWithPlays(Map<Integer, List<PlayDTO>> rounds,
                                           Integer round,
                                           List<? extends PlayOutput> playOutputs,
                                           List<PlayDTO> plays) {
        Boolean isRoundComplete = isRoundComplete(playOutputs, plays);

        if (isRoundComplete) {
            rounds.put(round, plays);
        }

        return isRoundComplete;
    }

    private Boolean isRoundComplete(List<? extends PlayOutput> playOutputs, List<PlayDTO> plays) {
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
