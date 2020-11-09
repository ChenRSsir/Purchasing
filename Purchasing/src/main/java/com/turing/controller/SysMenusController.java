package com.turing.controller;

import com.turing.entity.*;
import com.turing.service.SysMenuRoleService;
import com.turing.service.SysMenusService;
import com.turing.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形菜单控制层
 */
@Controller
public class SysMenusController {

    //依赖注入
    @Autowired
    private SysMenusService sysMenusService;

    //用户角色
    @Autowired
    private SysUserRoleService sysUserRoleService;

    //角色菜单
    @Autowired
    private SysMenuRoleService sysMenuRoleService;

    @GetMapping("/getMenus")
    @ResponseBody
    public List<SysMenus> getMenus(HttpSession session){
        SysUserRole sysUserRole=null;
        if(session.getAttribute("emp")!=null){
            Employee emp =(Employee) session.getAttribute("emp");
            //获取角色
             sysUserRole = sysUserRoleService.findUserRoleByUserId(emp.getUserId().intValue());
        }else if(session.getAttribute("supplier")!=null){
            Supplier supplier=(Supplier)session.getAttribute("supplier");
            //获取角色
             sysUserRole = sysUserRoleService.findUserRoleByUserId(supplier.getUserId().intValue());
            //获取菜单id
        }
        //获取菜单id
        List<SysMenuRole> roleList = sysMenuRoleService.findMenuRoleByRid(sysUserRole.getRoleId().intValue());

       List<Integer> menuList=new ArrayList<>();

        for (SysMenuRole s:roleList) {
            System.out.println(s.getMenuId());
            menuList.add(s.getMenuId().intValue());
        }
        List<SysMenus> menus = sysMenusService.findMenus(menuList);
        //根
        SysMenus menu = sysMenusService.findMenuById(1);
        menus.add(menu);
        return menus;
    }
}
