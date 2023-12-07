package com.rehuapro.aoc2023.day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day6_2 {

    record Race(long time, long distance) {
        public long waysToWin() {
            return LongStream.rangeClosed(0, time)
                    .filter(i -> i * (time - i) > distance)
                    .count();
        }
    }
    public Long solve(String fileName) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            return toRace(lines.toList()).waysToWin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Race toRace(List<String> rows) {
        var time = Long.parseLong(rows.get(0).split(":")[1]
                .chars()
                .filter(Character::isDigit)
                .mapToObj(Character::toString)
                .collect(Collectors.joining()));

        var distance = Long.parseLong(rows.get(1).split(":")[1]
                .chars()
                .filter(Character::isDigit)
                .mapToObj(Character::toString)
                .collect(Collectors.joining()));

        return new Race(time, distance);
    }
}

