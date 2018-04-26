package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static boolean isEven(int x) {
        return x%2 == 0;
    }

    public static void main(String[] args) {

        System.out.println( 1 + 2 * 3);

        List<Integer> list = Arrays.asList(1, 2, 3, 4);

        // # Java < 8
        for(int x : list) {
            if(isEven(x)) {
                System.out.println(x);
            }
        }

        // # Java8
        list.stream().filter(x -> isEven(x)).forEach(System.out::println);

        /////////////////////////////////////////////////////////////////

        if(2 + 2 == 5) {
            System.out.println("Hello form 1984.");
        } else if(2 + 2 == 3) {
            System.out.println("Hello from Remedial Math class?");
        } else {
            System.out.println("Hello from a non-Orwellian future.");
        }


        /*
        Error:(36, 23) java: illegal start of expression
        Error:(36, 25) java: not a statement
        Error:(36, 37) java: ';' expected
        Error:(38, 11) java: 'else' without 'if'

        String path = if(2 + 2 == 3) {
            System.getenv("PATH");
        } else {
            System.getenv("PATH");
        }
        */

        /////////////////////////////////////////////////////////////////

        List<String> dogBreeds = Arrays.asList("Doberman", "Yorkshire Terrier", "Dachshund", "Scottish Terrier", "Great Dane", "Portuguese Water Dog");

        for(String breed : dogBreeds){
            System.out.println(breed);
        }

        for(String breed : dogBreeds){
            if(breed.contains("Terrier")) {
                System.out.println(breed);
            }
        }

        for(String breed : dogBreeds){
            if(breed.contains("Terrier") && !breed.startsWith("Yorkshire")) {
                System.out.println(breed);
            }
        }

        // # Java < 8
        List<String> filteredBreeds = new ArrayList<>();
        for(String breed : dogBreeds){
            if(breed.contains("Terrier") && !breed.startsWith("Yorkshire")) {
                filteredBreeds.add(breed);
            }
        }

        // # Java8
        List<String> filteredBreeds2 = dogBreeds.stream().filter(breed -> breed.contains("Terrier") && !breed.startsWith("Yorkshire")).collect(Collectors.toList());
        filteredBreeds2.forEach(System.out::println);

        // http://www.baeldung.com/java-filter-stream-of-optional

        // http://www.baeldung.com/java-9-optional

        /////////////////////////////////////////////////////////////////
        Fruits fruits = Fruits.Apple;

        System.out.println(fruits);
        System.out.println(fruits.name());
        System.out.println(fruits.ordinal());

        Fruits fruits2 = Fruits.valueOf("Banana");

        System.out.println(fruits2);
        System.out.println(fruits2.name());
        System.out.println(fruits2.ordinal());

        Fruits fruits3 = Fruits.Banana;
        String color = fruits3.getColor();
        System.out.println(color);

        Service s = new Service();

        s.show();
        System.out.println("H " + s.message());
    }
}

enum Fruits {
    Apple("RED"), Banana("YELLOW");

    private String color;

    Fruits(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}

interface ServiceImpotante {

    public String message();

    default void show() {
        System.out.println("Hello");
    }
}

class Service implements ServiceImpotante {
    @Override
    public String message() {
        return "test";
    }
}
