package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public Map<String,String> getBotInfo() {
        Map<String,String> ad = new HashMap<>();
        ad.put("apple","hha");
        ad.put("banana","ww");
        ad.put("ornage","dd");
        return ad;
    }
}
