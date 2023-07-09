package com.kob.backend.comtroller.user.bot;

import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddService addService;

    @PostMapping("/api/user/bot/add/")
    public Map<String,String> add(@RequestParam Map<String,String> data) {
        System.out.println(data);
        return addService.add(data);
    }

    @GetMapping("/api/user/bot/code/")
    public Map<String,String> getCode() {
        return addService.getCode();
    }
}
