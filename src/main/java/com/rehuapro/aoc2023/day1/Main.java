package com.rehuapro.aoc2023.day1;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            var sum = new Day1().sumCalibrationValues(args[0]);
            System.out.println(sum);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
