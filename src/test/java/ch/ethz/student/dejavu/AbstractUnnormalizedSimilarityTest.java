package ch.ethz.student.dejavu;

import junit.framework.TestCase;

public abstract class AbstractUnnormalizedSimilarityTest extends TestCase {
	private static final double DELTA = 0.0001;
	private static final double SIMILARITY_EMPTY_EMPTY = 1.0;


	// ===== Metric Specific =====

	protected abstract TestInput[] getTestInput();

	protected abstract UnnormalizedSimilarityMetric getSimilarityMetric();

	// ===== Similarity Metric Tests =====

	public void testNullArguments() {
		IllegalArgumentException ex = null;

		try {
			getSimilarityMetric().computeUnnormalizedSimilarity(null, null);
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);

		ex = null;

		try {
			getSimilarityMetric().computeUnnormalizedSimilarity("any string", null);
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);

		ex = null;
		try {
			getSimilarityMetric().computeUnnormalizedSimilarity(null, "any string");
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);
	}

	public void testEmptyArguments() {
		String s1 = "any string";
		String s2 = "";

		getSimilarityMetric().computeUnnormalizedSimilarity(s1, s2);

		getSimilarityMetric().computeUnnormalizedSimilarity(s2, s1);

		double dist = getSimilarityMetric().computeUnnormalizedSimilarity(s2, s2);
		assertEquals(SIMILARITY_EMPTY_EMPTY, dist);
	}

	public void testCommutativity() {
		for (TestInput ti : getTestInput()) {
			double dist1 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);
			double dist2 = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s2, ti.s1);

			assertEquals(dist1, dist2);
		}
	}

	public void testSimilarity() {
		for (TestInput ti : getTestInput()) {
			double sim = getSimilarityMetric().computeUnnormalizedSimilarity(ti.s1, ti.s2);

			assertEquals("Input: '"+ti.s1+"'  and '"+ti.s2+"'", ti.sim, sim, DELTA);
		}
	}

	// ===== Helper Class =====

	public static class TestInput {
		public String s1;
		public String s2;
		public double sim;

		public TestInput(String s1, String s2, double sim) {
			this.s1 = s1;
			this.s2 = s2;
			this.sim = sim;
		}
	}
}
