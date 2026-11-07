package edu.psu.se411.lab09_inventory.model;

public class Book extends Item {
	private final String author;

	public Book(int id, String name, String author) {
		super(id, name);
		if (author == null || author.isBlank()) throw new IllegalArgumentException("Author cannot be empty");
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		return "Book [id=" + getId() + ", name=" + getName() + ", author=" + author + "]";
	}
}
