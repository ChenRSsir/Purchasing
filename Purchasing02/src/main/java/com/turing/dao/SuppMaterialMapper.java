package com.turing.dao;

import com.turing.entity.SuppMaterial;
import com.turing.entity.SuppMaterialExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuppMaterialMapper {
    long countByExample(SuppMaterialExample example);

    int deleteByExample(SuppMaterialExample example);

    int insert(SuppMaterial record);

    int insertSelective(SuppMaterial record);

    List<SuppMaterial> selectByExample(SuppMaterialExample example);

    int updateByExampleSelective(@Param("record") SuppMaterial record, @Param("example") SuppMaterialExample example);

    int updateByExample(@Param("record") SuppMaterial record, @Param("example") SuppMaterialExample example);
}