package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;

public class TverskyIndexTest extends AbstractSimilarityMetricTest {
	private static final TverskyIndex metric = TverskyIndex.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("MARTHA", "MARHTA", 1d),
		new TestInput("DWAYNE", "DUANE", 4d/(4d+1d+0.5d)),
		new TestInput("DIXON", "DICKSONX", 5d/(5d+0d+1.5d)),
		new TestInput("AABBB", "BBCCC", 1d/(1d+0.5+0.5)),
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
