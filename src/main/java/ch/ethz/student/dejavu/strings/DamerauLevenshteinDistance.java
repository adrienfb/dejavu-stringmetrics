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

import java.util.SortedMap;
import java.util.TreeMap;

import ch.ethz.student.dejavu.DistanceMetric;
import ch.ethz.student.dejavu.SimilarityMetric;
import ch.ethz.student.dejavu.utilities.Utilities;

/** 
 * The Damerau-Levenshtein distance between two strings is given by 
 * counting the minimum number of operations needed to transform one 
 * string into the other, where an operation is defined as an insertion, 
 * deletion, or substitution of a single character, or a transposition 
 * of two adjacent characters.
 * 
 * For creation please use default DamerauLevenshteinDistance or the Builder {@link Builder}.
 * 
 * <p>Wikipedia: <a href="http://en.wikipedia.org/wiki/Damerau-Levenshtein_distance">http://en.wikipedia.org/wiki/Damerau-Levenshtein_distance</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 * 
 */
public class DamerauLevenshteinDistance implements DistanceMetric, SimilarityMetric
{
	private DamerauLevenshteinDistance() {}

	// ===== Metric Methods =====

	@Override
	public double computeSimilarity(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.SIMILARITY_EMPTY_EMPTY;
		
		double sim = computeDistance(s1, s2);

		return 1 - (sim / Math.max(s1.length(),s2.length()));
	}

	/**
	 * @return double Damerau-Levenshtein distance between String s1 and s2
	 * @param String s1, first string
	 * @param String s2, second string
	 */
	@Override
	public double computeDistance(String s1, String s2) {
		if (!Utilities.checkInputs(s1, s2))
			return Utilities.DISTANCE_EMPTY_EMPTY;
		
		int INF = s1.length() + s2.length();

		double distance[][] = new double[s1.length()+2][s2.length()+2];
		SortedMap<String, Integer> dictionary = new TreeMap<String, Integer>();
		int i1,j1,d1;

		// dynamic programming table initialization
		distance[0][0] = INF;
		for (int i = 0; i <= s1.length(); i++)
		{
			distance[i+1][0] = INF;
			distance[i+1][1] = i;
		}
		for (int j = 0; j <= s2.length(); j++)
		{
			distance[0][j+1] = INF;
			distance[1][j+1] = j;
		}
		for (int d = 0; d < s1.length(); d++)
		{
			if (!dictionary.containsKey(String.valueOf(s1.charAt(d))))
			{
				dictionary.put(String.valueOf(s1.charAt(d)), 0);
			}
		}
		for (int d = 0; d < s2.length(); d++)
		{
			if (!dictionary.containsKey(String.valueOf(s2.charAt(d))))
			{
				dictionary.put(String.valueOf(s2.charAt(d)), 0);
			}
		}

		// dynamic programming loop
		for (int i = 1; i <= s1.length(); i++)
		{
			d1 = 0;
			for (int j = 1; j <= s2.length(); j++)
			{
				i1 = dictionary.get(String.valueOf(s2.charAt(j-1)));
				j1 = d1;
				if (s1.charAt(i-1) == s2.charAt(j-1))
				{
					distance[i+1][j+1] = distance[i][j];
					d1 = j;
				}
				else
				{
					distance[i+1][j+1] = Utilities.min(distance[i][j], distance[i+1][j], distance[i][j+1]) + 1;
				}
				distance[i+1][j+1] = Math.min(distance[i+1][j+1], distance[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
			}
			dictionary.put(String.valueOf(s1.charAt(i-1)), i);
		}
		return distance[s1.length()+1][s2.length()+1];
	}


	// ===== Builder Methods =====
	/**
	 * @return	{@link Builder}
	 * @see Builder
	 * @see DamerauLevenshteinDistance
	 */
	public static Builder getBuilder()
	{
		return new Builder();
	}
	/**
	 * @return A standard DamerauLevenshteinDistance object
	 * @return {@link DamerauLevenshteinDistance}
	 * @see DamerauLevenshteinDistance
	 */
	public static DamerauLevenshteinDistance getInstance()
	{
		return new Builder().build();
	}

	// ===== Builder Class =====
	/**
	 * DamerauLevenshteinDistance Builder
	 * 
	 * @author Adrien Favre-Bully
	 * @author Florian Froese
	 * @author Adrian Schmidmeister
	 * @since 1.0
	 */
	public static class Builder
	{	
		public Builder()
		{
		}

		public DamerauLevenshteinDistance build()
		{
			DamerauLevenshteinDistance dist = new DamerauLevenshteinDistance();

			return dist;
		}
	}

}
