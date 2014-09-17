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

import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import ch.ethz.student.dejavu.tokenizer.CharTokenizer;
import ch.ethz.student.dejavu.tokenizer.NGramTokenizer;
import ch.ethz.student.dejavu.tokenizer.SeparatorTokenizer;
import ch.ethz.student.dejavu.tokenizer.Tokenizer;
import ch.ethz.student.dejavu.tokenizer.WordTokeninzer;

/**
 * @param CHAR_BASED      String is separated characterwise
 * @param WORD_BASED      String is separated word wise with whitespace as delimiter
 * @param SEPARATOR_BASED String is separated by specified delimiter
 * @author Florian Froese
 * @since 1.0
 */
public abstract class VectorSimilarity {

  public static final Granularity DEFAULT_GRANULARITY = Granularity.CHAR_BASED;
  public static final String DEFAULT_DELIMITER = ",";
  public static final int DEFAULT_NGRAM = 3;
  public static final Tokenizer DEFAULT_TOKENIZER = null;
  public static final boolean DEFAULT_TAKE_TOKENIZER = true;
  public static final boolean DEFAULT_TAKE_KEYSET = true;

  protected VectorSimilarity(VectorBuilder<?> builder) {
    this.granularity = builder.granularity;

    if (builder.tokenizer == null) {
      if (granularity.equals(Granularity.CHAR_BASED)) {
        this.tokenizer = new CharTokenizer();
      } else if (granularity.equals(Granularity.WORD_BASED)) {
        this.tokenizer = new WordTokeninzer();
      } else if (granularity.equals(Granularity.SEPARATOR_BASED)) {
        this.tokenizer = new SeparatorTokenizer(builder.delimiter);
      } else if (granularity.equals(Granularity.NGRAM_BASED)) {
        this.tokenizer = new NGramTokenizer(builder.ngram);
      }
    }

    this.delimiter = builder.delimiter;
    this.ngram = builder.ngram;
    this.takeTokenizer = builder.takeTokenizer;
    this.takeKeyset = builder.takeKeyset;
  }

  protected Granularity granularity;
  protected String delimiter;
  protected Tokenizer tokenizer;
  protected int ngram;
  protected boolean takeTokenizer = false;
  protected boolean takeKeyset = false;

  // Internal fields
  protected HashMap<Object, Integer> vec1 = new HashMap<Object, Integer>();
  protected HashMap<Object, Integer> vec2 = new HashMap<Object, Integer>();

  protected HashSet<Object> keys1;
  protected HashSet<Object> keys2;

  public void computeTokens(String s1, String s2) {
    if (takeTokenizer) {
      if (takeKeyset) {
        keys1 = getKeySetTokenizer(s1, keys1);
        keys2 = getKeySetTokenizer(s2, keys2);
      } else {
        vec1 = getTokenVectorTokenizer(s1, vec1);
        vec2 = getTokenVectorTokenizer(s2, vec2);
      }
    } else {
      if (takeKeyset) {
        keys1 = getKeySet(s1, keys1);
        keys2 = getKeySet(s2, keys2);
      } else {
        vec1 = getTokenVector(s1, vec1);
        vec2 = getTokenVector(s2, vec2);
      }
    }
  }

  // ===== Builder Pattern Methods =====

  public static VectorBuilder<?> getBuilder() {
    return null;
  }

	/*
	 * Hashmap vectors ...
	 */

  protected HashMap<Object, Integer> getTokenVector(String s) {
    HashMap<Object, Integer> vec = new HashMap<Object, Integer>();

    vec = getTokenVector(s, null);

    return vec;
  }

  protected HashMap<Object, Integer> getTokenVector(String s, HashMap<Object, Integer> vec) {
    if (vec == null) {
      vec = new HashMap<Object, Integer>();
    } else {
      vec.clear();
    }

    if (this.granularity.equals(Granularity.CHAR_BASED)) {
      vec = getCharacterTokenVector(s, vec);
    } else {
      StringTokenizer tokenizer = null;

      if (this.granularity.equals(Granularity.WORD_BASED)) {
        tokenizer = new StringTokenizer(s);
      } else {
        tokenizer = new StringTokenizer(s, delimiter);
      }
      vec = getSparatorTokenVector(s, tokenizer, vec);
    }

    return vec;
  }

  protected HashMap<Object, Integer> getTokenVectorTokenizer(String s,
                                                             HashMap<Object, Integer> vec) {
    if (vec == null) {
      vec = new HashMap<Object, Integer>();
    } else {
      vec.clear();
    }

    tokenizer.reset(s);

    while (tokenizer.hasMoreTokens()) {
      Object token = tokenizer.nextToken();
      int count = 0;
      if (vec.containsKey(token)) {
        count = vec.get(token);
      }
      count++;
      vec.put(token, count);
    }

    return vec;
  }

  protected HashSet<Object> getKeySet(String s, HashSet<Object> keys) {
    if (keys == null) {
      keys = new HashSet<Object>();
    } else {
      keys.clear();
    }

    if (this.granularity.equals(Granularity.CHAR_BASED)) {

      for (int i = 0; i < s.length(); i++) {
        Character token = s.charAt(i);
        keys.add(token);
      }

    } else {
      StringTokenizer tokenizer = null;

      if (this.granularity.equals(Granularity.WORD_BASED)) {
        tokenizer = new StringTokenizer(s);
      } else {
        tokenizer = new StringTokenizer(s, delimiter);
      }

      while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        keys.add(token);
      }

    }

