package dd.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHA1 {
	public static String get_SHA_1_SecurePassword(String passwordToHash,
			String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String get_SHA_256_SecurePassword(String passwordToHash,
			String salt) {
				return salt;
		// Use MessageDigest md = MessageDigest.getInstance("SHA-256");
	}

	public static String get_SHA_384_SecurePassword(String passwordToHash,
			String salt) {
				return salt;
		// Use MessageDigest md = MessageDigest.getInstance("SHA-384");
	}

	public static String get_SHA_512_SecurePassword(String passwordToHash,
			String salt) {
				return salt;
		// Use MessageDigest md = MessageDigest.getInstance("SHA-512");
	}

	// Add salt
	public static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

}
