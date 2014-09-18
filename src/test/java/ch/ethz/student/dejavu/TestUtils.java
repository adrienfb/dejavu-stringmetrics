package ch.ethz.student.dejavu;

import java.util.Random;

public class TestUtils {

  private static final String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final Random rnd = new Random();

  public static final int N = 1000;
  public static final int MIN_LEN = 1;
  public static final int MAX_LEN = 10;
  
  public static final double DELTA = 0.0001;
  public static final double DISTANCE_EMPTY_EMPTY = 0.0;
  public static final double DISTANCE_SELF = 0.0;
  public static final double SIMILARITY_EMPTY_EMPTY = 1.0;
  public static final double SIMILARITY_EMPTY_ANY = 0.0;
  public static final double SIMILARITY_SELF = 1.0;
  
  public static String getRandomString() {
    return getRandomString(MIN_LEN, MAX_LEN);
  }
  
  public static String getRandomString(int length) {
    return getRandomString(length, length);
  }
  
  public static String getRandomString(int minLength, int maxLength) {
    if (minLength < 1 || maxLength < 1) throw new IllegalArgumentException("minLength and maxLength must both me positive");
    if (minLength > maxLength) throw new IllegalArgumentException("minLength cannot be smaller than maxLength");
    
    int length = (minLength == maxLength) ? minLength : rnd.nextInt(maxLength-minLength+1) + minLength;
    
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }

    return sb.toString();
  }
}
