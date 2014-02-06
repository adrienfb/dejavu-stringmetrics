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

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * The Levenshtein distance between two strings is defined as the minimum 
 * number of edits needed to transform one string into the other, with the 
 * allowable edit operations being insertion, deletion, or substitution of 
 * a single character.
 * The Optimal String Alignment distance is an extension of Levenshtein distance
 * were it treats adjacent transpositions as another basic operation
 * 
 * For creation please use default OptimalStringAlignmentDistance or the Builder {@link Builder}.
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Optimal_string_alignment">http://en.wikipedia.org/wiki/Optimal_string_alignment</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class OptimalStringAlignmentDistance implements DistanceMetric, SimilarityMetric {	
	private static final double DEFAULT_INSERTION_WEIGHT = 1.0;
	private static final double DEFAULT_DELETION_WEIGHT = 1.0;
	private static final double DEFAULT_SUBSTITUTION_WEIGHT = 1.0;
	private static final double DEFAULT_TRANSPOSITION_WEIGHT = 1.0;

	private double insertionWeight;
	private double deletionWeight;
	private double substitutionWeight;
	private double transpositionWeight;

	// costructors
	private OptimalStringAlignmentDistance(Builder b) {
		insertionWeight = b.bInsertionWeight;
		deletionWeight = b.bDeletionWeight;
		substitutionWeight = b.bSubstitutionWeight;
		transpositionWeight = b.bTranspositionWeight;
	}


	// ===== Metric Methods =====

	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		double dist = computeDistance(s1, s2);

		return 1 - (dist / Utilities.max(s1.length(),s2.length()) / substitutionWeight);
	}
	
	/**
	 * @return double Optimal String Alignment Distance between String s1 and s2
	 * @param s1 String, first string
	 * @param s2 String, second string
	 */
	@Override
	public double computeDistance(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.DISTANCE_EMPTY_EMPTY;
		
		double distance[][] = new double[s1.length()+1][s2.length()+1];
		double substitutionCost, transpositionCost;

		// dynamic programming table initialization
		for (int i = 0; i <= s1.length(); i++)
		{
			distance[i][0] = i;
		}
		for (int j = 0; j <= s2.length(); j++)
		{
			distance[0][j] = j;
		}

		// dynamic programming loop
		for (int i = 1; i <= s1.length(); i++)
		{
			for (int j = 1; j <= s2.length(); j++)
			{
				substitutionCost = (s1.charAt(i-1) != s2.charAt(j-1)) ? this.substitutionWeight : 0;
				distance[i][j] = Utilities.min(
						distance[i-1][j] + this.deletionWeight,
						distance[i][j-1] + this.insertionWeight,
						distance[i-1][j-1] + substitutionCost
						);
				if (i > 1 && j > 1 && s1.charAt(i-1) == s2.charAt(j-2) && s1.charAt(i-2) == s2.charAt(j-1))
				{
					transpositionCost = (s1.charAt(i-1) != s2.charAt(j-1)) ? this.transpositionWeight : 0;
					distance[i][j] = Utilities.min(
							distance[i][j],
							distance[i-2][j-2] + transpositionCost
							);
				}
			}
		}
		return distance[s1.length()][s2.length()];
	}


	// ===== Builder Methods =====
	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see OptimalStringAlignmentDistance
	 */
	public static Builder getBuilder()
	{
		return new Builder();
	}
	/**
	 * @return A standard OptimalStringAlignmentDistance object
	 * @return	{@link OptimalStringAlignmentDistance}
	 * @see OptimalStringAlignmentDistance
	 */
	public static OptimalStringAlignmentDistance getInstance()
	{
		return new Builder().build();
	}
	// ===== Builder =====
	/**
	 * OptimalStringAlignmentDistance Builder
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	public static class Builder
	{
		private double bInsertionWeight;
		private double bDeletionWeight;
		private double bSubstitutionWeight;
		private double bTranspositionWeight;

		public Builder()
		{
			bInsertionWeight = DEFAULT_INSERTION_WEIGHT;
			bDeletionWeight = DEFAULT_DELETION_WEIGHT;
			bSubstitutionWeight = DEFAULT_SUBSTITUTION_WEIGHT;
			bTranspositionWeight = DEFAULT_TRANSPOSITION_WEIGHT;
		}

		public OptimalStringAlignmentDistance build()
		{
			if (bInsertionWeight <= 0.0)
				throw new IllegalArgumentException("The insertion weight must be positive.");
			if (bDeletionWeight <= 0.0)
				throw new IllegalArgumentException("The deletion weight must be positive.");
			if (bSubstitutionWeight <= 0.0)
				throw new IllegalArgumentException("The substitution weight must be positive.");
			if (bTranspositionWeight <= 0.0)
				throw new IllegalArgumentException("The transposition weight must be positive.");

			OptimalStringAlignmentDistance d = new OptimalStringAlignmentDistance(this);

			return d;
		}

		/**
		 * Insertion weight (must be positive)
		 * 
		 * @param insertionWeight double
		 * @return this builder object
		 */
		public Builder insertionWeight(double insertionWeight)
		{
			bInsertionWeight = insertionWeight;
			return this;
		}

		/**
		 * Deletion weight (must be positive)
		 * 
		 * @param deletionWeight double
		 * @return this builder object
		 */
		public Builder deletionWeight(double deletionWeight)
		{
			bDeletionWeight = deletionWeight;
			return this;
		}

		/**
		 * Substitution weight (must be positive)
		 * 
		 * @param substitutionWeight double
		 * @return this builder object
		 */
		public Builder substitutionWeight(double substitutionWeight)
		{
			bSubstitutionWeight = substitutionWeight;
			return this;
		}

		/**
		 * Transposition weight (must be positive)
		 * 
		 * @param transpositionWeight double
		 * @return this builder object
		 */
		public Builder transpositionWeight(double transpositionWeight)
		{
			bTranspositionWeight = transpositionWeight;
			return this;
		}
	}

}
