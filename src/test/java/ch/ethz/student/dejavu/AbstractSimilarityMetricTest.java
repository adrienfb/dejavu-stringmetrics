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

public abstract class AbstractSimilarityMetricTest extends TestCase {

  // ===== Metric Specific =====

  protected String getValidRandomString() {
    return TestUtils.getRandomString();
  }
  
  protected abstract TestInput[] getTestInput();

  protected abstract SimilarityMetric getSimilarityMetric();

  // ===== Similarity Metric Tests =====

  public void testNullArguments() {
    IllegalArgumentException ex = null;

    try {
      getSimilarityMetric().computeSimilarity(null, null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);

    ex = null;

    try {
      getSimilarityMetric().computeSimilarity("any string", null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);

    ex = null;
    try {
      getSimilarityMetric().computeSimilarity(null, "any string");
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);
  }

  public void testEmptyArguments() {
    String s1 = "any string";
    String s2 = "";

    assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, getSimilarityMetric().computeSimilarity(s1, s2));
    assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, getSimilarityMetric().computeSimilarity(s2, s1));

    double dist = getSimilarityMetric().computeSimilarity(s2, s2);
    assertEquals(TestUtils.SIMILARITY_EMPTY_EMPTY, dist);
  }

  public void testCommutativity() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getSimilarityMetric().computeSimilarity(ti.s1, ti.s2);
      double dist2 = getSimilarityMetric().computeSimilarity(ti.s2, ti.s1);

      assertEquals(dist1, dist2);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      double dist1 = getSimilarityMetric().computeSimilarity(s1, s2);
      double dist2 = getSimilarityMetric().computeSimilarity(s2, s1);
      assertEquals(dist1, dist2);
    }
  }

  public void testSimilarityToSelf() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getSimilarityMetric().computeSimilarity(ti.s1, ti.s1);
      double dist2 = getSimilarityMetric().computeSimilarity(ti.s2, ti.s2);

      assertEquals(TestUtils.SIMILARITY_SELF, dist1);
      assertEquals(TestUtils.SIMILARITY_SELF, dist2);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();

      double dist = getSimilarityMetric().computeSimilarity(s1, s1);
      assertEquals("'"+s1+"'", TestUtils.SIMILARITY_SELF, dist);
    }
  }

  public void testSimilarity() {
    for (TestInput ti : getTestInput()) {
      double sim = getSimilarityMetric().computeSimilarity(ti.s1, ti.s2);

      assertEquals("Input: '" + ti.s1 + "'  and '" + ti.s2 + "'", ti.sim, sim, TestUtils.DELTA);
    }
  }

  public void testSimilarityBounds() {
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();
      double s = getSimilarityMetric().computeSimilarity(s1, s2);

      assertTrue(
          "Similarity s=" + s + " not in bounds [0,1] for input s1='" + s1 + "' and s2='" + s2
          + "'", s >= 0 && s <= 1);
    }
  }

  public void testRobustness() {
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      try {
        getSimilarityMetric().computeSimilarity(s1, s2);
      } catch (Exception e) {
        fail("Excpection is thrown for input s1='" + s1 + "' and s2='" + s2 + "'");
      }
    }
  }

  // ===== Helper Class =====

  public static class TestInput {

    public String s1;
    public String s2;
    public double sim;

    public TestInput(String s1, String s2, double sim) {
      this.s1 = s1;
      this.s2 = s2;
      this.sim = sim;
    }
  }
}
