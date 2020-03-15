package org.example.common.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.example.common.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Autowired
    private CacheService cacheService;
    @Autowired
    private CaptchaSetting captchaSetting;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Override
    public Optional<CaptchaResponse> generateCaptcha() {
        RandomImage captcha = getRandom();
        if (Objects.nonNull(captcha)) {
            String key = UUID.randomUUID().toString();
            cacheService.set(key, captcha.getText().toLowerCase(), captchaSetting.getExpire());
            return Optional.of(new CaptchaResponse(captcha.getImage(), key));
        }
        return Optional.empty();
    }

    @Override
    public CaptchaStatus validateCaptcha(CaptchaRequest request) {
        if (!cacheService.existKey(request.getKey())) {
            return CaptchaStatus.EXPIRED;
        }
        String captcha = (String) cacheService.get(request.getKey());
        cacheService.remove(request.getKey());
        if (request.getCaptcha().toLowerCase().equals(captcha)) {
            return CaptchaStatus.SUCCESS;
        }
        return CaptchaStatus.FAIL;
    }

    private RandomImage getRandom() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            String captcha = defaultKaptcha.createText();
            ImageIO.write(defaultKaptcha.createImage(captcha), "png", os);
            return new RandomImage(captcha, new BASE64Encoder().encode(os.toByteArray()));
        } catch (IOException e) {
            LOGGER.error("get RandomImage fail", e);
        }
        return null;
    }
}
