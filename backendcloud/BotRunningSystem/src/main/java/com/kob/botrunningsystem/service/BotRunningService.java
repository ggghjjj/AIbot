package com.kob.botrunningsystem.service;

import org.springframework.stereotype.Service;


public interface BotRunningService  {
    public String addBot(Integer userId, String botCode, String input);
}
