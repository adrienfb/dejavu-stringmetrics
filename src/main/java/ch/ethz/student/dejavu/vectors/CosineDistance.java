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

import ch.ethz.student.dejavu.UnnormalizedSimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;


/**
 * Cosine distance implementation based on a vector model. This implementation uses the formula:
 * cosineDistance = A * B / ( ||A|| * ||B||) where A and B are term frequency vectors. </br> Thus A
 * * B is a dot product and ||.|| is the euclidean norm of the frequency vector
 *
 * <p>For creation please use default CosineDistance or the Builder {@link Builder} provided by the
 * static methods.</p>
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Cosine_similarity">http://en.wikipedia.org/wiki/Cosine_similarity</a></p>
 *
 * @author Florian Froese
 * @see VectorSimilarity
 * @since 1.0
 */
public class CosineDistance extends VectorSimilarity implements UnnormalizedSimilarityMetric {

  private CosineDistance(Builder builder) {
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

    double dotProd = VectorUtils.scalarProduct(vec1, vec2);
    double nVector1 = VectorUtils.euclideanNorm(vec1);
    double nVector2 = VectorUtils.euclideanNorm(vec2);

    double cosineSimilarity = dotProd / (nVector1 * nVector2);                // ranges from -1 to 1

    return cosineSimilarity;
  }

  // ===== Builder Pattern Methods =====

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see CosineDistance
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard cosine distance that can be used to determine the cosine distance of two
   * strings.
   * @see CosineDistance
   */
  public static CosineDistance getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * CosineDistance Builder Restriction: takeKeyset must be false since cosine distance is based on
   * term frequency.
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

    public CosineDistance build() {
      // check constraints
      if (takeKeyset) {
        throw new IllegalArgumentException("Keyset cannot be set true");
      }

      // build object
      CosineDistance d = new CosineDistance(this);

      return d;
    }
  }
}
