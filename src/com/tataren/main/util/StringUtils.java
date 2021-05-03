package com.tataren.main.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class StringUtils {

	private static final int TRIM_LENGTH = 6;

	private static final String TAIL_STRING = "...";


	public static String biteOff(String str) {
		return biteOff(str, TRIM_LENGTH);
	}

	/**
	 * Returns string which prefix is specified parameter and suffix is
	 * TAIL_STRING.
	 * 
	 * @param str
	 * @param toSize
	 * @return string
	 */
	public static String biteOff(String str, int toSize) {
		return biteOff(str, toSize, TAIL_STRING);
	}

	/**
	 * Returns string which prefix is specified parameter and suffix is
	 * TAIL_STRING.
	 * 
	 * @param str
	 * @param tail
	 * @return string
	 */
	public static String biteOff(String str, String tail) {
		return biteOff(str, TRIM_LENGTH, tail);
	}

	/**
	 * Returns string which prefix is specified parameter and suffix is
	 * TAIL_STRING.
	 * 
	 * @param str
	 * @param toSize
	 * @param tail
	 * @return string
	 */
	public static String biteOff(String str, int toSize, String tail) {
		if (str == null || str.length() <= toSize)
			return str;
		if (tail.length() >= toSize)
			return str;
		return str.substring(0, toSize - tail.length()) + tail;
	}

	/**
	 * Splits the provided text into an array of strings by the provided
	 * separator
	 * 
	 * @param str
	 * @param separatorChars
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars) {
		return org.apache.commons.lang.StringUtils.split(str, separatorChars);
	}

	/**
	 * Returns string array which all elements is trimed.
	 * 
	 * @param array
	 * @return string array
	 */
	public static String[] trim(String[] array) {
		for (int i = 0; array != null && i < array.length; i++) {
			array[i] = array[i] == null ? null : array[i].trim();
		}
		return array;
	}

	/**
	 * Returns string array which all elements is trimed.
	 * 
	 * @param array
	 * @return string array
	 */
	public static String[] retainValid(String[] array) {
		ArrayList list = new ArrayList(array.length);
		for (int i = 0; array != null && i < array.length; i++) {
			if (isValid(array[i]))
				list.add(array[i]);
		}
		return (String[]) list.toArray(new String[] {});
	}

	/**
	 * Return true if only ths provided string is not <code>null</code> if
	 * provided judgeNonblank is false. Return true if only ths provided string
	 * is neither <code>null</code> nor string.trim().length!=0 if provided
	 * judgeNonblank is true.
	 * 
	 * @param str
	 * @return boolean.
	 */
	public static boolean isValid(String str, boolean judgeNonblank) {
		if (str == null)
			return false;

		return judgeNonblank ? str.trim().length() != 0 : true;
	}

	/**
	 * Return true if only ths provided string is neither <code>null</code> nor
	 * string.trim().length!=0.
	 * 
	 * @param value
	 * @param defaultString
	 * @return boolean
	 */
	public static boolean isValid(String str) {
		return isValid(str, true);
	}

	public static String captiallize(String string) {
		return toUpperCase(string, 0, 1);
	}

	public static String decaptiallize(String string) {
		return toLowerCase(string, 0, 1);
	}

	public static String toUpperCase(String string, int from, int to) {
		return changeCase(string, from, to, true);
	}

	public static String toLowerCase(String string, int from, int to) {
		return changeCase(string, from, to, true);
	}

	public static String changeCase(String string, int from, int to, boolean isToUpperCase) {
		if (from >= to || string.length() < to || from < 0) {
			return string;
		}

		StringBuffer sb = new StringBuffer();
		if (from > 0)
			sb.append(string.subSequence(0, from - 1));
		if (isToUpperCase)
			sb.append(string.substring(from, to).toUpperCase());
		else
			sb.append(string.substring(from, to).toLowerCase());

		sb.append(string.substring(to));
		return sb.toString();
	}

	/**
	 * Return NEW string array which contains unique string in original order
	 * 
	 * 
	 * @param array
	 * @return
	 */
	public static String[] uniqueArray(String[] array) {
		Set set = new LinkedHashSet();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return (String[]) set.toArray(new String[0]);
	}

	/**
	 * cccccccccS and and length()=toLength
	 * 
	 * @param s
	 * @param c
	 * @param toLength
	 * @return
	 */
	public static String appendPrefix(String s, char c, int toLength) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < toLength - s.length(); i++) {
			b.append(c);
		}
		b.append(s);
		return b.toString();
	}

	/**
	 * Sccccccccc and and length()=toLength
	 * 
	 * @param s
	 * @param c
	 * @param toLength
	 * @return
	 */
	public static String appendSufix(String s, char c, int toLength) {
		StringBuffer b = new StringBuffer(s);
		for (int i = 0; i < toLength - s.length(); i++) {
			b.append(c);
		}
		return b.toString();
	}

	public static String null2Spase(String s) {
		if (s == null)
			s = "";
		return s;
	}

	public static String quoteReplacement(String s) {
		if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
			return s;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\') {
				sb.append('\\');
				sb.append('\\');
			} else if (c == '$') {
				sb.append('\\');
				sb.append('$');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
