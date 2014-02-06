package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.AbstractDistanceAndSimilarityMetricTest;
import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;

public class L1DistanceTest extends AbstractDistanceAndSimilarityMetricTest{
	private static final L1Distance metric = L1Distance.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("MARTHA", "MARHTA", 0d, 1),
		new TestInput("DWAYNE", "DUANE", 3d, 0.7272),
		new TestInput("DIXON", "DICKSONX", 3d, 0.7693),
		new TestInput("aa", "aa", 0, 1),
		new TestInput("aa", "bb", 4, 0)
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
