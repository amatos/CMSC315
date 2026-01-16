import java.util.*;

public class QueueTest {
  public static void main(String[] args) {
    PriorityQueue<Integer> queue =
      new PriorityQueue<Integer>(
        Arrays.asList(60, 10, 50, 30, 40, 20));

    while (!queue.isEmpty())
      System.out.print(queue.poll() + " ");
  }
}
