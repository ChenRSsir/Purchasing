package com.turing.service;

import com.turing.entity.SuppMaterial;

import java.util.List;

public interface SuppMaterialService {

    /**
     * 通过商品i获取供应商id
     * @param mid
     * @return
     */
    public List<SuppMaterial> findSuppByMid(Integer mid);

}
