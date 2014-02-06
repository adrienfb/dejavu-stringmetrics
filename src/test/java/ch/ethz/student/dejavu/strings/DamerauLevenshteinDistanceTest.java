package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class DamerauLevenshteinDistanceTest extends AbstractDistanceAndSimilarityMetricTest {
	private static final DamerauLevenshteinDistance metric = DamerauLevenshteinDistance.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("abc", "ca", 2, 0.33333)
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
