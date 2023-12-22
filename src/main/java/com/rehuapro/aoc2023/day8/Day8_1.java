package com.rehuapro.aoc2023.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day8_1 {

    record Node(String left, String right) { }

    private final Map<String, Node> nodes = new HashMap<>();

    public Long solve(String fileName) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            List<String> linesList = lines.toList();
            var steps = linesList.get(0).chars().mapToObj(c -> (char) c).toList();

            IntStream.range(2, linesList.size())
                    .mapToObj(i -> linesList.get(i).split(" = "))
                    .forEach(parts -> {
                        var left = parts[1].split(", ")[0].substring(1, 4);
                        var right = parts[1].split(", ")[1].substring(0, 3);
                        nodes.put(parts[0], new Node(left, right));
                    });

            var currentNode = "AAA";
            var counter = 0;

            while (!currentNode.equals("ZZZ")) {
                var nextStep = steps.get(counter % steps.size());
                var node = nodes.get(currentNode);
                switch (nextStep) {
                    case 'L' -> currentNode = node.left;
                    case 'R' -> currentNode = node.right;
                    default -> throw new RuntimeException("Invalid step: " + nextStep);
                }
                counter++;
            }

            return (long) counter;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


