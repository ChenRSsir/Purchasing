package com.turing.dao;

import com.turing.entity.SysCodes;
import com.turing.entity.SysCodesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCodesMapper {
    long countByExample(SysCodesExample example);

    int deleteByExample(SysCodesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysCodes record);

    int insertSelective(SysCodes record);

    List<SysCodes> selectByExample(SysCodesExample example);

    SysCodes selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysCodes record, @Param("example") SysCodesExample example);

    int updateByExample(@Param("record") SysCodes record, @Param("example") SysCodesExample example);

    int updateByPrimaryKeySelective(SysCodes record);

    int updateByPrimaryKey(SysCodes record);
}