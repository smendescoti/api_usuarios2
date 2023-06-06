package br.com.cotiinformatica.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class MD5Service {

	public String encrypt(String password) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);
			StringBuilder hashText = new StringBuilder(no.toString(16));

			while (hashText.length() < 32) {
				hashText.insert(0, "0");
			}

			return hashText.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
