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

import java.util.HashSet;

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * L2 distance implementation based on a vector model. This distance is also referred to as
 * euclidean distance. This implementation uses the formula: l2 = sqrt( sum( (A_i - B_i)^2 ,i=0..n)
 * ) where A and B are term frequency vectors. </br>
 *
 * <p>For creation please use default L2Distance or the Builder {@link Builder} provided by the
 * static methods.</p>
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Euclidean_distance">http://en.wikipedia.org/wiki/Euclidean_distance</a></p>
 *
 * @author Florian Froese
 * @see VectorSimilarity
 * @since 1.0
 */
public class L2Distance extends VectorSimilarity implements DistanceMetric, SimilarityMetric {

  private L2Distance(Builder builder) {
    super(builder);
  }

  @Override
  public double computeDistance(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.DISTANCE_EMPTY_EMPTY;
    }

    computeTokens(s1, s2);

    // l2 distance
    HashSet<Object> mergedSet = new HashSet<Object>(vec1.keySet());
    mergedSet.addAll(vec2.keySet());

    double sum = 0;
    for (Object o : mergedSet) {

      int v1 = vec1.get(o) == null ? 0 : vec1.get(o);
      int v2 = vec2.get(o) == null ? 0 : vec2.get(o);

      sum += Math.pow(v1 - v2, 2);
    }

    return Math.sqrt(sum);
  }

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    double dist = computeDistance(s1, s2);

    return 1 - (dist) / (Math.sqrt(Math.pow(s1.length(), 2) + Math.pow(s2.length(), 2)));
  }

  // ===== Builder Pattern Methods =====

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see L2Distance
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard L2 distance that can be used to determine the L2 distance of two strings.
   * @see CosineDistance
   */
  public static L2Distance getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * L2Distance Builder Restriction: takeKeyset must be false since L2 distance is based on term
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

    public L2Distance build() {
      // check constraints
      if (takeKeyset) {
        throw new IllegalArgumentException("Keyset cannot be set true");
      }

      // build object
      L2Distance d = new L2Distance(this);

      return d;
    }
  }

}
