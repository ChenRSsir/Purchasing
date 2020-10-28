package com.turing.serviceImpl;

import com.turing.dao.SysMenusMapper;
import com.turing.entity.Attributes;
import com.turing.entity.SysMenus;
import com.turing.entity.SysMenusExample;
import com.turing.service.SysMenusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMenusServiceImpl implements SysMenusService {

    @Resource
    private SysMenusMapper sysMenusMapper;

    @Override
    public List<SysMenus> findMenus(Integer id) {
        //根据id查询根节点
        SysMenusExample example=new SysMenusExample();
        example.createCriteria().andIdEqualTo(new Long(id));
        List<SysMenus> sysMenus = sysMenusMapper.selectByExample(example);
        return findChildMenus(sysMenus);
    }

    public List<SysMenus> findChildMenus(List<SysMenus> sysMenus){
        //循环遍历子节点
        for(SysMenus menus:sysMenus){
            SysMenusExample example=new SysMenusExample();
            example.createCriteria().andParentIdEqualTo(new Long(menus.getId()));
            List<SysMenus> list = sysMenusMapper.selectByExample(example);
            menus.setMenusList(list);
            //将url和图片路径放入
            Attributes attributes=new Attributes();
            attributes.setIcon(menus.getImageUrl());
            attributes.setUrl(menus.getLinkUrl());
            menus.setAttributes(attributes);
            //递归
            findChildMenus(list);
        }
        return sysMenus;
    }
}
