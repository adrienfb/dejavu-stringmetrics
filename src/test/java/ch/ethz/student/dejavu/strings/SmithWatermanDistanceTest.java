package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class SmithWatermanDistanceTest extends AbstractDistanceAndSimilarityMetricTest {
	private static final SmithWatermanDistance metric = SmithWatermanDistance.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("ACACACTA", "AGCACACA", 12, -1),
		new TestInput("ACTGGA", "ACCATGGA", 10, -1),
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
