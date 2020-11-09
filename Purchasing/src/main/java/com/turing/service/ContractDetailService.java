package com.turing.service;

import com.turing.entity.ContractDetail;

public interface ContractDetailService {

    /**
     * 添加
     * @param contractDetail
     * @return
     */
    public int addContractDetail(ContractDetail contractDetail);


    /**
     * 通过合同id查询详情
     * @param cid
     * @return
     */
    public ContractDetail findDetailByCid(Integer cid);
}
