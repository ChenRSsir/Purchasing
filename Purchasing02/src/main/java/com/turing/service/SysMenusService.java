package com.turing.service;

import com.turing.entity.SysMenus;

import java.util.List;

public interface SysMenusService {

    //树形菜单
    public List<SysMenus> findMenus(Integer id);
}
