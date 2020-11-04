package com.turing.controller;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Material;
import com.turing.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资信息控制层
 */
@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    /**
     * 物资分页查询
     * @param cusPage 当前页数
     * @param pageSize 每页显示的条数
     * @return
     */
    @RequestMapping("/findMaterial")
    @ResponseBody
    public EasyUIDataGrid findMaterial(@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){

        return materialService.findMaterial(cusPage,pageSize);
    }


    /**
     * 物资录入
     * @param ids
     * @return
     */
    @GetMapping("/findMaterialById/{ids}")
    public ModelAndView findMaterialById(@PathVariable("ids")String ids){
        ModelAndView mv=new ModelAndView();
        List<Material> list=new ArrayList<>();
        String [] ids2= ids.split(",");
        for(int i=0;i<ids2.length;i++){
            int id=Integer.parseInt(ids2[i]);
            Material material = materialService.findMaterialById(id);
            list.add(material);
        }
        //System.out.println(list.size());
       mv.addObject("list",list);
        mv.setViewName("Order_newform");
       return mv;
    }
}
