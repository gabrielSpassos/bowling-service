package com.gabrielspassos.poc.writer;

import com.gabrielspassos.poc.dto.output.PlayOutput;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.gabrielspassos.poc.config.BowlingConfig.INPUT_FIELD_NAMES;
import static com.gabrielspassos.poc.config.BowlingConfig.OUTPUT_FILE_NAME;

@Component
public class BowlingGameFileWriter {

    @Qualifier("bowlingGameTextWriter")
    @Bean
    @StepScope
    public FlatFileItemWriter<PlayOutput> writeGameText() {
        BeanWrapperFieldExtractor<PlayOutput> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(INPUT_FIELD_NAMES.toArray(String[]::new));

        DelimitedLineAggregator<PlayOutput> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<PlayOutput> writer = new FlatFileItemWriter<>();

        writer.setResource(new FileSystemResource(OUTPUT_FILE_NAME));
        writer.setLineAggregator(lineAggregator);
        return writer;
    }
}
