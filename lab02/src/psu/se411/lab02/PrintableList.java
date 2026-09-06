package psu.se411.lab02;

import java.util.Arrays;
import java.util.List;

public class PrintableList<T> {

    private List<T> items;

    public PrintableList(T[] itemsArray) {
        this.items = Arrays.asList(itemsArray);
    }

    public void printItems() {
        for (T item : items) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        String[] names = {"Alice", "Bob", "Charlie"};
        PrintableList<String> list = new PrintableList<>(names);
        list.printItems();
    }
}