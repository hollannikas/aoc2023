package com.rehuapro.aoc2023.day3;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 3.1: " + new Day3_1().sumPartNumbers(args[0]));
            System.out.println("Day 3.2: " + new Day3_2().sumGearRatios(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
