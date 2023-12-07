package com.rehuapro.aoc2023.day6;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 6.1: " + new Day6_1().solve(args[0]));
            System.out.println("Day 6.2: " + new Day6_2().solve(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
