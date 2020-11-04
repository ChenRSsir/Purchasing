package com.turing.serviceImpl;

import com.turing.dao.EmployeeMapper;
import com.turing.entity.Employee;
import com.turing.entity.EmployeeExample;
import com.turing.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工业务类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee findEmpByUserId(Integer userId) {
        EmployeeExample example=new EmployeeExample();
        example.createCriteria().andUserIdEqualTo(new Long(userId));
        List<Employee> employeeList = employeeMapper.selectByExample(example);
        if(employeeList.size()>0){
            return employeeList.get(0);
        }
            return null;
    }
}
