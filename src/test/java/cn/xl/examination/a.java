package cn.xl.examination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class a {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void t(){
        String a = "1";
        System.out.println(passwordEncoder.encode(a));
    }
}
