package com.turing.serviceImpl;

import com.turing.dao.SuppMaterialMapper;
import com.turing.entity.SuppMaterial;
import com.turing.entity.SuppMaterialExample;
import com.turing.service.SuppMaterialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品供应商业务类
 */
@Service
public class SuppMaterialServiceImpl implements SuppMaterialService {

    @Resource
    private SuppMaterialMapper suppMaterialMapper;

    @Override
    public List<SuppMaterial> findSuppByMid(Integer mid) {
        SuppMaterialExample example=new SuppMaterialExample();
        example.createCriteria().andMaterialIdEqualTo(new Long(mid));
        return suppMaterialMapper.selectByExample(example);
    }
}
