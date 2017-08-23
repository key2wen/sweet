package com.wenwen.sweet.dao.mapper;

import com.wenwen.sweet.model.Word;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WordMapper {

    int insertWord(Word word);

    int deleteWordById(int wordId);

    int updateWord(Word word);

    Word getWord(int wordId);

    List<Word> selectWords(@Param("offset") int offset, @Param("size") int size);

    int countWords();

}
