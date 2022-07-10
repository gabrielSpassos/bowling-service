package com.gabrielspassos.poc.processor;

import com.gabrielspassos.poc.config.PlayValidator;
import com.gabrielspassos.poc.dto.input.PlayInput;
import com.gabrielspassos.poc.dto.output.PlayOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

import static com.gabrielspassos.poc.config.BowlingConfig.FAULT_SCORE_VALUE;
import static com.gabrielspassos.poc.config.BowlingConfig.FAULT_SYMBOL;

@Slf4j
@Qualifier("bowlingGameItemProcessor")
@StepScope
@Service
@AllArgsConstructor
public class BowlingGameItemProcessor implements ItemProcessor<PlayInput, PlayOutput> {

    private final PlayValidator validator;

    @Override
    public PlayOutput process(PlayInput item) {
        log.info("Play input: {}", item);
        PlayOutput playOutput = mapFromInput(item);
        validator.validate(playOutput);
        return playOutput;
    }

    private PlayOutput mapFromInput(PlayInput playInput) {
        Integer score = StringUtils.isNumeric(playInput.getPlayerScore())
                ? Integer.parseInt(playInput.getPlayerScore())
                : getFaultScore(playInput);

        return PlayOutput.builder()
                .playerName(playInput.getPlayerName())
                .playerScore(score)
                .build();
    }

    private Integer getFaultScore(PlayInput playInput) {
        if (!StringUtils.isAlpha(playInput.getPlayerScore())
                || !FAULT_SYMBOL.equals(playInput.getPlayerScore())) {
            throw new IllegalArgumentException("Invalid fault score");
        }

        return FAULT_SCORE_VALUE;
    }
}
