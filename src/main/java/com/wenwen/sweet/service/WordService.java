package com.wenwen.sweet.service;

import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.model.Word;

import java.util.List;


public interface WordService {


    int saveWord(Word word);

    int deleteWordById(int wordId);

    int updateWord(Word word);

    Word getWord(int wordId);

    PagedResult<Word> selectWords(Integer pageNum, Integer pageSize);

    List<Word> searchWords(Word word);

}
