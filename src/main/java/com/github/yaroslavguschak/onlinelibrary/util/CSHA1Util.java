package com.github.yaroslavguschak.onlinelibrary.util;

import java.security.MessageDigest;

public class CSHA1Util {
	
	public static byte[] getSHA1bytes(String s) throws java.security.GeneralSecurityException  {
		if (s == null) return null;
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(s.getBytes());
		return md.digest();
	}
	
	public static String getSHA1String(String s) throws java.security.GeneralSecurityException {
		if (s == null) return s;
		return bytesToHex(getSHA1bytes(s));
	}
	
	public static String bytesToHex(byte[] b) {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	   }

}
