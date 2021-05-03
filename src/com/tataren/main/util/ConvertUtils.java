package com.tataren.main.util;


public class ConvertUtils {

	// Suppresses default constructor, ensuring non-instantiability.
	private ConvertUtils() {
	}

	/**
	 * Convert value into int,if evor occured,returns defaultValue.
	 * 
	 * @param value
	 * @param defaultValue
	 * @return int
	 */
	public static int getInt(Object value, int defaultValue) {
		if (value == null)
			return defaultValue;
		if (value instanceof Integer)
			return ((Integer) value).intValue();

		// if long,byte....

		String s = value.toString();
		int result = defaultValue;
		try {
			result = Integer.parseInt(s);
		} catch (Exception e) {
			// silent
		}
		return result;
	}

	/**
	 * Mask provided String
	 * 
	 * @param value
	 * @param defaultString
	 * @return
	 */
	public static String maskNull(Object value, String defaultString) {
		return value == null ? defaultString : value.toString().trim();
	}

	/**
	 * Mask provided string.
	 * 
	 * @param value
	 * @return
	 */
	public static String maskNull(Object value) {
		return maskNull(value, "");
	}

	/**
	 * Mask redundant '.0' if there exits.
	 * 
	 * @param d
	 * @return string which represents the same value as to the provided double
	 */
	public static String trim0(double d) {
		if (true)
			return trim0((float) d);
		String s = String.valueOf(d);

		int index = s.indexOf('.');
		if (index == -1)
			return s;

		if (s.charAt(s.length() - 1) != '0')
			return s;
		return s.substring(0, index);
	}

	/**
	 * Mask redundant '.0' if there exits.
	 * 
	 * @param d
	 * @return string which represents the same value as to the provided double
	 */
	public static String trim0(float d) {
		String s = String.valueOf(d);

		int index = s.indexOf('.');
		if (index == -1)
			return s;

		if (s.charAt(s.length() - 1) != '0')
			return s;
		return s.substring(0, index);
	}

	/**
	 * @param string
	 * @return
	 */
	public static float getFolat(String value, float defaultValue) {

		if (value == null)
			return defaultValue;
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}