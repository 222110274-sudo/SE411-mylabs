package psu.se411.lab02;

import java.util.Arrays;
import java.util.List;

public class WildcardDemo {

    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        printList(names);

        List<Integer> nums = Arrays.asList(10, 20, 30);
        printList(nums);

        System.out.println("Sum: " + sumNumbers(nums));
    }
}