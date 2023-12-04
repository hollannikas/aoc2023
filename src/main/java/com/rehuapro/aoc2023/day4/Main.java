package com.rehuapro.aoc2023.day4;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Day 4.1: " + new Day4_1().sumWinningCards(args[0]));
            System.out.println("Day 4.2: " + new Day4_2().sumWinningCards(args[0]));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
