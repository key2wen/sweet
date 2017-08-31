package com.wenwen.sweet.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.modelvo.ExampleVO;
import com.wenwen.sweet.modelvo.WordVO;
import javafx.util.Pair;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbj on 17/8/26.
 */
public class WordConvert implements BaseConvert<Word, WordVO> {

    public WordVO toVO(Word word) {
        WordVO wordVO = new WordVO();
        try {
            BeanUtils.copyProperties(wordVO, word);
            if (StringUtils.isNotBlank(word.getDesc())) {
                String[] descs = word.getDesc().split("\\|");
                List<String> descList = new ArrayList<String>();
                for (String desc : descs) {
                    descList.add(desc);
                }
                wordVO.setDescList(descList);
            }

            if (StringUtils.isNotBlank(word.getExample())) {
                List<ExampleVO> exampleVoList = JSON.parseArray(word.getExample(), ExampleVO.class);
                wordVO.setExampleList(exampleVoList);
            }

        } catch (Exception e) {

        }
        return wordVO;
    }

    public Word toModel(WordVO wordVO) {
        return null;
    }
}
