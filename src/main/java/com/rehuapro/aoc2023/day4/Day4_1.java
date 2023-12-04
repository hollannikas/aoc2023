package com.rehuapro.aoc2023.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day4_1 {

    record Card(int number, List<Integer> numbers, List<Integer> winningNumbers) {
        public long score() {
            var wins = winningNumbers.stream()
                    .filter(numbers::contains)
                    .toList();

            var product = 0L;
            for (Integer ignored : wins) {
                    if (product == 0) {
                        product++;
                    } else {
                        product *= 2;
                    }
            }
            return product;
        }
    }

    public Long sumWinningCards(String fileName) throws URISyntaxException {
        try {
            Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
            return Files.readAllLines(path)
                    .stream().map(this::parse)
                    .peek(System.out::println)
                    .map(Card::score)
                    .reduce(0L, Long::sum);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Card parse(String line) {
        int number = Integer.parseInt(line.substring(5, 8).trim());

        var winningNumbers = getWinningNumbers(line);
        var numbers = getCardNumbers(line);
        return new Card(number, numbers, winningNumbers);
    }

    private List<Integer> getWinningNumbers(String line) {
        return getNumbers(line, 10, 10);
    }

    private List<Integer> getCardNumbers(String line) {
        return getNumbers(line, 42, 25);
    }
    private List<Integer> getNumbers(String line, int offset, int amount) {
        return Arrays.stream(line.substring(offset, offset + amount * 3 -1)
                .split(" "))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}