package com.example.module.service;

import com.example.module.entity.GameTagRelation;
import com.example.module.entity.Tag;
import com.example.module.mapper.TagMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-09-28
 */
@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;

    public Tag getById(BigInteger id) {
        return tagMapper.getById(id);
    }

    public List<Tag> getAll() {
        return tagMapper.getAll();
    }

    public Tag extractById(BigInteger id) {
        return tagMapper.extractById(id);
    }

    public int insert(Tag tag) {
        return tagMapper.insert(tag);
    }

    public int update(Tag tag) {
        return tagMapper.update(tag);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return tagMapper.delete(time, id);
    }

    public BigInteger insertOrUpdate(BigInteger id, String name) {
        if (name == null) {
            throw new RuntimeException("传入参数有null");
        }

        int time = (int) (System.currentTimeMillis() / 1000);
        Tag tag = new Tag();
        BigInteger resultId;
        tag.setName(name);
        tag.setUpdateTime(time);
        tag.setIsDeleted(0);

        //增
        if (id == null) {
            tag.setCreateTime(time);
            tagMapper.insert(tag);
            resultId = tag.getId();
        } else {
            //改
            if (tagMapper.getById(id) == null) {
                throw new RuntimeException("未查询到相关数据，请检查后再试");
            } else {
                tag.setUpdateTime(time);
                tag.setId(id);
                tagMapper.update(tag);
                resultId = tag.getId();
            }
        }

        return resultId;
    }

    public void insertToRelationTable(BigInteger gameId, BigInteger tagId) {
        if (gameId == null || tagId == null) {
            throw new RuntimeException("传入参数有null");
        }
        int time = (int) (System.currentTimeMillis() / 1000);
        GameTagRelation relation = new GameTagRelation();

        //设置除了id之外的值
        relation.setGameId(gameId).setTagId(tagId).setCreateTime(time).setUpdateTime(time).setIsDeleted(0);

        tagMapper.insertToRelationTable(relation);
    }

    public List<Tag> getTagsByGameId(BigInteger gameId) {
        return tagMapper.getTagsByGameId(gameId);
    }

    public int deleteRelationRecord(BigInteger gameId, BigInteger tagId) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return tagMapper.deleteRelationRecord(time, gameId, tagId);
    }

    public List<BigInteger> selectGameIdByTagId(BigInteger tagId) {
        return tagMapper.selectGameIdByTagId(tagId);
    }

    public Tag selectByName(String tagName) {
        return tagMapper.selectByName(tagName);
    }
}
