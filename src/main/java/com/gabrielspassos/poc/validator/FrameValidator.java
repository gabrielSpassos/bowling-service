package com.gabrielspassos.poc.validator;

import com.gabrielspassos.poc.dto.FrameDTO;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.gabrielspassos.poc.config.BowlingConfig.LAST_FRAME;

@Component
public class FrameValidator implements Validator<Map<Integer, List<FrameDTO>>> {

    @Override
    public void validate(Map<Integer, List<FrameDTO>> frames) throws ValidationException {
        if (frames.size() > LAST_FRAME) {
            throw new ValidationException("There is more then 10 frames");
        }

        if (frames.values().stream().anyMatch(this::playerHasMoreThanOneFrame)) {
            throw new ValidationException("Invalid frame, player with more than one play in this frame");
        }
    }

    private boolean playerHasMoreThanOneFrame(List<FrameDTO> frames) {
        return !frames.stream().map(FrameDTO::getPlayerName).allMatch(new HashSet<>()::add);
    }
}
