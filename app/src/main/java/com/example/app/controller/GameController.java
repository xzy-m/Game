package com.example.app.controller;

import com.example.app.domain.GameAppPageVo;
import com.example.app.domain.GameInfoVo;
import com.example.app.domain.GameListVo;
import com.example.app.domain.ImageVO;
import com.example.module.entity.Category;
import com.example.module.entity.Game;
import com.example.module.service.CategoryService;
import com.example.module.service.GameService;
import com.example.module.tool.AliOssTool;
import com.example.module.tool.ArTool;
import com.example.module.tool.ConvertSrcToMultipartFile;
import com.example.module.tool.WrapperParameterTool;
import com.example.module.wp.AppPageWP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:27
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    public AliOssTool aliOssTool;

    @Autowired
    public GameService gameService;

    @Autowired
    public CategoryService categoryService;

    @RequestMapping("/info")
    public GameInfoVo gameInfo(@RequestParam("id") BigInteger id) {

        //准备game实例和返回的vo实例
        GameInfoVo gameInfoVo = new GameInfoVo();
        Game game = gameService.gameInfo(id);
        if (game == null) {
            gameInfoVo.setTitle("所传id无法查询到数据");
            return gameInfoVo;
        }

        //对时间处理，new simpleDateFormat再.format（再new Date（可long可integer））
        gameInfoVo.setTitle(game.getTitle());
        List<String> stringList = Arrays.asList(game.getPictures().split("\\$"));
        gameInfoVo.setPictures(stringList);
        Integer time = game.getCreateTime();

        String resultTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000l));
        gameInfoVo.setCreateTime(resultTime);
        gameInfoVo.setDownloadLink(game.getDownloadLink());

        //返回需要的vo
        return gameInfoVo;
    }

    @RequestMapping("/page")
    public GameAppPageVo appPagination(@RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "wp", required = false) String wp) {

        //准备需要的Vo和WP
        GameAppPageVo gameAppPageVo = new GameAppPageVo();
        AppPageWP appPageWP = new AppPageWP();

        //传入wp不为null就解码,为null就是第一页
        if (wp != null) {
            try {
                appPageWP = WrapperParameterTool.decodeWP(wp);
            } catch (Exception e) {
                gameAppPageVo.setWrapperParameter("传入参数wp异常请检查");
                return gameAppPageVo;
            }
        } else {
            appPageWP.setPage(1);
            if (title != null) {
                appPageWP.setTitle(title.trim());
            }
        }

        int pageSize = 3;
        List<Game> pageLimit = gameService.pagination(appPageWP.getPage(), pageSize, appPageWP.getTitle());

        //这个list装游戏的
        ArrayList<GameListVo> list = new ArrayList<>();

        //获取所有游戏的类型id,一次查完Category
        ArrayList<BigInteger> cIdList = new ArrayList<>();
        for (Game game : pageLimit) {
            cIdList.add(game.getCategoryId());
        }
        List<Category> categoryList = categoryService.getCategoryList(cIdList);

        //categoryList的id与type装入map
        HashMap<BigInteger, String> map = new HashMap<>();
        for (Category category : categoryList) {
            map.put(category.getId(), category.getType());
        }
        //这个ArrayList一会来删oss里的图片
        ArrayList<String> imageNames = new ArrayList<>();

        for (Game game : pageLimit) {
            GameListVo gameListVo = new GameListVo();
            gameListVo.setGameId(game.getId());
            gameListVo.setTitle(game.getTitle());
            gameListVo.setType(map.get(game.getCategoryId()));

            //你单独一个VO
            ImageVO imageVO = new ImageVO();
            String imageSrc = game.getPictures().split("\\$")[0];
            imageVO.setSrc(imageSrc);
            imageVO.setAr(ArTool.getAr(imageSrc));
            gameListVo.setImageVO(imageVO);

            //不能每次都上传再返回吧塞满算了
            //本地路径src转为MultipartFile
            MultipartFile multipartFile = ConvertSrcToMultipartFile.getMultipartFile(imageSrc);
            //上传后拿oss路径,这不仅是路径还拼了图片名
            String ossResult = aliOssTool.uploadOss(multipartFile);
            //返回路径？前的内容
            int i = ossResult.indexOf("?");
            String imageLink = ossResult.substring(0, i);
            imageVO.setImageLink(imageLink);
            //一起返回了bucket里的图片名,装起来
            String objectName = ossResult.split("~")[1];
            imageNames.add(objectName);

            list.add(gameListVo);
        }
        gameAppPageVo.setPageList(list);

        //删了这些图片
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            for (String imageName : imageNames) {
                System.out.println(imageName);
                aliOssTool.deleteOss(imageName);
            }
        };
        executor.schedule(task, 3, TimeUnit.MINUTES);
        executor.shutdown();

        //< end为true     >= 可能有下一页，end为false
        if (list.size() >= pageSize) {
            gameAppPageVo.setIsEnd(false);
        } else {
            gameAppPageVo.setIsEnd(true);
        }

        //vo里面增加一个字段wrapperParameter，意为下次访问的全部传入参数
        appPageWP.setPage(appPageWP.getPage() + 1);
        String wrapperParameter = WrapperParameterTool.encodeWP(appPageWP);
        gameAppPageVo.setWrapperParameter(wrapperParameter);

        //返回Vo
        return gameAppPageVo;
    }

}
