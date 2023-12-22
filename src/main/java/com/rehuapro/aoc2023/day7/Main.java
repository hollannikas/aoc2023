package com.rehuapro.aoc2023.day7;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 7.1: " + new Day7_1().solve(args[0]));
            System.out.println("Day 7.2: " + new Day7_2().solve(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
