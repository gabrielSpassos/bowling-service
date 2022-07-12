package com.gabrielspassos.poc.config;

import com.gabrielspassos.poc.dto.input.PlayInput;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.processor.BowlingGameItemProcessor;
import com.gabrielspassos.poc.reader.BowlingGameFileReader;
import com.gabrielspassos.poc.writer.BowlingGameFileWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

import static com.gabrielspassos.poc.config.BowlingConfig.INPUT_FIELD_NAMES;
import static com.gabrielspassos.poc.config.BowlingConfig.OUTPUT_FILE_NAME;

@Configuration
@EnableBatchProcessing
public class BowlingGameBatchConfig extends DefaultBatchConfigurer {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BowlingGameBatchConfig(DataSource dataSource,
                                  JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory) {
        super(dataSource);
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @Qualifier("bowlingGameJob")
    protected Job bowlingGameJob(@Qualifier("bowlingGameFileStep") Step bowlingGameFileStep) {
        return jobBuilderFactory
                .get("bowlingGameJob")
                .start(bowlingGameFileStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @Qualifier("bowlingGameFileStep")
    protected Step bowlingGameFileStep(
            @Qualifier("bowlingGameFileReader") BowlingGameFileReader bowlingGameFileReader,
            @Qualifier("bowlingGameItemProcessor") BowlingGameItemProcessor bowlingGameItemProcessor,
            @Qualifier("bowlingGameTextWriter") BowlingGameFileWriter bowlingGameFileWriter) {
        return stepBuilderFactory.get("bowlingGameFileStep")
                .<PlayInput, PlayOutput>chunk(200)
                .reader(bowlingGameFileReader.reader())
                .processor(bowlingGameItemProcessor)
                .writer(bowlingGameFileWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
