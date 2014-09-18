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
package ch.ethz.student.dejavu;

import junit.framework.TestCase;

public abstract class AbstractDistanceAndSimilarityMetricTest extends TestCase {

  private DistanceMetricTest distanceMetricTest;
  private SimilarityMetricTest similarityMetricTest;

  // ===== Constructor =====

  public AbstractDistanceAndSimilarityMetricTest() {
    distanceMetricTest = new DistanceMetricTest(getTestInput());
    similarityMetricTest = new SimilarityMetricTest(getTestInput());
  }

  // ===== Metric Specific =====

  protected String getValidRandomString() {
    return TestUtils.getRandomString();
  }
  
  protected abstract TestInput[] getTestInput();

  protected abstract DistanceMetric getDistanceMetric();

  protected abstract SimilarityMetric getSimilarityMetric();

  // ===== Distance Metric Tests =====

  public void testDistanceNullArguments() {
    distanceMetricTest.testNullArguments();
  }

  public void testDistanceEmptyArguments() {
    distanceMetricTest.testEmptyArguments();
  }

  public void testDistanceCommutativity() {
    distanceMetricTest.testCommutativity();
  }

  public void testDistanceToSelf() {
    distanceMetricTest.testDistanceToSelf();
  }

  public void testDistance() {
    distanceMetricTest.testDistance();
  }
  
  public void testDistanceRobustness() {
    distanceMetricTest.testRobustness();
  }

  // ===== Similarity Metric Tests =====

  public void testSimilarityNullArguments() {
    similarityMetricTest.testNullArguments();
  }

  public void testSimilarityEmptyArguments() {
    similarityMetricTest.testEmptyArguments();
  }

  public void testSimilarityCommutativity() {
    similarityMetricTest.testCommutativity();
  }

  public void testSimilarityToSelf() {
    similarityMetricTest.testSimilarityToSelf();
  }

  public void testSimilarity() {
    similarityMetricTest.testSimilarity();
  }

  public void testSimilarityBounds() {
    similarityMetricTest.testSimilarityBounds();
  }

  public void testSimilarityRobustness() {
    similarityMetricTest.testRobustness();
  }

  // ===== Helper Classes =====

  public static class TestInput {

    public String s1;
    public String s2;
    public double dist;
    public double sim;

    public TestInput(String s1, String s2, double dist, double sim) {
      this.s1 = s1;
      this.s2 = s2;
      this.dist = dist;
      this.sim = sim;
    }
  }

  private class DistanceMetricTest extends AbstractDistanceMetricTest {

    private TestInput[] testInput;

    public DistanceMetricTest(AbstractDistanceAndSimilarityMetricTest.TestInput[] input) {
      testInput = new TestInput[input.length];

      for (int i = 0; i < input.length; i++) {
        testInput[i] = new TestInput(input[i].s1, input[i].s2, input[i].dist);
      }
    }

    @Override
    protected TestInput[] getTestInput() {
      return testInput;
    }

    @Override
    protected DistanceMetric getDistanceMetric() {
      return AbstractDistanceAndSimilarityMetricTest.this.getDistanceMetric();
    }

  }

  private class SimilarityMetricTest extends AbstractSimilarityMetricTest {

    private TestInput[] testInput;

    public SimilarityMetricTest(AbstractDistanceAndSimilarityMetricTest.TestInput[] input) {
      testInput = new TestInput[input.length];

      for (int i = 0; i < input.length; i++) {
        testInput[i] = new TestInput(input[i].s1, input[i].s2, input[i].sim);
      }
    }

    @Override
    protected TestInput[] getTestInput() {
      return testInput;
    }

    @Override
    protected SimilarityMetric getSimilarityMetric() {
      return AbstractDistanceAndSimilarityMetricTest.this.getSimilarityMetric();
    }

  }
}
