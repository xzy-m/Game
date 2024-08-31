package com.example.console.controller;

import com.example.console.domain.GameConsolePageVo;
import com.example.console.domain.GameListVo;
import com.example.module.dto.PageDto;
import com.example.module.entity.Game;
import com.example.module.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-13 上午 6:27
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    //String.trim(),去掉前后的空格,@RequestParam让传入参数一定不为null
    @RequestMapping("/insert")
    public String insert(@RequestParam("pictures") String pictures,
                         @RequestParam("title") String title,
                         @RequestParam("downloadLink") String downloadLink,
                         @RequestParam("categoryId") BigInteger categoryId) {
        try {
            return gameService.insertOrUpdate(null, pictures.trim(), title.trim(), downloadLink.trim(), categoryId) != null ? "新增成功" : "新增失败";
        } catch (Exception e) {
            //return e.getMessage();
            return e.getLocalizedMessage();
        }
    }

    //Controller不抛异常，直接在catch中return
    @RequestMapping("/update")
    public String update(@RequestParam("id") BigInteger id,
                         @RequestParam("pictures") String pictures,
                         @RequestParam("title") String title,
                         @RequestParam("downloadLink") String downloadLink,
                         @RequestParam("categoryId") BigInteger categoryId) {
        try {
            return gameService.insertOrUpdate(id, pictures.trim(), title.trim(), downloadLink.trim(), categoryId) != null ? "修改成功" : "修改失败";
        } catch (Exception e) {
            //e中取出异常原因
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") BigInteger id) {
        return gameService.delete(id) == 1 ? "删除成功" : "删除失败";
    }

    //分页 PC（管理后台） 分页， 前端传你page，你传给前端total、pageSize、list
    @RequestMapping("/page")
    public GameConsolePageVo pagination(@RequestParam("page") int page,
                                        @RequestParam(value = "title", required = false) String title) {

        //准备一个Vo
        GameConsolePageVo pageVo = new GameConsolePageVo();

        if (title != null) {
            title = title.trim();
        }

        //vo参数:pageSize
        pageVo.setPageSize(3);

        //vo参数:list
        List<Game> pageLimit = gameService.pagination(page, pageVo.getPageSize(), title);
        ArrayList<GameListVo> list = new ArrayList<>();
        for (Game game : pageLimit) {

            GameListVo gameListVo = new GameListVo();
            gameListVo.setGameId(game.getId());
            gameListVo.setTitle(game.getTitle());
            gameListVo.setPicture(game.getPictures().split("\\$")[0]);

            //pageVo.getPageList(),报错，不能为null
            list.add(gameListVo);
        }

        //vo参数:total,总数跟随查询的内容
        pageVo.setTotal(gameService.getTotal(title));
        pageVo.setPageList(list);

        return pageVo;
    }
}