    return keys;
  }

  protected HashSet<Object> getKeySetTokenizer(String s, HashSet<Object> keys) {
    if (keys == null) {
      keys = new HashSet<Object>();
    } else {
      keys.clear();
    }

    tokenizer.reset(s);

    while (tokenizer.hasMoreTokens()) {
      Object token = tokenizer.nextToken();
      keys.add(token);
    }

    return keys;
  }

  private HashMap<Object, Integer> getCharacterTokenVector(String s, HashMap<Object, Integer> vec) {

    for (int i = 0; i < s.length(); i++) {
      Character token = s.charAt(i);
      int count = 0;
      if (vec.containsKey(token)) {
        count = vec.get(token);
      }
      count++;
      vec.put(token, count);
    }
    return vec;
  }

  private HashMap<Object, Integer> getSparatorTokenVector(String s, StringTokenizer tokenizer,
                                                          HashMap<Object, Integer> vec) {

    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      int count = 0;
      if (vec.containsKey(token)) {
        count = vec.get(token);
      }
      count++;
      vec.put(token, count);
    }
    return vec;
  }

  // old
  @Deprecated
  private HashMap<Object, Integer> getCharacterVector(String s, HashMap<Object, Integer> vec) {
    if (vec == null) {
      vec = new HashMap<Object, Integer>();
    } else {
      vec.clear();
    }

    for (int i = 0; i < s.length(); i++) {
      Character token = s.charAt(i);
      int count = 0;
      if (vec.containsKey(token)) {
        count = vec.get(token);
      }
      count++;

      vec.put(token, count);
    }

    return vec;
  }

  // ===== Builder Class =====

  /**
   * VectorSimilarity Builder
   *
   * <p><code>VectorBuilder</code> is an abstract class that can set vector similarity
   * parameters.</p> <p>Usage of a Builder in general (e.g. JaccardDistance):</p> <p>Builder b =
   * JaccardDistance.getBuilder();</br> b.build();</br></p>
   *
   * <p>If options should be set the methods can be called:</br> b.granularity(Granularity.CHAR_BASED)</br>
   * More options are described in the parameter section.</p>
   *
   * <p>Of cause the parameter calls can be chained:</br> JaccardDistance d =
   * JaccardDistance.getBuilder()</br> .granularity(Granularity.SEPARATOR_BASED)</br>
   * .delimiter(",").build();</p>
   *
   * @param granularity   determines the granularity of the tokenizer (default: CHAR_BASED)
   * @param delimiter     delimiter to be used if granularity is SEPARATOR_BASED (default: ",")
   * @param ngram         number of characters that should form an ngram (default: 3)
   * @param tokenizer     tokenizer to be used (default: CharTokenizer)
   * @param takeTokenizer internal tokenizing of string: If true a Subclass of Tokenizer is used,
   *                      otherwise an internal faster implementation is used
   * @param takeKeyset    internal handling of vectors: If true a HashSet is used, otherwise a
   *                      HashMap. (For performance reasons in some similarities)
   * @author Florian Froese
   * @since 1.0
   */
  public abstract static class VectorBuilder<E> {

    protected Granularity granularity = DEFAULT_GRANULARITY;
    protected String delimiter = DEFAULT_DELIMITER;
    protected int ngram = DEFAULT_NGRAM;
    protected Tokenizer tokenizer = DEFAULT_TOKENIZER;
    protected boolean takeTokenizer = DEFAULT_TAKE_TOKENIZER;
    protected boolean takeKeyset = DEFAULT_TAKE_KEYSET;

    /**
     * Sets granularity of the builder. Possible values: {@link Granularity}
     *
     * @param g granularity
     * @return this builder object
     * @see Granularity
     */
    public E granularity(Granularity g) {
      granularity = g;
      return (E) this;
    }

    /**
     * Sets delimiter to be used if granularity is set to Granularity.SEPARATOR_BASED
     *
     * @param d Separator string
     * @return this builder object
     */
    public E delimiter(String d) {
      delimiter = d;
      return (E) this;
    }

    /**
     * Sets the number of characters that should form an ngram. Thus if nGram=2 the word "hello" is
     * split into ["he","el","ll","lo"]
     *
     * @param nGram Integer of ngram
     * @return this builder object
     */
    public E nGram(int nGram) {
      ngram = nGram;
      return (E) this;
    }

    /**
     * Optional tokenizer that can be used. Here a tokenizer can be specified that must inherit the
     * {@link Tokenizer} class.
     *
     * @param t tokenizer to be used
     * @return this builder object
     * @see Tokenizer
     */
    public E tokenizer(Tokenizer t) {
      tokenizer = t;
      return (E) this;
    }

    /**
     * If this option is set to true a subclass of {@link Tokenizer} is used to tokenize the string
     * Otherwise an internal sometimes faster method is used.
     *
     * @param b boolean value true/false
     * @return this builder object
     */
    public E takeTokenizer(boolean b) {
      takeTokenizer = b;
      return (E) this;
    }

    /**
     * If this option is set to true a HashSet is generated if similarity(s1,s2) is called. Thus
     * only the tokens and not their frequency are stored. Otherwise a HashMap is used and the
     * occurences of a token are counted in the value field.
     *
     * @param b boolean value true/false
     * @return this builder object
     */
    public E takeKeyset(boolean b) {
      takeKeyset = b;
      return (E) this;
    }
  }
}
