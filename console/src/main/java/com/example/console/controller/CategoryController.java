package com.example.console.controller;

import com.example.module.entity.Category;
import com.example.module.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-08-08 下午 2:48
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @RequestMapping("/info")
    public Category getById(@RequestParam("id") BigInteger id) {
        return categoryService.getById(id);
    }

    @RequestMapping("/extractInfo")
    public Category extractById(@RequestParam("id") BigInteger id) {
        return categoryService.extractById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestParam("type") String type) {
        try {
            return categoryService.insertOrUpdate(null, type) != null ? "新增成功" : "新增失败";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") BigInteger id,
                         @RequestParam("type") String type) {
        try {
            return categoryService.insertOrUpdate(id, type) != null ? "修改成功" : "修改失败";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") BigInteger id) {
        return categoryService.delete(id) != 0 ? "删除成功" : "删除失败";
    }
}
