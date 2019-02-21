package com.marcelo.inter.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACryptography {
	public static String encrypt(String text, String pKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] keyBytes = Base64.getDecoder().decode(pKey.getBytes("utf-8"));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key key = keyFactory.generatePublic(spec);
		
	    Cipher encryptCipher = Cipher.getInstance("RSA");
	    encryptCipher.init(Cipher.ENCRYPT_MODE, key);

	    byte[] cipherText = encryptCipher.doFinal(text.getBytes());

	    return Base64.getEncoder().encodeToString(cipherText);
		
	}
}