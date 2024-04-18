# Ch12-tech-support-io (Technical Support System)
@author Alper Hiz
@version 4/18/2024

This project implements a simple technical support system that communicates via text input/output in the terminal. It allows users to describe their problems and receive responses based on predefined keywords.

## How to Use

1. Clone the repository to your local machine.
2. Compile the Java files using your preferred IDE or the command line.
3. Run the "SupportSystem" class to start the technical support system.
4. Follow the on-screen instructions to describe your problem and receive assistance.

## Project Structure

- "SupportSystem.java": The main class that orchestrates the technical support system.
- "InputReader.java": Reads input from the user and processes it into a set of words.
- "Responder.java": Generates responses based on keywords entered by the user.

## Changes Made

- Implemented reading responses from a file instead of hardcoding them ("responses.txt").
- Updated the "generateResponse" method to handle cases where the user enters a whole sentence containing a keyword, ensuring correct responses are provided.
- Modified the "fillDefaultResponses" method to support multiline entries, where a blank line marks the end of an entry and lines of text get appended if they appear on the next consecutive line.


## License

This project is part of the material for the book

Objects First with Java - A Practical Introduction using BlueJ Sixth edition David J. Barnes and Michael Kölling Pearson Education, 2016.

