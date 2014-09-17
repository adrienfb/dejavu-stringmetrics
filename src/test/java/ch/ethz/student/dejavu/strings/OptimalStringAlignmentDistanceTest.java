package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class OptimalStringAlignmentDistanceTest extends AbstractDistanceAndSimilarityMetricTest {

  private static final OptimalStringAlignmentDistance
      metric =
      OptimalStringAlignmentDistance.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("Hello world!", "Hello world!", 0, 1),
      new TestInput("Hello world!", "Hello mamma!", 5, -1),
      new TestInput("Hello world!", "Hello world?", 1, -1),
      new TestInput("Hello world!", "Hello", 7, -1),
      new TestInput("Hello world!", "World Hello?", 9, -1),
      new TestInput("two", "three", 4, -1),
      new TestInput("two", "tow", 1, -1),
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

}
