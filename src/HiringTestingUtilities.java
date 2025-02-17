import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * A collection of useful methods for testing the Hiring implementations.
 */
public class HiringTestingUtilities {

  /**
   * Random instance for tests that randomly generate inputs. We set the random seed so that we
   * still get deterministic results that we can reproduce.
   */
  public static Random randGen = new Random(205);

  /**
   * Create a new CandidateList with candidates that have the given availabilities.
   * 
   * @param availabilities the availabilities of the candidates; one candidate per row of the array.
   * @return a new CandidateList with all the candidates
   */
  public static CandidateList makeCandidateList(boolean[][] availabilities) {
    CandidateList candidates = new CandidateList();

    for (boolean[] availability : availabilities) {
      Candidate candidate = new Candidate(availability);
      candidates.add(candidate);
    }

    return candidates;
  }

  /**
   * Create a new CandidateList with candidates that have the given availabilities and payrates.
   * 
   * @param availabilities the availabilities of the candidates; one candidate per row of the array.
   * @param payRates       the pay rate for each candidate; the elements of this array should
   *                       correspond with the rows of the availabilities array.
   * @return a new CandidateList with all the candidates
   */
  public static CandidateList makeCandidateList(boolean[][] availabilities, int[] payRates) {
    CandidateList candidates = new CandidateList();

    for (int c = 0; c < availabilities.length; ++c) {
      Candidate candidate = new Candidate(availabilities[c], payRates[c]);
      candidates.add(candidate);
    }

    return candidates;
  }

  /**
   * Generate a random set of candidates with availability schedules ranging over the given number
   * of hours.
   * 
   * @param numHours      the number of hours in our schedule that we are hiring for
   * @param numCandidates the number of candidate to hire from; the returned list will be this long
   * @param maxPayRate    the maximum pay rate for any candidate or -1 to indicate no pay rates
   * 
   * @return a list of randomly generated candidates
   */
  public static CandidateList generateRandomInput(int numHours, int numCandidates, int maxPayRate) {
    CandidateList candidates = new CandidateList();

    for (int c = 0; c < numCandidates; ++c) {
      boolean[] availability = new boolean[numHours];
      for (int h = 0; h < numHours; h++) {
        // flip a coin to determine if the candidate is available at time h
        availability[h] = randGen.nextDouble() < 0.50;
      }
      if (maxPayRate == -1) {
        candidates.add(new Candidate(availability));
      } else {
        int payRate = randGen.nextInt(maxPayRate) + 1;
        candidates.add(new Candidate(availability, payRate));
      }
    }

    return candidates;
  }

  /**
   * Generate a random set of candidates with availability schedules ranging over the given number
   * of hours.
   * 
   * @param numHours      the number of hours in our schedule that we are hiring for
   * @param numCandidates the number of candidate to hire from; the returned list will be this long
   *
   * 
   * @return a list of randomly generated candidates
   */
  public static CandidateList generateRandomInput(int numHours, int numCandidates) {
    // This only works be we know that -1 is the sentinel value that indicates no payrates...
    return generateRandomInput(numHours, numCandidates, -1);
  }

  /**
   * Compare two lists of candidates. If there are differences, print some helpful output and return
   * false. Otherwise, print nothing and return true.
   * 
   * @param expected the list representing the expected result
   * @param actual   the list representing the result from calling the method we are testing
   * 
   * @return true if and only if the contents of actual match expected
   */
  public static boolean compareCandidateLists(CandidateList expected, CandidateList actual) {
    ArrayList<CandidateList> lists = new ArrayList<>(1);
    if (expected != null) {
      lists.add(expected);
    }
    return compareCandidateLists(lists, actual);
  }

