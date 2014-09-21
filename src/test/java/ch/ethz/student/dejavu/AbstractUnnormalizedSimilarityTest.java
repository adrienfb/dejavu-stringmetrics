package ch.ethz.student.dejavu;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractUnnormalizedSimilarityTest {

  // ===== Metric Specific =====

  protected String getValidRandomString() {
    return TestUtils.getRandomString();
  }
  
  protected abstract TestInput[] getTestInput();

  protected abstract UnnormalizedSimilarityMetric getSimilarityMetric();

  // ===== Similarity Metric Tests =====

  @Test
  public void testNullArguments() {
    IllegalArgumentException ex = null;

    try {
      getSimilarityMetric().computeUnnormalizedSimilarity(null, null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    Assert.assertNotNull(ex);

    ex = null;

    try {
      getSimilarityMetric().computeUnnormalizedSimilarity("any string", null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    Assert.assertNotNull(ex);

    ex = null;
    try {
      getSimilarityMetric().computeUnnormalizedSimilarity(null, "any string");
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    Assert.assertNotNull(ex);
  }

  @Test
  public void testEmptyArguments() {
    String s1 = "any string";
    String s2 = "";

    double sim = getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
    Assert.assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, sim, TestUtils.DELTA);
    
    sim = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s1);
    Assert.assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, sim, TestUtils.DELTA);
    
    sim = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s2);
    Assert.assertEquals(TestUtils.SIMILARITY_EMPTY_EMPTY, sim, TestUtils.DELTA);
  }

  @Test
  public void testCommutativity() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);
      double dist2 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s2, ti.s1);

      Assert.assertEquals(dist1, dist2, TestUtils.DELTA);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      double dist1 = getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
      double dist2 = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s1);
      Assert.assertEquals(dist1, dist2, TestUtils.DELTA);
    }
  }

  @Test
  public void testSimilarity() {
    for (TestInput ti : getTestInput()) {
      double sim = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);

      Assert.assertEquals("Input: '" + ti.s1 + "'  and '" + ti.s2 + "'", ti.sim, sim,
                          TestUtils.DELTA);
    }
  }

  @Test
  public void testRobustness() {
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      try {
        getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
      } catch (Exception e) {
        Assert.fail("Excpection is thrown for input s1='" + s1 + "' and s2='" + s2 + "'");
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
