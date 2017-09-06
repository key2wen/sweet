package com.wenwen.sweet.service;

import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.model.MwordAffix;
import com.wenwen.sweet.util.SweetBusinessException;

import java.util.List;


public interface MwordAffixService {

    int saveMwordAffix(MwordAffix mwordAffix) throws SweetBusinessException;

    int deleteMwordAffixById(int mwordAffixId);

    int updateMwordAffix(MwordAffix mwordAffix);

    MwordAffix getMwordAffix(int mwordAffixId);

    PagedResult<MwordAffix> selectMwordAffixs(Integer pageNum, Integer pageSize);

    List<MwordAffix> searchMwordAffixs(MwordAffix mwordAffix);

}
