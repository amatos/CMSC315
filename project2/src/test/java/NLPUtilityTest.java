/*
 Author: Alberth Matos
 CMSC 315, Project 2
 Date: 3 February 2026
 Description: This class implements unit tests for Project 2, based on
 JUnit 5.  Each method defines required givens, replicates the call from
 Main.java to NLPUtility.java, and asserts that the returned value matches
 the expected value.

 Tested methods are:
   - NLPUtility.splitTextIntoTokens()
   - NLPUtility.countFilteredWords()
   - NLPUtility.sortByValueDescending()
   - NLPUtility.getSentiment()
   - NLPUtility.getWordsWithMaxFrequency()

 Values for each unit test were derived from the example text for each task
 in the requirements document.
 */

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NLPUtilityTest {

    /**
     * Given the input and expected output, verify that splitTextIntoTokens()
     * returns the expected result, i.e., matches expectedOutput.
     */
    @Test
    public void testTextIntoTokens() {
        // Given
        String input = "WOW!?!    That .?# is REALLY(reaLLy) amazing!      ";
        String[] expectedOutput = {
            "WOW",
            "That",
            "is",
            "REALLY",
            "reaLLy",
            "amazing",
        };

        // When
        String[] words = NLPUtility.splitTextIntoTokens(input);

        // Then
        Assertions.assertArrayEquals(expectedOutput, words);
        System.out.println("Test of textIntoTokens()");
        System.out.println("Input string: " + input);
        System.out.println(
            "Expected output: " + Arrays.toString(expectedOutput)
        );
        System.out.println("Actual output  : " + Arrays.toString(words));
        System.out.println();
    }

    /**
     * Given the input, verify that countFilteredWords() returns a TreeMap
     * containing each non-stop word, and its frequency, sorted alphabetically.
     * stopWords is borrowed from its definition in Main.java
     */
    @Test
    public void testCountFilteredWords() {
        // Given
        String[] words = {
            "i",
            "love",
            "a",
            "good",
            "BOOK",
            "and",
            "LOVE",
            "sad",
            "BooK",
            "book",
        };
        Set<String> stopWords = new HashSet<>(
            Arrays.asList(
                "the",
                "is",
                "in",
                "at",
                "of",
                "and",
                "a",
                "to",
                "it",
                "or",
                "was",
                "so"
            )
        );
        TreeMap<String, Integer> expectedOutput = new TreeMap<>(
            Map.of("book", 3, "good", 1, "i", 1, "love", 2, "sad", 1)
        );

        // When
        Map<String, Integer> mapSortedByKey = NLPUtility.countFilteredWords(
            words,
            stopWords
        );

        // Then
        Assertions.assertEquals(expectedOutput, mapSortedByKey);
        System.out.println("Test of countFilteredWords()");
        System.out.println("Input words: " + Arrays.toString(words));
        System.out.println("Input stop words: " + stopWords);
        System.out.println("Expected output: " + expectedOutput);
        System.out.println("Actual output  : " + mapSortedByKey);
        System.out.println();
    }

    /**
     * Given the input, verify that the returned LinkedHashMap contains
     * the words sorted by their frequency in descending order.
     */
    @Test
    public void testSortByValueDescending() {
        // Given
        Map<String, Integer> map = Map.of(
            "book",
            3,
            "good",
            1,
            "i",
            1,
            "love",
            2,
            "sad",
            1
        );
        LinkedHashMap<String, Integer> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put("book", 3);
        expectedOutput.put("love", 2);
        expectedOutput.put("good", 1);
        expectedOutput.put("i", 1);
        expectedOutput.put("sad", 1);

        // When
        Map<String, Integer> mapSortedByValue =
            NLPUtility.sortByValueDescending(map);

        // Then
        Assertions.assertEquals(expectedOutput, mapSortedByValue);
        System.out.println("Test of testSortByValueDescending()");
        System.out.println("Input map: " + map);
        System.out.println("Expected output: " + expectedOutput);
        System.out.println("Actual output  : " + mapSortedByValue);
        System.out.println();
    }

    /**
     * Given the input, verify that the returned string contains the
     * correct sentiment summary in the format: "Positive: X, Negative: Y"
     */
    @Test
    public void testGetSentiment() {
        // Given
        Map<String, Integer> map = Map.of(
            "book",
            3,
            "good",
            1,
            "i",
            1,
            "love",
            2,
            "sad",
            1
        );
        Set<String> positiveWords = new HashSet<>(
            Arrays.asList("good", "great", "happy", "love", "like")
        );
        Set<String> negativeWords = new HashSet<>(
            Arrays.asList("bad", "terrible", "horrible", "sad", "hate")
        );

        // When
        String sentiment = NLPUtility.getSentiment(
            map,
            positiveWords,
            negativeWords
        );

        // Then
        Assertions.assertEquals("Positive: 3, Negative: 1", sentiment);
        System.out.println("Test of getSentiment()");
        System.out.println("Input map: " + map);
        System.out.println("Input positive words: " + positiveWords);
        System.out.println("Input negative words: " + negativeWords);
        System.out.println("Expected output: Positive: 3, Negative: 1");
        System.out.println("Actual output  : " + sentiment);
        System.out.println();
    }

    /**
     * Given the input, verify that the returned map contains a sorted list
     * of words with the correct maximum frequency.
     */
    @Test
    public void testGetWordsWithMaxFrequency() {
        // Given
        Map<String, Integer> map = Map.of(
            "good",
            1,
            "i",
            1,
            "love",
            3,
            "book",
            3,
            "sad",
            1
        );
        LinkedHashMap<String, Object> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put("words", List.of("book", "love"));
        expectedOutput.put("frequency", 3);

        // When
        Map<String, Object> mostFrequent = NLPUtility.getWordsWithMaxFrequency(
            map
        );
        // Then

        Assertions.assertEquals(expectedOutput, mostFrequent);
        System.out.println("Test of getWordsWithMaxFrequency()");
        System.out.println("Input map: " + map);
        System.out.println("Expected output: " + expectedOutput);
        System.out.println("Actual output  : " + mostFrequent);
        System.out.println();
    }
}
