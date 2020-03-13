package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
public class TestApplication {
    public static void main(String[] args) {
        new SpringApplication(TestApplication.class).run(args);
    }
}
