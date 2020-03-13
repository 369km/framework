package org.example.common.rest.user;

public class UserLogin {
    private String loginAccount;
    private String loginPassword;
    private String captchaNum;
    private String captchaKey;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getCaptchaNum() {
        return captchaNum;
    }

    public void setCaptchaNum(String captchaNum) {
        this.captchaNum = captchaNum;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }
}
