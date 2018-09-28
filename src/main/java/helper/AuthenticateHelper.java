package helper;
import config.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticateHelper {

    public String hash(String password) {
        String generatedPassword = null;
        String salt = Config.APP_SECRET;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            String concatPassword = password + salt;
            md.update(concatPassword.getBytes());
            byte[] hash = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< hash.length ;i++)
            {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean verifyPassword(String password, String hash) {
        String passwordHash = hash(password);
        return passwordHash.equalsIgnoreCase(hash);
    }
}
