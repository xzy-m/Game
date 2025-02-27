package com.example.console.controller;

import com.example.module.entity.Tag;
import com.example.module.response.Response;
import com.example.module.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-09-28 下午 2:58
 */
@RestController
public class TagController {

    @Autowired
    public TagService tagService;

    @RequestMapping("/tag/info")
    public Tag getById(@RequestParam("id") BigInteger id) {
        return tagService.getById(id);
    }

    @RequestMapping("/tag/extractInfo")
    public Tag extractById(@RequestParam("id") BigInteger id) {
        return tagService.extractById(id);
    }

    @RequestMapping("/tag/insert")
    public Response insert(@RequestParam("name") String name) {
        try {
            String statusCode = tagService.insertOrUpdate(null, name) != null ? "1001" : "1003";
            Response response = new Response(statusCode);
            return response;
        } catch (Exception e) {
            Response response = new Response("1004");
            return response;
        }
    }

    @RequestMapping("/tag/update")
    public Response update(@RequestParam("id") BigInteger id,
                           @RequestParam("name") String name) {
        try {
            String statusCode = tagService.insertOrUpdate(id, name) != null ? "1001" : "1003";
            Response response = new Response(statusCode);
            return response;
        } catch (Exception e) {
            Response response = new Response("1004");
            return response;
        }
    }

    @RequestMapping("/tag/delete")
    public Response delete(@RequestParam("id") BigInteger id) {

        String statusCode = tagService.delete(id) != 0 ? "1001" : "1003";
        Response response = new Response(statusCode);

        return response;
    }
}
