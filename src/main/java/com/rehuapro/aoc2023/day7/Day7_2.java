package com.rehuapro.aoc2023.day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7_2 {
    private enum HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }
    private static class Hand {
        private final String cards;
        private final long bid;
        private final Set<Character> cardSet = new HashSet<>();

        public Hand(String cards, long bid) {
            this.cards = cards;
            this.bid = bid;
            cards.chars().filter(c -> c != 'J').forEach(c -> cardSet.add((char) c));
        }

        public long getBid() {
            return bid;
        }

        private HandType getType() {
            return switch (cardSet.size()) {
                case 5 -> HandType.HIGH_CARD;
                case 4 -> HandType.ONE_PAIR;
                case 3 -> isNOfAKind(3) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
                case 2 -> isNOfAKind(4) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
                case 0, 1 -> HandType.FIVE_OF_A_KIND;
                default -> throw new RuntimeException("Invalid hand: " + cards + " " + cardSet.size() + " " + cardSet);
            };
        }

        private boolean isNOfAKind(final int n) {
            final int jokers = (int) cards.chars().filter(c -> c == 'J').count();
            return cards.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                    .values().stream()
                    .anyMatch(count -> count + jokers >= n);
        }
    }

    private final Comparator<Hand> handComparator = new Comparator<>() {

        private static final List<String> CARD_VALUES = List.of(
                "J", "2", "3", "4", "5", "6", "7", "8", "9",
                "T", "Q", "K", "A"
        );

        @Override
        public int compare(Hand hand1, Hand hand2) {
            var handType = getHandType(hand1);
            if (handType != getHandType(hand2)) {
                return handType.compareTo(getHandType(hand2));
            }
            for (int i = 0; i < 5; i++) {
                String cards2 = hand2.cards;
                String cards1 = hand1.cards;
                if (CARD_VALUES.indexOf(cards2.substring(i, i + 1)) != CARD_VALUES.indexOf(cards1.substring(i, i + 1))) {
                    return CARD_VALUES.indexOf(cards2.substring(i, i + 1)) - CARD_VALUES.indexOf(cards1.substring(i, i + 1));
                }
            }
            return 0;
        }

        private HandType getHandType(Hand hand) {
            return hand.getType();
        }
    };

    public Long solve(String fileName) throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
        try (var lines = Files.lines(path)) {
            var hands = getHands(lines.toList()).stream()
                    .sorted(handComparator.reversed())
                    .toList();

            return IntStream.range(0, hands.size())
                    .peek(i -> System.out.println(hands.get(i).cards + " " + hands.get(i).getType()))
                    .mapToLong(i -> hands.get(i).getBid() * (i+1))
                    .reduce(0L, Long::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Hand> getHands(List<String> list) {
        return list.stream().map(line -> {
            var cards = line.substring(0,5);
            var bid = Long.parseLong(line.substring(6));
            return new Hand(String.join(" ", cards), bid);
        }).toList();
    }
}

