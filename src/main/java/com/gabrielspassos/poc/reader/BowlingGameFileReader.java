package com.gabrielspassos.poc.reader;

import com.gabrielspassos.poc.dto.input.PlayInput;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import static com.gabrielspassos.poc.config.BowlingConfig.INPUT_FIELD_NAMES;
import static com.gabrielspassos.poc.config.BowlingConfig.INPUT_FILE_NAME;

@Service
@Qualifier("bowlingGameFileReader")
public class BowlingGameFileReader {

    public FlatFileItemReader<PlayInput> reader() {
        return new FlatFileItemReaderBuilder<PlayInput>()
                .name("bowlingGameFileReader")
                .resource(new ClassPathResource(INPUT_FILE_NAME))
                .lineTokenizer(new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB) {{
                    setNames(INPUT_FIELD_NAMES.toArray(String[]::new));
                }})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(PlayInput.class);
                }})
                .build();
    }
}
