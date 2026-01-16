/* Chapter 20: Auto-Graded Programming Project 1
 *
 * (Perform set operations on priority queues)
 *
 * Write a program that creates two priority queues for integers. Prompt the
 * user to enter two lines of integers.
 *
 * Read each line of integers as a string and extract the integers from the
 * string and add the integers to the two queues, respectively. Display
 * their union, difference, and intersection. Note that the priority queue
 * can have duplicates. The union of two priority queues may have
 * duplicates. Display all output in increasing order.
 *
 * Sample Run
 *
 * Enter integers for priority queue 1: 1 9 2 30 21 4 1
 * Enter integers for priority queue 2: 12 19 2 10 12 1
 * The union of the two priority queues is
 * 1 1 1 2 2 4 9 10 12 12 19 21 30
 * The difference of the two priority queues is
 * 4 9 21 30
 * The intersection of the two priority queues is
 * 1 1 2
 *
 * For a hint on this program, please see
 * https://liveexample.pearsoncmg.com/javarevel13e.html.
 */

import java.util.PriorityQueue;
import java.util.Scanner;

public class Exercise {

    // Extract integers from String integerLine and add them to
    // PriorityQueue pqueue
    private static void addIntegersToQueue(
        PriorityQueue<Integer> pqueue,
        String integerLine
    ) {
        // Split the line into tokens based on spaces
        String[] tokens = integerLine.split("\\s+");

        // For each token, try to parse it as an integer and add to pqueue
        for (String token : tokens) {
            try {
                int number = Integer.parseInt(token);
                // Add the valid integer to the queue
                pqueue.offer(number);
            } catch (NumberFormatException e) {
                // Ignore non-integers, but let the user know
                System.out.println("Ignoring non-integer string: " + token);
            }
        }
    }

    // Display output PriorityQueue pqueue
    private static void printQueue(PriorityQueue<Integer> pqueue) {
        // Create a copy of the priority queue so that we don't modify the
        // original queue, pqueue
        PriorityQueue<Integer> copy = new PriorityQueue<>(pqueue);
        // Iterate through the queue while it is not empty, and print the
        // elements, while removing the entry from the queue.
        while (!copy.isEmpty()) {
            System.out.print(copy.poll() + " ");
        }
    }

    public static void main(String[] args) {
        // Create a Scanner object for input
        Scanner scanner = new Scanner(System.in);

        // Create priority queues
        PriorityQueue<Integer> queue1 = new PriorityQueue<>();
        PriorityQueue<Integer> queue2 = new PriorityQueue<>();

        // Prompt user for two lines of integers
        System.out.print("Enter integers for priority queue 1: ");
        String integers1 = scanner.nextLine();
        System.out.print("Enter integers for priority queue 2: ");
        String integers2 = scanner.nextLine();

        // Close the scanner
        scanner.close();

        // Read integers into the priority queues
        addIntegersToQueue(queue1, integers1);
        addIntegersToQueue(queue2, integers2);

        // Generate union queue (queue1 ∪ queue2)
        // Resulting queue should contain all individual elements from
        // both queues, including any duplicates.
        PriorityQueue<Integer> unionQueue = new PriorityQueue<>();
        unionQueue.addAll(queue1);
        unionQueue.addAll(queue2);

        // Generate Difference (queue1 ≏ queue2)
        // Difference of queue 1 and queue 2 is defined as all elements of
        // queue1 that are not in queue2, that is, A ≏ B = {x | x ∈ A ∧ x ∉ B}
        // differenceQueue is initialized as a copy of queue1.
        // differenceTempQueue is initialized as a copy of queue2.
        PriorityQueue<Integer> differenceQueue = new PriorityQueue<>(queue1);
        PriorityQueue<Integer> differenceTempQueue = new PriorityQueue<>(
            queue2
        );
        // Iterate through queue1.
        // if differenceTempQueue contains num, remove it from
        // differenceQueue.  This will leave us with differenceQueue
        // containing only the elements that are in queue1 but not in queue2.
        for (Integer num : queue1) {
            if (differenceTempQueue.contains(num)) {
                differenceQueue.remove(num);
            }
        }

        // Generate Intersection (queue1 ∩ queue2).
        // Intersection is defined as the common elements that are in both
        // queue1 and queue2.
        // intersectionQueue is initialized empty, and
        // intersectionTempQueue is initialized as a copy of queue2.
        PriorityQueue<Integer> intersectionQueue = new PriorityQueue<>();
        PriorityQueue<Integer> intersectionTempQueue = new PriorityQueue<>(
            queue2
        );
        // We iterate through queue1, and for each item contained in
        // intersectionTempQueue, we add that item to intersectionQueue.
        for (Integer num : queue1) {
            if (intersectionTempQueue.contains(num)) {
                intersectionQueue.add(num);
            }
        }

        // Display output in increasing order
        System.out.println("The union of the two priority queues is ");
        printQueue(unionQueue);
        System.out.println();
        System.out.println("The difference of the two priority queues is ");
        printQueue(differenceQueue);
        System.out.println();
        System.out.println("The intersection of the two priority queues is ");
        printQueue(intersectionQueue);
        System.out.println();
    }
}
