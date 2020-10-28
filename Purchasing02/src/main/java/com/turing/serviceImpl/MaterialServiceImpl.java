package com.turing.serviceImpl;

import com.turing.dao.MaterialMapper;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Material;
import com.turing.service.MaterialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Resource
    private MaterialMapper materialMapper;

    @Override
    public EasyUIDataGrid findMaterial(Integer cusPage, Integer pageSize) {

        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        //查到的集合
       easyUIDataGrid.setRows(materialMapper.findMaterial((cusPage-1)*pageSize,pageSize));
       //总条数
       easyUIDataGrid.setTotal(materialMapper.totalCount());
        return easyUIDataGrid;
    }

    @Override
    public Material findMaterialById(Integer id) {
        return materialMapper.selectByPrimaryKey(new Long(id));
    }

}
