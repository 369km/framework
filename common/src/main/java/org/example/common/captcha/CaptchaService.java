package org.example.common.captcha;

import java.util.Optional;

public interface CaptchaService {
    Optional<CaptchaResponse> generateCaptcha();

    CaptchaStatus validateCaptcha(CaptchaRequest request);
}
