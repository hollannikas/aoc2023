package com.rehuapro.aoc2023.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5_1 {

    record CategoryRow(long destinationStart, long sourceStart, long length) {
        public boolean isInRange(long sourceLocation) {
            return sourceLocation >= sourceStart && sourceLocation < sourceStart + length;
        }
        public long getDestinationValue(long sourceLocation) {
            return destinationStart + (sourceLocation - sourceStart);
        }
    }
    private final List<Long> seeds = new ArrayList<>();
    private final Set<String> categoryNames = new LinkedHashSet<>();
    private final Map<String, List<CategoryRow>> categories = new HashMap<>();
    public Long solve(String fileName) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            String category = "";
            for (var line : lines.toList()) {
                if (line.startsWith("seeds:")) {
                    seeds.addAll(Arrays.stream(line.split(" "))
                            .skip(1)
                            .map(Long::parseLong)
                            .toList());
                } else if (line.endsWith("map:")) {
                    var map = line.split("-to-");
                    String to = map[1].split(" ")[0];
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
            return seeds.stream().map(seed -> {
                long location = seed;
                for (var categoryName : categoryNames) {
                    var rows = categories.get(categoryName);
                    for (var row : rows) {
                        if (row.isInRange(location)) {
                            location = row.getDestinationValue(location);
                            break;
                        }
                    }
                }
                return location;
            }).min(Long::compareTo).orElseThrow();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

