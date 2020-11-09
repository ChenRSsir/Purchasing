package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.IdMapping;

import java.util.List;

public interface IdMappingService {

    /**
     * 添加一条
     * @param idMapping
     * @return
     */
    int addIdMapping(IdMapping idMapping);


    /**
     * 修改信息状态
     * @param idMapping
     * @return
     */
    int updOrderStatus(IdMapping idMapping);


    /**
     * 通过id删除编号对照
     * @param id
     * @return
     */
    int deleteIdMapping(Integer id);

    /**
     * 通过需求id获取编号对照对象
     * @param oid
     * @return
     */
    IdMapping findMdMappingByOid(Integer oid);

    /**
     * 修改
     * @param idMapping
     * @return
     */
    int updateIdMapping(IdMapping idMapping);


    /**
     * 查询合同不为空的信息
     * @return
     */
    EasyUIDataGrid findContractApplyByUnNull(Integer cusPage,Integer pageSize);
}
