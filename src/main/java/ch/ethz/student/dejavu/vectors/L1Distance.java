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
package ch.ethz.student.dejavu.vectors;

import java.util.HashMap;
import java.util.HashSet;

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * L1 distance implementation based on a vector model. This distance is also referred to as block
 * distance or Manhattan distance. This implementation uses the formula: l1 = sum( |A_i - B_i|
 * ,i=0..n) where A and B are term frequency vectors. </br> Sums up the absolute difference between
 * the term frequencies of both strings.
 *
 * <p>For creation please use default L1Distance or the Builder {@link Builder} provided by the
 * static methods.</p>
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/L1_distance">http://en.wikipedia.org/wiki/L1_distance</a></p>
 *
 * @author Florian Froese
 * @see VectorSimilarity
 * @since 1.0
 */
public class L1Distance extends VectorSimilarity implements DistanceMetric, SimilarityMetric {

  private L1Distance(Builder builder) {
    super(builder);
  }

  @Override
  public double computeDistance(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.DISTANCE_EMPTY_EMPTY;
    }

    computeTokens(s1, s2);

    HashSet<Object> mergedSet = new HashSet<Object>(vec1.keySet());
    mergedSet.addAll(vec2.keySet());

    double sum = 0;
    for (Object o : mergedSet) {

      int v1 = vec1.get(o) == null ? 0 : vec1.get(o);
      int v2 = vec2.get(o) == null ? 0 : vec2.get(o);

      sum += Math.abs(v1 - v2);
    }

    return sum;
  }

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    double dist = computeDistance(s1, s2);

    return 1 - (dist) / (s1.length() + s2.length());
  }

  @SuppressWarnings("unused")
  private double withOwnMerge(HashMap<Object, Integer> vector1, HashMap<Object, Integer> vector2) {
    double sum = 0;
    for (Object o : vector1.keySet()) {
      int v1 = vector1.get(o);
      int v2 = 0;
      if (vector2.containsKey(o)) {
        v2 = vector2.get(o);
      }

      sum += Math.abs(v1 - v2);
    }
    for (Object o : vector2.keySet()) {
      int v2 = vector2.get(o);
      if (!vector1.containsKey(o)) {
        int v1 = 0;
        sum += Math.abs(v2 - v1);
      }
    }
    return sum;
  }

  // ===== Builder Pattern Methods =====

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see L1Distance
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard L1 distance that can be used to determine the L1 distance of two strings.
   * @see L1Distance
   */
  public static L1Distance getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * L1Distance Builder Restriction: takeKeyset must be false since L1 distance is based on term
   * frequency.
   *
   * <p>For configuration and usage refer to {@link VectorBuilder}</p>
   *
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   * @see VectorBuilder
   * @since 1.0
   */
  public static class Builder extends VectorBuilder<Builder> {

    public Builder() {
      this.takeKeyset = false;
    }

    public L1Distance build() {
      // check constraints
      if (takeKeyset) {
        throw new IllegalArgumentException("Keyset cannot be set true");
      }

      // build object
      L1Distance d = new L1Distance(this);

      return d;
    }
  }

}
