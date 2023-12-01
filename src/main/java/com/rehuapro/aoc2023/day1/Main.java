package com.rehuapro.aoc2023.day1;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 1.1: " + new Day1_1().sumCalibrationValues(args[0]));
            System.out.println("Day 1.2: " + new Day1_2().sumCalibrationValues(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
