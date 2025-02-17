import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends ArrayList by adding utilities related to the hiring problem. Note that you may
 * not need all of these utilities in your implementation.
 */
public class CandidateList extends ArrayList<Candidate> {
  /**
   * Ignore this. Makes the compiler happy. If you are curious, google "java Serialization".
   */
  @Serial
  private static final long serialVersionUID = -1605782814356060242L;

  /**
   * Creates a new empty CandidateList
   */
  public CandidateList() {
    super();
  }

  /**
   * Creates a new CandidateList containing the given ArrayList of candidates. Pass-through for the
   * ArrayList super class constructor.
   *
   * @param candidates list of Candidates for this CandidateList to contain
   */
  public CandidateList(List<Candidate> candidates) {
    super(candidates);
  }

  /**
   * Makes a deep copy (not deepest copy) of this CandidateList while excluding the given candidate.
   * 
   * @param c a candidate to exclude from the deep copy.
   * 
   * @return a deep copy of this list without the given candidate.
   * @throws IllegalArgumentException if this list does not contain
   *                                  the given candidate.
   */
  public CandidateList withoutCandidate(Candidate c) {
    if (!this.contains(c)) {
      throw new IllegalArgumentException("List does not contain candidate.");
    }
    CandidateList wc = this.deepCopy();
    wc.remove(c);
    return wc;
  }

  /**
   * Makes a deep copy (not deepest copy) of this CandidateList while adding the given candidate.
   *
   * @param c a candidate to add to the deep copy.
   *
   * @return a deep copy of this list with the given candidate.
   * @throws IllegalArgumentException if this list already contains
   *                                  the given candidate.
   */
  public CandidateList withCandidate(Candidate c) {
    if (this.contains(c)) {
      throw new IllegalArgumentException("List already contains candidate.");
    }
    CandidateList wc = this.deepCopy();
    wc.add(c);
    return wc;
  }


  /**
   * For the candidates in this list, this method returns the total number of hours during the day
   * that there is at least one hired candidate who is available to hold office hours. This method
   * does not need to be recursive. <br>
   * 
   * For example, consider the following scenario with three candidates and their availabilities
   * over the course of four hours. <br>
   * <br>
   * 
   * <pre>{@code
   * | Candidate | 5PM | 6PM | 7PM | 8PM | Hired | 
   * | Alice     |  -  |  +  |  +  |  -  |  -    |  
   * | Bob       |  +  |  +  |  -  |  +  |  +    | 
   * | Carol     |  -  |  -  |  -  |  +  |  +    | 
   * --------------------------------------------- 
   * | Covered   |  +  |  +  |  -  |  +  |  3/4 hours covered
   * }</pre>
   * 
   * <br>
   * Of the two candidates which we have chosen to hire, one is available at 5 PM, one is available
   * at 6 PM, none are available at 7 PM, and two are available at 8 PM. Therefore, in this case the
   * method should return 3, as the hours of 5 PM, 6 PM, and 8 PM have at least one hired candidate
   * who is available.
   *
   * @return the total number of hours during the day that there is at least one hired candidate who
   *         is available to hold office hours
   */
  public int numCoveredHours() {
    if (this.size() == 0)
      return 0;

    int hours = 0;
    // For each hour...
    for (int h = 0; h < this.get(0).getAvailability().length; ++h) {
      for (Candidate c : this) {
        if (c.isAvailable(h)) {
          // If ANY candidate is available at this hour, increment the number of hours available
          hours++;
          // To stop double-counting
          break;
        }
      }
    }

    return hours;
  }

  /**
   * Compute the total cost of hiring this list of candidates.
   * 
   * @return the sum of pay rates for all candidates in this list.
   */
  public int totalCost() {
    int total = 0;
    for (Candidate c : this) {
      total += c.getPayRate();
    }
    return total;
  }

  /**
   * Returns a human-readable view of the candidates in this list.
   * 
   * @return a helpful debugging view of the candidates in this list.
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Candidate c : this) {
      s.append(c).append(" ; ");
    }
    if (this.isEmpty()) {
      s.append("<empty list> ; ");
    }

    s.append("(hours: ").append(numCoveredHours());

    if (totalCost() > 0) {
      s.append(", cost: ").append(totalCost()).append(")");
    } else {
      s.append(")");
    }

    return s.toString();
  }

  /**
   * Creates a deep copy (not deepest copy) of this list.
   * 
   * @return a deep copy of this list.
   */
  public CandidateList deepCopy() {
    return new CandidateList(this);
  }
}


