import java.util.HashSet;
import java.util.Set;

public class quiz_21_3_2 {
  public static void main(String[] args) {
    Set<Integer> s1 = new HashSet<>();
    Set<Integer> s2 = new HashSet<>();
    s1.add(1);
    s1.add(2);
    s1.add(5);
    s2.add(2);
    s2.add(3);
    s2.add(6);
    s1.addAll(s2);
    System.out.println(s2);
  }
}
