/*
 Author: Alberth Matos
 CMSC 315, Project 3
 Date: 17 February 2026
 Description: This class is the provided class from the project starter kit.
 I have made changes for tasks 1 through 5:
 Task 1. Create a second CompleteBinaryTree constructor.
 Task 2. Evolve the preorder method to use indentation to reflect the tree
         structure.
 Task 3. Check if a complete binary tree is a max-heap.
 Task 4. Check if a complete binary tree is a binary search tree.
 Task 5. Create an in-order list of values.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
          //Integer[] values = { 90, 70, 50, 20, 40 };
          // CompleteBinaryTree tree = new CompleteBinaryTree(values);
          System.out.print("Enter a binary tree: ");
          Scanner input = new Scanner(System.in);
          String treeString = input.nextLine();
          input.close();

          // Task 1. Create a second CompleteBinaryTree constructor.
          CompleteBinaryTree tree = new CompleteBinaryTree(treeString);
          // Task 2. Evolve the preorder method to use indentation to reflect
          //         the tree structure.
          tree.preorder();
          // Task 3. Check if a complete binary tree is a max-heap.
          System.out.println("Is a max-heap: " + tree.isMaxHeap());
          // Task 4. Check if a complete binary tree is a binary search tree.
          System.out.println("Is a binary search tree: " +
            tree.isBinarySearchTree());
          // Task 5. Create an in-order list of values.
          System.out.println("Inorder List: " + tree.inorderList());
        } catch (InvalidTreeException e) {
            System.out.println(e.getMessage());
        }
    }
}
