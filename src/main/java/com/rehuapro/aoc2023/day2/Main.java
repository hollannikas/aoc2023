package com.rehuapro.aoc2023.day2;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        String inputFile = args[0];
        try {
            System.out.println("Day 2.1: " + new Day2_1().sumValidGameIDs(inputFile));
            System.out.println("Day 2.2: " + new Day2_2().sumPower(inputFile));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
