package com.example.module.service;

import com.example.module.annotation.DataSource;
import com.example.module.entity.Game;
import com.example.module.entity.Tag;
import com.example.module.enums.DBTypeEnum;
import com.example.module.mapper.GameMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:37
 */
@Service
public class GameService {

    @Resource
    private TagService tagService;

    @Resource
    private GameMapper gameMapper;

    public List<Game> gameAll() {
        return gameMapper.gameAll();
    }

    public Game gameInfo(BigInteger id) {
        return gameMapper.getById(id);
    }


    //将service中的方法与其中被调用的东西装入某种关联关系中
    @Transactional(propagation = Propagation.REQUIRED)
    public BigInteger insertOrUpdate(BigInteger id, String pictures, String title, String downloadLink, BigInteger categoryId, String detail, String tags) {

        //如果pictures，title，downloadLink任意一个为null就抛异常
        if (pictures == null || title == null || downloadLink == null || categoryId == null || detail == null || tags == null) {
            throw new RuntimeException("传入参数为误");
        }

        //时间，以及增改两个都会要用的对象
        int time = (int) (System.currentTimeMillis() / 1000);
        Game game = new Game();
        BigInteger gameId;

        //两边都会用的提前，只要过了前面是否为null的判断
        game.setPictures(pictures);
        game.setTitle(title);
        game.setDownloadLink(downloadLink);
        game.setUpdateTime(time);
        game.setCategoryId(categoryId);
        game.setDetail(detail);
        game.setTags(tags);

        //现在传入的标签
        List<String> tagsNow = Arrays.stream(tags.split(",")).toList();

        //没传入id就是新增
        if (id == null) {
            game.setCreateTime(time);
            game.setIsDeleted(0);
            //新增至游戏表
            gameMapper.insert(game);
            gameId = game.getId();

            //如果此标签存在数据库  增加一条关系表记录  不存在则增加标签和关系表记录
            for (String tagName : tagsNow) {
                Tag tag = tagService.selectByName(tagName);
                if (tag == null) {
                    BigInteger tagId = tagService.insertOrUpdate(null, tagName);
                    tagService.insertToRelationTable(gameId, tagId);
                } else {
                    tagService.insertToRelationTable(gameId, tag.getId());
                }
            }
        } else {
            //传入id即修改
            if (gameMapper.getById(id) == null) {
                throw new RuntimeException("未查询到相关数据，请检查后再试");
            } else {
                //修改游戏表
                game.setId(id);
                gameMapper.update(game);
                gameId = game.getId();
                List<Tag> tagsOfThisGameInDB = tagService.getTagsByGameId(id);

                //传入有   数据库查不到
                for (String tagName : tagsNow) {
                    Tag tag = tagService.selectByName(tagName);
                    if (tag == null) {
                        BigInteger tagId = tagService.insertOrUpdate(null, tagName);
                        tagService.insertToRelationTable(gameId, tagId);
                    } else {
                        //数据库内如果有  不确定关系表内有没有   不查关系表  通过游戏是否有无此tag判断
                        if (!tagsOfThisGameInDB.contains(tag)) {
                            tagService.insertToRelationTable(gameId, tag.getId());
                        }
                    }
                }

                //数据库内有     传入没有
                for (Tag tag : tagsOfThisGameInDB) {
                    if (!tagsNow.contains(tag.getName())) {
                        tagService.deleteRelationRecord(gameId, tag.getId());
                    }
                }

            }
        }

        //返回id
        return gameId;
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return gameMapper.delete(id, time);
    }

    public List<Game> getPages(int page, int pageSize, String title) {
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

    public void batchInsert(List<Game> games) {
        int time = (int) (System.currentTimeMillis() / 1000);
        for (int i = 0; i < games.size(); i++) {
            games.get(i).setCreateTime(time);
            games.get(i).setUpdateTime(time);
            games.get(i).setIsDeleted(0);
        }
        gameMapper.batchInsert(games);
    }

    public int countGames() {
        return gameMapper.countGames();
    }
}
