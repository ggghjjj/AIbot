package com.kob.backend.comtroller.pk;

import com.kob.backend.service.pk.StartGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
public class StartGameController {
    @Autowired
    private StartGameService startGameService;

    public static Set<Integer> usersAndAi = new HashSet<>();
    @PostMapping("/pk/start/game/")
    public String StartGame(@RequestParam MultiValueMap<String,String> data) {
        System.out.println("hah");
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer aBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_bot_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        Integer bBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_bot_id")));
        return startGameService.startGame(aId,aBotId,bId,bBotId);
    }

    @PostMapping("/api/pk/start/gameai/")
    public String StartGameAI(@RequestParam MultiValueMap<String,String> data) {
        System.out.println("hah");
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("id")));
        Integer aBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("bot_id")));
        Integer bId = 1;
        Integer bBotId = 1;
        System.out.println(aId + " " + aBotId + " " + bId + " " + bBotId);
        usersAndAi.add(aId);
        return startGameService.startGame(aId,aBotId,bId,bBotId);
    }



}
