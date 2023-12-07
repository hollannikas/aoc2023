package com.rehuapro.aoc2023.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day3_2 {

    record Number(int line, int start, int end, int number) {}

    record Symbol(int line, int index) {}

    record Gear(boolean isValid, int gearRatio) {}

    public Integer sumGearRatios(String fileName) throws URISyntaxException {
        try {
            var path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());

            var numbers = new ArrayList<Number>();
            var gears = new ArrayList<Symbol>();

            List<String> lines = Files.readAllLines(path);
            for (var i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                var lineNumber = i;
                var numberMatcher = Pattern.compile("[0-9]+").matcher(line);
                numbers.addAll(numberMatcher.results().map(match ->
                        new Number(lineNumber, match.start(), match.end() - 1, Integer.parseInt(match.group()))).toList());
                var gearMatcher = Pattern.compile("\\*").matcher(line);
                gears.addAll(gearMatcher.results().map(match -> new Symbol(lineNumber, match.start())).toList());
            }

            return gears.stream()
                    .map(gear -> {
                        List<Number> matchingNumbers = numbers.stream()
                                .filter(number -> isAdjacent(number, gear))
                                .toList();
                        return new Gear(matchingNumbers.size() == 2,
                                matchingNumbers
                                        .stream()
                                        .mapToInt(Number::number)
                                        .reduce(1, this::times));
                    })
                    .filter(Gear::isValid)
                    .mapToInt(Gear::gearRatio)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer times(Integer a, Integer b) {
        return a * b;
    }
    static boolean isAdjacent(Number number, Symbol symbol) {
        return symbol.line <= number.line + 1 &&
                symbol.line >= number.line - 1 &&
                symbol.index >= number.start - 1 &&
                symbol.index <= number.end + 1;
    }
}