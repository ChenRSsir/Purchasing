package com.turing.serviceImpl;

import com.turing.dao.SupplierMapper;
import com.turing.entity.Supplier;
import com.turing.entity.SupplierExample;
import com.turing.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 供应商业务类
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public Supplier findSupplierById(Integer sid) {
        return supplierMapper.selectByPrimaryKey(new Long(sid));
    }

    @Override
    public Supplier findSupplierByUserId(Integer userId) {
        SupplierExample example=new SupplierExample();
        example.createCriteria().andUserIdEqualTo(new Long(userId));
        List<Supplier> supplierList = supplierMapper.selectByExample(example);
        if(supplierList.size()>0){
            return supplierList.get(0);
        }
        return null;
    }
}
