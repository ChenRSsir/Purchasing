package com.turing.serviceImpl;

import com.turing.dao.SysUserRoleMapper;
import com.turing.entity.SysUserRole;
import com.turing.entity.SysUserRoleExample;
import com.turing.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色业务类
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUserRole findUserRoleByUserId(Integer uid) {
        SysUserRoleExample example=new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(new Long(uid));
        List<SysUserRole> roleList = sysUserRoleMapper.selectByExample(example);
        if(roleList.size()>0){
            return roleList.get(0);
        }
        return null;
    }
}
