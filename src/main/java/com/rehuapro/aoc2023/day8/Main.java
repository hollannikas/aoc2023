package com.rehuapro.aoc2023.day8;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 8.1: " + new Day8_1().solve(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
