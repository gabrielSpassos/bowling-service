package com.gabrielspassos.poc.writer;

import com.gabrielspassos.poc.dto.PlayDTO;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.services.PlaysService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.gabrielspassos.poc.config.BowlingConfig.INPUT_FIELD_NAMES;
import static com.gabrielspassos.poc.config.BowlingConfig.OUTPUT_FILE_NAME;

@Component
@Qualifier("bowlingGameTextWriter")
@StepScope
@AllArgsConstructor
public class BowlingGameFileWriter implements ItemWriter<PlayOutput> {

    private PlaysService playsService;

    @Override
    public void write(List<? extends PlayOutput> playOutputs) {
        Map<Integer, List<PlayDTO>> rounds = playsService.mapPlaysToRounds((List<PlayOutput>) playOutputs);
        System.out.println(rounds);

        BeanWrapperFieldExtractor<PlayOutput> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(INPUT_FIELD_NAMES.toArray(String[]::new));

        DelimitedLineAggregator<PlayOutput> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<PlayOutput> writer = new FlatFileItemWriter<>();

        writer.setResource(new FileSystemResource(OUTPUT_FILE_NAME));
        writer.setLineAggregator(lineAggregator);
    }
}
