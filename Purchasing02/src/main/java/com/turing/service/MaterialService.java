package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Material;

public interface MaterialService {

    /**
     * 物资分页查询
     * @param cusPage 当前页数
     * @param pageSize 每页显示条数
     * @return
     */
    public EasyUIDataGrid findMaterial(Integer cusPage, Integer pageSize);

    /**
     * 通过id查询物资
     * @param id
     * @return
     */
    public Material findMaterialById(Integer id);

}
