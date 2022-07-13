package com.gabrielspassos.poc.services;

import com.gabrielspassos.poc.dto.FrameDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gabrielspassos.poc.config.BowlingConfig.MAX_SCORE_VALUE;
import static com.gabrielspassos.poc.config.BowlingConfig.MIN_SCORE_VALUE;

@Service
public class BowlingService {

    public void calculateFramesScore(Map<Integer, List<FrameDTO>> framesMap) {
        for (int i = 1; i <= framesMap.size(); i++) {
            List<FrameDTO> currentFrames = framesMap.get(i);
            List<FrameDTO> playersWithStrike = getFramesWithSpecialFrame(currentFrames, isStrike());
            List<FrameDTO> playersWithSpare = getFramesWithSpecialFrame(currentFrames, isSpare().and(isStrike().negate()));

            if (!playersWithStrike.isEmpty()) {
                calculateStrikeFrame(playersWithStrike, framesMap, i);
            }

            if (!playersWithSpare.isEmpty()) {
                calculateSpareFrame(playersWithSpare, framesMap, i);
            }

            int currentFrame = i;
            currentFrames.stream()
                    .filter(playerIsNotAtList(playersWithStrike).and(playerIsNotAtList(playersWithSpare)))
                    .forEach(frameDTO -> {
                        calculateNormalFrame(frameDTO, framesMap, currentFrame);
                    });
        }
    }

    private void calculateStrikeFrame(List<FrameDTO> playersWithStrike,
                                      Map<Integer, List<FrameDTO>> framesMap,
                                      Integer currentFrame) {
        List<FrameDTO> previousFrames = getFramesByKeyIfExits(framesMap, currentFrame - 1);
        List<FrameDTO> nextFrames = getFramesByKeyIfExits(framesMap, currentFrame + 1);
        List<FrameDTO> doubleNextFrames = getFramesByKeyIfExits(framesMap, currentFrame + 2);

        playersWithStrike.forEach(frame -> {
            FrameDTO playerPreviousFrame = getPlayerFrame(previousFrames, frame);
            FrameDTO playerNextFrame = getPlayerFrame(nextFrames, frame);
            FrameDTO playerDoubleNextFrame = getPlayerFrame(doubleNextFrames, frame);

            Integer previousScore = getPreviousFinalScore(playerPreviousFrame);
            Integer plusScore = getScores(playerNextFrame, playerDoubleNextFrame)
                    .stream()
                    .limit(2)
                    .reduce(0, Integer::sum);

            sumScores(frame, plusScore, previousScore);
        });
    }

    private void calculateSpareFrame(List<FrameDTO> playersWithSpare,
                                     Map<Integer, List<FrameDTO>> framesMap,
                                     Integer currentFrame) {
        List<FrameDTO> previousFrames = getFramesByKeyIfExits(framesMap, currentFrame - 1);
        List<FrameDTO> nextFrames = getFramesByKeyIfExits(framesMap, currentFrame + 1);

        playersWithSpare.forEach(frame -> {
            FrameDTO playerPreviousFrame = getPlayerFrame(previousFrames, frame);
            FrameDTO playerNextFrame = getPlayerFrame(nextFrames, frame);

            Integer previousScore = getPreviousFinalScore(playerPreviousFrame);
            Integer nextScore = getFirstScore(playerNextFrame);

            sumScores(frame, nextScore, previousScore);
        });
    }

    private void calculateNormalFrame(FrameDTO frame, Map<Integer, List<FrameDTO>> framesMap, Integer currentFrame) {
        List<FrameDTO> previousFrames = getFramesByKeyIfExits(framesMap, currentFrame - 1);
        FrameDTO playerPreviousFrame = getPlayerFrame(previousFrames, frame);
        Integer previousScore = getPreviousFinalScore(playerPreviousFrame);
        sumScores(frame, 0, previousScore);
    }

    private List<FrameDTO> getFramesWithSpecialFrame(List<FrameDTO> frames, Predicate<FrameDTO> isSpecialFrame) {
        return frames.stream()
                .filter(isSpecialFrame)
                .collect(Collectors.toList());
    }

    private Predicate<FrameDTO> isStrike() {
        return frame -> MAX_SCORE_VALUE.equals(frame.getFirstScore())
                || MAX_SCORE_VALUE.equals(frame.getSecondScore())
                || MAX_SCORE_VALUE.equals(frame.getThirdScore());
    }

    private Predicate<FrameDTO> isSpare() {
        return frame -> {
            Integer sumOfScores = frame.sumScores();
            return MAX_SCORE_VALUE.equals(sumOfScores);
        };
    }

    private Predicate<FrameDTO> playerIsNotAtList(List<FrameDTO> frames) {
        return frame -> frames.stream().noneMatch(frameDTO -> frame.getPlayerName().equals(frameDTO.getPlayerName()));
    }

    private void sumScores(FrameDTO frame, Integer plusScore, Integer previousScore) {
        Integer finalScore = frame.sumScores(plusScore, previousScore);
        frame.setFinalScore(finalScore);
    }

    private List<FrameDTO> getFramesByKeyIfExits(Map<Integer, List<FrameDTO>> framesMap, Integer key) {
        if (!framesMap.containsKey(key)) {
            return new ArrayList<>();
        }

        return framesMap.get(key);
    }

    private FrameDTO getPlayerFrame(List<FrameDTO> frames, FrameDTO frame) {
        return frames.stream()
                .filter(frameDTO -> frame.getPlayerName().equals(frameDTO.getPlayerName()))
                .findFirst()
                .orElse(null);
    }

    private Integer getFirstScore(FrameDTO frame) {
        return getScores(frame)
                .stream()
                .findFirst()
                .orElse(MIN_SCORE_VALUE);
    }

    private Integer getPreviousFinalScore(FrameDTO previousFrame) {
        return Objects.nonNull(previousFrame) ? previousFrame.getFinalScore() : MIN_SCORE_VALUE;
    }

    private List<Integer> getScores(FrameDTO... frames) {
        return Arrays.stream(frames)
                .filter(Objects::nonNull)
                .map(FrameDTO::getScores)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
