package com.turing.serviceImpl;

import com.turing.dao.SysUsersMapper;
import com.turing.entity.SysUsers;
import com.turing.entity.SysUsersExample;
import com.turing.service.SysUsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户业务类
 */
@Service
public class SysUsersServiceImpl implements SysUsersService {

    @Resource
    private SysUsersMapper sysUsersMapper;

    @Override
    public SysUsers findUserByName(String username) {
        SysUsersExample example=new SysUsersExample();
        example.createCriteria().andLoginIdEqualTo(username);
        List<SysUsers> sysUsers = sysUsersMapper.selectByExample(example);
         if(sysUsers.size()>0){
             return sysUsers.get(0);
         }
             return null;
    }
}
