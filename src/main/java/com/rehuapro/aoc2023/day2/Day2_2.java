package com.rehuapro.aoc2023.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day2_2 {
    private record Turn(int red, int green, int blue) {
        public static Turn fromString(String s) {
            var sets = s.split(", ");
            var myRed = 0;
            var myBlue = 0;
            var myGreen = 0;

            for (String setString : sets) {

                var set = setString.split(" ");
                var amount = Integer.parseInt(set[0]);
                var color = set[1];
                if (color.equals("red"))
                    myRed = amount;
                if (color.equals("green"))
                    myGreen = amount;
                if (color.equals("blue"))
                    myBlue = amount;
            }

            return new Turn(myRed, myGreen, myBlue);
        }

    }
    private record Game(int id, List<Turn> turns) {
        public int power() {
            var maxBlue = 1;
            var maxGreen = 1;
            var maxRed = 1;
            for (Turn turn : turns) {
                if (turn.blue > maxBlue)
                    maxBlue = turn.blue;
                if (turn.green > maxGreen)
                    maxGreen = turn.green;
                if (turn.red > maxRed)
                    maxRed = turn.red;
            }

            return maxBlue * maxGreen * maxRed;
        }
    }

    public Integer sumPower(String calibrationFileName) throws URISyntaxException {
        var path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(calibrationFileName)).toURI());
        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(this::toGame)
                    .map(Game::power)
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Game toGame(String s) {
        var idAndTurns = s.split(": ");
        var id = Integer.parseInt(idAndTurns[0].split(" ")[1]);

        var turnStrings = idAndTurns[1].split("; ");
        var turns = Arrays.stream(turnStrings).map(Turn::fromString).toList();

        return new Game(id, turns);
    }

}