  /**
   * Check if actual is in the list of expected solutions. If actual is not in the expected list,
   * print some helpful output and return false. Otherwise, print nothing and return true.
   * 
   * @param expectedLists a list of lists representing the expected results. If the list is empty,
   *                      then actual must be null.
   * @param actual        the list representing the result from calling the method we are testing.
   * 
   * @return true if and only if the contents of actual match one the lists in expected
   */
  public static boolean compareCandidateLists(ArrayList<CandidateList> expectedLists,
      CandidateList actual) {
    // Handle the special case where we want to test the results of an impossible problem (ie, one
    // where there is no solution).
    if (expectedLists.isEmpty()) {
      if (actual == null) {
        return true;
      } else {
        System.out.println("Expected: null");
        System.out.println("Actual:   " + actual);
        return false;
      }
    } else if (actual == null) {
      System.out.println("Expected: Non-null");
      System.out.println("Actual:   null");
      return false;
    }

    // Build some output. If we don't find a match, then we will print it.
    StringBuilder diffOutput = new StringBuilder();

    diffOutput.append("Comparing actual against ")
        .append(expectedLists.size())
        .append(" expected lists.\n");

    // Sort by the candidate ID.
    actual.sort(Comparator.comparing(Candidate::getId));

    // Return true if the actual list matches ANY of the expected lists.
    for (CandidateList expected : expectedLists) {
      // Sort by the candidate ID.
      expected.sort(Comparator.comparing(Candidate::getId));

      // Compare length.
      if (expected.size() != actual.size()) {
        diffOutput.append("Lists differ in size.\n");
        diffOutput.append("Expected: ").append(expected).append("\n");
        diffOutput.append("Actual:   ").append(actual).append("\n");
        continue;
      }

      boolean matchesExpected = true;

      // Compare element-wise.
      for (int c = 0; c < expected.size(); ++c) {
        if (!expected.get(c).equals(actual.get(c))) {
          // The lists don't match. End the comparison and move to the next possible expected list.
          diffOutput.append("Candidates differ.\n");
          diffOutput.append("Expected: ").append(expected.get(c)).append(" in ").append(expected).append("\n");
          diffOutput.append("Actual:   ").append(actual.get(c)).append(" in ").append(actual).append("\n");
          matchesExpected = false;
          break;
        }
      }

      if (matchesExpected) {
        // The lists match! Return without printing anything.
        return true;
      }
    }

    // None of the lists matched. Print and return false.
    System.out.println(diffOutput);
    return false;
  }

  /**
   * Returns the sub-list of candidates at the indices indicated by a bit-vector represented by a
   * long value.
   *
   * @param candidates original list of candidates
   * @param bits       long representing the candidates to select
   * @return the sub-list containing all candidates at the selected indices
   */
  public static CandidateList bitSubset(CandidateList candidates, long bits) {
    return new CandidateList(
            IntStream.range(0, candidates.size())
                    .filter(i -> (bits & (1L << i)) != 0)
                    .mapToObj(candidates::get)
                    .toList());
  }

  /**
   * Compute all possible optimal solutions in an iterative manner. This method takes the same
   * parameters with the same meanings as optimalHiring, but it computes all possible solutions.
   *
   * @param candidates the set of available candidates to hire from (excluding those already hired)
   * @param hiresLeft the maximum number of candidates to hire
   * @return a list of all optimal solutions to the min-cost problem
   */
  public static ArrayList<CandidateList> allOptimalSolutions(CandidateList candidates,
      int hiresLeft) {
    List<CandidateList> options =
        LongStream.range(0, 1L << candidates.size())
                .filter(i -> Long.bitCount(i) <= hiresLeft)
                .mapToObj(i -> bitSubset(candidates, i)).toList();

    if (options.isEmpty()) {
      return new ArrayList<>();
    } else {
      int maxCoveredHours =
          options.stream().map(CandidateList::numCoveredHours).max(Integer::compareTo).get();

      List<CandidateList> bestChoices =
          options.stream().filter(c -> c.numCoveredHours() == maxCoveredHours).toList();

      return new ArrayList<>(bestChoices);
    }
  }

  /**
   * Compute all possible min-coverage solutions in an iterative manner. This method takes the same
   * parameters with the same meanings as minCoverageHiring, but it computes all optimal solutions.
   *
   * @param candidates the set of available candidates to hire from (excluding those already hired)
   * @param minHours   the minimum number of hours we want to cover total
   * @return a list containing all optimal solutions to the min-coverage problem
   */
  public static ArrayList<CandidateList> allMinCoverageSolutions(CandidateList candidates,
      int minHours) {
    List<CandidateList> options =
        LongStream.range(0, 1L << candidates.size())
                .mapToObj(i -> bitSubset(candidates, i))
                .filter(c -> c.numCoveredHours() >= minHours).toList();

    if (options.isEmpty()) {
      return new ArrayList<>();
    } else {
      int minTotalCost =
          options.stream().map(CandidateList::totalCost).min(Integer::compareTo).get();

      List<CandidateList> bestChoices =
          options.stream().filter(c -> c.totalCost() == minTotalCost).toList();

      return new ArrayList<>(bestChoices);
    }
  }
}
