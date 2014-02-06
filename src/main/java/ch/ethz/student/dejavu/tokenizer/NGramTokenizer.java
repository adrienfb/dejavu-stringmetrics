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
package ch.ethz.student.dejavu.tokenizer;

public class NGramTokenizer extends Tokenizer{

	private int n;
	
	public NGramTokenizer(int nGrams) {
		super();
		this.n = nGrams;
	}
	
	public NGramTokenizer(String s, int nGrams) {
		super(s);
		this.n = nGrams;
	}

	@Override
	public boolean hasMoreTokens() {
		return currentPosition + (n-1) < s.length();
	}

	@Override
	public String nextToken() {
		String token = null;
		if (currentPosition + n <= s.length()) {
			token = s.substring(currentPosition, currentPosition + (n-1));
			currentPosition++;
		}
		return token;
	}

}
