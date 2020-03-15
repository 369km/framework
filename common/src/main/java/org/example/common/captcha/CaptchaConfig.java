package org.example.common.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.example")
public class CaptchaConfig {
    private static final String SECRET_CHARSET = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    @Bean
    public DefaultKaptcha getDefaultKaptcha(CaptchaSetting captchaSetting) {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        properties.setProperty("kaptcha.image.width", captchaSetting.getImageWidth());
        properties.setProperty("kaptcha.image.height", captchaSetting.getImageHeight());
        properties.setProperty("kaptcha.textproducer.char.length", "6");
        properties.setProperty("kaptcha.noise.color", "red");
        properties.setProperty("kaptcha.textproducer.font.size", captchaSetting.getFontSize());
        properties.setProperty("kaptcha.textproducer.char.string", SECRET_CHARSET);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
