package com;

import com.global.services.Mailer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Mailing {

    @Autowired
    private Mailer mailer;

    @Test
    public void contextLoads() {
        this.mailer.sendInternalMail("Test", "Correo de prueba de entorno, para verificar inicialización.");
    }

}
