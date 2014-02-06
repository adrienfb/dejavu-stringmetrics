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
package ch.ethz.student.dejavu.utilities;

public class Utilities {
	
	public static final double DISTANCE_EMPTY_EMPTY = 0.0;
	public static final double SIMILARITY_EMPTY_EMPTY = 1.0;
	
	static public boolean checkInputs(String s1, String s2) {
		if (s1 == null || s2 == null)
			throw new IllegalArgumentException("Input strings cannot be null!");
		
		return !(s1.isEmpty() && s2.isEmpty());
	}
	
	static public double min(double ...ds )
	{
		if (ds.length < 2)
			throw new IllegalArgumentException("Input values cannot be fewer than 2");
		
		double min_value = ds[0];
		for (int i = 1; i < ds.length; i++)
		{
			if (ds[i] < min_value)
			{
				min_value = ds[i];
			}
		}
		return min_value;
	}
	
	static public double max(double ...ds )
	{
		if (ds.length < 2)
			throw new IllegalArgumentException("Input values cannot be fewer than 2");
		
		double max_value = ds[0];
		for (int i = 1; i < ds.length; i++)
		{
			if (ds[i] > max_value)
			{
				max_value = ds[i];
			}
		}
		return max_value;
	}

}

