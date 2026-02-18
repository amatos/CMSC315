/*
 Author: Alberth Matos
 CMSC 315, Project 3
 Date: 17 February 2026
 Description: This class is the provided class from the project starter kit.
 I have made changes for tasks 1 through 5:
 Task 1. Added public CompleteBinaryTree(String levelOrderValues)
 Task 2. Update private void preorder() to output the tree using indentation
         to indicate the tree's structure.
 Task 3. Updated public boolean isMaxHeap() to call my recursive helper class,
         private boolean isMaxHeap() to determine whether the tree is a
         max heap tree.
 Task 4. Updated public boolean isBinarySearchTree() to use my recursive
         helper class, private boolean isBinarySearchTree(), to determine
         whether the tree is a valid binary search tree.
 Task 5. Updated public ArrayList<Integer> inorderList() to use my helper
         class, private void inorderList(), to create an in-order list of
         elements within the tree.
 */

import java.util.ArrayList;

public class CompleteBinaryTree {

    protected TreeNode root;

    /**
     * A static nested class representing a node in the binary tree.
     * Contains an integer value and references to left and right children.
     */
    public static class TreeNode {

        protected Integer value;
        protected TreeNode left;
        protected TreeNode right;

        /**
         * Constructs a TreeNode with a given integer value.
         *
         * @param value the value to store in the node
         */
        public TreeNode(Integer value) {
            this.value = value;
        }
    }

    /**
     * Constructs a CompleteBinaryTree from an array of Integer values that
     * represent a complete binary tree in level-order.
     *
     * If the input array is not null and contains elements, it initializes the
     * root of the tree by calling the recursive method `makeNode`, starting from
     * index 0.
     *
     * @param values an array of Integer values representing the binary tree
     *               in level-order
     * @throws InvalidTreeException if the array contains a null element
     *                              where a node is expected
     */
    public CompleteBinaryTree(Integer[] values) throws InvalidTreeException {
        if (values != null && values.length > 0) {
            root = makeNode(values, 0);
        }
    }

    /**
     * Constructs a CompleteBinaryTree from a whitespace-separated string of
     * integers representing the tree in level-order.
     *
     * The string is parsed into integer tokens and used to recursively build the
     * tree starting from index 0 via {@code makeNode}.
     *
     * If the input is null or contains only whitespace, the tree is considered
     * empty ({@code root} is null). If any token is not a valid integer, an
     * {@code InvalidTreeException} is thrown.
     *
     * @param levelOrderValues the level-order representation of the tree as a
     * string
     * @throws InvalidTreeException if any token is not a valid integer
     */
    public CompleteBinaryTree(String levelOrderValues)
        throws InvalidTreeException {
        /* Task 1. Create a second CompleteBinaryTree constructor.
         * The constructor should create a complete binary tree from the parameter
         * string:
         * 1. Check Input Validity: If the input string is null or contains only
         * whitespace, simply return to keep the tree empty (root is null).
         * 2. Trim and Split: Trim the string to remove leading and trailing
         *  whitespace, then split the string into tokens using whitespace as
         * the delimiter. A sequence of whitespace should be treated as a
         * single delimiter.
         * 3. Parse Tokens: Convert each token into an integer. If any token is
         * not a valid integer, throw an InvalidTreeException with an appropriate
         * message. This step should produce an Integer array.
         * 4. Initialize the Tree: Use the parsed integers to recursively build
         * the tree by calling the makeNode method, starting from index 0.
         */

        // Check if levelOrderValues is either null or contains only whitespace
        if (levelOrderValues == null || levelOrderValues.trim().isEmpty()) {
            // If either is true, set root to null and return.
            root = null;
            return;
        }
        // Otherwise, trim and split the string.
        String[] values = levelOrderValues.trim().split("\\s+");

        try {
            // Parse the tokens into an Integer array.
            Integer[] intValues = new Integer[values.length];
            for (int i = 0; i < values.length; i++) {
                intValues[i] = Integer.parseInt(values[i]);
            }
            // Use the parsed integers to build the tree.
            root = makeNode(intValues, 0);
        } catch (NumberFormatException e) {
            // If any token is not an integer, throw an InvalidTreeException.
            throw new InvalidTreeException(
                "All tokens must be valid integers."
            );
        }
    }

    /**
     * Recursively constructs a complete binary tree from an array.
     * The array is assumed to represent a complete binary tree in level-order
     * traversal.
     *
     * For each index `i` in the array:
     * - The element at index `i` represents the node.
     * - The left child of the node is at index `2*i + 1`.
     * - The right child of the node is at index `2*i + 2`.
     *
     * This method constructs the tree in a level-by-level manner.
     *
     * @param values array of integer values representing the tree in
     *               level-order
     * @param index  current index in the array that corresponds to the
     *               current node
     * @return TreeNode at the current index, with left and right children
     *         recursively constructed
     * @throws InvalidTreeException if a node value is null or invalid
     */
    protected TreeNode makeNode(Integer[] values, int index)
        throws InvalidTreeException {
        if (index >= values.length) {
            return null;
        }
        if (values[index] == null) {
            throw new InvalidTreeException("Node element must not be null");
        }

        TreeNode node = new TreeNode(values[index]);
        node.left = makeNode(values, 2 * index + 1);
        node.right = makeNode(values, 2 * index + 2);

        return node;
    }

    /**
     * Performs a preorder traversal of the tree.
     */
    public void preorder() {
        System.out.println("Preorder: ");
        preorder(root, 0);
    }

