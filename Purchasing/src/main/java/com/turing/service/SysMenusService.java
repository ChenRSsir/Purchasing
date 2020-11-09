package com.turing.service;

import com.turing.entity.SysMenus;

import java.util.List;

public interface SysMenusService {

    //树形菜单
    public List<SysMenus> findMenus(List<Integer> list);


    /**
     * 通过id查询
     * @param id
     * @return
     */
    public SysMenus findMenuById(Integer id);
}
