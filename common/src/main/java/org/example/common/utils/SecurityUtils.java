package org.example.common.utils;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Component
public class SecurityUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);
    @Autowired
    private TripleDesUtils des3;

    public String encrypt(String clearText) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchProviderException, InvalidKeyException {
        return this.des3.encrypt(clearText);
    }

    public String decrypt(String encryptedText) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, DecoderException, NoSuchProviderException, InvalidKeyException {
        return this.des3.decrypt(encryptedText);
    }

    public String generateHashAndSalt(String clearText) {
        return PasswordUtil.hash(clearText);
    }

    public boolean compareWithHashAndSalt(String clearText, String hashText) {
        return PasswordUtil.verify(clearText, hashText);
    }

    public String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            LOGGER.warn("URLEncode error: {}", var3.getMessage());
            throw new RuntimeException(var3);
        }
    }

    public String sha1(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] result = messageDigest.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; ++i) {
            sb.append(Integer.toString((result[i] & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public String md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] result = messageDigest.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; ++i) {
            sb.append(Integer.toString((result[i] & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }
}
