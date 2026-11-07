package edu.psu.se411.lab09_inventory.model;

public class ElectronicDevice extends Item {
	private final String category;

	public ElectronicDevice(int id, String name, String category) {
		super(id, name);
		if (category == null || category.isBlank()) throw new IllegalArgumentException("Category cannot be empty");
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "ElectronicDevice [id=" + getId() + ", name=" + getName() + ", category=" + category + "]";
	}
}
