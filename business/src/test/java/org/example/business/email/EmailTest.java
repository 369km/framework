package org.example.business.email;

import org.example.business.BaseJunit;
import org.example.common.email.Email;
import org.example.common.email.EmailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailTest extends BaseJunit {
    @Autowired
    private EmailService emailService;
    @Test
    public void send(){
        emailService.send(new Email("yangfudong@189.cn","hello","你好"));
    }
}
