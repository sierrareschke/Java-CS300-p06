//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Class containing various recursive functions to solve various
//           hiring problems (including greedyHiring, optimalHiring, and
//           minCoverageHiring)
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
// Online Sources:  Utilized chatGPT to figure out how to initialize bestCost
//                  to the maximum allowable integer value to then be able to 
//                  check if the cost from my implementation is smaller.
//                  Output: In Java, you can initialize an integer to its 
//                  maximum allowable value by using the constant Integer.MAX_VALUE. 
//                  This constant represents the maximum value that a 32-bit signed 
//                  integer can hold. This will set the maxValue variable to the 
//                  maximum allowable value for integers, which is 2,147,483,647 in Java. 
//                  You can use this constant when you need to represent the maximum value 
//                  for integer variables or when you want to compare against it in your code.
//
///////////////////////////////////////////////////////////////////////////////



/**
 * A simple program for solving various hiring problems.
 */
public class Hiring {

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a
   * maximum number of tas to hire, return the set of hires made using a greedy strategy that 
   * always chooses the candidate that increases hours covered the most. In this function, we will 
   * ignore pay rates.
   * 
   * @param candidates - the set of available candidates to hire from (excluding already hired)
   * @param hired - the list of those currently hired
   * @param hiresLeft - the maximum number of candidates to hire
   * @return
   */
  public static CandidateList greedyHiring(CandidateList candidates, 
      CandidateList hired, int hiresLeft) {

    //System.out.println(candidates);
    //System.out.println("Hires left: " + hiresLeft);

    // Base case
    if (hiresLeft == 0) {
      if (hired == null || hired.size() == 0) {
        return new CandidateList(); 
        // Return an empty CandidateList when no hires are made and no initial hired candidates
      }
      return hired; // Return the new hires made
    }

    // set the initial max difference and best candidate to compare against
    int maxDifference = -1; 
    Candidate bestCandidate = null;

    // loop through current candidates to find candidate available for max # hours
    for (Candidate candidate : candidates ) {

      // get numCoveredHours with current list of candidates
      int currentHours = hired.numCoveredHours();
      //System.out.println("currentHours: " + currentHours);

      // get numCoveredHours with adding candidate
      int hoursWithCandidate = hired.withCandidate(candidate).numCoveredHours();
      //System.out.println("hoursWithCandidate: " + hoursWithCandidate);

      // add candidate if difference is larger than with previous candidate; break 
      int hoursDifference = hoursWithCandidate - currentHours;
      if (hoursDifference > maxDifference) {
        bestCandidate = candidate;
        maxDifference = hoursDifference; 
      }
    }

    // hire candidate (add to hired list)
    CandidateList newHired = new CandidateList(hired);
    newHired.add(bestCandidate);

    // remove candidate from candidates list
    CandidateList remainingCandidates = new CandidateList(candidates);
    remainingCandidates.remove(bestCandidate);

    // call greedyHiring on the remaining candidates with hiresLeft-1
    return greedyHiring(remainingCandidates, newHired, hiresLeft - 1);

  }

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a
   * maximum number of tas to hire, return the set of hires that maximizes number of scheduled 
   * hours. In this function, we will ignore pay rates.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already hired)
   * @param hired - the list of those currently hired
   * @param hiresLeft - the maximum number of candidates to hire
   * @return
   */
  public static CandidateList optimalHiring(CandidateList candidates, 
      CandidateList hired, int hiresLeft) {

    // base case: no hires left, return empty CandidateList if no candidates in hired parameter
    // or return hired parameter if there are candidates in hired parameter
    if (hiresLeft == 0) {
      if (hired == null || hired.size() == 0) {
        return new CandidateList(); 
        // Return an empty CandidateList when no hires are made and no initial hired candidates
      }
      return hired; // Return the new hires made, or the original hired parameter
    }

    // set the max covered hours and optimal hires to be compared against
    int maxCoveredHours = 0;
    CandidateList optimalHires = new CandidateList();

    // loop through candidates (like permutations)
    for (int i = 0; i < candidates.size(); i++) {
      Candidate firstCandidate = candidates.get(i);
      // System.out.println("firstCandidate: " + firstCandidate);

      // "permute" the REST of the candidates
      CandidateList updatedHired = hired.withCandidate(firstCandidate);
      CandidateList remainingCandidates = candidates.withoutCandidate(firstCandidate);

      // System.out.println("updatedHired: " + updatedHired);
      // System.out.println("remainingCandidates" + remainingCandidates);

      // check if the possible new max covered hours is > the current max covered hours
      if (updatedHired.numCoveredHours() > maxCoveredHours ) {
        // if new max is larger, update the max covered hours and the optimal hires CharacterList
        maxCoveredHours = updatedHired.numCoveredHours();
        optimalHires = updatedHired.deepCopy();
        //        System.out.println();
        //        System.out.println("--");
        //        System.out.println("maxCoveredHours: " + maxCoveredHours);
        //        System.out.println("optimalHires" + optimalHires);
        //        System.out.println("--");
        //        System.out.println();
      }

      // compute the recursive call with the remaining candidates and hiresLeft-1
      CandidateList result = optimalHiring(remainingCandidates, updatedHired, hiresLeft - 1);

      // Compare result with optimalHires and update if necessary
      if (result.numCoveredHours() > maxCoveredHours) {
        maxCoveredHours = result.numCoveredHours();
        optimalHires = result.deepCopy();
      }
    }

    // return the current list of optimal hires
    //System.out.println("returning: " + optimalHires);
    return optimalHires;
  }


  /**
   * Knapsack dual problem: find the minimum-budget set of hires to achieve a threshold # of hours. 
   * That is, given a set of candidates, a set of already-hired candidates, and a minimum number of
   * hours we want covered, what is the cheapest set of candidates we can hire that cover at least
   * that minimum number of hours specified.

   * 
   * @param candidates - the set of available candidates to hire from (excluding those already hired)
   * @param hired - the set of candidates already hired
   * @param minHours - the minimum number of hours we want to cover total
   * @return
   */
  public static CandidateList minCoverageHiring(CandidateList candidates, 
      CandidateList hired, int minHours) {
    // base case: if no minimum hours are required, return an empty CandidateList
    if (minHours <= 0) {
      return hired; 
    }

    // base case: if there are no more candidates to hire, return null 
    if (candidates.isEmpty()) {
      return null;
    }

    // account for case where there is no solution as the min required hours cannot be met
    if ((candidates.numCoveredHours() + hired.numCoveredHours())< minHours) {
      return null;
    }

    // initialize the best solution and its cost
    CandidateList bestSolution = null;
    int bestCost = Integer.MAX_VALUE;

    // hire the current candidate
    Candidate currentCandidate = candidates.get(0);
    CandidateList withCurrent = hired.withCandidate(currentCandidate);
    CandidateList remainingCandidates = candidates.withoutCandidate(currentCandidate);

    // calculate the cost with the current candidate hired
    int costWithCurrent = withCurrent.totalCost();

    // get the current hours covered
    int hoursCovered = withCurrent.numCoveredHours();

    // check if the solution with the current candidate meets the minimum hours
    if (hoursCovered >= minHours) {
      // check if the current cost is less than the best cost
      if (costWithCurrent < bestCost) {
        // update the best cost and solution to the current cost and solution
        bestCost = costWithCurrent;
        bestSolution = withCurrent;
      }
    } else { // if the current hours does not meet the minimum hours, hire the current candidate
      // call minCoverageHiring again with the current candidate hired (in hired param)
      // update the minimum hours needed to be what remains after hiring the current candidate
      CandidateList solutionWithCurrent = minCoverageHiring(remainingCandidates, withCurrent, 
          minHours);

      // if the new cost is lower than the previous cost, update the best cost and solution
      if (solutionWithCurrent != null && solutionWithCurrent.totalCost() < bestCost) {
        bestCost = solutionWithCurrent.totalCost();
        bestSolution = solutionWithCurrent;
      }
    }

    // see if not hiring the current candidate would result in a lower totalCost
    CandidateList solutionWithoutCurrent = minCoverageHiring(remainingCandidates, hired, minHours);
    if (solutionWithoutCurrent != null && solutionWithoutCurrent.totalCost() < bestCost) {
      bestSolution = solutionWithoutCurrent;
    }

    // return the best solution found
    return bestSolution;

  }

}
