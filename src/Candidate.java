import java.util.Arrays;

/**
 * This class stores information related to a candidate TA
 */
public class Candidate {

  /**
   * Stores the next available candidate ID.
   */
  private static int nextCandidateId = 0;

  /**
   * The unique ID of this candidate.
   */
  private final int candidateId;

  /**
   * The availability of this candidate for each time slot. true indicates available; false
   * indicates not available.
   */
  private final boolean[] availability;

  /**
   * The pay rate of this candidate, or -1 if pay rate is not set.
   */
  private final int payRate;

  /**
   * Create a new candidate with the given availability and no pay rate. This is useful for the case
   * where we want to ignore budgets and pay rates and compare all candidates based solely on hours.
   * 
   * @param availability a boolean array with one boolean for each hour of the schedule. The boolean
   *                     should be false if the candidate is NOT available and true if they are
   *                     available.
   */
  public Candidate(boolean[] availability) {
    this.candidateId = nextCandidateId++;
    this.availability = availability;
    this.payRate = -1;
  }

  /**
   * Create a new candidate with the given availability and pay rate.
   * 
   * @param availability a boolean array with one boolean for each hour of the schedule. The boolean
   *                     should be false if the candidate is NOT available and true if they are
   *                     available.
   * @param payRate      the pay rate of the candidate.
   */
  public Candidate(boolean[] availability, int payRate) {
    this.candidateId = nextCandidateId++;
    this.availability = availability;
    this.payRate = payRate;
  }

  /**
   * @return the candidate's ID.
   */
  public int getId() {
    return candidateId;
  }

  /**
   * @return the availability of the candidate at all times.
   */
  public boolean[] getAvailability() {
    return availability;
  }

  /**
   * @param h the hour we want to check; index starts at 0.
   * @return true if this candidate is available at hour h.
   */
  public boolean isAvailable(int h) {
    return availability[h];
  }

  /**
   * @return the pay rate of this candidate.
   */
  public int getPayRate() {
    return payRate;
  }

  /**
   * Given a set of hours we need a TA to cover, return the set of remaining hours after we hire
   * this TA.
   * 
   * @param hoursNeeded a set of hours that we want to try to maximally cover.
   * 
   * @return hoursNeeded minus any hours covered by this candidate.
   */
  public boolean[] hire(boolean[] hoursNeeded) {
    boolean[] newHoursNeeded = Arrays.copyOf(hoursNeeded, hoursNeeded.length);
    for (int h = 0; h < newHoursNeeded.length; ++h) {
      if (availability[h])
        newHoursNeeded[h] = false;
    }
    return newHoursNeeded;
  }

  /**
   * @param obj another Candidate to compare against this one
   * 
   * @return true if obj's id, availability, and payrate are the same as this candidate.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Candidate other) {
      return this.candidateId == other.candidateId;
    } else {
      return false;
    }
  }

  /**
   * Prints a debugging view of this candidate. The output will be either
   * 
   * <p>
   * [id]AVAILABILITY
   * <p>
   * 
   * OR
   * 
   * <p>
   * [id]AVAILABILITY/PAYRATE
   * <p>
   * 
   * where AVAILABILITY is a list of '#' and '.', one for each time slot, with '#' indicating that
   * the candidate is available and '.' indicating that they are not. And PAYRATE is the candidate's
   * pay rate.
   * 
   * @return a debugging string representing this candidate's info.
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();

    s.append("[").append(candidateId).append("]");

    for (boolean b : availability) {
      s.append(b ? "#" : ".");
    }

    if (payRate != -1) {
      s.append("/").append(payRate);
    }
    return s.toString();
  }
}
