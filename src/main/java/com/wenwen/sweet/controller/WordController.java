package com.wenwen.sweet.controller;

import com.wenwen.sweet.commons.JsonWrapper;
import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
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

    @RequestMapping("/saveWord")
    public ModelAndView saveWord(@Valid Word word, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("/word/edit", "word", word)
                    .addObject("errMsg", result.getFieldError().getDefaultMessage());
        }
        int ret = wordService.saveWord(word);
        String msg = ret > 0 ? "保存成功" : "保存失败";
        redirectAttributes.addFlashAttribute("msg", msg);
        return new ModelAndView("redirect:/word/list/1");
    }

    @RequestMapping("/edit/{wordId}")
    public ModelAndView editWord(@PathVariable("wordId") int wordId) {
        Word word = wordService.getWord(wordId);
        return new ModelAndView("/word/edit", "word", word);
    }

    @RequestMapping("/list")
    public ModelAndView list(Integer pageNum, Integer pageSize) {
        PagedResult<Word> pagedWords = wordService.selectWords(pageNum, pageSize);
        return new ModelAndView("/word/list", "word", pagedWords);
    }

    @RequestMapping("/delete/{wordId}")
    public ModelAndView deleteWordById(@PathVariable("wordId") int wordId, RedirectAttributes redirectAttributes) {
        int ret = wordService.deleteWordById(wordId);
        String msg = ret > 0 ? "删除成功" : "删除失败";
        redirectAttributes.addFlashAttribute("msg", msg);
        return new ModelAndView("redirect:/word/list/1");
    }

}
