package com.wenwen.sweet.model;

import com.wenwen.sweet.commons.BaseBean;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zwh
 */
public class MwordAffix extends BaseBean {

    //词典dictionary:(baidu,youdao,system,whvip..)
    @NotBlank(message = "词典名称不能为空")
    @Size(max = 30, message = "词典名称不能超过30个字")
    private String dict;

    //'单词类型:1普通单词，2国际音标，3中国拼音',
    @NotNull(message = "单词类型不能为空")
    private Integer type;

    @Size(max = 30, message = "词典前缀不能超过30个字")
    private String wordPrefix;

    @Size(max = 30, message = "词典后缀不能超过30个字")
    private String wordSuffix;

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWordPrefix() {
        return wordPrefix;
    }

    public void setWordPrefix(String wordPrefix) {
        this.wordPrefix = wordPrefix;
    }

    public String getWordSuffix() {
        return wordSuffix;
    }

    public void setWordSuffix(String wordSuffix) {
        this.wordSuffix = wordSuffix;
    }

    public static class Type {
        //'单词类型:1普通单词，2国际音标，3中国拼音(包括了单个字母)',
        public static final int NORMAL = 1;
        public static final int SYMBOL = 2;
        public static final int CHINESE = 3;
    }


}
