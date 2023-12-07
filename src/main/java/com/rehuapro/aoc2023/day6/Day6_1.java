package com.rehuapro.aoc2023.day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Day6_1 {

    record Race(long time, long distance) {
        public long waysToWin() {
            return IntStream.rangeClosed(0, (int) time)
                    .filter(i -> i * (time - i) > distance)
                    .count();
        }
    }
    public Long solve(String fileName) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            List<Race> races = getRaces(lines.toList());
            BinaryOperator<Long> times = (a, b) -> a * b;
            return races.stream()
                    .map(Race::waysToWin)
                    .filter(ways -> ways > 0)
                    .reduce(1L, times);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Race> getRaces(List<String> rows) {
        List<Long> times = toLongList(rows.get(0));
        List<Long> distances = toLongList(rows.get(1));

        return IntStream.range(0, times.size())
                .mapToObj(i -> new Race(times.get(i), distances.get(i)))
                .toList();
    }

    private List<Long> toLongList(String row) {
        return Arrays.stream(row.split(" "))
                .skip(1)
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();
    }
}

