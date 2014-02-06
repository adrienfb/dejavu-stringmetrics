package ch.ethz.student.dejavu;

import java.util.Random;

public class TestUtils {
	private static final String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Random rnd = new Random();
	
	public static final int N = 1000;
	public static final int MIN_LEN = 0;
	public static final int MAX_LEN = 10;
	
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder( length );
		
		for( int i = 0; i < length; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		
		return sb.toString();
	}
}
