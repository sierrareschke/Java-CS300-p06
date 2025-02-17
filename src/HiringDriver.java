import java.util.Comparator;
import java.util.Scanner;

/**
 * The driver class for the Hiring application.
 */
public class HiringDriver {

  /*-
   * Contains the main driver loop, which causes reads input about the availability and pay rate of
   * TAs and the type of problem we would like to solve. Then, it outputs a solution.
   *
   * The input will follow exactly this format:
   *
   * <number of hours> <number of TAs> <type of problem>
   * <ta info> ...
   *
   * where
   * - <number of hours> is an integer indicating the total number of hours in the schedule
   * - <number of TAs> is an integer indicating the total number of candidates to consider
   * - <type of problem> is either "1 <num hires>" or "2 <num hours>":
   *    - 1: find the schedule that maximizes the number of covered hours with at most the given
   *         number of hires.
   *    - 2: find the least expensive set of hires that cover the specified minimum number of hours.
   * - <ta info> is info about the availability and pay rate of each candidate. Each candidate should
   *    be on its own line. Availability should be a space-separated list of "true" or "false", where
   *    the ith value indicates whether the TA is available at the ith time slot or not. The last
   *    value in the line should be an integer indicating the pay rate of the candidate if
   *    <type of problem> is 2.
   *
   * Example input:
   *
   * 6 4 1 2
   * true true true false false true
   * true true false true false true
   * true false true false true true
   * false false true true false false
   *
   * Example output:
   *
   * Greedy Solution: 0 1
   * Hours covered: 5
   * Optimal Solution: 1 2
   * Hours covered: 6
   *
   * Example input:
   *
   * 6 4 2 5
   * true true true false false true 5
   * true true false true false true 1
   * true false true false true true 5
   * false false true true false false 5
   *
   * Example output:
   *
   * Optimal Solution: 0 1
   * Hours covered: 5
   * Cost: 6
   *
   * Alternate Example output:
   *
   * Optimal Solution: 1 3
   * Hours covered: 5
   * Cost: 6
   *
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Read some input about the problem.
    int numHours = readIntOrError(scanner, "enter number of hours");
    int numCandidates = readIntOrError(scanner, "enter number of candidates");
    int probType = readIntOrError(scanner, "enter problem type");
    int numHiresOrNumHours = 0;
    if (probType == 1) {
      numHiresOrNumHours = readIntOrError(scanner, "enter the number of hires to make");
    } else {
      numHiresOrNumHours = readIntOrError(scanner, "enter the minimum hours to cover");
    }

    // Validate the problem input.
    if (numHours < 1 || numCandidates < 1 || (probType != 1 && probType != 2)
        || numHiresOrNumHours < 0) {
      System.out.println("Exiting due to invalid input.");
      return;
    }

    // Now read the list of candidates.
    CandidateList candidates = new CandidateList();

    for (int c = 0; c < numCandidates; ++c) {
      // Read availability.
      boolean[] availability = readNBoolsOrError(scanner, numHours,
          "availability must be space-separate boolean values");
      int payRate = 0;

      if (probType == 2) {
        payRate = readIntOrError(scanner, "enter the pay rate");
      }

      if (availability == null || payRate < 0) {
        System.out.println("Exiting due to invalid input.");
        return;
      }

      Candidate candidate;
      // Read the pay rate.
      if (probType == 1) {
        candidate = new Candidate(availability);
      } else {
        candidate = new Candidate(availability, payRate);
      }

      candidates.add(candidate);
    }

    // Solve the problem and print the output.
    if (probType == 1) {
      CandidateList greedySolution =
          Hiring.greedyHiring(candidates, new CandidateList(), numHiresOrNumHours);
      CandidateList optimalSolution =
          Hiring.optimalHiring(candidates, new CandidateList(), numHiresOrNumHours);
      if (optimalSolution == null || greedySolution == null) {
        System.out.println("No solution.");
      } else {
        // Sort by candidate id
        greedySolution.sort(Comparator.comparing(Candidate::getId));
        optimalSolution.sort(Comparator.comparing(Candidate::getId));
        // Print out
        System.out.print("Greedy Solution:");
        for (Candidate c : greedySolution) {
          System.out.print(" " + c.getId());
        }
        System.out.println("\nHours covered: " + greedySolution.numCoveredHours());
        System.out.print("Optimal Solution:");
        for (Candidate c : optimalSolution) {
          System.out.print(" " + c.getId());
        }
        System.out.println("\nHours covered: " + optimalSolution.numCoveredHours());
      }
    } else {
      CandidateList optimalSolution =
          Hiring.minCoverageHiring(candidates, new CandidateList(), numHiresOrNumHours);
      if (optimalSolution == null) {
        System.out.println("No solution.");
      } else {
        System.out.print("Optimal Solution:");
        for (Candidate c : optimalSolution) {
          System.out.print(" " + c.getId());
        }
        System.out.println("\nHours covered: " + optimalSolution.numCoveredHours());
        System.out.println("Cost: " + optimalSolution.totalCost());
      }
    }

    scanner.close();
  }

  /**
   * Read an integer from the keyboard and return it, or print an error message and exit if there is
   * an error.
   *
   * @param sc     a Scanner to use for getting input.
   * @param errMsg the error message to print in case of an error.
   * @return the read integer or -1 if there is an error.
   */
  private static int readIntOrError(Scanner sc, String errMsg) {
    try {
      return sc.nextInt();
    } catch (Exception e) {
      System.out.println("Error: expected integer: " + errMsg);
      System.exit(1);
      return -1; // never happens, but gotta appease the compiler
    }
  }

  /**
   * Read a list of boolean values from the keyboard and return as an array. If there is an error,
   * print the message and exit.
   *
   * @param sc     a Scanner to use for getting input.
   * @param n      the number of boolean values to attempt to read.
   * @param errMsg the error message to print in case of an error.
   * @return the array of read booleans or null if there is an error.
   */
  private static boolean[] readNBoolsOrError(Scanner sc, int n, String errMsg) {
    boolean[] values = new boolean[n];
    try {
      for (int i = 0; i < n; ++i) {
        values[i] = sc.nextBoolean();
      }
      return values;
    } catch (Exception e) {
      System.out.println("Error: expected " + n + " boolean values: " + errMsg);
      System.exit(1);
      return null; // never happens, but gotta appease the compiler
    }
  }

}
