package org.example.common.captcha;

public class CaptchaResponse {
    private String captchaBase64;
    private String key;

    public String getCaptchaBase64() {
        return captchaBase64;
    }

    public void setCaptchaBase64(String captchaBase64) {
        this.captchaBase64 = captchaBase64;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CaptchaResponse(String captchaBase64, String key) {
        this.captchaBase64 = captchaBase64;
        this.key = key;
    }

    public CaptchaResponse() {
    }
}
