package com.syc.dao;

import com.syc.model.TempTab;

public interface TempTabMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TempTab record);

    int insertSelective(TempTab record);

    TempTab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TempTab record);

    int updateByPrimaryKey(TempTab record);
}