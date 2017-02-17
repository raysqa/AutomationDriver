package raysullivan.unitTest;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import raysullivan.operation.AESencrp;
import raysullivan.operation.AutomationDriverException;
import raysullivan.operation.BCrypt;

public class TestGenerateEncryptedString {

	@Test(description = "encryptString", dataProvider = "encryptionStrings", enabled = true)
	public void encryptAESString(String toEncrypt, String keyString)
			throws Exception {
		String newString = AESencrp.encrypt(toEncrypt, keyString);
		//System.out.println("Encrypt " + toEncrypt + ":\t" + newString);
		String decryptString = AESencrp.decrypt(newString, keyString);
		assertThat(toEncrypt).isEqualTo(decryptString);
	}
	@Test(description = "BCryptencryptString", dataProvider = "BCryptencryptionStrings", enabled = true)
	public void encryptBCryptString(String toEncrypt, String expectedHash)
			throws Exception {
		//String newString = BCrypt.hashpw(toEncrypt, BCrypt.gensalt(12));
		//System.out.println("Hash " + toEncrypt + ":\t" + newString);
		Boolean match = (BCrypt.checkpw(toEncrypt, expectedHash));
		assertThat(match).isTrue();
	}
	@Test(description = "BCryptinvalidencryptString", dataProvider = "BCryptinvalidencryptionStrings", enabled = true)
	public void invalidencryptBCryptString(String toEncrypt, String expectedHash)
			throws Exception {
		Boolean match = (BCrypt.checkpw(toEncrypt, expectedHash));
		assertThat(match).isFalse();
	}
	@Test(description = "invalidEncryptString", dataProvider = "invalidEncryptionStrings", expectedExceptions = AutomationDriverException.class, enabled = true)
	public void invalidEncryptAESString(String toEncrypt, String keyString)
			throws Exception {
		AESencrp.encrypt(toEncrypt, keyString);
	}

	@Test(description = "decryptString", dataProvider = "decryptionStrings", enabled = true)
	public void decryptAESString(String toDecrypt, String keyString, String unEncrypted)
			throws Exception {
		String newString = AESencrp.decrypt(toDecrypt, keyString);
		assertThat(newString).isEqualTo(unEncrypted);
	}
	

	@Test(description = "decryptBadString", dataProvider = "decryptionStringsBad", enabled = true)
	public void decryptBadAESString(String toDecrypt, String keyString, String unEncrypted)
			throws Exception {
		String newString = AESencrp.decrypt(toDecrypt, keyString);
		assertThat(newString).isNotEqualTo(unEncrypted);
	}

	@Test(description = "invalidDecryptString", dataProvider = "invalidDecryptionStrings", expectedExceptions = AutomationDriverException.class, enabled = true)
	public void invalidDecryptAESString(String toDecrypt, String keyString, String unEncrypted)
			throws Exception {
		AESencrp.decrypt(toDecrypt, keyString);
	}
	
	@DataProvider
	public final Object[][] encryptionStrings() {
		return new String[][]{
				new String[]{"Welcome1","automationDriver"},
				new String[]{"Trummino65","automationDriver"},
				new String[]{"Welcome1","MerryChristmas!#"},
				new String[]{"Welcome2","MerryChristmas!#"},
				new String[]{"DoTheTwist","automationDriver"},
				new String[]{"Welcome2","BaseballNational"},
				new String[]{"Incredibly Long Text String with blanks and other stuff like special characters !@#$%^&*()+_|{}[]:<>,./?","automationDriver"},
				new String[]{"","BaseballNational"},
				};
	}
	
	@DataProvider
	public final Object[][] BCryptencryptionStrings() {
		return new String[][]{
				new String[]{"Welcome1","$2a$12$BrzLDQGhn5/eeCheblW7KubzNuX/Q9hCGUBrP/7fOgvT06UwfD9TS"},
				new String[]{"Trummino65","$2a$12$L/ElZkmKO0GLIbxCMbGpWeu00JefsqRBCoNdykcztZYQMFed8V7RW"},
				new String[]{"Welcome1","$2a$12$sqgPqxol2WhBgStEfWnNqebUfhi2PLjRZgEruv999cWUgP4UsZlie"},
				new String[]{"Welcome2","$2a$12$yXv9NjU4OMVR7.iFi27k7es7.zfF/jALu/rERWPhTUQB/8EAl1Hgy"},
				new String[]{"DoTheTwist","$2a$12$otY2NzWzxQ6OWtl8aen9V./W.AB.wTeIRlg2ehTUNsE8obk2JKI9G"},
				new String[]{"Welcome2","$2a$12$jeD1Dwq5UEpJaI9vu9opvO24QNNaYf6NxmcSx0I9cptyOt9Yn7Y3K"},
				new String[]{"Incredibly Long Text String with blanks and other stuff like special characters !@#$%^&*()+_|{}[]:<>,./?","$2a$12$TsqzgalzQtR839Hy1sn9jOqa4HtzXX/vu6ykWbT3MQGj0d0u3EMFC"},
				new String[]{"","$2a$12$kPm5z5YqnZV3CjVsNnEl9ODopDWgoPPOXFCTeViWoClUKBm4Kg71K"},
				};
	}
	
