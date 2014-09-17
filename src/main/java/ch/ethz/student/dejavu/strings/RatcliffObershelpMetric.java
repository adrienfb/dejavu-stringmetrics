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

import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * RatcliffObershelp distance implementation
 *
 * The implementation follows the definition of the National Institute of Standards and Technology
 *
 * <p>For creation please use default distance or the Builder {@link Builder} provided by the static
 * methods.</p>
 *
 * <p>Wikipedia: <a href="http://xlinux.nist.gov/dads/HTML/ratcliffObershelp.html">http://xlinux.nist.gov/dads/HTML/ratcliffObershelp.html</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class RatcliffObershelpMetric implements SimilarityMetric {

  private boolean caseSensitive;

  // ===== Metric Methods =====

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    int totalLength = s1.length() + s2.length();
    int cummulativeLCS = lcs(
        (caseSensitive) ? s1 : s1.toLowerCase(), 0, s1.length(),
        (caseSensitive) ? s2 : s2.toLowerCase(), 0, s2.length()
    );

    return 2d * (double) cummulativeLCS / (double) totalLength;
  }

  private static final int lcs(String s1, int s1min, int s1max, String s2, int s2min, int s2max) {
    // stop criteria
    if (s1min < 0 || s1max > s1.length() || s1max - s1min < 1 || s2min < 0 || s2max > s2.length()
        || s2max - s2min < 1) {
      return 0;
    }

    // dynamic programming: compute LCS in current region
    int array[][] = new int[s1max - s1min + 1][s2max - s2min + 1];

    for (int i = 1; i < array.length; i++) {
      for (int j = 1; j < array[i].length; j++) {
        if (s1.charAt(s1min + i - 1) == s2.charAt(s2min + j - 1)) {
          array[i][j] = array[i - 1][j - 1] + 1;
        } else {
          array[i][j] = Math.max(array[i - 1][j], array[i][j - 1]);
        }
      }
    }

    // backtracking: compute bounds of found LCS
    int s1LCSmin = s1max;
    int s1LCSmax = s1min;
    int s2LCSmin = s2max;
    int s2LCSmax = s1min;

    int i = array.length - 1;
    int j = array[0].length - 1;

    while (i > 0 && j > 0) {
      if (s1.charAt(s1min + i - 1) == s2.charAt(s2min + j - 1)) {
        if (s1min + i - 1 < s1LCSmin) {
          s1LCSmin = s1min + i - 1;
        }
        if (s1LCSmax < s1min + i - 1) {
          s1LCSmax = s1min + i;
        }

        if (s2min + j - 1 < s2LCSmin) {
          s2LCSmin = s2min + j - 1;
        }
        if (s2LCSmax < s2min + j - 1) {
          s2LCSmax = s2min + j;
        }

        i--;
        j--;
      } else {
        if (array[i - 1][j] > array[i][j - 1]) {
          i--;
        } else if (array[i - 1][j] < array[i][j - 1]) {
          j--;
        } else {
          i--;
        }
      }
    }

    if (array[array.length - 1][array[0].length - 1] == 0) {
      return 0;
    }

    return array[array.length - 1][array[0].length - 1]					/* LCS length of current region			*/
           + lcs(s1, s1min, s1LCSmin, s2, s2min, s2LCSmin)			/* LCS length in left unmatched region	*/
           + lcs(s1, s1LCSmax + 1, s1max, s2, s2LCSmax + 1, s2max);	/* LCS length in right unmatched region	*/
  }

  // ===== Builder Pattern Methods =====

  private RatcliffObershelpMetric(Builder b) {
    caseSensitive = b.caseSensitive;
  }

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see RatcliffObershelpMetric
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard RatcliffObershelp distance object that can be used to determine the distance
   * between two strings
   * @see JaroMetric
   */
  public static RatcliffObershelpMetric getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * RatcliffObershelp Builder
   *
   * @param b should character comparison be case sensitive?
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   * @since 1.0
   */
  public static class Builder {

    boolean caseSensitive;

    public Builder() {
      caseSensitive = true;
    }

    public RatcliffObershelpMetric build() {
      return new RatcliffObershelpMetric(this);
    }

    /**
     * Sets the sensitivity to character case
     *
     * @param b use case-sensitive comparison?
     * @return this builder object (allows to chain calls)
     */
    public Builder caseSensitive(boolean b) {
      caseSensitive = b;
      return this;
    }
  }

}
