package com.turing.service;

import com.turing.entity.IdMapping;

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


    int deleteIdMapping(Integer id);
}
