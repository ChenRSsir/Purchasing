package com.turing.service;

import com.turing.entity.Contract;
import com.turing.entity.EasyUIDataGrid;

import java.text.ParseException;

public interface ContractService {

    /**
     * 添加
     * @param contract
     * @return
     */
    public int addContract(Contract contract);


    /**
     * 获取编码
     * @return
     */
    public String getContractNumber() throws ParseException;


    /**
     * 查询集合
     * @param cusPage
     * @param pageSize
     * @return
     */
    public EasyUIDataGrid findContractList(Integer cusPage,Integer pageSize);


    /**
     * 通过id查询
     * @param cid
     * @return
     */
    public Contract findContractById(Integer cid);
}
