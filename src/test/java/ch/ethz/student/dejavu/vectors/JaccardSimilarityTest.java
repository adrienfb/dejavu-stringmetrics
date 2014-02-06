package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;

public class JaccardSimilarityTest extends AbstractSimilarityMetricTest {
	private static final JaccardSimilarity metric = JaccardSimilarity.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("MARTHA", "MARHTA", 1d),
		new TestInput("DWAYNE", "DUANE", 4d/7d),
		new TestInput("DIXON", "DICKSONX", 5d/8d),
		new TestInput("AABBB", "BBCCC", 1d/3d),
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
}
