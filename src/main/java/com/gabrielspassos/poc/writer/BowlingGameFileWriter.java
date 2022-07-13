package com.gabrielspassos.poc.writer;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.services.BowlingService;
import com.gabrielspassos.poc.services.FrameService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gabrielspassos.poc.config.BowlingConfig.LAST_FRAME;

@Component
@Qualifier("bowlingGameTextWriter")
@StepScope
@AllArgsConstructor
public class BowlingGameFileWriter implements ItemWriter<PlayOutput> {

    private FrameService frameService;
    private BowlingService bowlingService;

    @Override
    public void write(List<? extends PlayOutput> playOutputs) {
        Map<Integer, List<FrameDTO>> frames = frameService.mapPlaysToFrames((List<PlayOutput>) playOutputs);
        System.out.println(frames);
        bowlingService.calculateFramesScore(frames);
        System.out.println(frames);

        System.out.print("Frame");
        frames.keySet().forEach(key -> System.out.print("\t" + key));
        System.out.println();
        frames.forEach((key, value) -> {
            value.forEach(frameDTO -> {
                Boolean isLastFrame = LAST_FRAME.equals(key);
                System.out.println();
                System.out.println(frameDTO.getPlayerName());
                System.out.print("Pinfalls" + "\t");
                printFirstScoreLayout(frameDTO);
                printSecondScoreLayout(frameDTO, !isLastFrame);
                printThirdScoreLayout(frameDTO, isLastFrame);
                System.out.print("Score" + "\t");
                System.out.print(frameDTO.getFinalScore() + "\t");
            });
        });
        System.out.println();
    }

    private void printFirstScoreLayout(FrameDTO frameDTO) {
        if (Objects.isNull(frameDTO.getFirstScore())) {
            System.out.print(StringUtils.EMPTY + "\t");
        }

        if (frameDTO.isStrikeScore(frameDTO.getFirstScore())) {
            System.out.print("X" + "\t");
        }

        System.out.print(frameDTO.getFirstScore() + "\t");
    }

    private void printSecondScoreLayout(FrameDTO frameDTO, Boolean shouldEscapeLine) {
        if (Objects.isNull(frameDTO.getSecondScore())) {
            System.out.print(StringUtils.EMPTY + "\t");
        }

        if (frameDTO.isSecondScoreSpare()) {
            System.out.print("/" + "\t");
        }

        System.out.print(frameDTO.getSecondScore() + "\t");

        if (shouldEscapeLine) {
            System.out.println();
        }
    }

    private void printThirdScoreLayout(FrameDTO frameDTO, Boolean shouldEscapeLine) {
        if (Objects.isNull(frameDTO.getThirdScore())) {
            System.out.print(StringUtils.EMPTY + "\t");
        }

        if (frameDTO.isThirdScoreSpare()) {
            System.out.print("/" + "\t");
        }

        System.out.print(frameDTO.getThirdScore() + "\t");

        if (shouldEscapeLine) {
            System.out.println();
        }
    }
}
