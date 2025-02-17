//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Class containing various testing methods to test the recursive 
//           functions present in the Hiring class (including greedyHiring, 
//           optimalHiring, and minCoverageHiring). Tests base cases, 
//           recursive cases, and fuzz testing.
// Course:   CS 300 Fall 2023
//
// Author:   Sierra Reschke
// Email:    sgreschke@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         N/A
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;
/**
 * Testing class containing various methods to test the recursive functions present
 * in the Hiring class (including base case, recursive case, and fuzz testing).
 */
public class HiringTesting {

  /** 
   * Testing method to test greedyHiring on base case (non-recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyHiringBaseTest() {
    // testing the case where no recursive calls are made
    { // Case (1) greedyHiringBaseTest - hiresLeft = 0 with null CharacterLists
      CandidateList actual = Hiring.greedyHiring(new CandidateList(), new CandidateList(), 0);
      CandidateList expected = new CandidateList();

      // check that greedyHiring returns null when hiresLeft = 0
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false) {
        System.out.println("Error in greedyHiringBaseTest case 1");
        return false;
      }
    }

    { // Case (2) greedyHiringBaseTest - hiresLeft = 0 with non-null CharacterLists

      // random, non-null candidates
      CandidateList randomCandidatesTest = HiringTestingUtilities.generateRandomInput(8, 5);

      // non-null hired 
      CandidateList hiringTest = new CandidateList();

      // create candidate Alice with given availability and add to hired
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice); 
      hiringTest.add(alice);

      // call greedyHiring with non-null characteristics but with 0 hires left
      CandidateList actual = Hiring.greedyHiring(randomCandidatesTest, hiringTest, 0);

      // expected to return the CandidateList with Alice already hired
      CandidateList expected = new CandidateList();
      expected.add(alice);

      // check that greedyHiring returns hired parameter when hiresLeft = 0
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false) {
        System.out.println("Error in greedyHiringBaseTest case 2");
        return false;
      }
    }

    // all tests passed --> return true
    return true;
  }

  /**
   * Testing method to test greedyHiring on recursive case (including recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyHiringRecursiveTest() {

    { // Case (1) for greedyHiringRecursiveTest - base case as outlined in write-up
      // create Candidates based on write up to test in the greedingHiring method
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // want to hire 2 candidates out of the 3 (should be Alice and either Bob or Carol)
      int totalHires = 2;

      // call greedyHiring to get actual returned list
      CandidateList actual = Hiring.greedyHiring(candidatesTest, hiringTest, totalHires);

      // expected list should have alice and bob
      CandidateList expected = new CandidateList();
      expected.add(alice);
      expected.add(bob); // since bob and carol have same inc, will hire bob bc he's first in list

      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in greedyHiringRecursiveTest case 1");
        return false;
      }

    }

    { // Test (2) for greedyHiringRecursiveTest - change ordering of candidates in list
      // create candidates based on availability given
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();
      candidatesTest.add(bob);
      candidatesTest.add(carol);
      candidatesTest.add(alice);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // want to hire 2 candidates out of the 3 (should be Alice and either Bob or Carol)
      int totalHires = 2;

      // call greedyHiring to get actual returned list
      CandidateList actual = Hiring.greedyHiring(candidatesTest, hiringTest, totalHires);

      // expected list should have bob and alice
      CandidateList expected = new CandidateList();
      expected.add(bob); // since bob and carol have same inc, will hire bob bc he's first in list
      expected.add(alice);
      //System.out.println("Expected: " + expected);

      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in greedyHiringRecursiveTest case 2");
        return false;
      }
    }


    { // Case (3) for greedyHiringRecursiveTest - hired 
      // create Candidates based on write up to test in the greedingHiring method
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();
      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // want to hire 2 candidates out of the 3 (should be alice and either bob or carol)
      int totalHires = 2;

      // call greedyHiring to get actual returned list
      CandidateList actual = Hiring.greedyHiring(candidatesTest, hiringTest, totalHires);

      // expected list should have bob and alice
      CandidateList expected = new CandidateList();
      expected.add(alice);
      expected.add(bob); // since bob and carol have same inc, will hire bob bc he's first in list

      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in greedyHiringRecursiveTest case 3");
        return false;
      }

    }
    // all test cases pass --> return true
    return true;
  }


  /**
   * Testing method to test greedyHiring on base case (no recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all tests pass, false otherwise
   */
  public static boolean optimalHiringBaseTest() {
    { // Case (1) optimalHiringBaseTest - hiresLeft = 0 with null CharacterLists
      CandidateList actual = Hiring.optimalHiring(new CandidateList(), new CandidateList(), 0);
      CandidateList expected = new CandidateList();

      // check that optimalHiring returns null when hiresLeft = 0
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false) {
        System.out.println("Error in optimalHiringBaseTest case 1");
        return false;
      }
    }

