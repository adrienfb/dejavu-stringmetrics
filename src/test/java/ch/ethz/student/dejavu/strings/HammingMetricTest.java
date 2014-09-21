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

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.TestUtils;
import ch.ethz.student.dejavu.utilities.Utilities;

public class HammingMetricTest extends AbstractDistanceAndSimilarityMetricTest {
  private static final Random rnd = new Random();
  
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
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // success
    }
  }

  @Override
  @Test
  public void testDistanceEmptyArguments() {
    double dist = getDistanceMetric().computeDistance("", "");

    Assert.assertEquals(Utilities.DISTANCE_EMPTY_EMPTY, dist, TestUtils.DELTA);
  }

  @Override
  @Test
  public void testSimilarityEmptyArguments() {
    double dist = getSimilarityMetric().computeSimilarity("", "");

    Assert.assertEquals(Utilities.SIMILARITY_EMPTY_EMPTY, dist, TestUtils.DELTA);
  }

  @Override
  @Test
  public void testDistanceCommutativity() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getDistanceMetric().computeDistance(ti.s1, ti.s2);
      double dist2 = getDistanceMetric().computeDistance(ti.s2, ti.s1);

      Assert.assertEquals("Commutativity Test failed on strings " + ti.s1 + " and " + ti.s2, dist1, dist2, TestUtils.DELTA);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      int length = rnd.nextInt(TestUtils.MAX_LEN+1-TestUtils.MIN_LEN) + TestUtils.MIN_LEN;
      String s1 = TestUtils.getRandomString(length);
      String s2 = TestUtils.getRandomString(length);

      double dist1 = getDistanceMetric().computeDistance(s1, s2);
      double dist2 = getDistanceMetric().computeDistance(s2, s1);
      Assert.assertEquals(dist1, dist2, TestUtils.DELTA);
    }
  }
  
  @Override
  @Test
  public void testSimilarityCommutativity() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getDistanceMetric().computeDistance(ti.s1, ti.s2);
      double dist2 = getDistanceMetric().computeDistance(ti.s2, ti.s1);

      Assert.assertEquals("Commutativity Test failed on strings " + ti.s1 + " and " + ti.s2, dist1, dist2, TestUtils.DELTA);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      int length = rnd.nextInt(TestUtils.MAX_LEN+1-TestUtils.MIN_LEN) + TestUtils.MIN_LEN;
      String s1 = TestUtils.getRandomString(length);
      String s2 = TestUtils.getRandomString(length);

      double dist1 = getDistanceMetric().computeDistance(s1, s2);
      double dist2 = getDistanceMetric().computeDistance(s2, s1);
      Assert.assertEquals(dist1, dist2, TestUtils.DELTA);
    }
  }
  
  @Override
  public void testSimilarityBounds() {
    for (int i = 0; i < TestUtils.N; i++) {
      int length = rnd.nextInt(TestUtils.MAX_LEN+1-TestUtils.MIN_LEN) + TestUtils.MIN_LEN;
      String s1 = TestUtils.getRandomString(length);
      String s2 = TestUtils.getRandomString(length);
      double s = getSimilarityMetric().computeSimilarity(s1, s2);

      Assert.assertTrue(
          "Similarity s=" + s + " not in bounds [0,1] for input s1='" + s1 + "' and s2='" + s2
          + "'", s >= 0 && s <= 1);
    }
  }
  
  @Override
  public void testDistanceRobustness() {
    for (int i = 0; i < TestUtils.N; i++) {
      int length = rnd.nextInt(TestUtils.MAX_LEN+1-TestUtils.MIN_LEN) + TestUtils.MIN_LEN;
      String s1 = TestUtils.getRandomString(length);
      String s2 = TestUtils.getRandomString(length);

      try {
        getDistanceMetric().computeDistance(s1, s2);
      } catch (Exception e) {
        Assert.fail("Excpection is thrown for input s1='" + s1 + "' and s2='" + s2 + "'");
      }
    }
  }
  
  @Override
  public void testSimilarityRobustness() {
    for (int i = 0; i < TestUtils.N; i++) {
      int length = rnd.nextInt(TestUtils.MAX_LEN+1-TestUtils.MIN_LEN) + TestUtils.MIN_LEN;
      String s1 = TestUtils.getRandomString(length);
      String s2 = TestUtils.getRandomString(length);

      try {
        getDistanceMetric().computeDistance(s1, s2);
      } catch (Exception e) {
        Assert.fail("Excpection is thrown for input s1='" + s1 + "' and s2='" + s2 + "'");
      }
    }
  }
  
}
