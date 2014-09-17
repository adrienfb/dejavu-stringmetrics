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

import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.UnnormalizedSimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * Matching coefficient implementation based on a vector model. This implementation uses the
 * formula: matchingCoefficient = intersection (A, B) Where A and B are Sets of Tokens, thus no
 * duplicate entries.
 *
 * For creation please use default MatchingCoefficient or the Builder {@link Builder}.
 *
 * <p>Wikipedia: only simmetrics</p>
 *
 * @author Florian Froese
 * @see VectorSimilarity
 * @since 1.0
 */
public class MatchingCoefficient extends VectorSimilarity
    implements UnnormalizedSimilarityMetric, SimilarityMetric {

  private MatchingCoefficient(Builder builder) {
    super(builder);
  }

  @Override
  public double computeUnnormalizedSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    computeTokens(s1, s2);

    if (s1.length() == 0 && s2.length() == 0) {
      return 1.0;
    }

    double i = 0.0;
    if (takeKeyset) {
      i = VectorUtils.intersect(keys1, keys2);
    } else {
      i = VectorUtils.intersect(vec1, vec2);
    }

    double matchingCoefficient = i;

    return matchingCoefficient;
  }

  @Override
  public double computeSimilarity(String s1, String s2) {
    double unnormalized = computeUnnormalizedSimilarity(s1, s2);

    if (takeKeyset) {
      return unnormalized / Math.max(keys1.size(), keys2.size());
    } else {
      return unnormalized / Math.max(vec1.size(), vec2.size());
    }
  }

  // ===== Builder Pattern Methods =====

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see MatchingCoefficient
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard matching coefficient that can be used to determine the matching coefficient of
   * two strings.
   * @see DiceCoefficient
   */
  public static MatchingCoefficient getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * MatchingCoefficient Builder No further restrictions or additional parameters.
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

    public MatchingCoefficient build() {
      // check constraints

      // build object
      MatchingCoefficient d = new MatchingCoefficient(this);

      return d;
    }
  }

}
