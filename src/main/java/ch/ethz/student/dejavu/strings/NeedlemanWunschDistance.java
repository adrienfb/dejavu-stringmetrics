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

import java.util.TreeMap;

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/**
 * The Needleman-Wunsch algorithm performs a global alignment on two sequences. 
 * It is commonly used in bioinformatics to align protein or nucleotide sequences.
 * The algorithm was published in 1970 by Saul B. Needleman and Christian D. Wunsch.
 * 
 * For creation please use default NeedlemanWunschDistance or the Builder {@link Builder}.
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Needleman-Wunsch_algorithm">http://en.wikipedia.org/wiki/Needleman-Wunsch_algorithm</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class NeedlemanWunschDistance implements DistanceMetric, SimilarityMetric {
	private static final double DEFAULT_GAP_SCORE = -4.0;
	private static final double DEFAULT_MATCH_SCORE = 5.0;
	private static final double DEFAULT_MISMATCH_SCORE = -3.0;
	
	private TreeMap<String, TreeMap<String, Double> > similarityMatrix;
	private double gap;
	private double match;
	private double mismatch;
	
	private static final TreeMap<String, TreeMap<String, Double> > DEFAULT_SIMILARITY_MATRIX()
	{
		TreeMap<String, TreeMap<String, Double> > s = new TreeMap<String, TreeMap<String, Double> >();
		TreeMap<String, Double> temp = new TreeMap<String, Double>();
		temp.put("A", 10.0);
		temp.put("G", -1.1);
		temp.put("C", -3.0);
		temp.put("T", -4.0);
		s.put("A", temp);
		temp = new TreeMap<String, Double>();
		temp.put("A", -1.0);
		temp.put("G", 7.0);
		temp.put("C", -5.0);
		temp.put("T", -3.0);
		s.put("G", temp);
		temp = new TreeMap<String, Double>();
		temp.put("A", -3.0);
		temp.put("G", -5.0);
		temp.put("C", 9.0);
		temp.put("T", 0.0);
		s.put("C", temp);
		temp = new TreeMap<String, Double>();
		temp.put("A", -4.0);
		temp.put("G", -3.0);
		temp.put("C", 0.0);
		temp.put("T", 8.0);
		s.put("T", temp);
		
		s.clear(); //comment in order to use the DNA matrix
		return s;
	}
	
	private NeedlemanWunschDistance() {}
	
	
	// ===== Metric Methods =====
	
	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		double dist = computeDistance(s1, s2);
		double l = Utilities.max(s1.length(),s2.length());
		double base = Utilities.min(l * gap, l * mismatch);
		return (dist - base) / (l * match - base);
	}
	
	/**
	 * @return double Needleman-Wunsch Distance between String s1 and s2
	 * @param s1 String, first string
	 * @param s2 String, second string
	 */
	@Override
	public double computeDistance(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.DISTANCE_EMPTY_EMPTY;
		
		double alignment[][] = new double[s1.length()+1][s2.length()+1];
		double match, delete, insert;
		double gap = this.gap;
		
		for (int i=0; i<=s1.length(); i++)
			alignment[i][0] = gap * i;
		
		for (int j=0; j<=s2.length(); j++)
			alignment[0][j] = gap * j;
		
		for (int i=1; i<=s1.length(); i++)
		{
			for (int j=1; j<=s2.length(); j++)
			{
				match = alignment[i-1][j-1] + this.charSimilarity(s1.charAt(i-1),s2.charAt(j-1));
				delete = alignment[i-1][j] + gap;
				insert = alignment[i][j-1] + gap;
				alignment[i][j] = Utilities.max(match, delete, insert);
			}
		}
		
		return alignment[s1.length()][s2.length()];
	}
	
	
	private double charSimilarity(char c1, char c2)
	{
		if (this.similarityMatrix.containsKey(String.valueOf(c1))
				&& this.similarityMatrix.containsKey(String.valueOf(c2)))
		{
			return this.similarityMatrix.get(String.valueOf(c1)).get(String.valueOf(c2));
		}
		else
		{
			if (c1 == c2)
			{
				return this.match;
			}
			else
			{
				return this.mismatch;
			}
		}
	}

	// ===== Builder Methods =====
	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see NeedlemanWunschDistance
	 */
	public static Builder getBuilder()
	{
		return new Builder();
	}
	/**
	 * @return A standard NeedlemanWunschDistance object
	 * @return	{@link NeedlemanWunschDistance}
	 * @see NeedlemanWunschDistance
	 */
	public static NeedlemanWunschDistance getInstance()
	{
		return new Builder().build();
	}
	// ===== Builder Class =====
	/**
	 * NeedlemanWunschDistance Builder
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	public static class Builder
	{
		private TreeMap<String, TreeMap<String, Double> > bSimilarityMatrix;
		private double bGapScore;
		private double bMatchScore;
		private double bMismatchScore;
		
		public Builder()
		{
			bGapScore = DEFAULT_GAP_SCORE;
			bMatchScore = DEFAULT_MATCH_SCORE;
			bMismatchScore = DEFAULT_MISMATCH_SCORE;
			bSimilarityMatrix = DEFAULT_SIMILARITY_MATRIX();
		}
		
		public NeedlemanWunschDistance build()
		{
			// parameters have no constraints
			
			NeedlemanWunschDistance d = new NeedlemanWunschDistance();
			
			d.gap = bGapScore;
			d.match = bMatchScore;
			d.mismatch = bMismatchScore;
			d.similarityMatrix = bSimilarityMatrix;
			
			return d;
		}
		
		/**
		 * Gap Score (can be any double)
		 * 
		 * @param gapScore double
		 * @return this builder object
		 */
		public Builder gapScore(double gapScore)
		{
			bGapScore = gapScore;
			return this;
		}
		/**
		 * Match Score (can be any double)
		 * 
		 * @param matchScore double
		 * @return this builder object
		 */
		public Builder matchScore(double matchScore)
		{
			bMatchScore = matchScore;
			return this;
		}
		/**
		 * Mismatch Score (can be any double)
		 * 
		 * @param mismatchScore double
		 * @return this builder object
		 */
		public Builder mismatchScore(double mismatchScore)
		{
			bMismatchScore = mismatchScore;
			return this;
		}
		
		/**
		 * Similarity Matrix, any matrix with similarity values for the alphabet,
		 * If a character is not found in the matrix then the normal match, mismatch score is used
		 * 
		 * @param similarityMatrix TreeMap<String, TreeMap<String, Double> >
		 * @return this builder object
		 */
		public Builder similarityMatrix(TreeMap<String, TreeMap<String, Double> > similarityMatrix)
		{
			bSimilarityMatrix = similarityMatrix;
			return this;
		}
	}

}
