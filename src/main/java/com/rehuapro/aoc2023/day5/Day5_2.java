package com.rehuapro.aoc2023.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.LongStream;

public class Day5_2 {

    record CategoryRow(long destinationStart, long sourceStart, long length) {
        public boolean isInRange(long sourceLocation) {
            return sourceLocation >= sourceStart && sourceLocation < sourceStart + length;
        }
        public long getDestinationValue(long sourceLocation) {
            return destinationStart + (sourceLocation - sourceStart);
        }
    }
    record Pair(long first, long second) {}
    private final List<Pair> seeds = new ArrayList<>();
    private final Set<String> categoryNames = new LinkedHashSet<>();
    private final Map<String, List<CategoryRow>> categories = new HashMap<>();
    public Long solve(String fileName) throws URISyntaxException {
        var path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            String category = "";
            for (var line : lines.toList()) {
                if (line.startsWith("seeds:")) {
                    var numbers = Arrays.stream(line.split(" "))
                            .skip(1)
                            .map(Long::parseLong)
                            .toList();
                    for (int i = 0; i < numbers.size(); i += 2) {
                        Long firstNumber = numbers.get(i);
                        Long secondNumber = numbers.get(i + 1);
                        seeds.add(new Pair(firstNumber, secondNumber));
                    }
                } else if (line.endsWith("map:")) {
                    var map = line.split("-to-");
                    var to = map[1].split(" ")[0];
                    categoryNames.add(to);
                    categories.computeIfAbsent(to, k -> new ArrayList<>());
                    category = to;
                } else if (!line.trim().isBlank()) {
                    var parts = line.split(" ");
                    categories.get(category).add(new CategoryRow(
                            Long.parseLong(parts[0]),
                            Long.parseLong(parts[1]),
                            Long.parseLong(parts[2])
                    ));
                }
            }
            var ref = new Object() {
                long lowestLocation = Long.MAX_VALUE;
            };
            seeds.forEach(pair -> LongStream.range(pair.first, pair.first + pair.second).
                    forEach(seed -> {
                        var location = seed;
                        for (var categoryName : categoryNames) {
                            var rows = categories.get(categoryName);
                            for (var row : rows) {
                                if (row.isInRange(location)) {
                                    location = row.getDestinationValue(location);
                                    break;
                                }
                            }
                        }
                        if (location < ref.lowestLocation) ref.lowestLocation = location;
                    }));
            return ref.lowestLocation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}