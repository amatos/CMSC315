/*
 Author: Alberth Matos
 CMSC 315, Project 2
 Date: 3 February 2026
 Description:
  In this project, you'll implement a set of basic Natural Language
  Processing (NLP) utility methods to analyze a paragraph of text entered by
  the user.

  Task 1. public static String[] splitTextIntoTokens(String text)
  Splits the text into individual words, treating consecutive whitespace or
  punctuation characters as a single delimiter.
  NOTE: The regular expression given in the Pearson textbook in
  section 21.6 is incorrect. The plus sign should follow the character class
  to match 1 or more white space or punctuation characters.
  [\\s\\p{P}]+

  Task 2. public static TreeMap<String, Integer> countFilteredWords(
    String[] words, Set<String> stopWords)
  Counts the frequency of non-stop words in the given array of words,
  ignoring case. Returns a TreeMap sorted alphabetically by key (i.e. word).

  Task 3. public static LinkedHashMap<String, Integer> sortByValueDescending(
    Map<String, Integer> map)
  Returns a LinkedHashMap sorted by frequency in descending order. For ties,
  maintains the original order of keys as they appear in the map.
  Algorithm:
      1. Convert the word map entries to a list for sorting
      2. Sort the list of entries in descending order based on value
         (frequency)
      3. Create a LinkedHashMap and insert the sorted entries to maintain
         their order.

  Task 4. public static String getSentiment(Map<String, Integer> wordMap,
    Set<String> positiveWords, Set<String> negativeWords)
  Sums the total frequencies of words in the corresponding positive and
  negative word sets. Returns a summary string in the format
  "Positive: X, Negative: Y".

  Task 5. public static Map<String, Object> getWordsWithMaxFrequency(
    Map<String, Integer> wordMap)
  Returns a map containing an alphabetically sorted list of words that
  appear most frequently in the given word map, along with the
  corresponding frequency.
  Algorithm:
      - Finds the maximum frequency value in the input map
      - Collect a list of all words that occur with that frequency
      - Sorts the list alphabetically
      - Returns a new map with two entries having the following keys:
        - "words": a list of most frequent words, sorted alphabetically
        - "frequency": the maximum frequency as an integer
  Note: The returned map contains two entries with String keys: "words" and
  "frequency".
      - The value associated with "words" is a List<String> containing the
      most frequently occurring words.
      - The value for "frequency" is an Integer representing the highest
      frequency found.
  Because the values are of different types (List<String> and Integer), the
  method returns a map of type Map<String, Object>.
 */

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
    /*
     Check if the provided text is empty.  If so, return a 0-entry array.
     If it is not empty, return the array split by whitespace or
     punctuation.
     N.B., the contents of the else block can all go outside the
     if-else structure, but I believe that including it in the else
     block makes it more clear that it is one or the other.  Details of
     the regex appear in the comment inside the else block.
    */
    if (text.isEmpty()) {
      // We explicitly want to return a new, empty array.
      return new String[0];
    }

    /*
     Assuming that text was not empty, we return a regex splitting by
     whitespace and punctuation.
    */
    return text.split("[\\s\\p{Punct}]+");
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
    // Initialize a new TreeMap, wordFrequencyMap to store the words and
    // frequency of occurrence.
    TreeMap<String, Integer> wordFrequencyMap = new TreeMap<>();

    /*
     Loop through the words array, normalize each word to lower-case,
     and add them to wordFrequencyMap if they are not in stopWords.  If they
     are already in wordFrequencyMap, increment their frequency count by one.
     If the word is in stopWords, skip it.
     If words is an empty array, then the code will skip this loop (as there
     is no word in an empty words), and jump down returning an empty
     wordFrequencyMap.
    */
    for (String word : words) {

      // Normalize word to lower-case.
      String normalizedWord = word.toLowerCase();

      // Check if stopWords does NOT contain normalizedWord.
      if (!stopWords.contains(normalizedWord)) {
        // Add normalizedWord to wordFrequencyMap, or increment its count by 1
        // N.B., the default value is set to 0 in .getOrDefault().
        wordFrequencyMap.put(normalizedWord,
          wordFrequencyMap.getOrDefault(normalizedWord, 0) + 1);
      }
    }

    return wordFrequencyMap;
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
    // Initialize new LinkedHashMap to store the sorted entries
    LinkedHashMap<String, Integer> sortedEntriesMap = new LinkedHashMap<>();

    // If map is empty, return empty sortedEntriesMap.
    if (map.isEmpty()) {
      return sortedEntriesMap;
    }

    /*
     Stream map.entrySet(), sort by reversed (that is, in descending order)
     value, guaranteeing order with .forEachOrdered, and put each entry into
     sortedEntriesMap.
    */
    map.entrySet()
      .stream()
      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
      .forEachOrdered(entry -> sortedEntriesMap
        .put(entry.getKey(), entry.getValue()));

    // And return sortedEntriesMap.
    return sortedEntriesMap;
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
    // Initialize counters for positive and negative words.
    int positiveWordCount = 0;
    int negativeWordCount = 0;

    // If wordMap is empty, return "Positive: 0, Negative: 0"
    if (wordMap.isEmpty()) {
      return "Positive: 0, Negative: 0";
    }

    // Otherwise, loop through wordMap.entrySet(), get the word and
    // frequency for each entry, and count the number of positive and
    // negative words.
    for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
      String word = entry.getKey();
      int frequency = entry.getValue();

      // If positiveWords contains word, increment positiveWordCount by the
      // word's frequency.
      if (positiveWords.contains(word)) {
        positiveWordCount += frequency;
      }

      // If negativeWords contains word, increment negativeWordCount by the
      // word's frequency.
      if (negativeWords.contains(word)) {
        negativeWordCount += frequency;
      }
    }

    // Return the string containing the positive and negative word counts.
    return "Positive: " + positiveWordCount + ", Negative: "
      + negativeWordCount;
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
    // Initialize wordsWithMaxFrequencyMap as a linked hash map
    Map<String, Object> wordsWithMaxFrequencyMap = new LinkedHashMap<>();

    // If wordMap is empty, return an empty ArrayList and a frequency of 0.
    if (wordMap.isEmpty()) {
      wordsWithMaxFrequencyMap.put("words", new ArrayList<String>());
      wordsWithMaxFrequencyMap.put("frequency", 0);
      return wordsWithMaxFrequencyMap;
    }

    // Find the maximum frequency value in wordMap.
    int maxFrequency = Collections.max(wordMap.values());

    /*
     Find all words with the maximum frequency, and populate them into
     a new ArrayList, maxFrequencyWords, by looping through wordMap,
     finding any entry whose that is equal to maxFrequency, and
     adding its to maxFrequencyWords.
    */
    List<String> maxFrequencyWords = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
      if (entry.getValue() == maxFrequency) {
        maxFrequencyWords.add(entry.getKey());
      }
    }

    // Sort maxFrequencyWords alphabetically.
    Collections.sort(maxFrequencyWords);

    // Populate wordsWithMaxFrequencyMap with maxFrequencyWords and the
    // corresponding maxFrequency.
    wordsWithMaxFrequencyMap.put("words", maxFrequencyWords);
    wordsWithMaxFrequencyMap.put("frequency", maxFrequency);

    return wordsWithMaxFrequencyMap;
  }

}
