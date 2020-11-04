package com.turing.service;

import com.turing.entity.Employee;

public interface EmployeeService {

    /**
     * 通过用户id查询员工信息
     * @param userId
     * @return
     */
    public Employee findEmpByUserId(Integer userId);
}
