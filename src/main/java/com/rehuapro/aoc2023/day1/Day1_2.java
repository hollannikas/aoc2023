package com.rehuapro.aoc2023.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day1_2 {

    private final Pattern digitPattern = Pattern.compile("\\D");
    private static final String[] NUMBERS = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public Integer sumCalibrationValues(final String calibrationFileName) throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(calibrationFileName)).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::replaceText)
                    .map(this::toCoordinates)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String replaceText(final String input) {
        String returnValue = input;
        for (int i=0; i< NUMBERS.length; i++){
            returnValue = returnValue.replace(NUMBERS[i], insertInMiddle(NUMBERS[i], i+1));
        }

        return returnValue;
    }

    private String insertInMiddle(final String s, final int number) {
        StringBuilder sb = new StringBuilder(s);

        sb.insert(sb.length()/2, number);
        return sb.toString();
    }

    private Integer toCoordinates(final String s) {
        var onlyDigits = digitPattern.matcher(s).replaceAll("").toCharArray();

        return Integer.parseInt( "" +
                onlyDigits[0] +
                ((onlyDigits.length == 1) ? onlyDigits[0] : onlyDigits[onlyDigits.length-1])
        );
    }
}
