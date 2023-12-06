package com.rehuapro.aoc2023.day5;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 5.1: " + new Day5_1().solve(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
