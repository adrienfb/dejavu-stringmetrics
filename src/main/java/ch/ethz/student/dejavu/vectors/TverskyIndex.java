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
 * Tversky Index implementation based on a vector model. 
 * This implementation uses the formula: tverskyIndex = intersect(A, B) / ( intersect(A,B) + a * |A - B| + b * |B - A| )
 * where A and B are sets of tokens. </br>
 * |.| is the size of the token set and A - B denotes the relative complement of B in A.</br>
 * e.g. a = 0.5 and b = 0.5 produces the Dice Coefficient</br>
 * and a = 1 and b = 1 produces the Tanimoto Coefficient
 * 
 * <p>For creation please use default TverskyIndex or the Builder {@link Builder} provided by the static methods.</p>
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Tversky_index">http://en.wikipedia.org/wiki/Tversky_index</a></p>
 * 
 * @author Florian Froese
 * @see     VectorSimilarity
 * @since   1.0
 *
 */
public class TverskyIndex extends VectorSimilarity implements SimilarityMetric{
	// alpha = beta = 1 -> Tanimoto coefficient
	// alpha = beta = 0.5 ->  Dice's coefficient
	
	public static double DEFAULT_ALPHA = 0.5;
	public static double DEFAULT_BETA = 0.5;
	
	private double alpha;
	private double beta;
	
	private TverskyIndex(Builder builder) {
		super(builder);
		this.alpha = builder.alpha;
		this.beta = builder.beta;
	}
	
	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		computeTokens(s1, s2);
		
		if (s1.length() == 0 && s2.length() == 0) {
			return 1.0;
		}
		
		double i = 0.0;
		double compv1v2 = 0.0;
		double compv2v1 = 0.0;
		
		if (takeKeyset) {
			i = VectorUtils.intersect(keys1, keys2);
			compv1v2 = VectorUtils.complement(keys1, keys2);
			compv2v1 = VectorUtils.complement(keys2, keys1);
		} else {
			i = VectorUtils.intersect(vec1, vec2);
			compv1v2 = VectorUtils.complement(vec1, vec2);
			compv2v1 = VectorUtils.complement(vec2, vec1);
		}
		
		double tverskyCoefficient = i / (i + alpha * compv1v2 + beta * compv2v1);
		
		return tverskyCoefficient;
	}
	
	// ===== Builder Pattern Methods =====

	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see TverskyIndex
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * @return	standard tversky index that can be used to determine the tversky index of two strings.
	 * @see TverskyIndex
	 */
	public static TverskyIndex getInstance() {
		return new Builder().build();
	}

	// ===== Builder Class =====

	/**
	 * TverskyIndex Builder
	 * Restriction: alpha and beta parameters must both be greater or equal to 0!
	 * 
	 * <p>For configuration and usage refer to {@link VectorBuilder}</p>
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @param alpha		alphpa parameter of tversky
	 * @param beta 		beta parameter of tversky
	 * @see VectorBuilder
	 * @since 1.0
	 */
	public static class Builder extends VectorBuilder<Builder>{
		private double alpha = DEFAULT_ALPHA;
		private double beta = DEFAULT_BETA;

		public TverskyIndex build() {
			// check constraints
			if (alpha < 0) {
				throw new IllegalArgumentException("alpha must be greater or equal to 0!");
			}
			if (beta < 0) {
				throw new IllegalArgumentException("beta must be greater or equal to 0!");
			}

			// build object
			TverskyIndex d = new TverskyIndex(this);

			return d;
		}
		
		/**
		 * Sets the alpha parameter of tversky index.
		 * Has to be greater or equal to 0!
		 * @param a		alpha parameter
		 * @return		this builder object
		 */
		public Builder alpha(double a) {
			alpha = a;
			return this;
		}
		
		/**
		 * Sets the beta parameter of tversky index.
		 * Has to be greater or equal to 0!
		 * @param b		beta parameter
		 * @return		this builder object
		 */
		public Builder beta(double b) {
			beta = b;
			return this;
		}
	}
}
