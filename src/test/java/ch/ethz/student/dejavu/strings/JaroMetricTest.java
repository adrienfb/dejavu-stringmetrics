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
package ch.ethz.student.dejavu.strings;

import ch.ethz.student.dejavu.AbstractSimilarityMetricTest;
import ch.ethz.student.dejavu.SimilarityMetric;

public class JaroMetricTest extends AbstractSimilarityMetricTest {
	private static final JaroMetric metric = JaroMetric.getInstance();
	private static final TestInput[] testInput = {
		new TestInput("MARTHA", "MARHTA", 0.9444),
		new TestInput("DWAYNE", "DUANE", 0.8222),
		new TestInput("DIXON", "DICKSONX", 0.7666),
		new TestInput("jones", "johnson", 0.7904),
		new TestInput("fvie", "ten", 0.0),
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
