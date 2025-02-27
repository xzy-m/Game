package com.example.app.cron;

import com.example.module.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author XRS
 * @date 2024-10-09 下午 4:19
 */
@Component
public class GameCron {

    @Autowired
    private GameService gameService;

    //cron表达式由六或七个空格分隔的时间字段组成，分别表示秒、分、时、日、月、星期和年（可选）
    @Scheduled(cron = "0 0 0 * * ?")
    public void countGamesScheduled() {
        gameService.countGames();
    }
}
