package com.turing.config;

import com.turing.dao.SysUsersMapper;
import com.turing.entity.SysUsers;
import com.turing.entity.SysUsersExample;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录认证
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUsersMapper sysUsersMapper;

    /**
     *授权要做的事
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权");
        return null;
    }

    /**
     * 认证要做的事
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证");
        //强转token
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) authenticationToken;
        //从数据库中获取用户数据
        SysUsersExample example=new SysUsersExample();
        //从前端过来的数据
        example.createCriteria().andLoginIdEqualTo(usernamePasswordToken.getUsername());
        List<SysUsers> usersList = sysUsersMapper.selectByExample(example);
        //判断
        if(usersList.size()<1){
            return null;//shiro底层就会抛出错误
        }else {
            SysUsers users = usersList.get(0);
            //如果用户存在再查密码
            //第一个参数： 用户名 、密码、shiro的名称 getName()
            SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(users,users.getPassword(),getName());
            //设置加密’盐‘
            info.setCredentialsSalt(ByteSource.Util.bytes(users.getLoginId()));
            return info;
        }

    }
}
