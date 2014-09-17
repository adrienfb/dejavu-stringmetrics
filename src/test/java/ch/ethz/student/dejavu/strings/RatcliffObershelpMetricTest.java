package ch.ethz.student.dejavu.strings;

import org.junit.Test;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;

public class RatcliffObershelpMetricTest extends AbstractSimilarityMetricTest {

  private static final RatcliffObershelpMetric metric = RatcliffObershelpMetric.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("Pennsylvania", "Pencilvaneya", 16d / 24d),
      new TestInput("MARTHA", "MARHTA", 10d / 12d),
      new TestInput("DWAYNE", "DUANE", 8d / 11d),
      new TestInput("DIXON", "DICKSONX", 8d / 13d),
      new TestInput("jones", "johnson", 8d / 12d),
      new TestInput("fvie", "ten", 2d / 7d),
  };

  @Override
  protected TestInput[] getTestInput() {
    return testInput;
  }

  @Override
  protected SimilarityMetric getSimilarityMetric() {
    return metric;
  }


  @Test
  public void test_bug1() {
//		String s1 = "GE";
//		String s2 = "GLd6dN2jnJ";

    String s1 = "abcd";
    String s2 = "aefg";

    try {
      metric.computeSimilarity(s1, s2);
    } catch (StackOverflowError e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }
}
