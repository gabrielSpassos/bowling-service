package com.gabrielspassos.poc.writer;

import com.gabrielspassos.poc.dto.FrameDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.services.BowlingService;
import com.gabrielspassos.poc.services.FrameService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        System.out.println("Calculated");
        System.out.println(frames);
    }
}
