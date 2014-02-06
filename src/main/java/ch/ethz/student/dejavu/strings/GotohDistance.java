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
import ch.ethz.student.dejavu.utilities.Utilities;

/** 
 * The Gotoh distance is an extension of the Smith-Waterman algorithm
 * 
 *  * For creation please use default GotohDistance or the Builder {@link Builder}.
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Smith-Waterman_algorithm">http://en.wikipedia.org/wiki/Smith-Waterman_algorithm</a></p>
 * 
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * 
 * @see SmithWatermanDistance
 * @since 1.0
 * 
 */
public class GotohDistance implements DistanceMetric {

	public static final double DEFAULT_MATCH_SCORE = 2.0;
	public static final double DEFAULT_MISMATCH_SCORE = -1.0;
	public static final double DEFAULT_GAP_SCORE = -1.0;
	public static final int DEFAULT_WINDOW_SIZE = 100;

	private double matchScore;
	private double mismatchScore;
	private double gapScore;
	private int windowSize;

	private GotohDistance(Builder b) {
		matchScore = b.bMatchScore;
		mismatchScore = b.bMismatchScore;
		gapScore = b.bGapScore;
		windowSize = b.bWindowSize;
	}


	// ===== Metric Methods =====

	/**
	 * @return double Gotoh Distance between String s1 and s2
	 * @param String s1, first string
	 * @param String s2, second string
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	@Override
	public double computeDistance(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.DISTANCE_EMPTY_EMPTY;
		
		double alignment[][] = new double[s1.length()+1][s2.length()+1];

		double match, delete, insert, cost=0, maxScore = 0.0, maxGap;

		for (int i=0; i<=s1.length(); i++)
		{

			maxGap = 0;
			for (int k = (int) Utilities.max(1,i-windowSize); k < i; i++)
			{
				if (s1.charAt(i-k-1) == s2.charAt(0))
				{
					cost = this.matchScore;
				}
				else
				{
					cost = this.mismatchScore;
				}
				maxGap = Utilities.max(maxGap, alignment[i-k][0] - cost);
			}
			alignment[i][0] = Utilities.max(cost,0);
		}


		for (int j=0; j<=s2.length(); j++)
			alignment[0][j] = 0;

		for (int i=1; i<=s1.length(); i++)
		{
			for (int j=1; j<=s2.length(); j++)
			{
				if (s1.charAt(i-1) == s2.charAt(j-1))
				{
					cost = this.matchScore;
				}
				else
				{
					cost = this.mismatchScore;
				}
				match = alignment[i-1][j-1] + cost;

				delete = alignment[i-1][j] + this.gapScore;
				insert = alignment[i][j-1] + this.gapScore;
				alignment[i][j] = Utilities.max(match, delete, insert, 0);
				if (alignment[i][j] > maxScore)
					maxScore = alignment[i][j];
			}
		}

		return maxScore;
	}

	// ===== Builder Methods =====
	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see GotohDistance
	 */
	public static Builder getBuilder()
	{
		return new Builder();
	}
	/**
	 * @return A standard GotohDistance object
	 * @return	{@link GotohDistance}
	 * @see GotohDistance
	 */
	public static GotohDistance getInstance()
	{
		return new Builder().build();
	}

	// ===== Builder Class =====
	/**
	 * GotohDistance Builder
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	public static class Builder
	{
		private double bMatchScore;
		private double bMismatchScore;
		private double bGapScore;
		private int bWindowSize;

		public Builder()
		{
			bMatchScore = DEFAULT_MATCH_SCORE;
			bMismatchScore = DEFAULT_MISMATCH_SCORE;
			bGapScore = DEFAULT_GAP_SCORE;
			bWindowSize = DEFAULT_WINDOW_SIZE;
		}

		public GotohDistance build()
		{
			// scores can potentially be any value. no need to check

			GotohDistance d = new GotohDistance(this);

			return d;
		}

		public Builder matchScore(double score) {
			bMatchScore = score;
			return this;
		}
		public Builder mismatchScore(double score) {
			bMismatchScore = score;
			return this;
		}
		public Builder gapScore(double score) {
			bGapScore = score;
			return this;
		}
		public Builder windowSize(int size) {
			bWindowSize = size;
			return this;
		}
	}

}
