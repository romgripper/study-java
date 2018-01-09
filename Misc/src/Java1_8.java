import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface Formula {

  double calculate(int a);

  // The default keyword is necessary
  default double sqrt(int a) {
    return Math.sqrt(a);
  }
}

public class Java1_8 {

  private static void tryDefaultInterfaceMethod() {
    System.out.println("- Default interface method");
    Formula f =
        new Formula() {
          @Override
          public double calculate(int a) {
            return 0;
          }
        };
    System.out.println(f.sqrt(9));
  }

  private static void tryLambda() {
    System.out.println("- Lambda");
    List<String> names = Arrays.asList("Peter", "Anna", "Mike");
    Collections.sort(
        names,
        (String a, String b) -> {
          return b.compareTo(a);
        });
    for (String name : names) {
      System.out.println(name);
    }

    Collections.sort(names, (String a, String b) -> a.compareTo(b));
    for (String name : names) {
      System.out.println(name);
    }

    Collections.sort(names, (a, b) -> b.compareTo(a));
    for (String name : names) {
      System.out.println(name);
    }
  }

  // @FunctionalInterface is optional. However, as @Override, it's best practice to add it
  @FunctionalInterface
  interface Converter<F, T> {
    T convert(F from);
  }

  static class Person {
    String fname;
    String lname;

    public Person() {}

    public Person(String fname, String lname) {
      this.fname = fname;
      this.lname = lname;
    }

    @Override
    public String toString() {
      return "Person [fname=" + fname + ", lname=" + lname + "]";
    }
  }

  interface PersonFactory<P extends Person> {
    P create(String fname, String lname);
  }

  private static void tryFunctionalInterface() {
    System.out.println("- Functional interface");
    Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
    System.out.println(converter.convert("123") + 111);

    // ClassName::staticMethod
    converter = Integer::valueOf;
    System.out.println(converter.convert("123") + 111);

    PersonFactory<Person> factory = Person::new;
    System.out.println(factory.create("Mike", "Smith"));
  }

  public static void main(String[] args) {
    System.out.println("Java 1.8 new features:");
    tryDefaultInterfaceMethod();
    tryLambda();
    tryFunctionalInterface();
  }
}
