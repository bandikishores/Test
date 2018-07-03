package com.interview;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class OlaSolutionSubString {

    public static void main(String[] args) {
        System.out.println(getMaxOccurrences("ababab", 2, 3, 4));
    }

    static int getMaxOccurrences(String s, int minLength, int maxLength, int maxUnique) {
        if (s == null || s.length() < 2 || minLength < 2 || maxLength >= s.length() || maxUnique < 2
                || maxUnique > 26) {
            return 0;
        }
        int count = 0;
        for (int i = minLength; i <= maxLength; i++) {
            count = Math.max(getSubStringOccurencesWithLength(s, i, maxUnique), count);
        }
        return count;
    }


    private static int getSubStringOccurencesWithLength(String s, int length, int maxUnique) {
        Queue<Character> previousAlphabet = new LinkedList<>();
        Map<Character, Integer> characterCount = new HashMap<>();
        HashMap<String, Integer> totalOccurences = new HashMap<>();
        Integer maxUniqueSoFar = 0;
        for (int i = 0; i < length; i++) {
            Character charAt = s.charAt(i);
            previousAlphabet.add(charAt);
            if (characterCount.containsKey(charAt)) {
                characterCount.put(charAt, characterCount.get(charAt) + 1);
            } else {
                maxUniqueSoFar++;
                characterCount.put(charAt, 1);
            }
        }
        if (maxUniqueSoFar <= maxUnique && maxUniqueSoFar > 1) {
            totalOccurences.put(s.substring(0, length), 1);
        }
        for (int i = length; i < s.length() - length + 1; i++) {
            Character charAt = s.charAt(i);
            Character character = previousAlphabet.poll();
            previousAlphabet.add(charAt);
            if (characterCount.get(character) > 1) {
                characterCount.put(character, characterCount.get(character) - 1);
            } else {
                maxUniqueSoFar--;
                characterCount.remove(character);
            }

            if (characterCount.containsKey(charAt)) {
                characterCount.put(charAt, characterCount.get(charAt) + 1);
            } else {
                maxUniqueSoFar++;
                characterCount.put(charAt, 1);
            }

            if (maxUniqueSoFar <= maxUnique && maxUniqueSoFar > 1) {
                String substring = s.substring(i, i + length);
                if (totalOccurences.containsKey(substring)) {
                    totalOccurences.put(substring, totalOccurences.get(substring) + 1);
                } else {
                    totalOccurences.put(substring, 1);
                }
            }
        }

        Optional<Integer> max = totalOccurences.values().stream().max(Math::max);
        if (max.isPresent()) {
            return max.get();
        } else {
            return 0;
        }
    }

}
