package com.wenwen.sweet.model;

import com.wenwen.sweet.commons.BaseBean;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zwh
 */
public class Word extends BaseBean {


    @NotBlank(message = "单词不能为空")
    @Size(max = 50, message = "单词不能超过50个字")
    private String word;

    //'单词类型:1普通单词，2国际音标，3中国拼音',
    @NotNull(message = "单词类型不能为空")
    private Integer type;

    @NotNull(message = "状态不能为空")
    private Integer status;

    @Size(max = 60, message = "英国式发音音标不能超过60个字")
    private String ukSymbol;

    @Size(max = 60, message = "美国式发音音标不能超过60个字")
    private String usSymbol;

    @Size(max = 255, message = "英国发音文件存储位置不能超过255个字")
    private String ukVoicePath;

    @Size(max = 255, message = "美国发音文件存储位置不能超过255个字")
    private String usVoicePath;

    @Size(max = 512, message = "单词描述不能超过512个字")
    private String desc;

    @Size(max = 1024, message = "句子例子不能超过1024个字")
    private String example;

    @NotNull(message = "分类不能为空")
    private Integer classify;

    @Override
    public String toString() {
        return "WordVO{" +
                "word='" + word + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", ukSymbol='" + ukSymbol + '\'' +
                ", usSymbol='" + usSymbol + '\'' +
                ", ukVoicePath='" + ukVoicePath + '\'' +
                ", usVoicePath='" + usVoicePath + '\'' +
                ", desc='" + desc + '\'' +
                ", example='" + example + '\'' +
                '}';
    }

    public Word(){

    }
    public Word(String word){
        this.word = word;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public static class Classify {
        //无分类
        public static final int NullClass = 0;
        public static final int Animal = 1;
        public static final int Plant = 2;
        public static final int Fruits = 3;
        public static final int Sports = 4;
    }

    public static class Type {
        //'单词类型:1普通单词，2国际音标，3中国拼音(包括了单个字母)',
        public static final int NORMAL = 1;
        public static final int SYMBOL = 2;
        public static final int CHINESE = 3;
    }

    public static class Status{
        public static final int DELETED = 1;
        public static final int NORMAL = 0;
    }

}