	@DataProvider
	public final Object[][] invalidEncryptionStrings() {
		return new String[][]{
				new String[]{null,"BaseballNational"},
				new String[]{"Welcome2",null},
				new String[]{"Welcome2",""},
				new String[]{"Welcome2","CricketNational"},
				new String[]{"Welcome2","BaseballNationalLeague"},
				};
	}
	
	
	@DataProvider
	public final Object[][] BCryptinvalidencryptionStrings() {
		return new String[][]{
				new String[]{"Welcome1","$2a$12$BrzLDQGhn5/eeCheblW7KubzNuX/Q9hCGUBrP/7fOgvT06UwfD9TP"},
				new String[]{"Trummino65","$2a$12$L/ElZkmKO0GLIbxCMbGpWeu00JefsqRBCoNdykcztZYQMFed8V7RS"},
				new String[]{"Welcome1","$2a$12$sqgPqxol2WhBgStEfWnNqebUfhi2PLjRZgEruv999cWUgP4UsZlif"},
				new String[]{"Welcome2","$2a$12$yXv9NjU4OMVR7.iFi27k7es7.zfF/jALu/rERWPhTUQB/8EAl1Hga"},
				new String[]{"DoTheTwist","$2a$12$otY2NzWzxQ6OWtl8aen9V./W.AB.wTeIRlg2ehTUNsE8obk2JKI9i"},
				new String[]{"Welcome2","$2a$12$jeD1Dwq5UEpJaI9vu9opvO24QNNaYf6NxmcSx0I9cptyOt9Yn7Y34"},
				new String[]{"Incredibly Long Text String with blanks and other stuff like special characters !@#$%^&*()+_|{}[]:<>,./?","$2a$12$TsqzgalzQtR839Hy1sn9jOqa4HtzXX/vu6ykWbT3MQGj0d0u3EM2C"},
				new String[]{"","$2a$12$kPm5z5YqnZV3CjVsNnEl9ODopDWgoPPOXFCTeViWoClUgBm4Kg71K"},
				};
	}
	
	@DataProvider
	public final Object[][] decryptionStrings() {
		return new String[][]{
				new String[]{"ieKkIwygTyBf63mi62KD7w==","automationDriver", "Welcome1"},
				new String[]{"UTrj43lAOWmZjXaqGaKnBg==","MerryChristmas!#", "Welcome1"},
				new String[]{"tNs3FeJl0BYnShvQl0fqvA==","MerryChristmas!#", "Welcome2"},
				new String[]{"YtPA1/s+SdGJSKNeq5EjSA==","automationDriver", "DoTheTwist"},
				new String[]{"4QUUgpWUehdpYUKU2U8xZA==","BaseballNational", "Welcome2"},
				new String[]{"3t0JHljez4OIHnou97KwVw==","BaseballNational", ""},
				new String[]{"UbvSbIXxhSqpFp895+teXA==","automationDriver", "Trummino65"},
				new String[]{"A0TEWyN1ibkJ/1uMJ9BkqKrJfTPpnaEsgnz7sygQyl2IOHdXkeBk9Cz47ciwkekp5x8WYr+7cvDiphPAB7Sm3s5Q7fgt/5Sj3Xsp+PbE/MT43uiL2aWE6msoHnJk4JwWnSt5wYrtwtOEw07vBSDMlg==","automationDriver", "Incredibly Long Text String with blanks and other stuff like special characters !@#$%^&*()+_|{}[]:<>,./?"},
				new String[]{"", "automationDriver", ""},
				};
	}

	
	@DataProvider
	public final Object[][] decryptionStringsBad() {
		return new String[][]{
				new String[]{"ieKkIwygTyBf63mi62KD7w==","automationDriver", "Welcome2"},
				new String[]{"UTrj43lAOWmZjXaqGaKnBg==","MerryChristmas!#", "Welcome2"},
				new String[]{"tNs3FeJl0BYnShvQl0fqvA==","MerryChristmas!#", "Welcome1"},
				new String[]{"YtPA1/s+SdGJSKNeq5EjSA==","automationDriver", "DontDoTheTwist"},
				new String[]{"4QUUgpWUehdpYUKU2U8xZA==","BaseballNational", "Welcome1"},
				new String[]{"3t0JHljez4OIHnou97KwVw==","BaseballNational", "Merry Christmas"},
				new String[]{"A0TEWyN1ibkJ/1uMJ9BkqKrJfTPpnaEsgnz7sygQyl2IOHdXkeBk9Cz47ciwkekp5x8WYr+7cvDiphPAB7Sm3s5Q7fgt/5Sj3Xsp+PbE/MT43uiL2aWE6msoHnJk4JwWnSt5wYrtwtOEw07vBSDMlg==","automationDriver", "Incrediblyy Long Text String with blanks and other stuff like special characters !@#$%^&*()+_|{}[]:<>,./?"},
				new String[]{"", "automationDriver", "Welcome1"},
				};
	}
	@DataProvider
	public final Object[][] invalidDecryptionStrings() {
		return new String[][]{
				new String[]{null,"automationDriver", "Welcome1"},
				new String[]{"ieKkIwygTyBf63mi62KD7w==","", "Welcome1"},
				new String[]{"ieKkIwygTyBf63mi62KD7w==",null, "Welcome1"},
				new String[]{"ieKkIwygTyBf63mi62KD7w==","MerryChristmas!", "Welcome1"},
				new String[]{"ieKkIwygTyBf63mi62KD7w==","MerryChristmas!#Today", "Welcome1"},
				new String[]{"ieKkIwygTyBf63mi62KD7w==","MerryChristmas!#", "Welcome1"},
				};
	}
	
}
