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
 * Dice Coefficient implementation based on a vector model. 
 * This implementation uses the formula: diceCofficient = 2*intersect(A, B) / ( |A| + |B| )
 * where A and B are sets of tokens. </br>
 * |.| is the size of the token set.
 * 
 * <p>For creation please use default DiceCoefficient or the Builder {@link Builder} provided by the static methods.</p>
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Dice%27s_coefficient">http://en.wikipedia.org/wiki/Dice%27s_coefficient</a></p>
 * 
 * @author Florian Froese
 * @see     VectorSimilarity
 * @since   1.0
 *
 */
public class DiceCoefficient extends VectorSimilarity implements SimilarityMetric {
	
	private DiceCoefficient(Builder builder) {
		super(builder);
	}
	
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		computeTokens(s1, s2);
		
		if (s1.length() == 0 && s2.length() == 0) {
			return 1.0;
		}
		
		double i = 0.0;
		double size1 = 0.0;
		double size2 = 0.0;
		
		if (takeKeyset) {
			i = VectorUtils.intersect(keys1, keys2);
			size1 = VectorUtils.size(keys1);
			size2 = VectorUtils.size(keys2);
		} else {
			i = VectorUtils.intersect(vec1, vec2);
			size1 = VectorUtils.size(vec1);
			size2 = VectorUtils.size(vec2);
		}
		
		double diceCoefficient = 2 * i / (size1 + size2);
		
		return diceCoefficient;
	}
	
	
	// ===== Builder Pattern Methods =====

	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see DiceCoefficient
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * @return	standard dice coefficient that can be used to determine the dice coefficient of two strings.
	 * @see DiceCoefficient
	 */
	public static DiceCoefficient getInstance() {
		return new Builder().build();
	}

	// ===== Builder Class =====
	
	/**
	 * DiceCoefficient Builder
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

		public DiceCoefficient build() {
			// check constraints

			// build object
			DiceCoefficient d = new DiceCoefficient(this);

			return d;
		}
	}

}
