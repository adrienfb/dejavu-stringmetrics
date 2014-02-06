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

import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;


/**
 * Jaro distance implementation 
 * 
 * The implementation follows the definition given by Wikipedia
 * 
 * <p>For creation please use default JaroDistance or the Builder {@link Builder} provided by the static methods.</p>
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Jaro-Winkler_distance">http://en.wikipedia.org/wiki/Jaro-Winkler_distance</a></p>
 * 
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since   1.0
 *
 */
public class JaroMetric implements SimilarityMetric {

	// ===== Metric Methods =====

	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		int window = Math.max(s1.length(), s2.length()) / 2 - 1;
		StringBuilder matchings12 = getMatchings(s1, s2, window);
		StringBuilder matchings21 = getMatchings(s2, s1, window);

		// if zero matchings return immediately
		if (matchings12.length() == 0)
			return 0d;

		// else compute transpositions
		int t = 0;
		for (int i=0; i<matchings12.length(); i++)
			if (matchings12.charAt(i) != matchings21.charAt(i))
				t++;

		// compute Jaro distance
		int m = matchings12.length();
		return 1d/3d * ( (double)m / (double)s1.length() + (double)m / (double)s2.length() + (double)(m - (double)t/2) / (double)m);
	}
	
	private StringBuilder getMatchings(String a, String b, int window) {
		StringBuilder matchings = new StringBuilder();
		boolean[] matched = new boolean[b.length()];

		for (int i=0; i<a.length(); i++) {
			for (int j=Math.max(0, i-window); j<Math.min(b.length(), i+window+1); j++) {
				if (a.charAt(i) == b.charAt(j) && !matched[j]) {
					matched[j] = true;
					matchings.append(a.charAt(i));
					break;
				}

			}
		}

		return matchings;
	}

	// ===== Builder Pattern Methods =====
	
	private JaroMetric(Builder b) {}

	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see JaroMetric
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * @return	standard Jaro distance object that can be used to determine the distance between two strings
	 * @see JaroMetric
	 */
	public static JaroMetric getInstance() {
		return new Builder().build();
	}

	// ===== Builder Class =====

	/**
	 * JaroDistance Builder
	 * 
	 * <p>No parameters, just call build()</p>
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	public static class Builder {

		public JaroMetric build() {
			return new JaroMetric(this);
		}
	}

}
