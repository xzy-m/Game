package com.example.console.controller;

import com.alibaba.fastjson.JSON;
import com.example.console.domain.DetailVo;
import com.example.console.domain.GameConsolePageVo;
import com.example.console.domain.GameListVo;
import com.example.module.annotation.DataSource;
import com.example.module.entity.Category;
import com.example.module.enums.DBTypeEnum;
import com.example.module.response.Response;
import com.example.module.entity.Game;
import com.example.module.service.CategoryService;
import com.example.module.service.GameService;
import com.example.module.tool.GameDetailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-13 上午 6:27
 */
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;

    @DataSource(type = DBTypeEnum.MASTER)
    @RequestMapping("/game/categoryTree")
    public Response categoryTree() {
        List<Category> tree = categoryService.categoryTree();
        return new Response("1001", tree);
    }

    //String.trim(),去掉前后的空格,@RequestParam让传入参数一定不为null
    @DataSource(type = DBTypeEnum.SLAVE2)
    @RequestMapping("/game/insert")
    public Response insert(@RequestParam("pictures") String pictures,
                           @RequestParam("title") String title,
                           @RequestParam("downloadLink") String downloadLink,
                           @RequestParam("categoryId") BigInteger categoryId,
                           @RequestParam("detail") String detail,
                           @RequestParam("tags") String tags) {
        try {
            //判断detail
            byte[] decode = Base64.getUrlDecoder().decode(detail);
            String detailJson = new String(decode);
            List<DetailVo> detailVoList = JSON.parseArray(detailJson, DetailVo.class);
            for (DetailVo detailVo : detailVoList) {
                if (!GameDetailTool.checkDetail(detailVo.getType())) {
                    return new Response("1003", "type is not allowed");
                }
            }

            String statusCode = gameService.insertOrUpdate(null, pictures.trim(), title.trim(), downloadLink.trim(), categoryId, detail, tags.trim()) != null ? "1001" : "1003";
            Response response = new Response(statusCode);
            return response;
        } catch (Exception e) {
            Response response = new Response("1004");
            return response;
        }
    }

    //Controller不抛异常，直接在catch中return
    @DataSource(type = DBTypeEnum.SLAVE1)
    @RequestMapping("/game/update")
    public Response update(@RequestParam("id") BigInteger id,
                           @RequestParam("pictures") String pictures,
                           @RequestParam("title") String title,
                           @RequestParam("downloadLink") String downloadLink,
                           @RequestParam("categoryId") BigInteger categoryId,
                           @RequestParam("detail") String detail,
                           @RequestParam("tags") String tags) {
        try {
            byte[] decode = Base64.getUrlDecoder().decode(detail);
            String detailJson = new String(decode);
            List<DetailVo> detailVoList = JSON.parseArray(detailJson, DetailVo.class);
            for (DetailVo detailVo : detailVoList) {
                if (!GameDetailTool.checkDetail(detailVo.getType())) {
                    return new Response("1003", "type is not allowed");
                }
            }

            String statusCode = gameService.insertOrUpdate(id, pictures.trim(), title.trim(), downloadLink.trim(), categoryId, detail, tags.trim()) != null ? "1001" : "1003";
            Response response = new Response(statusCode);
            return response;
        } catch (Exception e) {
            Response response = new Response("1004");
            return response;
        }
    }

    @DataSource(type = DBTypeEnum.MASTER)
    @RequestMapping("/game/delete")
    public Response delete(@RequestParam("id") BigInteger id) {
        String statusCode = gameService.delete(id) == 1 ? "1001" : "1003";
        Response response = new Response(statusCode);
        return response;
    }

    //分页 PC（管理后台） 分页， 前端传你page，你传给前端total、pageSize、list
    @DataSource(type = DBTypeEnum.SLAVE1)
    @RequestMapping("/game/page")
    public Response pagination(@RequestParam("page") int page,
                               @RequestParam(value = "title", required = false) String title) {

        //准备一个统一返回和所需Vo
        GameConsolePageVo pageVo = new GameConsolePageVo();

        if (title != null) {
            title = title.trim();
        }

        //vo参数:pageSize
        pageVo.setPageSize(3);

        //vo参数:list
        List<Game> pageLimit = gameService.getPages(page, pageVo.getPageSize(), title);
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

        //统一返回
        Response<GameConsolePageVo> response = new Response<>("1001");
        response.setData(pageVo);
        return response;
    }
}
