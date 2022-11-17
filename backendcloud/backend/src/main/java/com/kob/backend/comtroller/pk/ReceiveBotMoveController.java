package com.kob.backend.comtroller.pk;

import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ReceiveBotMoveController {

    @Autowired
    ReceiveBotMoveService receiveBotMoveService;

    @PostMapping("/pk/receive/bot/move/")
    public String reveiveBotmove(@RequestParam MultiValueMap<String,String> data) {
        System.out.println("我接受到了botmove");
        Integer userid = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        Integer direction  = Integer.parseInt(Objects.requireNonNull(data.getFirst("direction")));
        return receiveBotMoveService.receiveBotMove(userid, direction);
    }
}
