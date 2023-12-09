# Battleship in Java

This is the Battlship game written in Java. Two players on the same local network can play together.

The code is very messy, awaiting you to imbue it with grace and elegance.

## Prerequisites
- Java 8 or above
- JUnit 5 .jar file in the `lib` folder

## Run the game

To play, from the root directory, run
```
javac -cp lib/*:. -d bin $(find src -name "*.java")
java -cp bin cs3343.battleship.Main
```

If you don't have JUnit or don't need to compile the test files:
```
javac -d bin $(find src -name "*.java" -not -path "**/test/**")
java -cp bin cs3343.battleship.Main
```