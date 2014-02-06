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

import junit.framework.TestCase;

public abstract class AbstractDistanceMetricTest extends TestCase {
	public static final double DELTA = 0.0001;
	public static final double DISTANCE_EMPTY_EMPTY = 0.0;

	// ===== Metric Specific =====

	protected abstract TestInput[] getTestInput();

	protected abstract DistanceMetric getDistanceMetric();

	// ===== Distance Metric Tests =====

	public void testNullArguments() {
		IllegalArgumentException ex = null;

		try {
			getDistanceMetric().computeDistance(null, null);
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);

		ex = null;

		try {
			getDistanceMetric().computeDistance("any string", null);
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);

		ex = null;
		try {
			getDistanceMetric().computeDistance(null, "any string");
		} catch (IllegalArgumentException e) {
			ex = e;
		}
		assertNotNull(ex);
	}

	public void testEmptyArguments() {
		String s1 = "any string";
		String s2 = "";

		getDistanceMetric().computeDistance(s1, s2);

		getDistanceMetric().computeDistance(s2, s1);

		double dist = getDistanceMetric().computeDistance(s2, s2);
		assertEquals(DISTANCE_EMPTY_EMPTY, dist);
	}

	public void testCommutativity() {
		for (TestInput ti : getTestInput()) {
			double dist1 = getDistanceMetric().computeDistance(ti.s1, ti.s2);
			double dist2 = getDistanceMetric().computeDistance(ti.s2, ti.s1);

			assertEquals("Commutativity Test failed on strings " + ti.s1 + " and " + ti.s2, dist1, dist2);
		}
	}

	public void testDistanceToSelf() {
		for (TestInput ti : getTestInput()) {
			double dist1 = getDistanceMetric().computeDistance(ti.s1, ti.s1);
			double dist2 = getDistanceMetric().computeDistance(ti.s2, ti.s2);

			assertEquals("'"+ti.s1+"'", 0.0, dist1);
			assertEquals("'"+ti.s2+"'", 0.0, dist2);
		}
	}

	public void testDistance() {
		for (TestInput ti : getTestInput()) {
			double dist = getDistanceMetric().computeDistance(ti.s1, ti.s2);

			assertEquals("Input: '"+ti.s1+"'  and '"+ti.s2+"'", ti.dist, dist, DELTA);
		}
	}
	
	public void testRobustness() {
		Random rand = new Random();
		
		for (int i=0; i<TestUtils.N; i++) {
			String s1 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);
			String s2 = TestUtils.getRandomString(rand.nextInt(TestUtils.MAX_LEN+1)+TestUtils.MIN_LEN);
			
			try {
				getDistanceMetric().computeDistance(s1, s2);
			} catch(Exception e) {
				fail("Excpection is thrown for input s1='"+s1+"' and s2='"+s2+"'");
			}
		}
	}
	
	// ====== Helper Metrics =====
	
	public static class TestInput {
		public String s1;
		public String s2;
		public double dist;

		public TestInput(String s1, String s2, double dist) {
			this.s1 = s1;
			this.s2 = s2;
			this.dist = dist;
		}
	}
}
