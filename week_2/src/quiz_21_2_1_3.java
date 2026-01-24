import java.util.*;
public class quiz_21_2_1_3 {
  public static void main(String[] args) {
    Set<A3> set = new HashSet<>();
    set.add(new A3());
    set.add(new A3());
    set.add(new A3());
    set.add(new A3());
    System.out.println(set);
  }
}

class A3 {
  int r = 1;

  public String toString() {
    return r + "";
  }

  public int hashCode() {
    return r;
  }
}
