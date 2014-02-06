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
package ch.ethz.student.dejavu.vectors;

import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;



/**
 * Overlap Coefficient implementation based on a vector model. 
 * This implementation uses the formula: overlapCofficient = intersect(A, B) / ( Min(|A|, |B|) )
 * where A and B are sets of tokens. </br>
 * |.| is the size of the token set.
 * 
 * <p>For creation please use default OverlapCoefficient or the Builder {@link Builder} provided by the static methods.</p>
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Overlap_coefficient">http://en.wikipedia.org/wiki/Overlap_coefficient</a></p>
 * 
 * @author Florian Froese
 * @see     VectorSimilarity
 * @since   1.0
 *
 */
public class OverlapCoefficient extends VectorSimilarity implements SimilarityMetric {
	
	private OverlapCoefficient(Builder builder) {
		super(builder);
	}

	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		computeTokens(s1, s2);
		
		if (s1.length() == 0 || s2.length() == 0) {
			return 0.0;
		}
		
		double i = 0.0;
		double mVector1 = 0.0;
		double mVector2 = 0.0;
		
		if (takeKeyset) {
			i = VectorUtils.intersect(keys1, keys2);
			mVector1 = VectorUtils.size(keys1);
			mVector2 = VectorUtils.size(keys2);
		} else {
			i = VectorUtils.intersect(vec1, vec2);
			mVector1 = VectorUtils.size(vec1);
			mVector2 = VectorUtils.size(vec2);
		}
		
		double overlapCoefficient = i / Math.min(mVector1, mVector2);
		
		return overlapCoefficient;
	}
	
	// ===== Builder Pattern Methods =====

	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see OverlapCoefficient
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * @return	standard overlap coefficient that can be used to determine the overlap coefficient of two strings.
	 * @see OverlapCoefficient
	 */
	public static OverlapCoefficient getInstance() {
		return new Builder().build();
	}
	
	// ===== Builder Class =====

	/**
	 * OverlapCoefficient Builder
	 * No further restrictions or additional parameters.
	 * 
	 * <p>For configuration and usage refer to {@link VectorBuilder}</p>
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @see VectorBuilder
	 * @since 1.0
	 */
	public static class Builder extends VectorBuilder<Builder>{

		public OverlapCoefficient build() {
			// check constraints


			// build object
			OverlapCoefficient d = new OverlapCoefficient(this);

			return d;
		}
	}
		
}
