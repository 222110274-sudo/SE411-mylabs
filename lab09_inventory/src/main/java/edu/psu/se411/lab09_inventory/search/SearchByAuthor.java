package edu.psu.se411.lab09_inventory.search;

import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.Item;

/**
 * Book-specific strategy. It is typed on Item so it can be used on a mixed
 * Inventory&lt;Item&gt;: items that are not books never match.
 */
public class SearchByAuthor implements SearchStrategy<Item> {
	private final String author;

	public SearchByAuthor(String author) {
		this.author = author;
	}

	@Override
	public boolean matches(Item item) {
		return item instanceof Book book && book.getAuthor().equalsIgnoreCase(author);
	}
}
