package com.turing.service;

import com.turing.entity.ContractApply;
import com.turing.entity.EasyUIDataGrid;

import java.text.ParseException;
import java.util.List;

public interface ContractApplyService {

    /**
     * 添加合同申请
     *
     * @param contractApply
     * @return
     */
    int addContractApply(ContractApply contractApply);


    /**
     * 修改信息
     * @param contractApply
     * @return
     */
    int updateContractApply(ContractApply contractApply);

    /**
     * 获取编码
     * @return
     */
    public String getContractApplyNumber() throws ParseException;

    /**
     * 通过id获取信息
     * @param cid
     * @return
     */
    public ContractApply findContractApplyByIDI(Integer cid);


    /**
     * 待编制合同申请
     * @return
     */
    public EasyUIDataGrid findContractApplyList(Integer cusPage,Integer pageSize);
}
