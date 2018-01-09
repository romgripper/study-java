import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  private static void tryStream() {
    List<Product> products = new ArrayList<>();
    products.add(new Product(1, "HP Laptop", 25000f));
    products.add(new Product(2, "Dell Laptop", 30000f));
    products.add(new Product(3, "Lenovo Laptop", 28000f));
    products.add(new Product(4, "Sony Laptop", 28000f));
    products.add(new Product(5, "Apple Laptop", 90000f));

    List<Float> prices =
        products
            .stream()
            .filter(p -> p.price > 25000)
            .map(p -> p.price)
            .collect(Collectors.toList());
    System.out.println(prices);
    products
        .stream()
        .filter(p -> p.price >= 25000)
        .sorted((p1, p2) -> (int) ((p2.price - p1.price) * 100))
        .limit(2)
        .forEach(product -> System.out.println(product.name + ": " + product.price));

    Map<String, Float> productMap =
        products
            .stream()
            .collect(Collectors.toMap(product -> product.name, product -> product.price));
    for (Map.Entry<String, Float> product : productMap.entrySet()) {
      System.out.println(product.getKey() + ": " + product.getValue());
    }
  }

  public static void main(String[] args) {
    System.out.println("Java 1.8 new features:");
    tryDefaultInterfaceMethod();
    tryLambda();
    tryFunctionalInterface();
    tryStream();
  }
}

class Product {
  int id;
  String name;
  float price;

  public Product(int id, String name, float price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }
}
