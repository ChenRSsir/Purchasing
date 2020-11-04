package com.turing.dao;

import com.turing.entity.SysRoles;
import com.turing.entity.SysRolesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolesMapper {
    long countByExample(SysRolesExample example);

    int deleteByExample(SysRolesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    List<SysRoles> selectByExample(SysRolesExample example);

    SysRoles selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysRoles record, @Param("example") SysRolesExample example);

    int updateByExample(@Param("record") SysRoles record, @Param("example") SysRolesExample example);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);
}