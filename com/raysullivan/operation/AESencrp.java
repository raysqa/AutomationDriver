package raysullivan.operation;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

import java.util.Base64;

//import org.openqa.selenium.internal.Base64Encoder;
/**
 * AESencryp Encrypt/Decrypt a test value
 * 
 * @author rsullivan
 *
 */
public class AESencrp {
	private static final String ALGO = "AES";

	/**
	 * generateKey
	 * 
	 * @param keyValue
	 * @return key
	 * @throws Exception
	 */
	private static Key generateKey(final byte[] keyValue) throws Exception {
		try {
			final Key key = new SecretKeySpec(keyValue, ALGO);
			return key;
		} catch (IllegalArgumentException iae) {
			throw new AutomationDriverException("Error:  Encrypt/decrypt keystring cannot be null or blank");
		}
	}
	/**
	 * encrypt
	 * 
	 * @param data
	 * @param keyString
	 * @return encryptedValue
	 * @throws Exception
	 */
	public static String encrypt(final String data, final String keyString)
			throws Exception {
		byte[] keyValue;
		final Cipher cipher = Cipher.getInstance(ALGO);
		try {
			keyValue = keyString.getBytes();
			final Key key = generateKey(keyValue);
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NullPointerException npe) {
			throw new AutomationDriverException(
					"Error:  Encrypt keystring cannot be null or blank");

		} catch (InvalidKeyException ike) {
			throw new AutomationDriverException(
					"Error:  Encrypt keystring must be 16 characters; keystring length of " + keyString.length() + " is invalid.");
		}
		byte[] encVal;
		try {
			encVal = cipher.doFinal(data.getBytes());
//			final String encryptedValue = new Base64Encoder().encode(encVal);
			final String encryptedValue = Base64.getEncoder().encodeToString(encVal);
			return encryptedValue;
		} catch (NullPointerException npe) {
			throw new AutomationDriverException(
					"Error:  String to encrypt cannot be null");
		}

	}
	/**
	 * decrypt
	 * 
	 * @param encryptedData
	 * @param keyString
	 * @return decryptedValue
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedData, final String keyString)
			throws Exception {
		final Cipher cipher = Cipher.getInstance(ALGO);
		try {
			byte[] keyValue = keyString.getBytes();
			Key key = generateKey(keyValue);
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NullPointerException npe) {
			throw new AutomationDriverException(
					"Error:  Decrypt keystring cannot be null or blank");

		} catch (InvalidKeyException ike) {
			throw new AutomationDriverException(
					"Error:  Decrypt keystring must be 16 characters; keystring length of " + keyString.length() + " is invalid.");
		}
		byte[] decodedValue;
		try {
//			decodedValue = new Base64Encoder().decode(encryptedData);
			decodedValue = Base64.getDecoder().decode(encryptedData);
			final byte[] decValue = cipher.doFinal(decodedValue);
			final String decryptedValue = new String(decValue);
			return decryptedValue;
		} catch (NullPointerException npe) {
			throw new AutomationDriverException(
					"Error:  String to encrypt/decrypt cannot be null");
		
	} catch (BadPaddingException bpe) {
		throw new AutomationDriverException(
				"Error:  Unable to Decrypt; keystring " + keyString + " invalid for encrypted text provided");
	}
	}
}
