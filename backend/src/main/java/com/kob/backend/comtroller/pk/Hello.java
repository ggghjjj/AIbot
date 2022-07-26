package com.kob.backend.comtroller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class Hello {
    @RequestMapping("/")
    public String Index() {
        return "pk/hello.html";
    }
}
