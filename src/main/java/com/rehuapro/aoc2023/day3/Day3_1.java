package com.rehuapro.aoc2023.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day3_1 {

    record Number(int line, int start, int end, int number) {}
    record Symbol(int line, int index) {}

    public Integer sumPartNumbers(String fileName) throws URISyntaxException {
        try {
            var path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());

            var numbers = new ArrayList<Number>();
            var symbols = new ArrayList<Symbol>();

            var lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                var line = lines.get(i);

                var symbolMatcher = Pattern.compile("[^0-9|.]+").matcher(line);
                int lineNumber = i;
                symbols.addAll(symbolMatcher.results().map(match -> new Symbol(lineNumber, match.start())).toList());

                var numberMatcher = Pattern.compile("[0-9]+").matcher(line);

                numbers.addAll(numberMatcher.results().map(match ->
                        new Number(lineNumber, match.start(), match.end() - 1, Integer.parseInt(match.group()))).toList());
            }

            return numbers.stream()
                    .filter(number -> symbols.stream().anyMatch(symbol -> isAdjacent(number, symbol)))
                    .mapToInt(Number::number)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isAdjacent(Number number, Symbol symbol) {
        return symbol.line <= number.line + 1 &&
                symbol.line >= number.line - 1 &&
                symbol.index >= number.start - 1 &&
                symbol.index <= number.end + 1;
    }
}