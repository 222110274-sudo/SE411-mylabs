package edu.psu.se411.lab09_inventory.model;

/**
 * Base type for every product stored in the inventory.
 */
public abstract class Item {
	private final int id;
	private final String name;

	protected Item(int id, String name) {
		if (name == null || name.isBlank()) throw new IllegalArgumentException("Item name cannot be empty");
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + "]";
	}
}
