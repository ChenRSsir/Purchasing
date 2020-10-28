package com.turing.controller;

import com.turing.entity.SysMenus;
import com.turing.service.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 树形菜单控制层
 */
@Controller
public class SysMenusController {

    //依赖注入
    @Autowired
    private SysMenusService sysMenusService;

    @GetMapping("/getMenus")
    @ResponseBody
    public List<SysMenus> getMenus(){
        List<SysMenus> menus = sysMenusService.findMenus(1);
        return menus;
    }
}
