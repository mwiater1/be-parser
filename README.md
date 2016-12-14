# Boolean Expression Parser

This application parses boolean expressions and evaluates them. This was written as Assignment #1 for CSC444 at SUNY Oswego.

## Getting Started

All commands should be ran from the root project directory. All compiled code and distributions are placed in the **build** folder.

The program support evaluation boolean expression. The only variables available are a-z. They all start off initialized to false.
Below are some sample commands and their outputs. ("#" precedes user input)

```
# a = 1
# a?
  1
# b = a | 0
# c = b & ~b & (a | a)
# c?
  0
# c = ~~~a
# c?
  0
```

## Running

To run the program:

```
./gradlew run
```

## Distribution

To create a runnable .jar of the application:

```
./gradlew fatJar
```

## Built With
* [ANTLRv4](http://www.antlr.org/)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
