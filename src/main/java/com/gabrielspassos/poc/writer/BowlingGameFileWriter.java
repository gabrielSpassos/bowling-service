package com.gabrielspassos.poc.writer;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.enumerator.ScoreEnum;
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

@Component
@Qualifier("bowlingGameTextWriter")
@StepScope
@AllArgsConstructor
public class BowlingGameFileWriter implements ItemWriter<PlayOutput> {

    private FrameService frameService;
    private BowlingService bowlingService;

    private static final String TAB = "\t";
    private static final String DUBLE_TAB = TAB + TAB;
    private static final String STIKE = "X";
    private static final String SPARE = "/";

    @Override
    public void write(List<? extends PlayOutput> playOutputs) {
        Map<Integer, List<FrameDTO>> frames = frameService.mapPlaysToFrames((List<PlayOutput>) playOutputs);
        bowlingService.calculateFramesScore(frames);

        System.out.print("Frame");
        frames.keySet().forEach(key -> System.out.print(DUBLE_TAB + key));
        System.out.println();

        List<FrameDTO> frame1 = frames.get(1);
        List<FrameDTO> frame2 = frames.get(2);
        List<FrameDTO> frame3 = frames.get(3);
        List<FrameDTO> frame4 = frames.get(4);
        List<FrameDTO> frame5 = frames.get(5);
        List<FrameDTO> frame6 = frames.get(6);
        List<FrameDTO> frame7 = frames.get(7);
        List<FrameDTO> frame8 = frames.get(8);
        List<FrameDTO> frame9 = frames.get(9);
        List<FrameDTO> frame10 = frames.get(10);

        for (int i = 0; i < frame1.size(); i++) {
            System.out.println(frame1.get(i).getPlayerName());
            System.out.println("Pinfalls" + TAB
                    + getScoresToPrint(frame1, i)
                    + getScoresToPrint(frame2, i)
                    + getScoresToPrint(frame3, i)
                    + getScoresToPrint(frame4, i)
                    + getScoresToPrint(frame5, i)
                    + getScoresToPrint(frame6, i)
                    + getScoresToPrint(frame7, i)
                    + getScoresToPrint(frame8, i)
                    + getScoresToPrint(frame9, i)
                    + getScoresToPrint(frame10, i)
            );
            System.out.println("Score" + DUBLE_TAB
                    + getFinalScoreToPrint(frame1, i)
                    + getFinalScoreToPrint(frame2, i)
                    + getFinalScoreToPrint(frame3, i)
                    + getFinalScoreToPrint(frame4, i)
                    + getFinalScoreToPrint(frame5, i)
                    + getFinalScoreToPrint(frame6, i)
                    + getFinalScoreToPrint(frame7, i)
                    + getFinalScoreToPrint(frame8, i)
                    + getFinalScoreToPrint(frame9, i)
                    + getFinalScoreToPrint(frame10, i)
            );
        }

    }

    private String getScoresToPrint(List<FrameDTO> frames, Integer index) {
        FrameDTO frame = frames.get(index);
        return printScore(frame, frame.getFirstScore(), ScoreEnum.FIRST, TAB)
                + printScore(frame, frame.getSecondScore(), ScoreEnum.SECOND, TAB)
                + printScore(frame, frame.getThirdScore(), ScoreEnum.THIRD, StringUtils.EMPTY);
    }

    private String getFinalScoreToPrint(List<FrameDTO> frames, Integer index) {
        return frames.get(index).getFinalScore() + DUBLE_TAB;
    }

    private String printScore(FrameDTO frameDTO, Integer score, ScoreEnum scorePosition, String defaultOption) {
        if (Objects.isNull(score)) {
            return defaultOption;
        }

        if (frameDTO.isStrikeScore(score)) {
            return STIKE + TAB;
        }

        if (ScoreEnum.SECOND.equals(scorePosition) && frameDTO.isSecondScoreSpare()) {
            return SPARE + TAB;
        }

        if (ScoreEnum.THIRD.equals(scorePosition) && frameDTO.isThirdScoreSpare()) {
            return SPARE + TAB;
        }

        return score + TAB;
    }
}
