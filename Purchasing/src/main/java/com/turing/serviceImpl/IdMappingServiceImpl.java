package com.turing.serviceImpl;

import com.turing.dao.IdMappingMapper;
import com.turing.entity.IdMapping;
import com.turing.entity.IdMappingExample;
import com.turing.service.IdMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public IdMapping findMdMappingByOid(Integer oid) {
        IdMappingExample example=new IdMappingExample();

        example.createCriteria().andOrderIdEqualTo(new Long(oid));
        List<IdMapping> idMappings = idMappingMapper.selectByExample(example);
        if(idMappings.size()>0){
           return idMappings.get(0);
        }
        return null;
    }

    @Override
    public int updateIdMapping(IdMapping idMapping) {
        return idMappingMapper.updateByPrimaryKeySelective(idMapping);
    }
}
