package com.example.app.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.module.entity.Game;
import com.example.module.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XRS
 * @date 2024-09-24 下午 12:00
 */
@Component
public class ExcelListener extends AnalysisEventListener<Game> {

    @Autowired
    private GameService gameService;

    List<Game> list = new ArrayList<>();

    @Override
    public void invoke(Game game, AnalysisContext analysisContext) {
        list.add(game);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (Game game : list) {
            //gameService.insertOrUpdate(null, game.getPictures(), game.getTitle(), game.getDownloadLink(), game.getCategoryId());
        }
        gameService.batchInsert(list);
        list.clear();
    }
}
