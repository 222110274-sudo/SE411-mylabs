# Lab 09 – Managing an inventory with the Strategy pattern

Run: `mvn clean package exec:java`

## Part 1 – Generic inventory
- `inventory/Inventory<T>`: `addItem`, `removeItem`, `getAllItems` (read-only view).
- `model/Item` (abstract: id, name), `model/Book` (+ author), `model/ElectronicDevice` (+ category).
- `App` creates an `Inventory<Item>` holding books and electronic devices.

## Part 2 – Search with the Strategy pattern
- `search/SearchStrategy<T>`: `boolean matches(T item)`, a `@FunctionalInterface`.
- `Inventory.findItems(SearchStrategy<? super T> strategy)` returns every item that matches.
  `? super T` also lets an `Item` strategy be used on an `Inventory<Book>`.
- Strategies: `SearchById`, `SearchByName` (any Item), `SearchByAuthor` (Book), `SearchByCategory` (ElectronicDevice).
- `App` searches by name (`inventory.findItems(new SearchByName("Concurrency in Java"))`), id, author and category,
  and uses lambdas as strategies (`item -> item.getName().contains("Java")`).

## Part 3 – Display any inventory
- `App.displayInventory(Inventory<?> inventory)` prints every item of any inventory,
  using the unbounded wildcard. It is called with both an `Inventory<Item>` and an `Inventory<Book>`.
