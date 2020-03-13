package org.example.common.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {
    String getTokenName();

    Cookie createCookie(String encryptTokenString, String host);

    Cookie createCookie(String tokenName, String encryptTokenString, String host);

    Cookie createCookie(String tokenName, String encryptTokenString, String host, String domain, int maxAge);

    void validateToken(HttpServletRequest request);

    void validateToken(HttpServletRequest request, Class<Token> c);

    void validateToken(HttpServletRequest request, Class<Token> c, boolean headerFirst);

    String getTokenStringFromRequest(HttpServletRequest request, boolean headerFirst);

    void validateToken(String encryptedToken, Class<Token> c);

    void validateToken(Token token, Long timeOut);

    void validateToken(Token token);

    void storeToken(HttpServletResponse response, Token token, String host);

    void restoreToken(HttpServletResponse response, String host);

    void removeTokenFromRedis(HttpServletRequest request);
}
