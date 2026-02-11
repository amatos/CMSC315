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

          CompleteBinaryTree tree = new CompleteBinaryTree(treeString);
          tree.preorder();
          System.out.println("Is a max-heap: " + tree.isMaxHeap());
          System.out.println("Is a binary search tree: " + tree.isBinarySearchTree());
          System.out.println("Inorder List: " + tree.inorderList());
        } catch (InvalidTreeException e) {
            System.out.println(e.getMessage());
        }
    }
}