    /**
     * Recursive helper method for preorder traversal.
     *
     * @param root the current subtree root
     */
    private void preorder(TreeNode root, int level) {
        // Task 2. Evolve the preorder method to use indentation to reflect
        // the tree structure.

        // If the root is null, return immediately.
        if (root == null) {
            return;
        }

        /*
       Per the example in Task two, each indentation level should be 4
       spaces.  We use this in combination with the subsequent println()
       to print the correct indentation.  The first (that is, 0th) level
       is NOT indented.
      */
        int spacesPerLevel = 4 * level;
        System.out.println(" ".repeat(spacesPerLevel) + root.value + " ");

        // If root.left contains a value and is not null, recursively call
        // preorder(root, level) with root.left, and the current level + 1.
        if (root.left != null) {
            preorder(root.left, level + 1);
        }

        // If root.right contains a value and is not null, recursively call
        // preorder(root, level) with root.right, and the current level + 1.
        if (root.right != null) {
            preorder(root.right, level + 1);
        }
    }

    /**
     * Checks whether the binary tree satisfies the max-heap property.
     *
     * @return true if the tree is a max-heap, false otherwise
     */
    public boolean isMaxHeap() {
        // Task 3. Check if a complete binary tree is a max-heap.
        // Call recursive helper method, isMaxHeap(root, level)
        return isMaxHeap(root, 0);
    }

    private boolean isMaxHeap(TreeNode root, int level) {
        // If root is null, return true, since by definition, a null tree is
        // max-heap, since it has no children.
        if (root == null) {
            return true;
        }

        // If both root.left and root.right are null, then this is a leaf node,
        // and we can stop checking.
        if (root.left == null && root.right == null) {
            return true;
        }

        // If either root.left or root.right are NOT null, check whether the tree
        // satisfies the max-heap property.
        if (root.left != null || root.right != null) {
            /*
       This could all be consolidated into a single large return statement,
       but, for clarity, we'll break it out into the four individual
       checks:
         1. Is root greater than or equal to the left value?
         2. Is root greater than or equal to the right value?
         3. Is the left subtree a max-heap?  Determine via recursive call to
            isMaxHeap(root.left, level + 1).
         4. Is the right subtree a max-heap?  Determine via recursive call to
            isMaxHeap(root.right, level + 1).
       Return the combined value of all four checks anded together.
       Initial values of "true" are defined for all four checks, for
       rootLeftIsMaxHeap and rootRightIsMaxHeap.  We check to see if root.left
       or root.right are null, and, in case they are, we don't want the test
       to fail with a null pointer exception, and, since a null value is, by
       definition, true, we explicitly define true here.
      */
            boolean rootValueGreaterThanLeft = true;
            boolean rootValueGreaterThanRight = true;
            boolean rootLeftIsMaxHeap = true;
            boolean rootRightIsMaxHeap = true;
            if (root.left != null) {
                rootValueGreaterThanLeft = root.value >= root.left.value;
                rootLeftIsMaxHeap = isMaxHeap(root.left, level + 1);
            }
            if (root.right != null) {
                rootValueGreaterThanRight = root.value >= root.right.value;
                rootRightIsMaxHeap = isMaxHeap(root.right, level + 1);
            }
            return (
                rootValueGreaterThanLeft &&
                rootValueGreaterThanRight &&
                rootLeftIsMaxHeap &&
                rootRightIsMaxHeap
            );
        } else {
            // If the above check fails, then the tree is not a max heap.
            return false;
        }
    }

    /**
     * Checks whether the tree is a valid binary search tree (BST).
     * A BST is valid if, for every node:
     * - All nodes in the left subtree are strictly less than the node's
     *   value.
     * - All nodes in the right subtree are strictly greater than the node's
     *   value.
     *
     * @return true if the tree satisfies BST properties, false otherwise
     */
    public boolean isBinarySearchTree() {
        // Task 4
        return isBinarySearchTree(root, null, null);
    }

    /**
     * Recursive helper method to check BST property using range limits.
     * At each node, ensures:
     * - Node's value is strictly greater than the min bound.
     * - Node's value is strictly less than the max bound.
     * Recursively checks left and right subtrees with updated bounds.
     *
     * @param node current node in the tree
     * @param min lower bound (exclusive) for the node's value
     * @param max upper bound (exclusive) for the node's value
     * @return true if the subtree rooted at the current node is a valid BST,
     * false otherwise
     */
    private boolean isBinarySearchTree(
        TreeNode node,
        Integer min,
        Integer max
    ) {
        // An empty root is valid, so we are done at this node.
        if (node == null) {
            return true;
        }

        // Check current node against bounds
        // If min has a value AND node.value is less than or equal to min, return
        // false.
        if (min != null && node.value <= min) {
            return false;
        }
        // If max has a value AND node.value is greater than or equal to max, return
        // false.
        if (max != null && node.value >= max) {
            return false;
        }

        // Recursively validate left and right subtrees
        return (
            isBinarySearchTree(node.left, min, node.value) &&
            isBinarySearchTree(node.right, node.value, max)
        );
    }

    /**
     * Returns an ArrayList containing the values of the tree nodes in in-order
     * traversal, visiting nodes in the following order:
     * - Left subtree
     * - Current node
     * - Right subtree
     *
     * @return an ArrayList containing the values of the nodes in in-order
     * traversal
     */
    public ArrayList<Integer> inorderList() {
        // Task 5. Create an in-order list of values.
        ArrayList<Integer> inorderArray = new ArrayList<Integer>();
        // Call the helper function to perform the list population.
        inorderList(root, inorderArray);

        return inorderArray;
    }

    private void inorderList(TreeNode node, ArrayList<Integer> list) {
        // If the node is null, just return, since there won't be any values
        // to process.
        if (node == null) {
            return;
        }

        // Recursively descend the nodes on the left, populate the value, and then
        // recursively descend on the right.
        inorderList(node.left, list);
        list.add(node.value);
        inorderList(node.right, list);
    }
}
