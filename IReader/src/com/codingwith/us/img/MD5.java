/**
 * 
 */
package com.codingwith.us.img;

import java.security.MessageDigest;

/**
 * @author chenwei10
 * 
 */
public class MD5 {
	static public String md5_string(String s) {
		byte[] unencodedPassword = s.getBytes();

		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return s;
		}

		md.reset();
		md.update(unencodedPassword);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}
}
