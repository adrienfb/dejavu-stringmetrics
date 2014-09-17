package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class NeedlemanWunschDistanceTest extends AbstractDistanceAndSimilarityMetricTest {

  private static final NeedlemanWunschDistance metric = NeedlemanWunschDistance.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("ABCNYRQCLCRPM", "AYCYNRCKCRBP", 14, -1),
      new TestInput("ACTGGA", "ACCATGGA", 22, -1),
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
