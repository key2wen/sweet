package com.wenwen.sweet.dao.mapper;

import com.wenwen.sweet.model.MwordAffix;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MwordAffixMapper {

    int insertMwordAffix(MwordAffix mwordAffix);

    int deleteMwordAffixById(int mwordAffixId);

    int updateMwordAffix(MwordAffix mwordAffix);

    MwordAffix getMwordAffix(int mwordAffixId);

    List<MwordAffix> selectMwordAffixs(@Param("offset") int offset, @Param("size") int size);

    int countMwordAffixs();

    List<MwordAffix> searchMwordAffixs(MwordAffix word);

}
