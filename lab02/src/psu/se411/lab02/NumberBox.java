package psu.se411.lab02;

import java.util.Arrays;
import java.util.List;

public class NumberBox<T extends Number> {

    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public static double sumList(List<? extends Number> numbers) {
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        NumberBox<Integer> intBox = new NumberBox<>();
        intBox.setItem(42);
        System.out.println("Integer item: " + intBox.getItem());

        NumberBox<Double> doubleBox = new NumberBox<>();
        doubleBox.setItem(3.14);
        System.out.println("Double item: " + doubleBox.getItem());

        List<Integer> intList = Arrays.asList(1, 2, 3, 4);
        System.out.println("Sum of ints: " + sumList(intList));

        List<Double> doubleList = Arrays.asList(1.5, 2.5, 3.0);
        System.out.println("Sum of doubles: " + sumList(doubleList));
    }
}