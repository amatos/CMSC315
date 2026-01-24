import java.util.*;

public class quiz_21_2_2_2_1 {
  public static void main(String[] args) {
    // Create a linked hash set
    Set<String> set = new LinkedHashSet<>();

    // Add strings to the set
    set.add("London");
    set.add("Paris");
    set.add("New York");
    set.add("San Francisco");
    set.add("Beijing");
    set.add("New York");

    System.out.println(set);
  }
}
