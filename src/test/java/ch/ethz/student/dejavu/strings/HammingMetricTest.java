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

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

public class HammingMetricTest extends AbstractDistanceAndSimilarityMetricTest {

  private static final HammingMetric metric = HammingMetric.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("Hello world!", "Hello mamma!", 5, 0.5833),
      new TestInput("Hello world!", "Hello mamma?", 6, 0.5),
      new TestInput("Hello world!", "Hello world?", 1, 0.9167),
  };

  @Override
  protected TestInput[] getTestInput() {
    return testInput;
  }

  @Override
  protected DistanceMetric getDistanceMetric() {
    return metric;
  }

  @Override
  protected SimilarityMetric getSimilarityMetric() {
    return metric;
  }

  // ===== Equal Length Constraints =====

  public void testEqualLenghtConstraints() {
    try {
      getDistanceMetric().computeDistance("some length", "other length");
      fail();
    } catch (IllegalArgumentException e) {
      // success
    }
  }

  @Override
  public void testDistanceEmptyArguments() {
    double dist = getDistanceMetric().computeDistance("", "");

    assertEquals(Utilities.DISTANCE_EMPTY_EMPTY, dist);
  }

  @Override
  public void testSimilarityEmptyArguments() {
    double dist = getSimilarityMetric().computeSimilarity("", "");

    assertEquals(Utilities.SIMILARITY_EMPTY_EMPTY, dist);
  }

}
