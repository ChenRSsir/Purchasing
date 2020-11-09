package com.turing.dao;

import com.turing.entity.Contract;
import com.turing.entity.ContractExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractMapper {
    long countByExample(ContractExample example);

    int deleteByExample(ContractExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);

    //查询最新一条数据
    Contract findForInsert();

    //合同集合
    List<Contract> findContractList(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);
    //数量
    int findContractListTotal();
}