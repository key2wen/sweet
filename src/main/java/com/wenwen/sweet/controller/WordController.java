package com.wenwen.sweet.controller;

import com.wenwen.sweet.auth.Auth;
import com.wenwen.sweet.commons.JsonWrapper;
import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.convert.WordConvert;
import com.wenwen.sweet.dao.mapper.WordMapper;
import com.wenwen.sweet.model.UserInfo;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.modelvo.WordVO;
import com.wenwen.sweet.service.WordService;
import com.wenwen.sweet.util.SweetBusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("currUser")
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @ResponseBody
    @RequestMapping("/test")
    public JsonWrapper<Word> test() {
        Word word = new Word();
        return JsonWrapper.buildSuccessResult(word);
    }

    @Auth({UserInfo.RoleType.NON_PAYMENT_USER})
    @RequestMapping("/testError")
    public JsonWrapper<Word> testError() {
        throw new NullPointerException("测试错误处理。。。");
    }

    @RequestMapping("/saveWord")
    public ModelAndView saveWord(@Valid Word word, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("/word/edit", "word", word)
                    .addObject("errMsg", result.getFieldError().getDefaultMessage());
        }
        String msg = null;
        try {
            int ret = wordService.saveWord(word);
            msg = ret > 0 ? "保存成功" : "保存失败";
        } catch (SweetBusinessException sbe) {
            // 业务异常，非系统异常，catch自己处理
            msg = sbe.getMessage();
        }

        redirectAttributes.addFlashAttribute("msg", msg);
        return new ModelAndView("redirect:/word/list");
    }

    @RequestMapping("/edit/{wordId}")
    public ModelAndView editWord(@PathVariable("wordId") int wordId) {
        Word word = wordService.getWord(wordId);
        if(word == null){

        }
        WordVO wordVO = wordConvert.toVO(word);
        return new ModelAndView("/word/edit", "word", wordVO);
    }

    @Auth(value = {UserInfo.RoleType.NON_PAYMENT_USER})
    @RequestMapping("/list")
    public ModelAndView list(Integer pageNum, Integer pageSize) {
        PagedResult<Word> pagedWords = wordService.selectWords(pageNum, pageSize);
        return new ModelAndView("/word/list", "words", pagedWords);
    }

    @RequestMapping("/delete/{wordId}")
    public ModelAndView deleteWordById(@PathVariable("wordId") int wordId, RedirectAttributes redirectAttributes) {
        int ret = wordService.deleteWordById(wordId);
        String msg = ret > 0 ? "删除成功" : "删除失败";
        redirectAttributes.addFlashAttribute("msg", msg);
        return new ModelAndView("redirect:/word/list");
    }

    WordConvert wordConvert = new WordConvert();


    @RequestMapping("/mword/{w}")
    public ModelAndView mword(@PathVariable("w") String w) {
        Word word = new Word();
        word.setWord(w);
        List<Word> wordList = wordService.searchWords(word);

        if (CollectionUtils.isEmpty(wordList)) {
            throw new SweetBusinessException("暂无该单词:" + w);
        }
        WordVO wordVO = wordConvert.toVO(wordList.get(0));
        return new ModelAndView("/word/mword", "word", wordVO);
    }

}
