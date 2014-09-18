package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.TestUtils;
import ch.ethz.student.dejavu.strings.DiceCoefficientMetric;

public class DiceCoefficientTest extends AbstractSimilarityMetricTest {

  private static final DiceCoefficient metric = DiceCoefficient.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("MARTHA", "MARHTA", 1d),
      new TestInput("DWAYNE", "DUANE", 8d / 11d),
      new TestInput("DIXON", "DICKSONX", 10d / 13d),
      new TestInput("AABBB", "BBCCC", 2d / 4d),
      new TestInput("aa", "aa", 1d),
      new TestInput("aa", "bb", 0)
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
    return TestUtils.getRandomString(1, TestUtils.MAX_LEN);
  }
  
  // ===== Special Test Cases =====

  @Override
  public void testEmptyArguments() {
    String s1 = "any string";
    String s2 = "";

    try {
      getSimilarityMetric().computeSimilarity(s1, s2);
      fail("Empty string should trigger IllegalArgumentException");
    } catch (IllegalArgumentException e) { /* all good */ }

    try {
      getSimilarityMetric().computeSimilarity(s1, s2);
      fail("Empty string should trigger IllegalArgumentException");
    } catch (IllegalArgumentException e) { /* all good */ }

    double dist = getSimilarityMetric().computeSimilarity(s2, s2);
    assertEquals(TestUtils.SIMILARITY_EMPTY_EMPTY, dist);
  }
  
  // TODO: finish this test!
  public void testTokenizer() {
    DiceCoefficient.Builder builder = DiceCoefficient.getBuilder();
    builder.nGram(2);
    DiceCoefficient diceCoefficient = builder.build();

    DiceCoefficientMetric diceCoefficientMetric = DiceCoefficientMetric.getInstance();

    String[][] a = new String[][]{{"ab", "ab"}};

  }
}
