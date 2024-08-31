package com.example.module.service;

import com.example.module.entity.Category;
import com.example.module.mapper.CategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-08-08
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public Category getById(BigInteger id) {
        return categoryMapper.getById(id);
    }

    public Category extractById(BigInteger id) {
        return categoryMapper.extractById(id);
    }

    public BigInteger insertOrUpdate(BigInteger id, String type) {

        if (type == null) {
            throw new RuntimeException("游戏类型未填写，请检查~");
        }

        Category category = new Category();
        BigInteger resultId = null;
        int time = (int) (System.currentTimeMillis() / 1000);
        category.setType(type);
        category.setCreateTime(time);
        category.setUpdateTime(time);

        if (id == null) {
            //新增，返回id
            category.setIsDeleted(0);
            categoryMapper.insert(category);
            resultId = category.getId();
        } else {
            //修改，返回id
            if (categoryMapper.getById(id) == null) {
                throw new RuntimeException("未查询到您的数据，请检查id是否有误");
            } else {
                category.setId(id);
                category.setCreateTime(null);
                category.setUpdateTime((int) (System.currentTimeMillis() / 1000));
                categoryMapper.update(category);
                resultId = category.getId();
            }
        }
        System.out.println(resultId);
        return resultId;
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return categoryMapper.delete(time, id);
    }

    public List<Category> getCategoryList(ArrayList<BigInteger> idList) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < idList.size(); i++) {
            if (i != idList.size() - 1) {
                stringBuffer.append(idList.get(i)).append(",");
            } else {
                stringBuffer.append(idList.get(i));
            }
        }

        String ids = stringBuffer.toString();
        return categoryMapper.getCategoryList(ids);
    }
}
