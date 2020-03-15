package org.example.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @AUTHOR yan
 * @DATE 2020/2/6
 */

@Aspect
public class EncryptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionAspect.class);

    @Autowired
    private SecurityUtils securityUtils;

    @Around("@annotation(SaltAndHash) && execution(public void * (String))")
    public Object saltAndHashValue(ProceedingJoinPoint p) throws Throwable {
        Object[] args = p.getArgs();
        if (args != null && args.length == 1) {
            LOGGER.info("hashed string exception: {}", args[0]);
            return p.proceed(new Object[]{securityUtils.generateHashAndSalt((String) args[0])});
        } else {
            return p.proceed();
        }
    }
}
