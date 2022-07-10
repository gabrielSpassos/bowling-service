package com.gabrielspassos.poc.config;

import com.gabrielspassos.poc.dto.input.PlayInput;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import com.gabrielspassos.poc.processor.BowlingGameItemProcessor;
import com.gabrielspassos.poc.reader.BowlingGameFileReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
            @Qualifier("bowlingGameTextWriter") ItemWriter<PlayOutput> bowlingGameFileWriter) {
        return stepBuilderFactory.get("bowlingGameFileStep")
                .<PlayInput, PlayOutput>chunk(2)
                .reader(bowlingGameFileReader.reader())
                .processor(bowlingGameItemProcessor)
                .writer(bowlingGameFileWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
