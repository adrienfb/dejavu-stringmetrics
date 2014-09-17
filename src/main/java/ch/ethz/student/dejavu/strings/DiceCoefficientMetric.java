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

import java.util.HashSet;
import java.util.Set;

import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * Dice coefficient distance implementation
 *
 * This implementation operates on char-bigrams and follows the definition given on Wikipedia. Given
 * the set of bi-grams S1 and S2 extracted from input strings s1 and s2 respectively the similarity
 * is computed according to the formula:
 *
 * <p>sim(s1, s2) = 2 * (S1 intersect S2 ) / ( size(S1) + size(S2) )</p>
 *
 * <p>If you would like to use this distance with another base as character bi-grams please refer to
 * the same named class in the vectors package!</p>
 *
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Sorensen-Dice_coefficient">http://en.wikipedia.org/wiki/Sorensen-Dice_coefficient</a></p>
 *
 * <p>For creation please use default DiceCoefficient or the Builder {@link Builder} provided by the
 * static methods.</p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class DiceCoefficientMetric implements SimilarityMetric {

  // ===== Metric Methods =====

  @Override
  public double computeSimilarity(String s1, String s2) {
    if (!Utilities.checkInputs(s1, s2)) {
      return Utilities.SIMILARITY_EMPTY_EMPTY;
    }

    Set<Bigram> bigrams1 = extractBigrams(s1);
    Set<Bigram> bigrams2 = extractBigrams(s2);

    // for efficiency set bigrams1 to be the smaller set
    if (bigrams2.size() < bigrams1.size()) {
      Set<Bigram> tmp = bigrams2;
      bigrams2 = bigrams1;
      bigrams1 = tmp;
    }

    int intersect = 0;
    for (Bigram bigram : bigrams1) {
      if (bigrams2.contains(bigram)) {
        intersect++;
      }
    }

    return 2.0d * (double) intersect / (double) (bigrams1.size() + bigrams2.size());
  }

  private static final Set<Bigram> extractBigrams(String s) {
    int length = s.length();

    // empty string case
    if (length == 0) {
      return new HashSet<Bigram>(0);
    }
    // normal case
    else {
      HashSet<Bigram> res = new HashSet<Bigram>(length - 1);

      for (int i = 1; i < length; i++) {
        Bigram bigram = new Bigram(s.charAt(i - 1), s.charAt(i));
        res.add(bigram);
      }

      return res;
    }
  }

  // ===== Helper Class =====

  private static class Bigram {

    private char c1;
    private char c2;

    public Bigram(char c1, char c2) {
      this.c1 = c1;
      this.c2 = c2;
    }

    @Override
    public String toString() {
      return "'" + String.valueOf(c1) + String.valueOf(c2) + "'";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + c1;
      result = prime * result + c2;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Bigram other = (Bigram) obj;
      if (c1 != other.c1) {
        return false;
      }
      if (c2 != other.c2) {
        return false;
      }
      return true;
    }
  }

  // ===== Builder Pattern Methods =====

  private DiceCoefficientMetric(Builder b) {
  }

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see DiceCoefficientMetric
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard Dice Coefficient distance object that can be used to determine the distance
   * between two strings
   * @see DiceCoefficientMetric
   */
  public static DiceCoefficientMetric getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * DiceCoefficientDistance Builder
   *
   * <p>No parameters, just call build()</p>
   *
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   * @since 1.0
   */
  public static class Builder {

    public DiceCoefficientMetric build() {
      return new DiceCoefficientMetric(this);
    }
  }

}
