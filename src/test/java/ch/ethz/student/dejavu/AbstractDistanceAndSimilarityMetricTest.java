/*
 * Copyright 2013 Adrien Favre-Bully, Florian Froese, Adrian Schmidmeister
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ethz.student.dejavu;

import java.util.Random;

import ch.ethz.student.dejavu.strings.HammingMetric;
import junit.framework.TestCase;

public abstract class AbstractDistanceAndSimilarityMetricTest extends TestCase {

	private DistanceMetricTest distanceMetricTest;
	private SimilarityMetricTest similarityMetricTest;


	// ===== Constructor =====

	public AbstractDistanceAndSimilarityMetricTest() {
		distanceMetricTest = new DistanceMetricTest(getTestInput());
		similarityMetricTest = new SimilarityMetricTest(getTestInput());
	}

	// ===== Metric Specific =====

	protected abstract TestInput[] getTestInput();

	protected abstract DistanceMetric getDistanceMetric();

	protected abstract SimilarityMetric getSimilarityMetric();

	// ===== Distance Metric Tests =====

	public void testDistanceNullArguments() {
		distanceMetricTest.testNullArguments();
	}

	public void testDistanceEmptyArguments() {
		distanceMetricTest.testEmptyArguments();
	}

	public void testDistanceCommutativity() {
		distanceMetricTest.testCommutativity();
	}

	public void testDistanceToSelf() {
		distanceMetricTest.testDistanceToSelf();
	}

	public void testDistance() {
		distanceMetricTest.testDistance();
	}

	// ===== Similarity Metric Tests =====

	public void testSimilarityNullArguments() {
		similarityMetricTest.testNullArguments();
	}

	public void testSimilarityEmptyArguments() {
		similarityMetricTest.testEmptyArguments();
	}

	public void testSimilarityCommutativity() {
		similarityMetricTest.testCommutativity();
	}

	public void testSimilarityToSelf() {
		similarityMetricTest.testSimilarityToSelf();
	}

	public void testSimilarity() {
		similarityMetricTest.testSimilarity();
	}

	public void testSimilarityBounds() {
		if (getSimilarityMetric() instanceof HammingMetric) {
			for (int i=0; i<TestUtils.N; i++) {
				String s1 = TestUtils.getRandomString(TestUtils.MAX_LEN+1);
				String s2 = TestUtils.getRandomString(TestUtils.MAX_LEN+1);
				double s = getSimilarityMetric().computeSimilarity(s1, s2);

				assertTrue("Similarity s="+s+" not in bounds [0,1] for input s1='"+s1+"' and s2='"+s2+"'", s>=0 && s<=1);
			}
		} else {
			Random rand = new Random();

			for (int i=0; i<TestUtils.N; i++) {
				String s1 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);
				String s2 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);
				double s = getSimilarityMetric().computeSimilarity(s1, s2);

				assertTrue("Similarity s="+s+" not in bounds [0,1] for input s1='"+s1+"' and s2='"+s2+"'", s>=0 && s<=1);
			}
		}
	}

	public void testRobustness() {
		if (getSimilarityMetric() instanceof HammingMetric) {
			for (int i=0; i<TestUtils.N; i++) {
				String s1 = TestUtils.getRandomString(TestUtils.MAX_LEN+1);
				String s2 = TestUtils.getRandomString(TestUtils.MAX_LEN+1);

				try {
					getSimilarityMetric().computeSimilarity(s1, s2);
				} catch(Exception e) {
					fail("Excpection is thrown for input s1='"+s1+"' and s2='"+s2+"'");
				}
			}
		} else {
			Random rand = new Random();

			for (int i=0; i<TestUtils.N; i++) {
				String s1 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);
				String s2 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);

				try {
					getSimilarityMetric().computeSimilarity(s1, s2);
				} catch(Exception e) {
					fail("Excpection is thrown for input s1='"+s1+"' and s2='"+s2+"'");
				}
			}
		}
	}

	// ===== Helper Classes =====

	public static class TestInput {
		public String s1;
		public String s2;
		public double dist;
		public double sim;

		public TestInput(String s1, String s2, double dist, double sim) {
			this.s1 = s1;
			this.s2 = s2;
			this.dist = dist;
			this.sim = sim;
		}
	}

	private class DistanceMetricTest extends AbstractDistanceMetricTest {
		private TestInput[] testInput;

		public DistanceMetricTest(AbstractDistanceAndSimilarityMetricTest.TestInput[] input) {
			testInput = new TestInput[input.length];

			for (int i=0; i<input.length; i++)
				testInput[i] = new TestInput(input[i].s1, input[i].s2, input[i].dist);
		}

		@Override
		protected TestInput[] getTestInput() {
			return testInput;
		}

		@Override
		protected DistanceMetric getDistanceMetric() {
			return AbstractDistanceAndSimilarityMetricTest.this.getDistanceMetric();
		}

	}

	private class SimilarityMetricTest extends AbstractSimilarityMetricTest {
		private TestInput[] testInput;

		public SimilarityMetricTest(AbstractDistanceAndSimilarityMetricTest.TestInput[] input) {
			testInput = new TestInput[input.length];

			for (int i=0; i<input.length; i++)
				testInput[i] = new TestInput(input[i].s1, input[i].s2, input[i].sim);
		}

		@Override
		protected TestInput[] getTestInput() {
			return testInput;
		}

		@Override
		protected SimilarityMetric getSimilarityMetric() {
			return AbstractDistanceAndSimilarityMetricTest.this.getSimilarityMetric();
		}

	}
}
