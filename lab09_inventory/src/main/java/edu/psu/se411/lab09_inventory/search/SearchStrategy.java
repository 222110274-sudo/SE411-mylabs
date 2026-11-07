package edu.psu.se411.lab09_inventory.search;

/**
 * Strategy interface: defines how to check if an item matches a criteria.
 * It has a single abstract method, so it is a functional interface and can
 * be implemented with a lambda expression.
 */
@FunctionalInterface
public interface SearchStrategy<T> {
	boolean matches(T item);
}
