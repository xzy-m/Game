package com.example.app.controller;

import com.alibaba.fastjson.JSON;
import com.example.app.domain.*;
import com.example.app.tool.AliOssTool;
import com.example.module.annotation.DataSource;
import com.example.module.entity.*;
import com.example.module.enums.DBTypeEnum;
import com.example.module.response.Response;
import com.example.module.service.*;
import com.example.module.tool.ArTool;
import com.example.module.tool.WPTool;
import com.example.module.wp.AppPageWP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:27
 */
@RestController
@Slf4j
public class GameController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public AliOssTool aliOssTool;

    @Autowired
    public GameService gameService;

    @Autowired
    public CategoryService categoryService;

    //⼀、⼆级分类⻚接⼝：查出一级分类和二级分类即可
    @DataSource(type = DBTypeEnum.SLAVE1)
    @RequestMapping("/game/categories/level1and2")
    public Response categoryLevel1And2() {
        List<Category> result = categoryService.categoryLevel1And2();
        return new Response<>("1001", result);
    }

    //三级及以上分类⻚接⼝：传入一个分类id，返回此分类的全部子分类，此分类对应的部分游戏，不传就没有
    @DataSource
    @RequestMapping("/game/categories/level3")
    public Response categoryLevel3AndAbove(BigInteger id) {
        //先拿到它的全部子分类
        List<Category> categoryList = categoryService.categoryLevel3AndAbove(id);
        List<BigInteger> ids = new ArrayList<>();
        for (Category category : categoryList) {
            ids.add(category.getId());
        }
        List<Game> gameList = categoryService.getGamesByCategoryId(ids);

        CategoryVo result = new CategoryVo().setCategoryList(categoryList).setGameList(gameList);
        return new Response<>("1001", result);
    }

    @DataSource(type = DBTypeEnum.SLAVE1)
    @RequestMapping("/game/info")
    public Response gameInfo(@RequestParam("id") BigInteger id) {

        //准备game实例和返回的vo实例
        GameInfoVo gameInfoVo = new GameInfoVo();
        Game game = gameService.gameInfo(id);
        if (game == null) {
            Response response = new Response("1006");
            return response;
        }

        //对时间处理，new simpleDateFormat再.format（再new Date（可long可integer））
        gameInfoVo.setTitle(game.getTitle());
        List<String> stringList = Arrays.asList(game.getPictures().split("\\$"));
        gameInfoVo.setPictures(stringList);
        Integer time = game.getCreateTime();

        String resultTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000l));
        gameInfoVo.setCreateTime(resultTime);
        gameInfoVo.setDownloadLink(game.getDownloadLink());

        //增加一个图文详细介绍
        String detail = game.getDetail();
        byte[] decode = Base64.getUrlDecoder().decode(detail);
        String detailJson = new String(decode);
        List<DetailVo> detailVoList = JSON.parseArray(detailJson, DetailVo.class);
        gameInfoVo.setDetail(detailVoList);

        //估计返回要切一下
        gameInfoVo.setTags(Arrays.stream(game.getTags().split(",")).toList());

        //统一返回的三个字段
        Response<GameInfoVo> response = new Response<GameInfoVo>("1001");
        response.setData(gameInfoVo);

        return response;
    }

    @DataSource
    @RequestMapping("/game/page")
    public Response appPagination(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "wp", required = false) String wp) {

        //准备需要的VO加WP
        GameAppPageVo gameAppPageVo = new GameAppPageVo();
        AppPageWP appPageWP = new AppPageWP();
        List<GameListVo> gameList = new ArrayList<>();
        int pageSize = 3;

        //1,解码处理传入参数wp
        if (wp != null) {
            try {
                appPageWP = WPTool.decodeWP(wp);
            } catch (Exception e) {
                Response<GameAppPageVo> response = new Response<>("2002");
                response.setData(gameAppPageVo);
                return response;
            }
        } else {
            appPageWP.setPage(1);
            if (title != null) {
                appPageWP.setTitle(title.trim());
            }
        }

        //2,检查缓存    或者查询数据库
        Boolean ifDataInRedis = redisTemplate.hasKey("game_page_" + appPageWP.getPage() + "_" + appPageWP.getTitle());

        if (ifDataInRedis) {
            log.info("提示：根据wp中页码与标题在缓存中取出数据");
            gameList = (List) redisTemplate.opsForValue().get("game_page_" + appPageWP.getPage() + "_" + appPageWP.getTitle());
        } else {
            //获取所有游戏的类型id,一次查完Category
            List<Game> pageLimit = gameService.getPages(appPageWP.getPage(), pageSize, appPageWP.getTitle());
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

            for (Game game : pageLimit) {
                GameListVo gameListVo = new GameListVo();
                gameListVo.setGameId(game.getId());
                gameListVo.setTitle(game.getTitle());
                gameListVo.setType(map.get(game.getCategoryId()));

                //你单独一个VO
                ImageVo imageVO = new ImageVo();
                String imageSrc = game.getPictures().split("\\$")[0];
                imageVO.setSrc(imageSrc);
                imageVO.setAr(ArTool.getAr(imageSrc));
                gameListVo.setImageVO(imageVO);

                gameList.add(gameListVo);
            }

            //当前页游戏数据   存redis五分钟
            redisTemplate.opsForValue().set("game_page_" + appPageWP.getPage() + "_" + appPageWP.getTitle(), gameList, 5, TimeUnit.MINUTES);
        }
        gameAppPageVo.setPageList(gameList);

        //3,游戏数据外的两个参数
        if (gameList.size() >= pageSize) {
            gameAppPageVo.setIsEnd(false);
        } else {
            gameAppPageVo.setIsEnd(true);
        }

        //vo里面增加一个字段wrapperParameter，意为下次访问的全部传入参数
        appPageWP.setPage(appPageWP.getPage() + 1);
        String wrapperParameter = WPTool.encodeWP(appPageWP);
        gameAppPageVo.setWrapperParameter(wrapperParameter);

        //统一返回的两个字段
        Response<GameAppPageVo> response = new Response<>("1001");
        response.setData(gameAppPageVo);
        return response;

    }

    //多线程去查询多张表   如同各人做各事
    @DataSource
    @RequestMapping("/game/homePage")
    public Response homePage() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(4);

        //轮播
        ArrayList<BannerVo> bannerVos = new ArrayList<>();
        {
            List<Banner> banners = bannerService.getAll();
            for (Banner banner : banners) {
                BannerVo bannerVo = new BannerVo();
                bannerVo.setImage(banner.getImage()).setLink("abd://game/" + banner.getType() + "?id=" + banner.getId());
                bannerVos.add(bannerVo);
            }
            countDownLatch.countDown();
        }

        //频道
        ArrayList<ChannelVo> channelVos = new ArrayList<>();
        {
            List<Channel> channels = channelService.getAll();
            for (Channel channel : channels) {
                ChannelVo channelVo = new ChannelVo();
                channelVo.setId(channel.getId()).setImage(channel.getImage());
                channelVos.add(channelVo);
            }
            countDownLatch.countDown();
        }

        //活动
        ArrayList<ActivityVo> activityVos = new ArrayList<>();
        {
            List<Activity> activities = activityService.getAll();
            for (Activity activity : activities) {
                ActivityVo activityVo = new ActivityVo();
                activityVo.setId(activity.getId()).setImage(activity.getImage()).setTitle(activity.getTitle());
                activityVos.add(activityVo);
            }
            countDownLatch.countDown();
        }

        //推荐
        ArrayList<RecommendVo> recommendVos = new ArrayList<>();
        {
            List<Recommend> recommends = recommendService.getAll();
            for (Recommend recommend : recommends) {
                RecommendVo recommendVo = new RecommendVo();
                recommendVo.setId(recommend.getId())
                        .setPictures(recommend.getPictures())
                        .setTitle(recommend.getTitle())
                        .setDownloadLink(recommend.getDownloadLink());
                recommendVos.add(recommendVo);
            }
            countDownLatch.countDown();
        }

        //主线程等待各支线程执行
        countDownLatch.await();
        HomePageVo homePageVo = new HomePageVo();
        homePageVo.setBannerVos(bannerVos).setChannelVos(channelVos).setActivityVos(activityVos).setRecommendVos(recommendVos);

        return new Response("1001", homePageVo);
    }

}
