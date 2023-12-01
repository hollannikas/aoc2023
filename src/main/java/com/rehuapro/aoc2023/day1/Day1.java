package com.rehuapro.aoc2023.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day1 {

    Pattern digitPattern = Pattern.compile("\\D");

    public Integer sumCalibrationValues(String calibrationFileName) throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(calibrationFileName)).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(this::toCoordinates)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer toCoordinates(String s) {
        var onlyDigits = digitPattern.matcher(s).replaceAll("").toCharArray();

        return Integer.parseInt( "" +
                onlyDigits[0] +
                ((onlyDigits.length == 1) ? onlyDigits[0] : onlyDigits[onlyDigits.length-1])
        );
    }
}
