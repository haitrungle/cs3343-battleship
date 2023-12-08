# Battleship in Java

This is the Battlship game written in Java. Two players on the same local network can play together.

The code is very messy, awaiting you to imbue it with grace and elegance.

To play, from the root directory, run
```
javac -cp lib/*:. -d bin $(find src -name "*.java")
java -cp ./bin cs3343.battleship.Main
```