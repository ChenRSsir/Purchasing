package com.turing.controller;

import com.turing.entity.*;
import com.turing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.*;

/**
 * 采购控制层
 */
@Controller
public class StockContorller {

    //采购依赖注入
    @Autowired
    private StockService stockService;

    //需求依赖注入
    @Autowired
    private OrdersService ordersService;

    //产品依赖注入
    @Autowired
    private MaterialService materialService;

    //商品供应商依赖注入
    @Autowired
    private SuppMaterialService suppMaterialService;

    //供应商依赖注入
    @Autowired
    private SupplierService supplierService;

    //编号对照依赖注入
    @Autowired
    private IdMappingService idMappingService;

    //拟定采购页面
    @RequestMapping("/protocolStock")
    public ModelAndView protocolStock(Integer oid) throws ParseException {
        ModelAndView mv=new ModelAndView("/bianzhicaigoujihua");
        //获取需求对象
        Orders order = ordersService.findOrderById(oid);
        //获取产品信息
        Material material = materialService.findMaterialByNum(order.getMaterialCode());
        //获取商品对应供应商
        List<SuppMaterial> suppByMid = suppMaterialService.findSuppByMid(material.getId().intValue());
        List<Supplier> supplierList=new ArrayList<>();
        for (SuppMaterial sm:suppByMid) {
            Supplier supplier = supplierService.findSupplierById(sm.getSupplierId().intValue());
            supplierList.add(supplier);
        }
        //获取采购编号
        String requirementPlanNumber = stockService.getRequirementPlanNumber();

        //需求信息
        mv.addObject("order",order);
        //供应商信息
        mv.addObject("supplierList",supplierList);
        //采购编码
        mv.addObject("requirementPlanNumber",requirementPlanNumber);
          return mv;
    }

    //创建采购计划
    @RequestMapping("/createStock")
    @ResponseBody
    public String createStock(Stock stock,Integer oid){

        //设置默认信息
        //编制人

        //采购类型
        stock.setStockType("C000-1");
        //编制时间
        stock.setStartDate(new Date());

        //添加
        stockService.addStock(stock);
        //通过oid获取编号对照表信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
        //添加编号对照
        idMapping.setStockId(new Long(stock.getId()));
        //修改状态
        idMapping.setStatus("C001-30");
        int rows = idMappingService.updOrderStatus(idMapping);
        if(rows>0){
            return "true";
        }
            return "false";
    }

    //查询采购集合
    @RequestMapping("/findStockList")
    @ResponseBody
    public EasyUIDataGrid findStockList(@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid stockList = stockService.findStockList(cusPage, pageSize);
        List<Stock> rows =(List<Stock>) stockList.getRows();
        for (Stock s: rows) {
            Map<String,String> map=new HashMap<>();
            map.put("stockName",s.getStockName());//采购计划名称
            map.put("stockType","制造中心采购公开求购");//采购类型
            map.put("stockStatus",s.getIdMapping().getStatus());//采购状态
            if(s.getSubmitDate()==null){
                map.put("submitDate"," ");//下达时间
            }else {
                map.put("submitDate", s.getSubmitDate().toString());//下达时间
            }
            if(s.getEnquire()!=null){
                map.put("enquireName", s.getEnquire().getEnquireName());//对应询价书
            }else {
                map.put("enquireName"," ");//对应询价书
            }
            map.put("sid",s.getId().toString());//对应采购id
            map.put("oid",s.getIdMapping().getOrderId().toString());//对应需求id

            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        //总页数
        easyUIDataGrid.setTotal(stockList.getTotal());
        //页面显示数据
        easyUIDataGrid.setRows(list);

        return easyUIDataGrid;
    }

}
