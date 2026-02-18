/*
 Author: Alberth Matos
 CMSC 315, Project 3
 Date: 17 February 2026
 Description: Unit tests for CompleteBinaryTree
 */

import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class CompleteBinaryTreeTest {

  /*
   To validate methods that print straight to System.out, we need to
   capture System.out.  The following two methods help us to this, but
   require some setup before each test and after each test.  This setup
   Is accomplished in the @BeforeEach and @AfterEach methods immediately
   following.
  */
  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream outContent;

  @BeforeEach
  void setUp() {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void testStringConstructorCreatesTree() throws InvalidTreeException {
    // Verify we build a tree using CompleteBinaryTree(String)
    // Given
    String validTreeInput = "90 70 50 20 40";

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(validTreeInput);

    // Then
    Assertions.assertNotNull(tree);
    Assertions.assertTrue(tree.isMaxHeap());
  }

  @Test
  void testStringConstructorWithExtraWhitespace() throws InvalidTreeException {
    // Verify we build a tree using CompleteBinaryTree(String)
    // Given
    String validTreeExtraSpacesInput = "       90   70        50 20     40";

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(validTreeExtraSpacesInput);

    // Then
    Assertions.assertNotNull(tree);
    Assertions.assertTrue(tree.isMaxHeap());
  }

  @Test
  void testStringConstructorWithInvalidTokenThrowsException() throws InvalidTreeException {
    // Verify that we get an InvalidTreeException when trying to build an
    // Integer tree with a string
    // Given
    String invalidTreeInput = "50 30 abc 99";

    // When
    try {
      CompleteBinaryTree tree = new CompleteBinaryTree(invalidTreeInput);
    } catch (InvalidTreeException e) {
    // Then
      Assertions.assertThrows(InvalidTreeException.class, () -> new CompleteBinaryTree(invalidTreeInput));
    }
  }

  @Test
  void testStringConstructorWithEmptyStringCreatesEmptyTree() throws InvalidTreeException {
    // Verify that we get a valid tree when using an empty string.  An empty
    // string IS a valid tree.
    // Given
    String emptyInput = "";

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(emptyInput);

    // Then
    Assertions.assertNotNull(tree);
    Assertions.assertTrue(tree.isMaxHeap()); // empty tree counts as heap
  }

  @Test
  void testPreorderPrintsIndentedTree() throws InvalidTreeException {
    /*
     Validate that preorder() prints the tree in the expected indentation.
     n.b., the String expectedOutput uses System.lineSeparator() to
     terminate each line of the multi-line string so that it works on both
     Unix, Unix-like, and MacOS (LF character), as well as Windows (CR LF
     characters)
    */
    // Given
    String validTreeInput = "90 70 50 20 40 10 25";
    CompleteBinaryTree tree = new CompleteBinaryTree(validTreeInput);
    String expectedOutput = "Preorder: " + System.lineSeparator() +
      "90 " + System.lineSeparator() +
      "    70 " + System.lineSeparator() +
      "        20 " + System.lineSeparator() +
      "        40 " + System.lineSeparator() +
      "    50 " + System.lineSeparator() +
      "        10 " + System.lineSeparator() +
      "        25";

    // When
    tree.preorder();
    String actualOutput = outContent.toString().trim();

    // Then
    Assertions.assertEquals(expectedOutput, actualOutput);
  }


  @Test
  void testIsMaxHeapReturnsTrueForValidHeap() throws InvalidTreeException {
    // Given
    Integer[] values = {90, 70, 50, 20, 40, 10, 25};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertTrue(tree.isMaxHeap());
  }

  @Test
  void testIsMaxHeapReturnsFalseWhenHeapPropertyViolated() throws InvalidTreeException {
    // Given
    Integer[] values = {40, 35, 17, 22, 19, 20};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertFalse(tree.isMaxHeap());
  }

  @Test
  void testIsMaxHeapOnEmptyNodeTree() throws InvalidTreeException {
    // Given
    Integer[] values = {};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertTrue(tree.isMaxHeap());
  }

  @Test
  void testValidBinarySearchTree() throws InvalidTreeException {
    // Given
    Integer[] values = {10, 5, 15, 2, 7, 12, 20};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertTrue(tree.isBinarySearchTree());
  }

  @Test
  void testInvalidBinarySearchTreeRootViolation() throws InvalidTreeException {
    // Given
    Integer[] values = {20, 18, 15, 10};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertFalse(tree.isBinarySearchTree());
  }

  @Test
  void testSingleNodeTreeIsBST() throws InvalidTreeException {
    // Given
    Integer[] values = {6};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertTrue(tree.isBinarySearchTree());
  }

  @Test
  void testEmptyTreeIsBST() throws InvalidTreeException {
    // Given
    Integer[] values = {};

    // When
    CompleteBinaryTree tree = new CompleteBinaryTree(values);

    // Then
    Assertions.assertTrue(tree.isBinarySearchTree());
  }
}
