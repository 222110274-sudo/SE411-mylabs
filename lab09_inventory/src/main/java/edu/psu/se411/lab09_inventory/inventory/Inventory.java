package edu.psu.se411.lab09_inventory.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.psu.se411.lab09_inventory.search.SearchStrategy;

/**
 * Generic, type-safe inventory holding objects of type T.
 */
public class Inventory<T> {
	private final List<T> items = new ArrayList<>();

	public void addItem(T item) {
		if (item == null) throw new IllegalArgumentException("Cannot add a null item");
		items.add(item);
	}

	public boolean removeItem(T item) {
		return items.remove(item);
	}

	/** Returns a read-only view so callers cannot modify the inventory directly. */
	public List<T> getAllItems() {
		return Collections.unmodifiableList(items);
	}

	/**
	 * Returns every item that matches the given search strategy.
	 * Accepting SearchStrategy&lt;? super T&gt; also lets a strategy written for a
	 * supertype (e.g. SearchByName on Item) be used on an Inventory&lt;Book&gt;.
	 */
	public List<T> findItems(SearchStrategy<? super T> strategy) {
		List<T> result = new ArrayList<>();
		for (T item : items) {
			if (strategy.matches(item)) {
				result.add(item);
			}
		}
		return result;
	}

	public int size() {
		return items.size();
	}
}
