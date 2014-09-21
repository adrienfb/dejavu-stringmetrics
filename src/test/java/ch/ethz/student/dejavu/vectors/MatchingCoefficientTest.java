package ch.ethz.student.dejavu.vectors;

import org.junit.Test;

import ch.ethz.student.dejavu.AbstractUnnormalizedSimilarityTest;
import ch.ethz.student.dejavu.UnnormalizedSimilarityMetric;

public class MatchingCoefficientTest extends AbstractUnnormalizedSimilarityTest {

  private static final MatchingCoefficient metric = MatchingCoefficient.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("MARTHA", "MARHTA", 5),
      new TestInput("DWAYNE", "DUANE", 4),
      new TestInput("DIXON", "DICKSONX", 5),
      new TestInput("AABBB", "BBCCC", 1),
      new TestInput("aa", "aa", 1d),
      new TestInput("aa", "bb", 0)
  };

  @Override
  protected TestInput[] getTestInput() {
    return testInput;
  }

  @Override
  protected UnnormalizedSimilarityMetric getSimilarityMetric() {
    return metric;
  }

  @Override
  @Test(expected = IllegalStateException.class)
  public void testEmptyArguments() {
    super.testEmptyArguments();
  }
}
