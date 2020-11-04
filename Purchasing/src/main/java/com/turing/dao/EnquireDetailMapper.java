package com.turing.dao;

import com.turing.entity.EnquireDetail;
import com.turing.entity.EnquireDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnquireDetailMapper {
    long countByExample(EnquireDetailExample example);

    int deleteByExample(EnquireDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EnquireDetail record);

    int insertSelective(EnquireDetail record);

    List<EnquireDetail> selectByExample(EnquireDetailExample example);

    EnquireDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EnquireDetail record, @Param("example") EnquireDetailExample example);

    int updateByExample(@Param("record") EnquireDetail record, @Param("example") EnquireDetailExample example);

    int updateByPrimaryKeySelective(EnquireDetail record);

    int updateByPrimaryKey(EnquireDetail record);
}