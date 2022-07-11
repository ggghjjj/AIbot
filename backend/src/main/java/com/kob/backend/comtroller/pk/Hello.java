package com.kob.backend.comtroller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pk/")
public class Hello {
    @RequestMapping("index/")
    public String Index() {
        return "pk/hello.html";
    }
}
