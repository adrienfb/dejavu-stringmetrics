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

/**
 * 
 * 
 * @author Florian Froese
 * @since   1.0
 *
 */
public enum Granularity {
	/**
	 * CHAR_BASED String is separated characterwise
	 */
	CHAR_BASED,
	/**
	 * WORD_BASED String is separated word wise with whitespace as delimiter
	 */
	WORD_BASED,
	/**
	 * SEPERATOR_BASED String is separated by specified delimiter
	 */
	SEPARATOR_BASED,
	/**
	 * NGRAM_BASED String is split up into ngrams specified in ngram
	 */
	NGRAM_BASED;
}