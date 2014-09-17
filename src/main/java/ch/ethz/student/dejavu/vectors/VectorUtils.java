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
import java.util.Iterator;
import java.util.Set;

/**
 * VectorUtils
 *
 * Implementation of some vector functionalities.
 *
 * @author Florian Froese
 * @since 1.0
 */
public class VectorUtils {

  public static double union(HashMap<Object, Integer> vector1, HashMap<Object, Integer> vector2) {
    HashSet<Object> union = new HashSet<Object>(vector1.keySet());
    union.addAll(vector2.keySet());
    return union.size();
  }

  public static double union(Set<Object> keys1, Set<Object> keys2) {
    Set<Object> union = new HashSet<Object>(keys1);
    union.addAll(keys2);
    return union.size();
  }

  public static double intersect(HashMap<Object, Integer> vector1,
                                 HashMap<Object, Integer> vector2) {
    HashSet<Object> intersect = new HashSet<Object>(vector1.keySet());
    intersect.retainAll(vector2.keySet());
    return intersect.size();
  }

  public static double intersect(Set<Object> keys1, Set<Object> keys2) {
    Set<Object> intersect = new HashSet<Object>(keys1);
    intersect.retainAll(keys2);
    return intersect.size();
  }

  public static double complement(HashMap<Object, Integer> vector1,
                                  HashMap<Object, Integer> vector2) {
    HashSet<Object> complement = new HashSet<Object>(vector1.keySet());
    complement.removeAll(vector2.keySet());
    return complement.size();
  }

  public static double complement(Set<Object> keys1, Set<Object> keys2) {
    Set<Object> complement = new HashSet<Object>(keys1);
    complement.removeAll(keys2);
    return complement.size();
  }

  public static double size(HashMap<Object, Integer> vector) {
    return vector.keySet().size();
  }

  public static double size(Set<Object> keys) {
    return keys.size();
  }

  public static double euclideanNorm(HashMap<Object, Integer> vector) {
    double res = 0;

    for (Iterator<Integer> iterator = vector.values().iterator(); iterator.hasNext(); ) {
      Integer v = (Integer) iterator.next();
      res += v * v;
    }

    return Math.sqrt(res);
  }

  public static double manhattenNorm(HashMap<Object, Integer> vector) {
    double res = 0;

    for (Iterator<Integer> iterator = vector.values().iterator(); iterator.hasNext(); ) {
      Integer v = (Integer) iterator.next();
      res += Math.abs(v);
    }

    return res;
  }

  public static double scalarProduct(HashMap<Object, Integer> vector1,
                                     HashMap<Object, Integer> vector2) {
    double res = 0;

    HashSet<Object> mergedSet = new HashSet<Object>(vector1.keySet());
    mergedSet.addAll(vector2.keySet());

    for (Object o : mergedSet) {
      int v1 = vector1.get(o) == null ? 0 : vector1.get(o);
      int v2 = vector2.get(o) == null ? 0 : vector2.get(o);
      res += v1 * v2;
    }

    return res;
  }
}
