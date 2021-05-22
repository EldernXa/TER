package mainTER.DBManage;

import mainTER.PropertiesReader;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class SecureManage {
    private static final String SECURE_KEY = PropertiesReader.getValue("secureKey");
    private static final Key aesKey = new SecretKeySpec(SECURE_KEY.getBytes(), "AES");

    private SecureManage(){

    }

    /**
     * Encrypt a string.
     * @param textToEncrypt text we want to encrypt.
     * @return a string who is encrypted.
     */
    protected static String getEncrypted(String textToEncrypt){
        try {
            Cipher cipher = Cipher.getInstance("AES");

            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        }catch(Exception ignored){

        }
        return "";
    }

    /**
     * Decrypt a string.
     * @param textToDecrypt text we want to decrypt.
     * @return a string who is decrypted.
     */
    protected static String getDecrypted(String textToDecrypt){
        try {
            Cipher cipher = Cipher.getInstance("AES");

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(textToDecrypt)));
        }catch(Exception ignored){

        }
        return "";
    }

}
