package com.turing.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {


    //1,配置ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
      ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
      //关联安全管理器
      shiroFilterFactoryBean.setSecurityManager(securityManager);
        /***
         * anon:不需要认证就可访问
         * authc:一定要认证才能访问
         */
        //配置过滤器链
        //需要拦截的资源
        Map<String,String> filterMap=new HashMap<>();
        //拦截的资源
        filterMap.put("/*","authc");
        //不拦截的资源 如：登录操作
        filterMap.put("/loginUser","anon");
        //拦截后跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
      return shiroFilterFactoryBean;
    }

    //2.配置安全管理器SecurityManager\
    @Bean
    public SecurityManager securityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //管理Realm
        defaultWebSecurityManager.setRealm(realm);
        return  defaultWebSecurityManager;
    }

    //3、需要配置Realm
    @Bean
    public Realm realm(HashedCredentialsMatcher matcher){
        UserRealm realm=new UserRealm();
        realm.setCredentialsMatcher(matcher);
      return realm;
    }

    //4,配置散列加密器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        //创建加密器
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(3);
        return matcher;
    }
}
