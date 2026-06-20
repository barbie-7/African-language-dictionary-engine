# CS214 Project 2026, Electronic Khoekhoegowab Dictionary

## Student Information
- Student Number: 29381487  

## Project Description
This project is a simple electronic dictionary for Khoekhoegowab–English translations.

It takes the info from the file and then parses the dictionary entries into objects containing info like: the actual word, dialect, parts of speech, origin and definition among others.

The goal is to have a working program with good parsing and efficient searching due to limited time constraints.

## How to Compile 
Run this from the project root:

```bash
javac -cp lib/stdlib.jar:lib/algs4.jar -d bin src/*.java

```
## How to Run
```bash
java -cp ./bin:lib/stdlib.jar:lib/algs4.jar Khoe noGUI <input file> <handin> <args>
```
