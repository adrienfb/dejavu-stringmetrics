package ch.ethz.student.dejavu.vectors;

import org.junit.Test;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class L2DistanceTest extends AbstractDistanceAndSimilarityMetricTest {

  private static final L2Distance metric = L2Distance.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("MARTHA", "MARHTA", 0d, 1),
      new TestInput("DWAYNE", "DUANE", Math.sqrt(3), 0.7782),
      new TestInput("DIXON", "DICKSONX", Math.sqrt(3), 0.8164),
      new TestInput("AABBB", "BBCCC", Math.sqrt(14), 0.4708),
      new TestInput("aa", "aa", 0, 1),
      new TestInput("aa", "bb", Math.sqrt(8), 0)
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

  @Override
  @Test(expected = IllegalStateException.class)
  public void testSimilarityEmptyArguments() {
    super.testSimilarityEmptyArguments();
  }

  @Override
  @Test(expected = IllegalStateException.class)
  public void testDistanceEmptyArguments() {
    super.testDistanceEmptyArguments();
  }
}
