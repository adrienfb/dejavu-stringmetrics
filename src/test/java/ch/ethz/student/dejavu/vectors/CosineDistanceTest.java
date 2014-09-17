package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.AbstractUnnormalizedSimilarityTest;
import ch.ethz.student.dejavu.UnnormalizedSimilarityMetric;

public class CosineDistanceTest extends AbstractUnnormalizedSimilarityTest {

  private static final CosineDistance metric = CosineDistance.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("MARTHA", "MARHTA", 1d),
      new TestInput("DWAYNE", "DUANE", 0.7302967433),
      new TestInput("DIXON", "DICKSONX", 0.790569415),
      new TestInput("AABBB", "BBCCC", 0.4615384615),
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
}
