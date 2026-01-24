import java.util.HashSet;
import java.util.Set;


public class quiz_21_2_1_2 {
  public static void main(String[] args) {
    Set<A2> set = new HashSet<A2>();
    set.add(new A2());
    set.add(new A2());
    set.add(new A2());
    set.add(new A2());
    System.out.println(set);
  }
}

class A2 {
  int r = 1;

  public String toString() {
    return r + "";
  }

  public boolean equals(Object o) {
    return this.r == ((A2) o).r;
  }

  public int hashCode() {
    return r;
  }
}
