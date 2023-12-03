package com.rehuapro.aoc2023.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day3_1 {

    private static final Set<String> symbols = new HashSet<>();
    private record Box(int startX, int startY, int endX, int endY, int value, String content) {
        public boolean isValid() {
            for (var symbol : symbols) {
                if (content.contains(symbol)) {
                    return true;
                }
            }
            return false;
        }
    }
    public Integer sumPartNumbers(String calibrationFileName) throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(calibrationFileName)).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            var charArray = constructCharArray(lines.toList());

            symbols.addAll(getSymbols(charArray));

            var boxes = findIntegers(charArray);

            return boxes.stream()
                    .filter(Box::isValid)
                    .map(Box::value)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<String> getSymbols(char[][] charArray) {
        var symbols = new HashSet<String>();
        for (var row : charArray) {
            for (var c : row) {
                if (!Character.isDigit(c) && c != '.') {
                    symbols.add(""+c);
                }
            }
        }
        return symbols;
    }

    private char[][] constructCharArray(List<String> stringList) {
        var rows = stringList.size();
        var cols = stringList.get(0).length();

        var charArray = new char[rows][cols];

        for (var i = 0; i < rows; i++) {
            charArray[i] = stringList.get(i).toCharArray();
        }

        return charArray;
    }
    private List<Box> findIntegers(char[][] charArray) {
        var rows = charArray.length;
        var cols = charArray[0].length;

        List<Box> boxes = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Character.isDigit(charArray[i][j]) &&
                        (j == 0 || !Character.isDigit(charArray[i][Math.max(0, j - 1)]))) {

                    var endX = j;
                    while (endX + 1 < cols && Character.isDigit(charArray[i][endX + 1])) {
                        endX++;
                    }

                    String content = getContent(charArray, i, j, endX);
                    var value = Integer.parseInt(String.valueOf(charArray[i], j, endX - j + 1));
                    Box box = new Box(j, i, endX, i, value, content);
                    boxes.add(box);
                }
            }
        }
        return boxes;
    }

    private String getContent(char[][] charArray, int row, int startCol, int endCol) {
        StringBuilder content = new StringBuilder();

        for (var r = Math.max(0, row-1); r <= Math.min(row + 1, charArray.length -1); r++) {
            for (var c = Math.max(0, startCol - 1); c <= Math.min(endCol + 1, charArray[0].length - 1); c++) {
                content.append(charArray[r][c]);
            }
        }

        return content.toString();
    }

}