    { // Case (2) optimalHiringBaseTest - hiresLeft = 0 with non-null CharacterLists

      // random, non-null candidates
      CandidateList randomCandidatesTest = HiringTestingUtilities.generateRandomInput(8, 5); 

      // non-null hired 
      CandidateList hiringTest = new CandidateList();

      // create candidate Alice with given availability and add to hired
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice); 
      hiringTest.add(alice);

      // call optimalHiring with non-null characteristics but with 0 hires left
      CandidateList actual = Hiring.optimalHiring(randomCandidatesTest, hiringTest, 0);

      // expected to return the CandidateList with Alice already hired
      CandidateList expected = new CandidateList();
      expected.add(alice);

      // check that optimalHiring returns hired parameter when hiresLeft = 0
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false) {
        System.out.println("Error in optimalHiringBaseTest case 2");
        return false;
      }
    }

    // all tests passed --> return true
    return true;
  }

  /**
   * Testing method to test optimalHiring on recursive case (including recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all tests pass, false otherwise
   */
  public static boolean optimalHiringRecursiveTest() {

    { // Case (1) - Alice, Bob, Carol from write-up; hire Carol and Bob
      // create Candidates based on write up to test in the optimalHiring method
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // want to hire 2 candidates out of the 3 (should be Bob and Carol)
      int totalHires = 2;

      // call optimalHiring to get actual returned solution
      CandidateList actual = Hiring.optimalHiring(candidatesTest, hiringTest, totalHires);

      // expected list should have bob and carol
      CandidateList expected = new CandidateList();
      expected.add(carol);
      expected.add(bob); // hiring carol and bob is an optimal solution with hours covered = 6

      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in optimalHiringRecursiveTest case 1");
        return false;
      }

    }

    { // Case (2) - one candidate already hired, 3 candidates with 1 hire

      // create candidates and availability arrays
      boolean[] availabilityAlice = {false, true, false, false, false, false, false};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, false, false, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, true, true, false};
      Candidate carol = new Candidate(availabilityCarol);

      boolean[] availabilityDave = {false, false, false, true, true, false, false};
      Candidate dave = new Candidate(availabilityDave);

      // create a new CandidateList and add carol, bob, dave
      CandidateList candidatesTest = new CandidateList();
      candidatesTest.add(bob);
      candidatesTest.add(carol);
      candidatesTest.add(dave);

      // create a hired list and add alice
      CandidateList hiringTest = new CandidateList();
      hiringTest.add(alice);

      // want to hire 1 candidates out of the 3 (should be Carol; Alice already hired)
      int totalHires = 1;

      // call optimalHiring to get actual returned solution
      CandidateList actual = Hiring.optimalHiring(candidatesTest, hiringTest, totalHires);

      // expected solution should have alice and carol
      CandidateList expected = new CandidateList();
      expected.add(alice); // Alice already hired
      expected.add(carol); // hiring carol is an optimal solution with hours covered = 6


      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in optimalHiringRecursiveTest case 2");
        return false;
      }
    }


    { // Case (3) - no candidates already hired, 4 candidates, hire 3
      // create candidates
      boolean[] availabilityAlice = {false, true, false, false, false, false, false};
      Candidate alice = new Candidate(availabilityAlice);

      boolean[] availabilityBob = {true, true, false, false, false, false, false};
      Candidate bob = new Candidate(availabilityBob);

      boolean[] availabilityCarol = {false, false, true, true, true, true, false};
      Candidate carol = new Candidate(availabilityCarol);

      boolean[] availabilityDave = {false, false, false, false, false, false, true};
      Candidate dave = new Candidate(availabilityDave);

      // create a new CandidateList and add alice, carol, bob, dave
      CandidateList candidatesTest = new CandidateList();
      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);
      candidatesTest.add(dave);


      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // want to hire 3 candidates out of the 4 (should be Bob, Carol, Dave)
      int totalHires = 3;

      // call optimalHiring to get actual returned solution
      CandidateList actual = Hiring.optimalHiring(candidatesTest, hiringTest, totalHires);

      // expected list should have bob, carol, adn dave
      CandidateList expected = new CandidateList();
      expected.add(bob); 
      expected.add(carol); 
      expected.add(dave); // hiring bob, carol, dave is optimal solution with hours = 7


      // check if expected == actual via utility method

      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in optimalHiringRecursiveTest case 3");
        return false;
      }
    }
    // all tests pass, return true
    return true; // all cases passed
  }

  /**
   * Fuzz test for optimalHiring to test 100-200 (150 chosen) randomly -
   * generated problem instances and ensure the returned solution is 
   * present in the expected list of solutions.
   * 
   * @return true if all randomly generated problem instances return true,
   * false otherwise
   */
  public static boolean optimalHiringFuzzTest() {
    // create and seed Random instance
    Random randGen = new Random(222);

    // test 150 randomly generated problem instances
    int numTests = 150;
    for (int i = 0; i < numTests; i++) {
      // generate random number [1,5] for numHours param
      int numHours = randGen.nextInt(5) + 1; // [0,5) + 1 --> [1,5]

      // generate random number [1,10] for numCandidates
      int numCandidates = randGen.nextInt(10) + 1; // [0,10) + 1 --> [1,10]

      // (optimalHiring) generate random number of desired hires in range [1, numCandidates]
      int desiredHires = randGen.nextInt(0,numCandidates) + 1; // [1, numCandidates]

      // function already flips a coin for availability

      // generate candidate list
      CandidateList candidates = 
          HiringTestingUtilities.generateRandomInput(numHours, numCandidates);

      // generate hired list
      CandidateList hired = new CandidateList();

      // call my Hiring implementation to solve the generated input (actual)
      CandidateList actual = Hiring.optimalHiring(candidates, hired , desiredHires);

      // generate ArrayList of all optimal solutions
      ArrayList<CandidateList> expectedLists = 
          HiringTestingUtilities.allOptimalSolutions(candidates, desiredHires);

      // ensure produced solution is in the solution from utilities allOptimalSolutions method
      // compareCandidateLists with expected = result from allOptimalSolutions 
      // and actual = from my implementation
      boolean solutionPresent = HiringTestingUtilities.compareCandidateLists(expectedLists, actual);

      if (solutionPresent == false) {
        System.out.println("actual solution is not in list of expected solutions");
        System.out.println("actual: " + actual);
        System.out.println("expected: " + expectedLists);
        return false;
      }
    }
    //System.out.println("optimalHiringFuzzTest solution verified.");
    return true;
  }

  /**
   * Testing method to test minCoverageHiring on base case (no recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all cases pass, false otherwise
   */
  public static boolean minCoverageHiringBaseTest() {
    // testing the case where no recursive calls are made

    { // Case (2) - min 5 hours but not possible --> no solution, should return null
      // create Candidates based on write up to test in the optimalHiring method
      boolean[] availabilityAlice = {true, false, false, false, false, false};
      Candidate alice = new Candidate(availabilityAlice, 4);

      boolean[] availabilityBob = {true, false, false, false, false, false};
      Candidate bob = new Candidate(availabilityBob, 2);

      boolean[] availabilityCarol = {false, false, false, false, false, false};
      Candidate carol = new Candidate(availabilityCarol, 3);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // desired min hours covered = 5
      int minHours = 5;

      // call the function to get the result produced by the method in Hiring
      CandidateList actual = Hiring.minCoverageHiring(candidatesTest, hiringTest, minHours);
      //System.out.println("actual: " + actual);
      CandidateList expected = null;
      //System.out.println("expected: " + expected);

      // check if expected == actual via utility method

      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in minCoverageHiringBaseTest case 2");
        return false;
      }
    }
    // all tests passed --> return true
    return true;
  }

  /**
   * Testing method to test minCoveragehiring on recursive cases case (with recursion calls)
   * on a variety of testing cases
   * 
   * @return true if all tests pass, false otherwise
   */
  public static boolean minCoverageHiringRecursiveTest() {
    { // Case (1) - Alice, Bob, Carol from write-up; 4 hours --> hire Alice, $4 
      // create Candidates based on write up to test in the optimalHiring method
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice, 4);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob, 2);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol, 3);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // desired min hours covered = 4
      int minHours = 4;

      // call the function to get the result produced by the method in Hiring
      CandidateList actual = Hiring.minCoverageHiring(candidatesTest, hiringTest, minHours);

      CandidateList expected = new CandidateList();
      expected.add(alice); // hiring alice is optimal solution with $4 total cost

      // check if expected == actual via utility method

      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in minCoverageHiringRecursiveTest case 1");
        return false;
      }
    }

    { // Case (2) - min 5 hours --> hire Bob and Carol for $5 and 6 hours covered
      // create Candidates based on write up to test in the optimalHiring method
      boolean[] availabilityAlice = {true, false, false, true, true, true};
      Candidate alice = new Candidate(availabilityAlice, 4);

      boolean[] availabilityBob = {true, true, false, false, true, false};
      Candidate bob = new Candidate(availabilityBob, 2);

      boolean[] availabilityCarol = {false, false, true, true, false, true};
      Candidate carol = new Candidate(availabilityCarol, 3);

      // create a new CandidateList and add alice, carol, bob
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(alice);
      candidatesTest.add(bob);
      candidatesTest.add(carol);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // desired min hours covered = 5
      int minHours = 5;

      // call the function to get the result produced by the method in Hiring
      CandidateList actual = Hiring.minCoverageHiring(candidatesTest, hiringTest, minHours);
      //System.out.println("actual: " + actual);
      CandidateList expected = new CandidateList();
      expected.add(bob); 
      expected.add(carol); // hiring bob and carol is optimal solution with $5 total cost
      //System.out.println("expected: " + expected);

      // check if expected == actual via utility method

      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in minCoverageHiringRecursiveTest case 2");
        return false;
      }
    }


    { // Case (3) - failing solution in fuzz testing
      // create Candidates based on write up to test in the optimalHiring method
      boolean[] availability875 = {false, false, true, true, false};
      Candidate candidate875 = new Candidate(availability875, 1);

      boolean[] availability876 = {false, true, true, true, false};
      Candidate candidate876 = new Candidate(availability876, 1);

      boolean[] availability877 = {true, false, false, true, true};
      Candidate candidate877 = new Candidate(availability877, 2);

      boolean[] availability878 = {false, false, true, false, false};
      Candidate candidate878 = new Candidate(availability878, 2);

      boolean[] availability879 = {true, false, false, true, true};
      Candidate candidate879 = new Candidate(availability879, 1);

      // create a new CandidateList and add all candidates
      CandidateList candidatesTest = new CandidateList();

      candidatesTest.add(candidate875);
      candidatesTest.add(candidate876);
      candidatesTest.add(candidate877);
      candidatesTest.add(candidate878);
      candidatesTest.add(candidate879);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // desired min hours covered = 3
      int minHours = 3;

      // call the function to get the result produced by the method in Hiring
      CandidateList actual = Hiring.minCoverageHiring(candidatesTest, hiringTest, minHours);
      //System.out.println("actual: " + actual);
      CandidateList expected = new CandidateList();
      expected.add(candidate876); 
      //System.out.println("expected: " + expected);

      // check if expected == actual via utility method

      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in minCoverageHiringRecursiveTest case 3");
        return false;
      }
    }


    { // Case (4)  
      // create Candidates from fuzz testing
      boolean[] availability892 = {true, false, false};
      Candidate candidate892 = new Candidate(availability892, 1);

      boolean[] availability893 = {false, true, false};
      Candidate candidate893 = new Candidate(availability893, 1);

      boolean[] availability894 = {true, false, true};
      Candidate candidate894 = new Candidate(availability894, 1);

      // create a new CandidateList and add all
      CandidateList candidatesTest = new CandidateList();
      candidatesTest.add(candidate892);
      candidatesTest.add(candidate893);
      candidatesTest.add(candidate894);
      //System.out.println(candidatesTest);

      // create an empty hired list
      CandidateList hiringTest = new CandidateList();

      // desired min hours covered = 3
      int minHours = 3;

      // call the function to get the result produced by the method in Hiring
      CandidateList actual = Hiring.minCoverageHiring(candidatesTest, hiringTest, minHours);

      CandidateList expected = new CandidateList();
      expected.add(candidate893);
      expected.add(candidate894); 
      // covers 3 hours with cost = $2

      //      System.out.println("actual: " + actual);
      //      System.out.println("expected: " + expected);

      // check if expected == actual via utility method
      boolean result = HiringTestingUtilities.compareCandidateLists(expected, actual);
      if (result == false ) { 
        System.out.println("Error in minCoverageHiringRecursiveTest case 4");
        return false;
      }
    }
    // all tests pass, return true
    return true;
  }

  /**
   * Fuzz test for minCoverageHiring to test 100-200 (150 chosen) randomly -
   * generated problem instances and ensure the returned solution is 
   * present in the expected list of solutions.
   * 
   * @return true if all randomly generated problem instances return true,
   * false otherwise
   */
  public static boolean minCoverageHiringFuzzTest() {
    // create and seed Random instance
    Random randGen = new Random(444);

    // test 150 randomly generated problem instances
    int numTests = 150;
    for (int i = 0; i < numTests; i++) {
      // generate random number [1,5] for numHours in schedule param
      int numHours = randGen.nextInt(5) + 1; // [0,5) + 1 --> [1,5]

      // generate random number [1,10] for numCandidates
      int numCandidates = randGen.nextInt(10) + 1; // [0,10) + 1 --> [1,10]

      // (minCoverageHiring) generate a random # of hours to schedule in range [1, numHours]
      int minHours = randGen.nextInt(0, numHours) + 1;
      //System.out.println("minHours: " + minHours);
      // function already flips a coin for availability

      // set maxPayRate to be relatively small ( < # candidates/2)
      //if (numCandidates == 0) {continue;}
      int maxPayRate;
      if (numCandidates == 1 ) {
        maxPayRate = randGen.nextInt(0, 1) + 1 ;
      }
      else {maxPayRate = randGen.nextInt(0, numCandidates/2) + 1 ;}

      // generate candidate list
      CandidateList candidates = 
          HiringTestingUtilities.generateRandomInput(numHours, numCandidates, maxPayRate);
      //System.out.println("Candidates: " + candidates);

      // generate hired list
      CandidateList hired = new CandidateList();

      // call my Hiring implementation to solve the generated input (actual)
      CandidateList actual = Hiring.minCoverageHiring(candidates, hired , minHours);

      // generate ArrayList of all optimal solutions
      ArrayList<CandidateList> expectedLists = 
          HiringTestingUtilities.allMinCoverageSolutions(candidates, minHours); 

      // ensure produced solution is in the solution from utilities allOptimalSolutions method
      // compareCandidateLists with expected = result from allOptimalSolutions
      // and actual = from my implementation
      boolean solutionPresent = HiringTestingUtilities.compareCandidateLists(expectedLists, actual);

      if (solutionPresent == false) {
        System.out.println("actual solution is not in list of expected solutions");
        System.out.println("actual: " + actual);
        System.out.println("expected: " + expectedLists);
        return false;
      }
    }

    //System.out.println("minCoverageHiring solution verified");
    // all minCoverageHiringFuzzTest iterations verified, return true
    return true;
  }

  /**
   * Main method to call the testing methods and print out the results.
   * @param args
   */
  public static void main(String[] args) {
    // set boolean values for all the results of the testing methods
    boolean greedyHiringBaseTestResult = greedyHiringBaseTest();
    boolean greedyHiringRecursiveTestResult = greedyHiringRecursiveTest();
    boolean optimalHiringBaseTestResult = optimalHiringBaseTest();
    boolean optimalHiringRecursiveTestResult = optimalHiringRecursiveTest();
    boolean optimalHiringFuzzTestResult = optimalHiringFuzzTest();
    boolean minCoverageHiringBaseTestResult = minCoverageHiringBaseTest();
    boolean minCoverageHiringRecursiveTestResult = minCoverageHiringRecursiveTest();
    boolean minCoverageHiringFuzzTestResult = minCoverageHiringFuzzTest();

    // print out the results of the test cases
    System.out.println();
    System.out.println("-------------------------------------------------------");
    System.out.println("greedyHiringBaseTest result: " + greedyHiringBaseTestResult);
    System.out.println("greedyHiringRecursiveTest result: " + greedyHiringRecursiveTestResult);
    System.out.println("optimalHiringBaseTest result: " + optimalHiringBaseTestResult);
    System.out.println("optimalHiringRecursiveTest result: " + optimalHiringRecursiveTestResult);
    System.out.println("optimalHiringFuzzTest result: " + optimalHiringFuzzTestResult);
    System.out.println("minCoverageHiringBaseTest result: " + minCoverageHiringBaseTestResult);
    System.out.println("minCoverageHiringRecursiveTest result: " 
        + minCoverageHiringRecursiveTestResult);
    System.out.println("minCoverageHiringFuzzTest result: " + minCoverageHiringFuzzTestResult);
    System.out.println("-------------------------------------------------------");

  }

}
