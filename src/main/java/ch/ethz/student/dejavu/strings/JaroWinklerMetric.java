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
 * JaroWinkler distance implementation 
 * 
 * The implementation follows the definition given by Wikipedia
 * 
 * <p>For creation please use default JaroWinklerDistance or the Builder {@link Builder} provided by the static methods.</p>
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Jaro-Winkler_distance">http://en.wikipedia.org/wiki/Jaro-Winkler_distance</a></p>
 * 
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since   1.0
 *
 */
public class JaroWinklerMetric implements SimilarityMetric {
	public static final double DEFAULT_SCALING_FACTOR = 0.1d;
	public static final double DEFAULT_PREFIX_LENGTH = 4;

	private double scalingFactor;
	private double prefixLength;


	// ===== Metric Methods =====

	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		// compute length of common prefix (max 4)
		double l = 0;
		int i=0;

		while (l < prefixLength && i<s1.length() && i<s2.length() && s1.charAt(i) == s2.charAt(i)) {
			i++;
			l++;
		}

		// compute Jaro-Winkler distance
		JaroMetric jdm = new JaroMetric.Builder().build();
		double jd = jdm.computeSimilarity(s1, s2);

		return jd + ( l * scalingFactor  * (1-jd) );
	}


	// ===== Builder Pattern Methods =====

	private JaroWinklerMetric(Builder b) {
		scalingFactor = b.scalingFactor;
		prefixLength = b.prefixLength;
	}
	
	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see JaroWinklerMetric
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * @return	standard JaroWinkler distance object that can be used to determine the distance between two strings
	 * @see JaroMetric
	 */
	public static JaroWinklerMetric getInstance() {
		return new Builder().build();
	}


	// ===== Builder Class =====

	/**
	 * JaroWinkler Builder
	 * 
	 * <p>Note that setting either prefixLength or scalingFactor to zero computes the standard Jaro distance</p>
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @param f		scaling factor
	 * @param pl	prefix length
	 * @since 1.0
	 */
	public static class Builder {

		private double scalingFactor;
		private double prefixLength;

		public Builder() {
			scalingFactor = DEFAULT_SCALING_FACTOR;
			prefixLength = DEFAULT_PREFIX_LENGTH;
		}

		public JaroWinklerMetric build() {
			// check constraints
			if (scalingFactor < 0 || scalingFactor > 1/prefixLength)
				throw new IllegalArgumentException("Scaling factor must lie within [0, 1/prefixLength]");
			if (prefixLength < 0)
				throw new IllegalArgumentException("The prefix length cannot be negative.");

			return new JaroWinklerMetric(this);
		}

		/**
		 * Sets the scaling factor (has to be in the interval [0, 1/prefixLength])
		 * @param f	scaling factor
		 * @return this builder object (allows to chain calls)
		 */
		public Builder scalingFactor(double f) {
			scalingFactor = f;
			return this;
		}

		/**
		 * Sets the prefix length (has to be positive)
		 * @param pl prefix length
		 * @return this builder object (allows to chain calls)
		 */
		public Builder prefixLength(int pl) {
			prefixLength = pl;
			return this;
		}
	}

}
