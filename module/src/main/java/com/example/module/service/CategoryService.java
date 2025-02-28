package com.example.module.service;

import com.example.module.entity.Category;
import com.example.module.entity.Game;
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

    public BigInteger insertOrUpdate(BigInteger id, String type, BigInteger parentId) {

        if (type == null) {
            throw new RuntimeException("游戏类型未填写，请检查~");
        }

        Category category = new Category();
        BigInteger resultId = null;
        int time = (int) (System.currentTimeMillis() / 1000);
        category.setType(type);
        category.setParentId(parentId);
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

    public List<Category> categoryLevel1And2() {

        List<Category> tops = categoryMapper.getTops();
        List<Category> categories = categoryMapper.getAll();

        for (Category top : tops) {
            List<Category> children = getChildren(categories, top);
            top.setChildren(children);
        }
        return tops;
    }

    public List<Category> categoryLevel3AndAbove(BigInteger id) {
        Category category = categoryMapper.getById(id);
        List<Category> categories = categoryMapper.getAll();
        List<Category> children = getChildren(categories, category);
        return children;
    }

    public List<Game> getGamesByCategoryId(List<BigInteger> ids) {
        return categoryMapper.getGamesByCategoryId(ids);
    }

    /**
     * 1，找到全部最顶级
     * 2，每个顶级分类递归找子类
     */
    public List<Category> categoryTree() {

        List<Category> all = categoryMapper.getAll();

        //顶级有了
        List<Category> resultTree = categoryMapper.getTops();

        for (Category top : resultTree) {
            recursionInCategory(all, top);
        }

        return resultTree;
    }

    public void recursionInCategory(List<Category> categoryList, Category category) {
        //从这个范围内找到目标分类的子类别
        List<Category> children = getChildren(categoryList, category);
        category.setChildren(children);

        //再对其子类别逐个做同样操作
        for (Category child : children) {
            recursionInCategory(categoryList, child);
        }

    }

    //查找某分类的下一级
    public List<Category> getChildren(List<Category> list, Category goalCategory) {
        ArrayList<Category> children = new ArrayList<>();

        for (Category category : list) {
            if ((category.getParentId() != null) && (category.getParentId().equals(goalCategory.getId()))) {
                children.add(category);
            }
        }
        return children;
    }

    //判断是否有下一级分类
    public boolean ifHasChildren(List<Category> list, Category category) {
        return getChildren(list, category).size() > 0;
    }

}
