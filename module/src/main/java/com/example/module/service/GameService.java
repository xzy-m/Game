package com.example.module.service;

import com.example.module.entity.Game;
import com.example.module.mapper.GameMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:37
 */
@Service
public class GameService {

    @Resource
    private GameMapper gameMapper;

    public List<Game> gameAll() {
        return gameMapper.gameAll();
    }

    public Game gameInfo(BigInteger id) {
        return gameMapper.getById(id);
    }

    //service中的异常会抛到controller
    public BigInteger insertOrUpdate(BigInteger id, String pictures, String title, String downloadLink, BigInteger categoryId) {

        //如果pictures，title，downloadLink任意一个为null就抛异常
        if (pictures == null || title == null || downloadLink == null || categoryId == null) {
            throw new RuntimeException("传入参数为误");
        }

        //时间，以及增改两个都会要用的对象
        int time = (int) (System.currentTimeMillis() / 1000);
        Game game = new Game();
        BigInteger resultId = null;

        //两边都会用的提前，只要过了前面是否为null的判断
        game.setPictures(pictures);
        game.setTitle(title);
        game.setDownloadLink(downloadLink);
        game.setUpdateTime(time);
        game.setCategoryId(categoryId);

        //如果没传入id，这里是新增
        if (id == null) {
            game.setCreateTime(time);
            game.setIsDeleted(0);
            gameMapper.insert(game);
            resultId = game.getId();
        } else {
            //传入了id，这里是修改；但是查询不到，throw new RuntimeException
            if (gameMapper.getById(id) == null) {
                throw new RuntimeException("未查询到相关数据，请检查后再试");
            } else {
                game.setId(id);
                gameMapper.update(game);
                resultId = game.getId();
            }
        }

        //返回id
        System.out.println(resultId);
        return resultId;
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return gameMapper.delete(id, time);
    }

    public List<Game> pagination(int page, int pageSize, String title) {
        int start = (page - 1) * pageSize;
        String categoryIdList = convertCategoryIdListToStringByTitle(title);
        return gameMapper.pagination(title, categoryIdList, start, pageSize);
    }

    public int getTotal(String title) {
        String categoryIdList = convertCategoryIdListToStringByTitle(title);
        return gameMapper.total(title, categoryIdList);
    }

    public String convertCategoryIdListToStringByTitle(String title) {
        if (title == null) {
            return null;
        }
        //拼接用title去like到的类型id合集
        List<BigInteger> ids = gameMapper.getCategoryIdList(title);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i != ids.size() - 1) {
                sb.append(ids.get(i)).append(",");
            } else {
                sb.append(ids.get(i));
            }
        }

        String categoryIdList = sb.toString();
        return categoryIdList;
    }

}
