/*
 * Copyright 2013 Adrien Favre-Bully, Florian Froese, Adrian Schmidmeister
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * The Smith-Waterman algorithm performs local sequence alignment; that is, for determining similar
 * regions between two strings or nucleotide or protein sequences. Instead of looking at the total
 * sequence, the Smith-Waterman algorithm compares segments of all possible lengths and optimizes
 * the similarity measure.
 *
 * For creation please use default SmithWatermanDistance or the Builder {@link Builder}.
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Levenshtein_distance">http://en.wikipedia.org/wiki/Levenshtein_distance</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class SmithWatermanDistance implements DistanceMetric, SimilarityMetric {

  private static final double DEFAULT_MATCH_SCORE = 2.0;
  private static final double DEFAULT_MISMATCH_SCORE = -1.0;
  private static final double DEFAULT_GAP_SCORE = -1.0;

  private double matchScore;
  private double mismatchScore;
  private double gapScore;

  private SmithWatermanDistance(Builder b) {
    matchScore = b.bMatchScore;
    mismatchScore = b.bMismatchScore;
    gapScore = b.bGapScore;
  }

  // ===== Metric Methods =====

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    double dist = computeDistance(s1, s2);

    return dist / Utilities.max(s1.length(), s2.length()) / matchScore;
  }

  /**
   * @param s1 String, first string
   * @param s2 String, second string
   * @return double Smith-Waterman Distance between String s1 and s2
   */
  @Override
  public double computeDistance(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.DISTANCE_EMPTY_EMPTY;
    }

    double alignment[][] = new double[s1.length() + 1][s2.length() + 1];
    double mismatchScore = this.mismatchScore;
    double matchScore = this.matchScore;
    double gapScore = this.gapScore;

    double match, delete, insert, cost;

    double maxScore = 0.0;

    for (int i = 0; i <= s1.length(); i++) {
      alignment[i][0] = 0;
    }

    for (int j = 0; j <= s2.length(); j++) {
      alignment[0][j] = 0;
    }

    for (int i = 1; i <= s1.length(); i++) {
      for (int j = 1; j <= s2.length(); j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          cost = matchScore;
        } else {
          cost = mismatchScore;
        }
        match = alignment[i - 1][j - 1] + cost;
        delete = alignment[i - 1][j] + gapScore;
        insert = alignment[i][j - 1] + gapScore;
        alignment[i][j] = Utilities.max(match, delete, insert, 0);
        if (alignment[i][j] > maxScore) {
          maxScore = alignment[i][j];
        }
      }
    }

    return maxScore;
  }

  // ===== Builder Methods =====

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see SmithWatermanDistance
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return A standard SmithWatermanDistance object
   * @return        {@link SmithWatermanDistance}
   * @see SmithWatermanDistance
   */
  public static SmithWatermanDistance getInstance() {
    return new Builder().build();
  }
  // ===== Builder Class =====

  /**
   * SmithWatermanDistance Builder
   *
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   * @since 1.0
   */
  public static class Builder {

    private double bMatchScore;
    private double bMismatchScore;
    private double bGapScore;

    public Builder() {
      bMatchScore = DEFAULT_MATCH_SCORE;
      bMismatchScore = DEFAULT_MISMATCH_SCORE;
      bGapScore = DEFAULT_GAP_SCORE;
    }

    public SmithWatermanDistance build() {
      // parameters have no constraints

      SmithWatermanDistance d = new SmithWatermanDistance(this);

      return d;
    }

    /**
     * Match score (can be any double)
     *
     * @param matchScore double
     * @return this builder object
     */
    public Builder matchScore(double matchScore) {
      bMatchScore = matchScore;
      return this;
    }

    /**
     * Mismatch score (can be any double)
     *
     * @param mismatchScore double
     * @return this builder object
     */
    public Builder mismatchScore(double mismatchScore) {
      bMismatchScore = mismatchScore;
      return this;
    }

    /**
     * Gap score (can be any double)
     *
     * @param gapScore double
     * @return this builder object
     */
    public Builder gapScore(double gapScore) {
      bGapScore = gapScore;
      return this;
    }
  }

}
