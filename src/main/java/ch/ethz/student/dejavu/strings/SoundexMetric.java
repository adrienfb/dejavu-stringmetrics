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


/**
 * Soundex distance implementation
 *
 * The implementation follows the definition of the National Archives (US Census Bureau)
 *
 * <p>For creation please use default distance or the Builder {@link Builder} provided by the static
 * methods.</p>
 *
 * <p>You can either generate the code from an input string, using method code() or compare the
 * codes computed form two input strings by using method similarity(), the default distance method
 * used for comparing the codes is the Hamming distance (other distances can be specified when using
 * object instantiation through the provided builder) </p> TODO: update no more similarity -> get
 * codes and use other metrics
 *
 * <p>Definition: <a href="http://www.archives.gov/research/census/soundex.html">http://www.archives.gov/research/census/soundex.html</a></p>
 *
 * @author Adrien Favre-Bully
 * @author Florian Froese
 * @author Adrian Schmidmeister
 * @since 1.0
 */
public class SoundexMetric {

  private static final int CODE_LENGTH = 4;

  // ===== Metric Methods =====

  public String computeCode(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    if (s.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be empty.");
    }

    return String.valueOf(calculateSoundex(s.toUpperCase()));
  }

  private static char[] calculateSoundex(String s) {
    char[] code = new char[CODE_LENGTH];

    boolean lastWasVowel = false;

    // first character
    switch (s.charAt(0)) {
      case 'B':
      case 'F':
      case 'P':
      case 'V':
        code[0] = '1';
        break;
      case 'C':
      case 'G':
      case 'J':
      case 'K':
      case 'Q':
      case 'S':
      case 'X':
      case 'Z':
        code[0] = '2';
        break;
      case 'D':
      case 'T':
        code[0] = '3';
        break;
      case 'L':
        code[0] = '4';
        break;
      case 'M':
      case 'N':
        code[0] = '5';
        break;
      case 'R':
        code[0] = '6';
        break;
      default:
    }

    int j = 1;
    for (int i = 1; i < s.length(); i++) {
      if (j == 4) {
        break;
      }

      char c = s.charAt(i);
      switch (c) {
        case 'B':
        case 'F':
        case 'P':
        case 'V':
          if (code[j - 1] != '1' || code[j - 1] == '1' && lastWasVowel) {
            code[j] = '1';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'C':
        case 'G':
        case 'J':
        case 'K':
        case 'Q':
        case 'S':
        case 'X':
        case 'Z':
          if (code[j - 1] != '2' || code[j - 1] == '2' && lastWasVowel) {
            code[j] = '2';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'D':
        case 'T':
          if (code[j - 1] != '3' || code[j - 1] == '3' && lastWasVowel) {
            code[j] = '3';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'L':
          if (code[j - 1] != '4' || code[j - 1] == '4' && lastWasVowel) {
            code[j] = '4';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'M':
        case 'N':
          if (code[j - 1] != '5' || code[j - 1] == '5' && lastWasVowel) {
            code[j] = '5';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'R':
          if (code[j - 1] != '6' || code[j - 1] == '6' && lastWasVowel) {
            code[j] = '6';
            j++;
            lastWasVowel = false;
          }
          break;
        case 'A':
        case 'E':
        case 'I':
        case 'O':
        case 'U':
          lastWasVowel = true;
          break;
        default:
          lastWasVowel = false;
      }
    }

    // fill up with 0s (if necessary)
    while (j < 4) {
      code[j] = '0';
      j++;
    }

    // put letter in first place
    code[0] = s.charAt(0);

    return code;
  }

  // ===== Builder Pattern Methods =====

  private SoundexMetric(Builder b) {
  }

  /**
   * @return        {@link Builder}
   * @see Builder
   * @see SoundexMetric
   */
  public static Builder getBuilder() {
    return new Builder();
  }

  /**
   * @return standard Soundex distance object that can be used to determine the distance between two
   * strings
   * @see JaroMetric
   */
  public static SoundexMetric getInstance() {
    return new Builder().build();
  }

  // ===== Builder Class =====

  /**
   * Soundex Builder
   *
   * @author Adrien Favre-Bully
   * @author Florian Froese
   * @author Adrian Schmidmeister
   *
   *         <p>No parameters, just call build()!</p>
   * @since 1.0
   */
  public static class Builder {

    public SoundexMetric build() {
      return new SoundexMetric(this);
    }
  }
}
