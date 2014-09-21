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

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.TestUtils;

public class DiceCoefficientMetricTest extends AbstractSimilarityMetricTest {

  private static final DiceCoefficientMetric metric = DiceCoefficientMetric.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("Hello world!", "Hello world?", 0.9090),
      new TestInput("Hello world?", "Hello mamma?", 0.4761),
      new TestInput("Bigrams are great", "Bigrams are the worst", 0.6666),
      new TestInput("bag of words model", "model of bag words", 0.8823),
  };

  @Override
  protected TestInput[] getTestInput() {
    return testInput;
  }

  @Override
  protected SimilarityMetric getSimilarityMetric() {
    return metric;
  }
  
  @Override
  protected String getValidRandomString() {
    return TestUtils.getRandomString(2, TestUtils.MAX_LEN);
  }
  
  // ===== Special Test Cases =====
  
  @Override
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyArguments() {
    String s1 = "any string";
    String s2 = "";
    
    getSimilarityMetric().computeSimilarity(s1, s2);
    getSimilarityMetric().computeSimilarity(s1, s2);

    double dist = getSimilarityMetric().computeSimilarity(s2, s2);
    Assert.assertEquals(TestUtils.SIMILARITY_EMPTY_EMPTY, dist, TestUtils.DELTA);
  }

}
