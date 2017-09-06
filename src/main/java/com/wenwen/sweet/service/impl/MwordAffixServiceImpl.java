package com.wenwen.sweet.service.impl;

import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.dao.mapper.MwordAffixMapper;
import com.wenwen.sweet.model.MwordAffix;
import com.wenwen.sweet.service.MwordAffixService;
import com.wenwen.sweet.util.NumberUtils;
import com.wenwen.sweet.util.SweetBusinessException;
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
public class MwordAffixServiceImpl implements MwordAffixService {

    @Autowired
    private MwordAffixMapper mwordAffixMapper;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int saveMwordAffix(MwordAffix mwordAffix) throws SweetBusinessException {
        // 如果不带id,说明是第一次保存
        if (!NumberUtils.isPositive(mwordAffix.getId())) {

            return mwordAffixMapper.insertMwordAffix(mwordAffix);
        } else {
            //
            return mwordAffixMapper.updateMwordAffix(mwordAffix);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteMwordAffixById(int mwordAffixId) {
        return mwordAffixMapper.deleteMwordAffixById(mwordAffixId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updateMwordAffix(MwordAffix mwordAffix) {
        return mwordAffixMapper.updateMwordAffix(mwordAffix);
    }

    public MwordAffix getMwordAffix(int mwordAffixId) {
        return mwordAffixMapper.getMwordAffix(mwordAffixId);
    }

    public PagedResult<MwordAffix> selectMwordAffixs(Integer pageNum, Integer pageSize) {
        PagedResult<MwordAffix> pagedResult = new PagedResult<MwordAffix>(pageNum, pageSize);

        pagedResult.setList(mwordAffixMapper.selectMwordAffixs(pagedResult.getOffset(), pagedResult.getLimit()));
        pagedResult.setTotal(mwordAffixMapper.countMwordAffixs());
        return pagedResult;
    }

    public List<MwordAffix> searchMwordAffixs(MwordAffix mwordAffix) {
        Assert.notNull(mwordAffix, "词典前后缀对象不能为空");
        return mwordAffixMapper.searchMwordAffixs(mwordAffix);
    }
}
