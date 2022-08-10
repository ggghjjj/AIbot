package com.kob.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println(passwordEncoder.encode("456789"));
        System.out.println(passwordEncoder.encode("566"));
        System.out.println(passwordEncoder.encode("894"));
    }
    @Test
    void tt() {
            System.out.println(Pattern.compile("a?").matcher("ab").matches());    //true
            System.out.println(Pattern.compile(".").matcher("ab").matches());   //false

            System.out.println(Pattern.compile("A.B").matcher("AIB").matches());    //true
            System.out.println(Pattern.compile("A.B").matcher("ABI").matches());    //false

            System.out.println(Pattern.compile("a?").matcher("AIBsfasf").matches());     //true
        }
    }
