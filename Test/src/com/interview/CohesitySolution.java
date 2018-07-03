package com.interview;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CohesitySolution {

    public static void smallestSubString(String[] args) throws IOException {
        /**
         * aaccbbacaab abcbddde
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        String S = br.readLine();

        int out_ = SmallestSubString(S);
        System.out.println(out_);

        wr.close();
        br.close();
    }


    static int SmallestSubString(String S) {
        Set<Character> distinctCharacters = new HashSet<>();
        for (int i = 0; i < S.length(); i++) {
            distinctCharacters.add(S.charAt(i));
        }
        Map<Character, Integer> characterOccurence = new HashMap<>();
        int totalMatchesSoFar = 0;
        int minLength = -1;
        int startIndex = 0;
        for (int i = 0; i < S.length(); i++) {
            if (characterOccurence.containsKey(S.charAt(i))) {
                if (S.charAt(startIndex) == S.charAt(i)) {
                    startIndex++;
                } else {
                    characterOccurence.put(S.charAt(i), characterOccurence.get(S.charAt(i)) + 1);
                }
            } else {
                totalMatchesSoFar++;
                characterOccurence.put(S.charAt(i), 1);
            }
            if (totalMatchesSoFar == distinctCharacters.size()) {
                if (minLength > -1) {
                    while (characterOccurence.containsKey(S.charAt(startIndex))) {
                        if (characterOccurence.get(S.charAt(startIndex)) == 1) {
                            break;
                        } else {
                            characterOccurence.put(S.charAt(startIndex),
                                    characterOccurence.get(S.charAt(startIndex)) - 1);
                            startIndex++;
                        }
                    }
                }

                if (minLength == -1) {
                    minLength = i - startIndex + 1;
                } else if ((i - startIndex + 1) < minLength) {
                    minLength = i - startIndex + 1;
                }
            }
        }
        return minLength;
    }

    public static void main(String[] args) {
        /*
        This is sample tests\b \nThis is \bnew line
        This is also a new line
         */
        // Scanner
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextLine()) {
                String line = s.nextLine(); // Reading input from STDIN
                if (line == null || line.length() == 0) {
                    break;
                } else {
                    StringBuilder sb = new StringBuilder(line);
                    for (int i = 0; i < sb.length(); i++) {
                        /*if ((i + 2) < line.length() && line.charAt(i+1) == '\\' && line.charAt(i+2) == 'b') {
                            i+=2;
                            continue;
                        } else if (line.charAt(i) == '\\' && (i + 1) < line.length()) {
                            if (line.charAt(i + 1) == 'n') {
                                // sb.append('\n');
                                System.out.println(sb.toString().trim());
                                sb = new StringBuilder("");
                            }
                            i++;
                        } else {
                            sb.append(line.charAt(i));
                        }*/
                         if (sb.charAt(i) == '\\' && (i + 1) < sb.length()) {
                            if (sb.charAt(i + 1) == 'b') {
                                sb.deleteCharAt(i);
                                sb.deleteCharAt(i);
                                if (i - 1 >= 0) {
                                    sb.deleteCharAt(i - 1);
                                    i--;
                                }
                                i -= 2;
                            } else if (sb.charAt(i + 1) == 'n') {
                                sb.deleteCharAt(i);
                                sb.deleteCharAt(i);
                                sb.insert(i, '\n');
                                i -= 2;
                            }
                        }
                        if (i < 0) {
                            i = 0;
                        }
                    }
                    System.out.println(sb.toString());
                }
            }
        }


    }

}
