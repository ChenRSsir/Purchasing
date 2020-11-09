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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 询价控制层
 */
@Controller
public class EnquireController {
    //采购依赖注入
    @Autowired
    private StockService stockService;

    //需求依赖注入
    @Autowired
    private OrdersService ordersService;

    //编号对照依赖注入
    @Autowired
    private IdMappingService idMappingService;

    //询价依赖
    @Autowired
    private EnquireService enquireService;

    //询价详情
    @Autowired
    private EnquireDetailService enquireDetailService;

    /**
     * 编制询价书
     * @param enquire 询价书对象
     * @param oid 需求id
     * @param time1 开始时间
     * @param time2 结束时间
     * @param i 变量 判断保存还是发布
     * @return
     * @throws ParseException
     */
    @RequestMapping("/prepareInquiry")
    @ResponseBody
    public String prepareInquiry(Enquire enquire,Integer oid,String time1,String time2,Integer i) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //通过需求id获取信息
           //需求信息
           Orders order = ordersService.findOrderById(oid);
           //编号对着信息
           IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
           //获取采购信息
           Stock stock = stockService.findStockById(idMapping.getStockId().intValue());
          //修改采购信息
           stock.setStartDate(sdf.parse(time1));
           stock.setEndDate(sdf.parse(time2));
           stockService.updateStock(stock);

