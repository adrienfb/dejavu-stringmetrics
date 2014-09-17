package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractDistanceMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;

public class GotohDistanceTest extends AbstractDistanceMetricTest {

  private static final GotohDistance metric = GotohDistance.getInstance();
  private static final TestInput[] testInput = {
      new TestInput("ACACACTA", "AGCACACA", 12),
  };

  @Override
  protected TestInput[] getTestInput() {
    return testInput;
  }

  @Override
  protected DistanceMetric getDistanceMetric() {
    return metric;
  }

}
