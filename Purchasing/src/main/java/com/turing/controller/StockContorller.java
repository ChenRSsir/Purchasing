package com.turing.controller;

import com.turing.entity.*;
import com.turing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    //询价依赖
    @Autowired
    private  EnquireService enquireService;

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
    public String createStock(Stock stock, Integer oid, HttpSession session){

        //设置默认信息
        //编制人
        Employee employee=(Employee) session.getAttribute("emp");
        stock.setAuthorId(employee.getId().toString());
        stock.setAuthor(employee.getEmpName());
        //采购类型
        stock.setStockType("C000-1");

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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
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
                map.put("submitDate", sdf.format(s.getSubmitDate()));//下达时间
            }
            if(s.getEnquire()!=null){
                map.put("enquireName", s.getEnquire().getEnquireName());//对应询价书
            }else {
                map.put("enquireName"," ");//对应询价书
            }
            map.put("sid",s.getId().toString());//对应采购id
            map.put("oid",s.getIdMapping().getOrderId().toString());//对应需求id
            map.put("imId",s.getIdMapping().getId().toString());//对照表id

            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        //总页数
        easyUIDataGrid.setTotal(stockList.getTotal());
        //页面显示数据
        easyUIDataGrid.setRows(list);

        return easyUIDataGrid;
    }


    //采购明细查询
    @RequestMapping("/findStockDetails")
    public ModelAndView findStockDetails(Integer sid,Integer oid,String stockStatus){
      ModelAndView mv=new ModelAndView("/xjfatz_xjfamx");
      //获取采购信息
        Stock stock = stockService.findStockById(sid);
        //获取需求信息
        Orders order = ordersService.findOrderById(oid);
        mv.addObject("stock",stock);
        mv.addObject("order",order);
        mv.addObject("stockStatus",stockStatus);

        return mv;
    }


    //报批
    @RequestMapping("/StockApproval")
    @ResponseBody
    public String StockApproval(Integer imId){
        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        idMapping.setStatus("C001-40");
        int rows = idMappingService.updOrderStatus(idMapping);
        if(rows>0){
            return "true";
        }
            return "false";
    }

    //采购申请一览
    @RequestMapping("/purchaseRequest")
    @ResponseBody
    public EasyUIDataGrid purchaseRequest(@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        String status="C001-40";
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid = stockService.purchaseRequest(status, cusPage, pageSize);
        List<Stock> rows =(List<Stock>)easyUIDataGrid.getRows();
        for(Stock stock:rows){
            Map<String,String> map=new HashMap<>();
            map.put("stockName",stock.getStockName());//采购计划名称
            map.put("stockType",stock.getStockType());//采购类型
            map.put("budget",stock.getBudget().toString());//预算金额
            map.put("status","待审");//状态
            map.put("remark",stock.getRemark());//备注
            map.put("sid",stock.getId().toString());//对应采购id
            map.put("oid",stock.getIdMapping().getOrderId().toString());//对应需求id
            map.put("imId",stock.getIdMapping().getId().toString());//对照表id
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setTotal(easyUIDataGrid.getTotal());
        easyUIDataGrid1.setRows(list);
        return easyUIDataGrid1;
    }

    /**
     * /采购审批查看
     * @param sid 采购ID
     * @param oid 需求id
     * @param imId 状态ID
     * @return
     */
    @RequestMapping("/applyList")
    public ModelAndView applyList(Integer sid,Integer oid,Integer imId){
        ModelAndView mv=new ModelAndView("Apply_list_do");
        //获取采购信息
        Stock stock = stockService.findStockById(sid);
        //获取需求信息
        Orders order = ordersService.findOrderById(oid);
        //获取状态信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
        mv.addObject("stock",stock);
        mv.addObject("order",order);
        mv.addObject("imId",imId);
        return mv;
    }

    //审批查询
    @RequestMapping("/applyCaiWuShenPi")
    public ModelAndView applyCaiWuShenPi(Integer sid,Integer oid,Integer imId,HttpSession session){
        ModelAndView mv=new ModelAndView("Apply_caiwushenpi");
        //获取采购信息
        Stock stock = stockService.findStockById(sid);
        //获取需求信息
        Orders order = ordersService.findOrderById(oid);
        //获取状态信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
        mv.addObject("stock",stock);
        mv.addObject("order",order);
        mv.addObject("idMapping",idMapping);
        return mv;
    }

    //审批
    @RequestMapping("/auditStock")
    @ResponseBody
    public String auditStock(Integer sid,Integer imId,String status,String leadOpinion,HttpSession session){
        String leadAgree="";//部长是否同意
        int rows=0;//判断依据

          if(leadOpinion==null){
              leadOpinion="";
          }
          if(status.equals("C001-50")){
              leadAgree="S002-1";
          }else{
              leadAgree="S002-0";
          }
          //获取审核人
          Employee employee=(Employee) session.getAttribute("emp");
          Stock stock=new Stock();
          //审核操作
          stock.setLeaderId(employee.getId().toString());//审核人id
          stock.setLeader(employee.getEmpName());//名称
          stock.setId(new Long(sid));//编号
          stock.setLeadAgree(leadAgree);//是否同意
          stock.setLeadOpinion(leadOpinion);//部长意见
          stock.setLeadDate(new Date());//审核时间
        int i = stockService.updateStock(stock);
        //改变状态
        if(i>0){
            IdMapping idMapping=new IdMapping();
            idMapping.setId(new Long(imId));
            idMapping.setStatus(status);
             rows = idMappingService.updateIdMapping(idMapping);
        }
        if(rows>0){
            return "true";
        }
            return "false";
    }

    //采购计划下达查询
    @RequestMapping("/selTransmitStock")
    @ResponseBody
    public EasyUIDataGrid selTransmitStock(@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        String status="C001-50";
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid = stockService.purchaseRequest(status, cusPage, pageSize);
        List<Stock> rows =(List<Stock>)easyUIDataGrid.getRows();
        for(Stock stock:rows){
            Map<String,String> map=new HashMap<>();
            map.put("stockName",stock.getStockName());//采购计划名称
            map.put("stockType","制造中心采购公开求购");//采购类型
            map.put("budget",stock.getBudget().toString());//预算金额
            map.put("status",stock.getIdMapping().getStatus());//状态
            map.put("remark",stock.getRemark());//备注
            map.put("sid",stock.getId().toString());//对应采购id
            map.put("oid",stock.getIdMapping().getOrderId().toString());//对应需求id
            map.put("imId",stock.getIdMapping().getId().toString());//对照表id
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setTotal(easyUIDataGrid.getTotal());
        easyUIDataGrid1.setRows(list);
        return easyUIDataGrid1;
    }


    //采购下达 imId 编号对照id
    @RequestMapping("/transmitStock")
    @ResponseBody
    public String transmitStock(Integer imId,Integer sid){
        //下达时间
        Stock stock=new Stock();
        stock.setId(new Long(sid));
        stock.setSubmitDate(new Date());
        stockService.updateStock(stock);

        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        idMapping.setStatus("C001-60");
        int i = idMappingService.updateIdMapping(idMapping);
        if(i>0){
            return "true";
        }
        return "false";
    }

    @RequestMapping("/unExamineStock")
    @ResponseBody
    public EasyUIDataGrid unExamineStock(String status,@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        String [] statues=new String[]{"C001-51","C001-52"};
        if(status==null || status==""){
            statues=new String[]{"C001-51","C001-52"};
        }else if(status.equals("C001-51")){
            statues=new String[]{status};
        }else if(status.equals("C001-52")){
            statues=new String[]{status};
        }
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid = stockService.unPurchaseRequest(statues, cusPage, pageSize);
        List<Stock> rows =(List<Stock>)easyUIDataGrid.getRows();
        for(Stock stock:rows){
            Map<String,String> map=new HashMap<>();
            map.put("stockName",stock.getStockName());//采购计划名称
            map.put("stockType","制造中心采购公开求购");//采购类型
            map.put("budget",stock.getBudget().toString());//预算金额
            map.put("status",stock.getIdMapping().getStatus());//状态
            map.put("leadOpinion",stock.getLeadOpinion());//备注
            map.put("leader",stock.getLeader());//审批人
            map.put("sid",stock.getId().toString());//对应采购id
            map.put("oid",stock.getIdMapping().getOrderId().toString());//对应需求id
            map.put("imId",stock.getIdMapping().getId().toString());//对照表id
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setTotal(easyUIDataGrid.getTotal());
        easyUIDataGrid1.setRows(list);
        return easyUIDataGrid1;
    }

    //删除采购信息
    @RequestMapping("/deleteStock")
    @ResponseBody
    public String deleteStock(Integer sid){
        int i = stockService.deleteStock(sid);
        if(i>0){
            return "true";
        }
        return "false";
    }

    //未询价采购计划查询
    @RequestMapping("/unInquiry")
    @ResponseBody
    public EasyUIDataGrid unInquiry(@RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String status="C001-60";
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid = stockService.purchaseRequest(status, cusPage, pageSize);
        List<Stock> rows =(List<Stock>)easyUIDataGrid.getRows();
        for(Stock stock:rows){
            Map<String,String> map=new HashMap<>();
            map.put("stockName",stock.getStockName());//采购计划名称
            map.put("stockType","制造中心采购公开求购");//采购类型
            map.put("budget",stock.getBudget().toString());//预算金额
            if(stock.getSubmitDate()==null){
                map.put("submitDate"," ");//下达时间
            }else {
                map.put("submitDate", sdf.format(stock.getSubmitDate()));//下达时间
            }
            map.put("remark",stock.getRemark());//备注
            map.put("sid",stock.getId().toString());//对应采购id
            map.put("oid",stock.getIdMapping().getOrderId().toString());//对应需求id
            map.put("imId",stock.getIdMapping().getId().toString());//对照表id
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setTotal(easyUIDataGrid.getTotal());
        easyUIDataGrid1.setRows(list);
        return easyUIDataGrid1;
    }

    //编制询价书预览
    @RequestMapping("/InquiryStock")
    public ModelAndView InquiryStock(Integer sid,Integer oid,HttpSession session) throws ParseException {
        ModelAndView mv=new ModelAndView("/Enquire_bianzhi");
        //获取采购信息
        Stock stock = stockService.findStockById(sid);
        //获取需求信息
        Orders order = ordersService.findOrderById(oid);
        //获取状态信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
        //获取员工信息
        Employee employee=(Employee)session.getAttribute("emp");
        //获取编码
        String number = enquireService.getEnquirePlanNumber();
        mv.addObject("number",number);
        mv.addObject("stock",stock);
        mv.addObject("order",order);
        mv.addObject("idMapping",idMapping);
        mv.addObject("employee",employee);
        return mv;
    }
}
