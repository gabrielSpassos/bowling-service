package com.gabrielspassos.poc.validator;

import com.gabrielspassos.poc.dto.output.PlayOutput;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.stereotype.Component;

import static com.gabrielspassos.poc.config.BowlingConfig.MAX_SCORE_VALUE;
import static com.gabrielspassos.poc.config.BowlingConfig.MIN_SCORE_VALUE;


@Component
public class PlayValidator implements Validator<PlayOutput> {

    @Override
    public void validate(PlayOutput playOutput) throws ValidationException {
        if (StringUtils.isBlank(playOutput.getPlayerName())) {
            throw new ValidationException("Player name must be informed");
        }

        if (playOutput.getPlayerScore() == null) {
            throw new ValidationException("Player score must be informed");
        }

        if (playOutput.getPlayerScore() < MIN_SCORE_VALUE) {
            throw new ValidationException("The minimal score is 0");
        }

        if (playOutput.getPlayerScore() > MAX_SCORE_VALUE) {
            throw new ValidationException("The maximum score is 10");
        }
    }
}
