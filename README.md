# MyTreeMap Java Project

Welcome to the MyTreeMap Java Project, a simple and efficient implementation of a TreeMap in Java. This project provides a TreeMap structure for key-value pairs, offering functionalities like adding, getting, and removing elements, as well as checking for the existence of keys and values.

## Table of Contents

- [Usage](#usage)
- [Methods](#methods)
- [Contributing](#contributing)
- [License](#license)

## Usage

You can use the MyTreeMap class in your Java projects by including it in your codebase. Here's a basic example:

```java
public class Main {
    public static void main(String[] args) {
        MyTreeMap<String, Integer> myMap = new MyTreeMap<>();

        // Add key-value pairs to the map
        myMap.put("One", 1);
        myMap.put("Two", 2);
        myMap.put("Three", 3);

        // Check if the map contains a key
        System.out.println("Contains key 'Two': " + myMap.containsKey("Two"));

        // Get the value associated with a key
        System.out.println("Value for key 'Three': " + myMap.get("Three"));

        // Remove a key-value pair from the map
        myMap.remove("One");

        // Get the size of the map
        System.out.println("Size of the map: " + myMap.size());
    }
}
```

## Methods

- `size()`: Returns the size of the map.
- `clear()`: Clears the map.
- `containsKey(K key)`: Checks if the map contains a specified key.
- `containsVal(V value)`: Checks if the map contains a specified value.
- `values()`: Returns a collection of all values in the map.
- `keys()`: Returns a set of all keys in the map.
- `isEmpty()`: Checks if the map is empty.
- `put(K key, V value)`: Adds a key-value pair to the map.
- `get(K key)`: Returns the value associated with a specified key.
- `remove(K key)`: Removes a key-value pair from the map.

## Contributing

If you have any ideas for improvements or new features, feel free to contribute! Fork the repository, make your changes, and submit a pull request. Your input is highly appreciated.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute it as needed.
