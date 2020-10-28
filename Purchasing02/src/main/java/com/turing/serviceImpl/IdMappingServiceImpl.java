package com.turing.serviceImpl;

import com.turing.dao.IdMappingMapper;
import com.turing.entity.IdMapping;
import com.turing.service.IdMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单对照表
 */
@Service
public class IdMappingServiceImpl implements IdMappingService {

    @Resource
    private IdMappingMapper idMappingMapper;

    @Override
    public int addIdMapping(IdMapping idMapping) {
        return idMappingMapper.insert(idMapping);
    }

    @Override
    public int updOrderStatus(IdMapping idMapping) {

        return idMappingMapper.updateByPrimaryKeySelective(idMapping);
    }

    @Override
    public int deleteIdMapping(Integer id) {
        return idMappingMapper.deleteByPrimaryKey(new Long(id));
    }
}
