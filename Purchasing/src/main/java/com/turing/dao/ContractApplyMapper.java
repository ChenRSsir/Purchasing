package com.turing.dao;

import com.turing.entity.ContractApply;
import com.turing.entity.ContractApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractApplyMapper {
    long countByExample(ContractApplyExample example);

    int deleteByExample(ContractApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ContractApply record);

    int insertSelective(ContractApply record);

    List<ContractApply> selectByExample(ContractApplyExample example);

    ContractApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ContractApply record, @Param("example") ContractApplyExample example);

    int updateByExample(@Param("record") ContractApply record, @Param("example") ContractApplyExample example);

    int updateByPrimaryKeySelective(ContractApply record);

    int updateByPrimaryKey(ContractApply record);

    //获取最新的一条数据
    ContractApply findForInsert();


    //待编制合同申请
    List<ContractApply> findContractApplys(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);
    //数量
    int findContractApplysTotal();
}