package com.turing.serviceImpl;


import com.turing.dao.SysMenuRoleMapper;
import com.turing.entity.SysMenuRole;
import com.turing.entity.SysMenuRoleExample;
import com.turing.service.SysMenuRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色菜单业务类
 */
@Service
public class SysMenuRoleServiceImpl implements SysMenuRoleService {

    @Resource
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public List<SysMenuRole> findMenuRoleByRid(Integer rid) {
        SysMenuRoleExample example=new SysMenuRoleExample();
        example.createCriteria().andRoleIdEqualTo(new Long(rid));

        return sysMenuRoleMapper.selectByExample(example);
    }
}
