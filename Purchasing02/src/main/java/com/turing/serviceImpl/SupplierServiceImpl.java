package com.turing.serviceImpl;

import com.turing.dao.SupplierMapper;
import com.turing.entity.Supplier;
import com.turing.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
