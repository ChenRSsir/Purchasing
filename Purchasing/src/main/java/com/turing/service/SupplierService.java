package com.turing.service;

import com.turing.entity.Supplier;

public interface SupplierService {

    /**
     * 通过id查询供应商信息
     * @param sid
     * @return
     */
    public Supplier findSupplierById(Integer sid);


    /**
     * 通过用户id查询供应商
     * @param userId
     * @return
     */
    public Supplier findSupplierByUserId(Integer userId);
}
