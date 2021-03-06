package com.wenwen.sweet.service.impl;

import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.dao.mapper.WordMapper;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.service.WordService;
import com.wenwen.sweet.util.NumberUtils;
import com.wenwen.sweet.util.SweetBusinessException;
import org.apache.commons.collections.CollectionUtils;
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
    public int saveWord(Word word) throws SweetBusinessException {
        // 如果不带id,说明是第一次保存
        if (!NumberUtils.isPositive(word.getId())) {

            List<Word> list = wordMapper.searchWords(new Word(word.getWord()));
            if (CollectionUtils.isNotEmpty(list)) {
                Word oldWord = list.get(0);
                if (oldWord.getStatus() == Word.Status.DELETED) {
                    word.setStatus(Word.Status.NORMAL);
                    word.setId(oldWord.getId());
                    return wordMapper.updateWord(word);
                } else {
                    throw new SweetBusinessException("单词已存在,新增失败");
                }
            }

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

    public PagedResult<Word> selectWords(Integer pageNum, Integer pageSize, Word word) {
        PagedResult<Word> pagedResult = new PagedResult<Word>(pageNum, pageSize);

        String wordName = null;
        Integer classify = null;
        if(word != null){
            wordName = word.getWord();
            classify = word.getClassify();
        }

        pagedResult.setList(wordMapper.selectWords(pagedResult.getOffset(), pagedResult.getLimit(), wordName, classify));
        pagedResult.setTotal(wordMapper.countWords(wordName, classify));
        return pagedResult;
    }

    public List<Word> searchWords(Word word) {
        Assert.notNull(word, "单词对象不能为空");
        return wordMapper.searchWords(word);
    }
}