           //添加询价书信息
           enquire.setEndDate(sdf.parse(time2));
        int i1 = enquireService.addEnquire(enquire);
        if(i1>0){
            //添加询价详情
            EnquireDetail detail=new EnquireDetail();
            //询价id
            detail.setEnquireId(new Long(enquire.getId()));
            //需求id
            detail.setOrderId(new Long(oid));
            //物资编码
            detail.setMaterialCode(order.getMaterialCode());
            //物资名称
            detail.setMaterialName(order.getMaterialName());
            //数量
            detail.setAmount(order.getAmount());
            //单位
            detail.setMeasureUnit(order.getMeasureUnit());
            //备注
            detail.setRemark(enquire.getRemark());
            //添加详情
            enquireDetailService.addEnquireDetail(detail);
            if(i==1){
                //保存
                idMapping.setStatus("C001-70");
            }else{
                //发布
                idMapping.setStatus("C001-80");
            }
            //询价书id
             idMapping.setEnquireId(enquire.getId());
             idMappingService.updateIdMapping(idMapping);
        }
            return "success";
    }


    //询价书列表
    @RequestMapping("/InquiryList")
    @ResponseBody
    public EasyUIDataGrid InquiryList(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        EasyUIDataGrid easyUIDataGrid1 = enquireService.getEnquireList(cusPage, pageSize);
        List<Enquire> enquireList =(List<Enquire>)easyUIDataGrid1.getRows();
        for(Enquire enquire:enquireList){
            Map<String,String> map=new HashMap<>();
            map.put("enquireName",enquire.getEnquireName());//询价书名称
            //获取询价开始结束时间
            Long stockId = enquire.getIdMapping().getStockId();
            Stock stock = stockService.findStockById(stockId.intValue());
            map.put("startDate",sdf.format(stock.getStartDate()));//开始时间
            map.put("endDate", sdf.format(stock.getEndDate()));//截至时间
            map.put("status",enquire.getIdMapping().getStatus());//状态
            map.put("stockType","采购中心采购公开采购");//采购类型
            map.put("eid",enquire.getId().toString());//询价书id
            map.put("imId",enquire.getIdMapping().getId().toString());//状态id
            list.add(map);
        }
        easyUIDataGrid.setRows(list);
        easyUIDataGrid.setTotal(easyUIDataGrid1.getTotal());
        return easyUIDataGrid;
    }

   //修改询价书查询
    @RequestMapping("/findEnquireById")
   public ModelAndView findEnquireById(Integer eid,Integer imId){
        ModelAndView mv=new ModelAndView("Enquire_update");
        //获取信息 询价书
        Enquire enquire = enquireService.findEnquireById(eid);
        //询价详情
        EnquireDetail detail = enquireDetailService.findEnquireDetailByEid(eid);
        //状态信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(detail.getOrderId().intValue());
        //需求信息
        Orders order = ordersService.findOrderById(idMapping.getOrderId().intValue());
        mv.addObject("enquire",enquire);
        mv.addObject("order",order);
        mv.addObject("detail",detail);
        return mv;
   }


   @RequestMapping("/updateEnquire")
   @ResponseBody
   public String updateEnquire(Enquire enquire,Integer oid,Integer edId,String time1,String time2,Integer i) throws ParseException {

       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       //通过需求id获取信息
       //需求信息
       Orders order = ordersService.findOrderById(oid);
       //编号对着信息
       IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
       //获取采购信息
       Stock stock = stockService.findStockById(idMapping.getStockId().intValue());
       //修改采购信息
       stock.setStartDate(sdf.parse(time1));
       stock.setEndDate(sdf.parse(time2));
       stockService.updateStock(stock);

       //修改询价书信息
       enquire.setEndDate(sdf.parse(time2));
       int i1 = enquireService.updateEnquire(enquire);
       if(i1>0){
           //修改询价详情
           EnquireDetail detail=new EnquireDetail();
           //询价id
           detail.setId(new Long(edId));
           //备注
           detail.setRemark(enquire.getRemark());
           //修改详情
           enquireDetailService.updateEnquireDetail(detail);
           if(i==1){
               //保存
               idMapping.setStatus("C001-70");
           }else{
               //发布
               idMapping.setStatus("C001-80");
           }
           idMappingService.updateIdMapping(idMapping);
       }
       return "success";
   }


   @RequestMapping("/deleteEnquire")
   @ResponseBody
   public String deleteEnquire(Integer eid,Integer imId){
        int rows=0;
       int i = idMappingService.deleteIdMapping(imId);
       if(i>0){
           //获取详情
           EnquireDetail detail = enquireDetailService.findEnquireDetailByEid(eid);

           int i1 = enquireDetailService.delEnquireDetail(detail.getId().intValue());
           if(i1>0){
               rows = enquireService.delEnquire(eid);

               }
           }
       if(rows>0){
           return "true";
       }
           return "false";
   }


   //询价书报价显示
    @RequestMapping("/quoteForinquiry")
    @ResponseBody
   public EasyUIDataGrid quoteForinquiry(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //过时的询价信息
        List<Enquire> list = enquireService.updateEnquireStatus();
        for (Enquire enquire:
             list) {
            IdMapping idMapping=new IdMapping();
            idMapping.setId(enquire.getIdMapping().getId());
            idMapping.setStatus("C001-90");
            idMappingService.updateIdMapping(idMapping);
        }
        //待报价询价书
        List<Map<String,String>> list1=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid1 = enquireService.findForEnquire(cusPage, pageSize);
        List<Enquire> enquireList =(List<Enquire>) easyUIDataGrid1.getRows();
        for (Enquire e:enquireList){
            Map<String,String> map=new HashMap<>();
            map.put("enquireName",e.getEnquireName());//询价名称
            map.put("company",e.getCompany());//单位
            map.put("endDate",sdf.format(e.getEndDate()));//报价截止时间
            map.put("stockType","公开采购");//采购类型
            map.put("status",e.getIdMapping().getStatus());//状态
            map.put("imId",e.getIdMapping().getId().toString());//状态id
            map.put("eid",e.getId().toString());//询价id
            list1.add(map);
        }
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(list1);
        easyUIDataGrid.setTotal(easyUIDataGrid1.getTotal());

        return easyUIDataGrid;
   }

   //报价显示
    @RequestMapping("/quotationAccordingTo")
    @ResponseBody
    public ModelAndView quotationAccordingTo(Integer eid, Integer imId){
        ModelAndView mv=new ModelAndView("tianjiabaojiashu");
        Enquire enquire = enquireService.findEnquireById(eid);//询价书
        EnquireDetail detail = enquireDetailService.findEnquireDetailByEid(eid);//询价详情
        IdMapping idMapping = idMappingService.findMdMappingByOid(detail.getOrderId().intValue());//状态信息
        Orders order = ordersService.findOrderById(detail.getOrderId().intValue());//需求信息
        mv.addObject("enquire",enquire);
        mv.addObject("detail",detail);
        mv.addObject("idMapping",idMapping);
        mv.addObject("order",order);
        return mv;
    }


    //询价书明细
    @RequestMapping("/detailsOfInquiry")
    public ModelAndView detailsOfInquiry(Integer eid){
       ModelAndView mv=new ModelAndView("Apply_xjsmx");
       //询价
        Enquire enquire = enquireService.findEnquireById(eid);
        //详情
        EnquireDetail detail = enquireDetailService.findEnquireDetailByEid(eid);
        //需求信息
        Orders order = ordersService.findOrderById(detail.getOrderId().intValue());
        mv.addObject("enquire",enquire);
        mv.addObject("detail",detail);
        mv.addObject("order",order);
        return mv;
    }
}
