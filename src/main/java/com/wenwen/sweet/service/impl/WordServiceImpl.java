package com.wenwen.sweet.service.impl;

import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.dao.mapper.WordMapper;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.service.WordService;
import com.wenwen.sweet.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 事务默认支持加只读（适用于读操作，写操作需要额外配置 ）
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WordServiceImpl implements WordService {

    @Autowired
    private WordMapper wordMapper;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int saveWord(Word word) {
        // 如果不带id,说明是第一次保存
        if (!NumberUtils.isPositive(word.getId())) {
            // 第一次保存,插入数据库
            return wordMapper.insertWord(word);
        } else {
            //
            return wordMapper.updateWord(word);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteWordById(int wordId) {
        return wordMapper.deleteWordById(wordId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updateWord(Word word) {
        return wordMapper.updateWord(word);
    }

    public Word getWord(int wordId) {
        return wordMapper.getWord(wordId);
    }

    public PagedResult<Word> selectWords(Integer pageNum, Integer pageSize) {
        PagedResult<Word> pagedResult = new PagedResult<Word>(pageNum, pageSize);

        pagedResult.setList(wordMapper.selectWords(pagedResult.getOffset(), pagedResult.getLimit()));
        pagedResult.setTotal(wordMapper.countWords());
        return pagedResult;
    }

    public List<Word> searchWords(Word word) {
        Assert.notNull(word, "单词对象不能为空");
        return wordMapper.searchWords(word);
    }
}
