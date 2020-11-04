package com.turing.service;

import com.turing.entity.SysUsers;

public interface SysUsersService {

    /**
     * 通过名称查询用户
     * @param username
     * @return
     */
    public SysUsers findUserByName(String username);
}
