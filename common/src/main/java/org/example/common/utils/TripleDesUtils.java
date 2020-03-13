package org.example.common.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

/**
 * @AUTHOR yan
 * @DATE 2020/2/6
 */

@Component
public class TripleDesUtils {

    public static final String PASSWORD_HASH_ALGORITHM = "SHA";
    private static final String TRIPLE_DES_TRANSFORMATION = "DESede/ECB/PKCS7Padding";
    private static final String ALGORITHM = "DESede";
    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String UNICODE_FORMAT = "UTF8";
    @JsonProperty(
            value = "key",
            defaultValue = "123456788765432112345678"
    )
    private String key="123456788765432112345678";

    public TripleDesUtils() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    private byte[] encode(byte[] input, String key) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        this.init();
        Cipher encrypter = Cipher.getInstance("DESede/ECB/PKCS7Padding", "BC");
        encrypter.init(1, this.buildKey(key.toCharArray()));
        return encrypter.doFinal(input);
    }

    private byte[] decode(byte[] input, String key) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        this.init();
        Cipher decrypter = Cipher.getInstance("DESede/ECB/PKCS7Padding", "BC");
        decrypter.init(2, this.buildKey(key.toCharArray()));
        return decrypter.doFinal(input);
    }

    private byte[] getByte(String string) throws UnsupportedEncodingException {
        return string.getBytes("UTF8");
    }

    private String getString(byte[] byteText) {
        return new String(byteText);
    }

    private Key buildKey(char[] password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.init();
        MessageDigest digester = MessageDigest.getInstance("SHA");
        digester.update(String.valueOf(password).getBytes("UTF8"));
        byte[] keys = digester.digest();
        byte[] keyDes = Arrays.copyOf(keys, 24);
        return new SecretKeySpec(keyDes, "DESede");
    }

    public String encrypt(String plainText, String key) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        byte[] encryptedByte = this.encode(this.getByte(plainText), key);
        return Hex.encodeHexString(encryptedByte);
    }

    public String encrypt(String plainText) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        return this.encrypt(plainText, this.getKey());
    }

    public String decrypt(String cipherText, String key) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, org.apache.commons.codec.DecoderException {
        byte[] decryptedByte = this.decode(Hex.decodeHex(cipherText.toCharArray()), key);
        return this.getString(decryptedByte);
    }

    public String decrypt(String cipherText) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, DecoderException {
        return this.decrypt(cipherText, this.getKey());
    }

    public String generateSHA(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.init();
        MessageDigest digester = MessageDigest.getInstance("SHA");
        digester.update(String.valueOf(password.toCharArray()).getBytes("UTF8"));
        byte[] keys = digester.digest();
        return Hex.encodeHexString(keys);
    }

}

