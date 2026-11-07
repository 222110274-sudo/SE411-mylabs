package edu.psu.se411.lab09_inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab09_inventory.inventory.Inventory;
import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.ElectronicDevice;
import edu.psu.se411.lab09_inventory.model.Item;
import edu.psu.se411.lab09_inventory.search.SearchByAuthor;
import edu.psu.se411.lab09_inventory.search.SearchByCategory;
import edu.psu.se411.lab09_inventory.search.SearchById;
import edu.psu.se411.lab09_inventory.search.SearchByName;
import edu.psu.se411.lab09_inventory.search.SearchStrategy;

/**
 * Build and run using Maven goal: clean package exec:java
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");

		try {
			// ---------- Part 1: generic inventory ----------
			Inventory<Item> inventory = new Inventory<>();
			inventory.addItem(new Book(1, "Concurrency in Java", "Brian Goetz"));
			inventory.addItem(new Book(2, "Effective Java", "Joshua Bloch"));
			inventory.addItem(new Book(3, "Java Puzzlers", "Joshua Bloch"));
			inventory.addItem(new ElectronicDevice(4, "MacBook Pro", "Laptop"));
			inventory.addItem(new ElectronicDevice(5, "iPhone 16", "Phone"));
			inventory.addItem(new ElectronicDevice(6, "ThinkPad X1", "Laptop"));

			Item toRemove = new ElectronicDevice(7, "Old Router", "Networking");
			inventory.addItem(toRemove);
			System.out.println("Removed 'Old Router': " + inventory.removeItem(toRemove));

			// ---------- Part 3: display any inventory ----------
			displayInventory(inventory);

			// ---------- Part 2: strategy-based search ----------
			printResults("Search by name 'Concurrency in Java'",
					inventory.findItems(new SearchByName("Concurrency in Java")));
			printResults("Search by name 'iPhone 16'",
					inventory.findItems(new SearchByName("iPhone 16")));
			printResults("Search by id 2",
					inventory.findItems(new SearchById(2)));
			printResults("Search books by author 'Joshua Bloch'",
					inventory.findItems(new SearchByAuthor("Joshua Bloch")));
			printResults("Search devices by category 'Laptop'",
					inventory.findItems(new SearchByCategory("Laptop")));

			// Lambda expression as a SearchStrategy (functional interface)
			printResults("Lambda: names containing 'Java'",
					inventory.findItems(item -> item.getName().contains("Java")));

			SearchStrategy<Item> idGreaterThan3 = item -> item.getId() > 3;
			printResults("Lambda: id > 3", inventory.findItems(idGreaterThan3));

			// The same strategies also work on a more specific inventory
			Inventory<Book> books = new Inventory<>();
			books.addItem(new Book(10, "Clean Code", "Robert C. Martin"));
			books.addItem(new Book(11, "Clean Architecture", "Robert C. Martin"));
			printResults("Inventory<Book> lambda: author 'Robert C. Martin'",
					books.findItems(b -> b.getAuthor().equals("Robert C. Martin")));
			displayInventory(books);

		} catch (IllegalArgumentException e) {
			logger.error("Invalid inventory operation: " + e.getMessage(), e);
			System.out.println("Error: " + e.getMessage());
		}

		logger.info("Application is closing...");
	}

	/**
	 * Displays the contents of any inventory, regardless of its product type,
	 * using the unbounded wildcard Inventory&lt;?&gt;.
	 */
	public static void displayInventory(Inventory<?> inventory) {
		System.out.println("===== Inventory (" + inventory.size() + " items) =====");
		for (Object item : inventory.getAllItems()) {
			System.out.println("  " + item);
		}
		System.out.println();
	}

	private static void printResults(String title, List<? extends Item> results) {
		System.out.println(title + ":");
		if (results.isEmpty()) {
			System.out.println("  (no match)");
		}
		for (Item item : results) {
			System.out.println("  " + item);
		}
		System.out.println();
	}
}
