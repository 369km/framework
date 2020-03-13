package org.example.common.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;

import java.util.Base64;

/**
 * @AUTHOR yan
 * @DATE 2020/2/6
 */


public class PasswordUtil {
    private static final DigestRandomGenerator GENERATOR = new DigestRandomGenerator(new SHA512Digest());

    private PasswordUtil() {
    }

    public static String hash(String plainPassword) {
        return hash(plainPassword, salt(32), 256, 50751);
    }

    public static String hash(String plainPassword, byte[] salt) {
        return hash(plainPassword, salt, 256, 50751);
    }

    public static String hash(String plainPassword, byte[] salt, int keyLength, int iterations) {
        Preconditions
                .checkArgument(!Strings.isNullOrEmpty(plainPassword), "password can not be empty or null");
        Preconditions.checkArgument(keyLength > 0, "the key length must be greater than 0");
        Preconditions.checkArgument(iterations >= 0, "the number of iterations must be positive");
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(PBEParametersGenerator.PKCS5PasswordToBytes(plainPassword.toCharArray()), salt, iterations);
        return String.format("%s|%s", encode(salt), encode(((KeyParameter) generator.generateDerivedParameters(keyLength)).getKey()));
    }

    public static boolean verify(String plainPassword, String hash) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(plainPassword));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(hash));
        return hash(plainPassword, decode(extractSalt(hash))).equals(hash);
    }

    private static byte[] salt(int count) {
        GENERATOR.addSeedMaterial((long) (Math.random() * 1.0E9D));
        byte[] salt = new byte[count];
        GENERATOR.nextBytes(salt);
        return salt;
    }

    private static String encode(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    private static byte[] decode(String input) {
        return Base64.getDecoder().decode(input.getBytes(Charsets.UTF_8));
    }

    private static String extractSalt(String input) {
        return input.substring(0, input.indexOf("|"));
    }

}

