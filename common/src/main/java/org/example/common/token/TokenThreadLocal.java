package org.example.common.token;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TokenThreadLocal {
    private static TransmittableThreadLocal<Token> tokenThreadLocal = new TransmittableThreadLocal<>();

    private TokenThreadLocal() {
    }

    public static Token getToken() {
        return tokenThreadLocal.get();
    }

    public static void setToken(Token token) {
        tokenThreadLocal.set(token);
    }

    public static void remove() {
        tokenThreadLocal.remove();
    }
}
