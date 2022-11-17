package com.kob.botrunningsystem.controller;

import com.kob.botrunningsystem.service.BotRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Map;
import java.util.Objects;

@RestController
public class BotRunningController {
    @Autowired
    BotRunningService botRunningService;

    @PostMapping("/bot/add/")
    public String addBot(@RequestParam MultiValueMap<String,String> data) {
        System.out.println("到打botrun");
        Integer userid = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String botcode = data.getFirst("bot_code");
        String input = data.getFirst("input");
        return botRunningService.addBot(userid,botcode,input);
    }


}
