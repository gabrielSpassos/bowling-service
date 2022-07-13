package com.gabrielspassos.poc.config;

import java.util.Arrays;
import java.util.List;

public class BowlingConfig {

    public static final String INPUT_FILE_NAME = "scores.txt";
    public static final List<String> INPUT_FIELD_NAMES = Arrays.asList("playerName", "playerScore");
    public static final String OUTPUT_FILE_NAME = "src/main/resources/result.txt";
    public static final String FAULT_SYMBOL = "F";
    public static final Integer FAULT_SCORE_VALUE = 0;
    public static final Integer MIN_SCORE_VALUE = 0;
    public static final Integer MAX_SCORE_VALUE = 10;
    public static final Integer LAST_FRAME = 10;
}
