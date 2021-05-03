package com.tataren.main.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordEncryptor {


	public static String encryptPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(String.valueOf(password).getBytes());
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// should never happen!
			return password;
		}
	}

	/**
	 * Convert a byte array to a String of hexadecimal digits and return it.
	 * 
	 * @param buffer
	 *            The byte array to be converted
	 */
	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(encryptPassword("jw"));
	}
}
