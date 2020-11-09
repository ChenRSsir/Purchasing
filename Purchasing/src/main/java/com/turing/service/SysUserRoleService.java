package com.turing.service;

import com.turing.entity.SysUserRole;

public interface SysUserRoleService {

    /**
     * 通过用户id判断是员工还是供应商
     * @param uid
     * @return
     */
    public SysUserRole findUserRoleByUserId(Integer uid);
}
