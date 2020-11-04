package com.turing.dao;

import com.turing.entity.SysLogs;
import com.turing.entity.SysLogsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogsMapper {
    long countByExample(SysLogsExample example);

    int deleteByExample(SysLogsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysLogs record);

    int insertSelective(SysLogs record);

    List<SysLogs> selectByExample(SysLogsExample example);

    SysLogs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysLogs record, @Param("example") SysLogsExample example);

    int updateByExample(@Param("record") SysLogs record, @Param("example") SysLogsExample example);

    int updateByPrimaryKeySelective(SysLogs record);

    int updateByPrimaryKey(SysLogs record);
}