package ch.ethz.student.dejavu.vectors;

import org.junit.Test;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;

public class OverlapCoefficientTest extends AbstractSimilarityMetricTest {

  private static final OverlapCoefficient metric = OverlapCoefficient.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("MARTHA", "MARHTA", 1d),
      new TestInput("DWAYNE", "DUANE", 4d / 5d),
      new TestInput("DIXON", "DICKSONX", 5d / 5d),
      new TestInput("AABBB", "BBCCC", 1d / 2d),
      new TestInput("night", "nacht", 0.6),
      new TestInput("context", "contact", 4d / 5d),
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
  @Test(expected = IllegalStateException.class)
  public void testEmptyArguments() {
    super.testEmptyArguments();
  }
}
