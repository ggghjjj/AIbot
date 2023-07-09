package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

import static com.kob.backend.comtroller.pk.StartGameController.usersAndAi;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer  direction) {
        System.out.println("receive bot move: " + userId + " " + direction + " ");
        System.out.println(userId);

        if (WebSocketServer.users.get(userId) != null) {
            System.out.println("获取game");
            Game game = WebSocketServer.users.get(userId).game;
            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    System.out.println("给A用户移动指令");
                    game.setNextStepA(direction);

                } else if (game.getPlayerB().getId().equals(userId)) {
                    System.out.println("给B用户移动指令");
                    game.setNextStepB(direction);
                }
            }
        }else if(userId == 1) {
            usersAndAi.forEach(uid->{
                Game game = WebSocketServer.users.get(uid).game;
                System.out.println(uid);
                if (game != null) {
                    if (game.getPlayerA().getId().equals(userId)) {
                        System.out.println("给A用户移动指令");
                        game.setNextStepA(direction);

                    } else if (game.getPlayerB().getId().equals(userId)) {
                        System.out.println("给B用户移动指令");
                        game.setNextStepB(direction);
                    }
                }
            });
        }
        return "receive bot move success";

    }

}
