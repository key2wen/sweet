package com.wenwen.sweet.modelao;

import com.alibaba.fastjson.JSONObject;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.modelvo.ExampleVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zwh
 */
public class WordAO {

    private Integer id;

    private String word;

    //'单词类型:1普通单词，2国际音标，3中国拼音',
    private Integer type;

    //默认
    private Integer status = 0;

    private String ukSymbol;

    private String usSymbol;


//    private String ukVoicePath;

//    private String usVoicePath;

    private List<String> descList;

    private String desc1;
    private String desc2;
    private String desc3;
    private String desc4;
    private String desc5;

    private List<ExampleVO> exampleList = new ArrayList<ExampleVO>();
    private String example1;
    private String example2;
    private String example3;
    private String example4;
    private String example5;
    private String trans1;
    private String trans2;
    private String trans3;
    private String trans4;
    private String trans5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }

    public String getExample3() {
        return example3;
    }

    public void setExample3(String example3) {
        this.example3 = example3;
    }

    public String getExample4() {
        return example4;
    }

    public void setExample4(String example4) {
        this.example4 = example4;
    }

    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }

    public String getExample5() {
        return example5;
    }

    public void setExample5(String example5) {
        this.example5 = example5;
    }

    public String getTrans5() {
        return trans5;
    }

    public void setTrans5(String trans5) {
        this.trans5 = trans5;
    }

    public String getTrans1() {
        return trans1;
    }

    public void setTrans1(String trans1) {
        this.trans1 = trans1;
    }

    public String getTrans2() {
        return trans2;
    }

    public void setTrans2(String trans2) {
        this.trans2 = trans2;
    }

    public String getTrans3() {
        return trans3;
    }

    public void setTrans3(String trans3) {
        this.trans3 = trans3;
    }

    public String getTrans4() {
        return trans4;
    }

    public void setTrans4(String trans4) {
        this.trans4 = trans4;
    }

    public Word buildWord(Word word) throws Exception {
        BeanUtils.copyProperties(word, this);

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(desc1)) {
            sb.append(desc1).append("|");
        }
        if (StringUtils.isNotBlank(desc2)) {
            sb.append(desc2).append("|");
        }
        if (StringUtils.isNotBlank(desc3)) {
            sb.append(desc3).append("|");
        }
        if (StringUtils.isNotBlank(desc4)) {
            sb.append(desc4).append("|");
        }
        if (StringUtils.isNotBlank(desc5)) {
            sb.append(desc5);
        }

        word.setDesc(sb.toString());

        if(StringUtils.isNotBlank(example1)) {
            exampleList.add(new ExampleVO(example1, trans1));
        }
        if(StringUtils.isNotBlank(example2)) {
            exampleList.add(new ExampleVO(example2, trans2));
        }
        if(StringUtils.isNotBlank(example3)) {
            exampleList.add(new ExampleVO(example3, trans3));
        }
        if(StringUtils.isNotBlank(example4)) {
            exampleList.add(new ExampleVO(example4, trans4));
        }
        if(StringUtils.isNotBlank(example5)) {
            exampleList.add(new ExampleVO(example5, trans5));
        }
        String jsonArrayExap = JSONObject.toJSONString(this.exampleList);

        word.setExample(jsonArrayExap);
        return word;
    }
}
