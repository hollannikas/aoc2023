package com.rehuapro.aoc2023.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day4_2 {

    private final Map<Integer, Integer> cards = new HashMap<>();
    record Card(int number, List<Integer> numbers, List<Integer> winningNumbers) {
        public int score() {
            return Long.valueOf(winningNumbers.stream()
                    .filter(numbers::contains)
                    .count()).intValue();
        }
    }

    public int sumWinningCards(String fileName) throws URISyntaxException {
        var path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            lines
                .map(this::parse)
                .forEach(card -> {
                    addCards(card.number, 1);
                    addCardsFromScore(card.number, card.score());
                });

            return cards.values().stream().reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCards(int cardNumber, int count) {
        cards.compute(cardNumber, (k, v) -> v == null ? count : v + count);
    }

    private void addCardsFromScore(int cardNumber, int matches) {
        if (matches > 0) {
            var copiesOfCurrentCard = cards.getOrDefault(cardNumber, 0);
            IntStream.rangeClosed(cardNumber + 1, cardNumber + matches)
                    .forEach(newCardIndex -> addCards(newCardIndex, copiesOfCurrentCard));
        }
    }

    private Card parse(String line) {
        var number = Integer.parseInt(line.substring(5, 8).trim());

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