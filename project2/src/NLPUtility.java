import java.util.*;

public class NLPUtility {

    /**
     * Splits the given text into word tokens using one or more whitespace
     * or punctuation characters as delimiters.
     *
     * @param text the input string to be tokenized
     * @return an array of word tokens, excluding punctuation and whitespace
     */
    public static String[] splitTextIntoTokens(String text) {
        // TODO
        return null;
    }

    /**
     * Counts the frequency of words in the given array, excluding those present in
     * the specified set of stop words.
     * The comparison is case-insensitive, and results are stored in a
     * {@link TreeMap} sorted alphabetically by word.
     *
     * @param words     An array of tokenized words to analyze.
     * @param stopWords A set of words to exclude from the frequency count (e.g.,
     *                  common stop words like "the", "and").
     * @return A {@link TreeMap} mapping each non-stop word to its frequency, sorted
     *         alphabetically.
     */
    public static TreeMap<String, Integer> countFilteredWords(String[] words, Set<String> stopWords) {
        // TODO
        return null;
    }

    /**
     * Sorts the entries of a map by their values in descending order.
     * The result is returned as a {@link LinkedHashMap} to preserve the order of
     * sorted entries.
     *
     * @param map A map containing keys and integer values to be sorted by value.
     * @return A {@link LinkedHashMap} containing the same entries as the input map,
     *         sorted in descending order by value.
     */
    public static LinkedHashMap<String, Integer> sortByValueDescending(Map<String, Integer> map) {
        // TODO
        return null;
    }

    /**
     * Performs sentiment analysis by scanning the word-frequency map.
     * Adds up the total frequency of all words found in the predefined
     * positive and negative word sets.
     *
     * @param wordMap A map of words and their frequencies.
     * @return A summary string in the format: "Positive: X, Negative: Y"
     *         where X and Y are the total counts of positive and negative words.
     */
    public static String getSentiment(Map<String, Integer> wordMap, Set<String> positiveWords,
            Set<String> negativeWords) {
        // TODO
        return null;
    }

    /**
     * Finds the words with the highest frequency in the given map and returns a map
     * containing a sorted word list along with the maximum frequency value.
     *
     * @param wordMap A map of words and their corresponding frequencies.
     * @return A map containing:
     *         - "words": A list of words with the highest frequency, sorted
     *         alphabetically.
     *         - "frequency": The highest frequency value.
     */
    public static Map<String, Object> getWordsWithMaxFrequency(Map<String, Integer> wordMap) {
        // TODO
        return null;
    }

}
