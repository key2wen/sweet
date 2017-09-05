package com.wenwen.sweet.modelvo;

import java.util.List;

/**
 * @author zwh
 */
public class WordVO {


    private String word;

    //'单词类型:1普通单词，2国际音标，3中国拼音',
    private Integer type;

    private Integer status;

    private String ukSymbol;

    private String usSymbol;

    private String ukVoicePath;

    private String usVoicePath;

    private List<String> descList;

    private List<ExampleVO> exampleList;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUkSymbol() {
        return ukSymbol;
    }

    public void setUkSymbol(String ukSymbol) {
        this.ukSymbol = ukSymbol;
    }

    public String getUsSymbol() {
        return usSymbol;
    }

    public void setUsSymbol(String usSymbol) {
        this.usSymbol = usSymbol;
    }

    public String getUkVoicePath() {
        return ukVoicePath;
    }

    public void setUkVoicePath(String ukVoicePath) {
        this.ukVoicePath = ukVoicePath;
    }

    public String getUsVoicePath() {
        return usVoicePath;
    }

    public void setUsVoicePath(String usVoicePath) {
        this.usVoicePath = usVoicePath;
    }

    public List<String> getDescList() {
        return descList;
    }

    public void setDescList(List<String> descList) {
        this.descList = descList;
    }

    public List<ExampleVO> getExampleList() {
        return exampleList;
    }

    public void setExampleList(List<ExampleVO> exampleList) {
        this.exampleList = exampleList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
