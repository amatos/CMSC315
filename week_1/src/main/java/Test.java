import java.util.*;

public class Test {
  public static void main(String[] args) {
    ArrayList<Student> list = new ArrayList<>();
    list.add(new Student("Peter", 65));
    list.add(new Student("Jill", 50));
    list.add(new Student("Sarah", 34));
    Collections.sort(list);
    System.out.print(list + " ");

    Collections.sort(list, new StudentComparator1());
    System.out.println(list);
  }

  static class StudentComparator1 implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
      return s1.name.compareTo(s2.name);
    }
  }

  static class Student implements Comparable<Student> {
    String name;
    int age;
    Student(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public int compareTo(Student s) {
      return this.age - s.age;
    }

    public String toString() {
      return "[" + name + ", " + age + "]";
    }
  }
}
