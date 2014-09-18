package ch.ethz.student.dejavu;

import junit.framework.TestCase;

public abstract class AbstractUnnormalizedSimilarityTest extends TestCase {

  // ===== Metric Specific =====

  protected String getValidRandomString() {
    return TestUtils.getRandomString();
  }
  
  protected abstract TestInput[] getTestInput();

  protected abstract UnnormalizedSimilarityMetric getSimilarityMetric();

  // ===== Similarity Metric Tests =====

  public void testNullArguments() {
    IllegalArgumentException ex = null;

    try {
      getSimilarityMetric().computeUnnormalizedSimilarity(null, null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);

    ex = null;

    try {
      getSimilarityMetric().computeUnnormalizedSimilarity("any string", null);
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);

    ex = null;
    try {
      getSimilarityMetric().computeUnnormalizedSimilarity(null, "any string");
    } catch (IllegalArgumentException e) {
      ex = e;
    }
    assertNotNull(ex);
  }

  public void testEmptyArguments() {
    String s1 = "any string";
    String s2 = "";

    double sim = getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
    assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, sim);
    
    sim = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s1);
    assertEquals(TestUtils.SIMILARITY_EMPTY_ANY, sim);
    
    sim = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s2);
    assertEquals(TestUtils.SIMILARITY_EMPTY_EMPTY, sim);
  }

  public void testCommutativity() {
    // run test on manually specified test input
    for (TestInput ti : getTestInput()) {
      double dist1 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);
      double dist2 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s2, ti.s1);

      assertEquals(dist1, dist2);
    }
    
    // run test on randomly generated strings
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      double dist1 = getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
      double dist2 = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s1);
      assertEquals(dist1, dist2);
    }
  }

  public void testSimilarity() {
    for (TestInput ti : getTestInput()) {
      double sim = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);

      assertEquals("Input: '" + ti.s1 + "'  and '" + ti.s2 + "'", ti.sim, sim, TestUtils.DELTA);
    }
  }
  
  public void testRobustness() {
    for (int i = 0; i < TestUtils.N; i++) {
      String s1 = getValidRandomString();
      String s2 = getValidRandomString();

      try {
        getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);
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
