# TA Hiring System

This repository contains the implementation for **TA Hiring System**, a **CS 300: Programming II** assignment completed in Fall 2023. This project applies **recursive algorithms** to solve an **optimization problem**: selecting the best set of Teaching Assistants (TAs) to **maximize office hour coverage** or **minimize budget constraints**.

## Features
- **Recursive Hiring Algorithms**:
  - **Greedy Hiring**: Uses a **greedy approach** to maximize TA availability by always selecting the candidate that covers the most new hours.
  - **Optimal Hiring**: Uses **exhaustive recursion** to find the best possible set of TAs that **maximize coverage**.
  - **Minimum Budget Hiring**: Selects the **cheapest set of TAs** that still covers a required number of hours.
- **Candidate Management**:
  - Each **Candidate** has a **name, availability schedule, and pay rate**.
  - **CandidateList** extends `ArrayList<Candidate>` for **easy management**.
- **Unit Testing & Fuzz Testing**:
  - Implements **base case and recursive case tests**.
  - Uses **fuzz testing** to verify correctness against a **reference implementation**.
- **Algorithm Comparison**:
  - Compares **greedy vs. optimal hiring** strategies.
  - Uses **reference implementations** to validate recursive solutions.

## Files Included
- `Hiring.java`: Implements **recursive hiring algorithms** for TA selection.
- `HiringTester.java`: Contains **unit tests** to verify the correctness of the recursive methods.
- `Candidate.java`: Represents an **individual TA candidate** with availability and pay rate.
- `CandidateList.java`: Extends `ArrayList<Candidate>`, providing **utility methods** for coverage and cost calculations.
- `HiringDriver.java`: Provides a **command-line interface** for running hiring simulations.
- `HiringTestingUtilities.java`: Reference **iterative implementations** to validate recursive solutions.
- `P06 - TA Hiring.pdf`: The assignment specification document.

## Learning Objectives
- Understand **recursive problem-solving** with base and recursive cases.
- Implement **greedy algorithms** and compare them to **optimal solutions**.
- Apply **recursion** in an **optimization context**.
- Utilize **fuzz testing** and **reference implementations** for verification.
- Work with **lists, comparators, and custom data structures** in Java.

## Running the Program
1. Ensure **Java 17** is installed and set up in your development environment.
2. Import the project into **Eclipse** (or another Java IDE).
3. Compile all Java files.
4. Run `HiringDriver.java` to simulate the TA hiring process.
5. Run `HiringTester.java` to execute **unit tests** and validate **recursive solutions**.

## Acknowledgments
This project was developed as part of **CS 300: Programming II** at the **University of Wisconsin-Madison**. All rights and materials are reserved by the university.
