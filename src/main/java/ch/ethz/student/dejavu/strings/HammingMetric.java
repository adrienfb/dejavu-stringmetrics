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
 * Hamming distance implementation
 *
 * The Hamming distance compares two strings of equal length and returns the number of non-matching
 * characters
 *
 * <p>For creation please use default HammignDistance or the Builder {@link Builder} provided by the
 * static methods.</p>
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Hamming_distance">http://en.wikipedia.org/wiki/Hamming_distance</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class HammingMetric implements DistanceMetric, SimilarityMetric {

  // ===== Metric Methods =====

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    double dist = computeDistance(s1, s2);

    return 1 - (dist / s1.length());
  }

  @Override
  public double computeDistance(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.DISTANCE_EMPTY_EMPTY;
    }

    double dist = 0;

    if (s1.length() != s2.length()) {
      throw new IllegalArgumentException("Input strings must be of equal length");
    }

    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();

    for (int i = 0; i < c1.length; i++) {
      if (c1[i] != c2[i]) {
        dist++;
      }
    }

    return dist;
  }

  // ===== Builder Pattern Methods =====

  private HammingMetric(Builder b) {
  }

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see HammingMetric
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard Hamming distance object that can be used to determine the distance between two
   * strings
   * @see HammingMetric
   */
  public static HammingMetric getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * HammingDistance Builder
   *
   * <p>No parameters, just call build()</p>
   *
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   * @since 1.0
   */
  public static class Builder {

    public HammingMetric build() {
      return new HammingMetric(this);
    }
  }

}
