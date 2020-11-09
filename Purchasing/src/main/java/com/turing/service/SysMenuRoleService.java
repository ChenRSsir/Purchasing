package com.turing.service;

import com.turing.entity.SysMenuRole;

import java.util.List;

public interface SysMenuRoleService {

    /**
     * 通过角色id获取菜单id
     * @param rid
     * @return
     */
    public List<SysMenuRole> findMenuRoleByRid(Integer rid);
}
