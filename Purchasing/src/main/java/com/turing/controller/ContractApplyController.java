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
 * 合同申请控制层
 */
@Controller
public class ContractApplyController {

    //合同申请
    @Autowired
    private ContractApplyService contractApplyService;

    //需求
    @Autowired
    private OrdersService ordersService;

    //采购依赖注入
    @Autowired
    private StockService stockService;

    //询价依赖
    @Autowired
    private EnquireService enquireService;

    //报价
    @Autowired
    private QuoteService quoteService;

    //报价详情
    @Autowired
    private QuoteDetailService quoteDetailService;

    //编号对照
    @Autowired
    private IdMappingService idMappingService;

    //合同业务
    @Autowired
    private ContractService contractService;

    @RequestMapping("/contractPreparationApplication")
    @ResponseBody
    public String contractPreparationApplication(HttpSession session, ContractApply c, Integer oid) throws ParseException {
        int row=0;
        //添加合同申请信息
       c.setContAppNum(contractApplyService.getContractApplyNumber());
         //获取编制人
        Employee employee=(Employee)session.getAttribute("emp");
        c.setAuthorId(employee.getId().toString());//编制人序号
        c.setAuthor(employee.getEmpName());//编制人
        int i = contractApplyService.addContractApply(c);
        if(i>0){
            //获取编号对照信息
            IdMapping idMapping = idMappingService.findMdMappingByOid(oid);
            idMapping.setContAppId(c.getId());//合同申请id
            idMapping.setStatus("C001-120");//需要获取id
            row = idMappingService.updateIdMapping(idMapping);
        }
        if(row>0){
            return "true";
        }else {
            return "false";
        }
    }
    //财务审批列表
    @RequestMapping("/financialApprovalList")
    @ResponseBody
    public EasyUIDataGrid financialApprovalList(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String,String>> mapList=new ArrayList<>();
        EasyUIDataGrid idMapDateGrid = idMappingService.findContractApplyByUnNull(cusPage,pageSize);
        List<IdMapping> idMappings =(List<IdMapping>) idMapDateGrid.getRows();
        for(IdMapping m :idMappings){
            Map<String,String> map=new HashMap<>();
            Stock stock = stockService.findStockById(m.getStockId().intValue());
            map.put("stockName",stock.getStockName());//采购名称
            map.put("status",m.getStatus());//合同进度
            Quote quote = quoteService.findQuoteById(m.getQuoteId().intValue());
            map.put("endTime",sdf.format(quote.getEndDate()));//揭示时间
            map.put("overallPrice",quote.getOverallPrice().toString());//报价总金额
            Enquire enquire = enquireService.findEnquireById(m.getEnquireId().intValue());
            map.put("enquireName",enquire.getEnquireName());//询价书名称
            map.put("imId",m.getId().toString());//编号对照id
            map.put("qid",quote.getId().toString());//报价id
            mapList.add(map);
        }
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(mapList);
        easyUIDataGrid.setTotal(idMapDateGrid.getTotal());
        return easyUIDataGrid;
    }


    //审批页面显示及详情
    @RequestMapping("/financialApprovalPage")
     public ModelAndView financialApprovalPage(Integer qid,Integer imId,Integer variable){
        ModelAndView mv=new ModelAndView();
        //获取报价信息
        Quote quote = quoteService.findQuoteById(qid);
        //报价详情
        QuoteDetail quoteDetail = quoteDetailService.findDetailByqID(qid);
        //询价信息
        Enquire enquire = enquireService.findEnquireById(quote.getEnquireId().intValue());
        //需求信息
        Orders order = ordersService.findOrderById(quoteDetail.getOrderId().intValue());
        //对照信息
        IdMapping idMapping = idMappingService.findMdMappingByOid(order.getId().intValue());
        //合同信息
        ContractApply contractApply = contractApplyService.findContractApplyByIDI(idMapping.getContAppId().intValue());
        mv.addObject("quote",quote);
        mv.addObject("quoteDetail",quoteDetail);
        mv.addObject("enquire",enquire);
        mv.addObject("order",order);
        mv.addObject("idMapping",idMapping);
        mv.addObject("contractApply",contractApply);
        //变量判断，跳转页面
        if(variable==1){
            mv.setViewName("Apply_list_do22");//信息查看
        }else if(variable==2){
            mv.setViewName("Apply_caiwuhetongshenpi");//财务审批
        }else if(variable==3){
            mv.setViewName("Apply_jihuashenpi");//计划审批
        }else if(variable==4){
            mv.setViewName("Apply_changzhangshenpi");//厂长审批
        }
        return mv;
    }

    //财务部长审批
    @RequestMapping("/financeApprove")
    @ResponseBody
    public String financeApprove(ContractApply c,Integer imId,String plandate,Integer p_agree) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int row=0;
        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        c.setPlanDate(sdf.parse(plandate));
        //判断是否同意
        if(p_agree==1){
            //同意
            c.setPlanAgree("S002-1");
            idMapping.setStatus("C001-130");
        }else{
            //不同意
            c.setPlanAgree("S002-0");
            idMapping.setStatus("C001-131");
        }
        int i = contractApplyService.updateContractApply(c);
        if(i>0){
           row= idMappingService.updateIdMapping(idMapping);
        }

        if(row>0){
            return "true";
        }else{
            return "false";
        }
    }


    //计划审批
    @RequestMapping("/planApprove")
    @ResponseBody
    public String planApprove(ContractApply c,Integer imId,String leaddate,Integer p_agree) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int row=0;
        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        c.setLeadDate(sdf.parse(leaddate));
        //判断是否同意
        if(p_agree==1){
            //同意
            c.setLeadAgree("S002-1");
            idMapping.setStatus("C001-140");
        }else{
            //不同意
            c.setLeadAgree("S002-0");
            idMapping.setStatus("C001-141");
        }
        int i = contractApplyService.updateContractApply(c);
        if(i>0){
            row= idMappingService.updateIdMapping(idMapping);
        }

        if(row>0){
            return "true";
        }else{
            return "false";
        }
    }


    //厂长审批
    @RequestMapping("/factoryManagerApprove")
    @ResponseBody
    public String factoryManagerApprove(Integer imId,Integer p_agree){
        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        //判断是否同意
        if(p_agree==1){
            //同意
            idMapping.setStatus("C001-150");
        }else{
            //不同意
            idMapping.setStatus("C001-151");
        }
        int row= idMappingService.updateIdMapping(idMapping);

        if(row>0){
            return "true";
        }else{
            return "false";
        }
    }


    //待编制合同
    @RequestMapping("/toPrepareTheContract")
    @ResponseBody
    public EasyUIDataGrid toPrepareTheContract(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid easyUIDataGrid = contractApplyService.findContractApplyList(cusPage, pageSize);
        List<ContractApply> contractApplyList = (List<ContractApply>) easyUIDataGrid.getRows();
        for (ContractApply c:contractApplyList){
         Map<String,String> map=new HashMap<>();
            map.put("contAppNum",c.getContAppNum());//合同申请编码
            map.put("leadDate",sdf.format(c.getLeadDate()));//时间
            map.put("allSumPrice",c.getAllSumPrice().toString());//总金额
            map.put("everconfirmed","是");//是否确认
            map.put("Whetherthearchive","是");//是否归档
            map.put("qid",c.getIdMapping().getQuoteId().toString());//报价id
            map.put("imId",c.getIdMapping().getId().toString());//对照id
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setRows(list);
        easyUIDataGrid1.setTotal(easyUIDataGrid.getTotal());
        return easyUIDataGrid1;

    }


    //编制合同列表
    @RequestMapping("/showContract")
    public ModelAndView showContract(Integer qid,Integer imId) throws ParseException {
      ModelAndView mv=new ModelAndView("bianzhihetong");
        //获取报价信息
        Quote quote = quoteService.findQuoteById(qid);
        //报价详情
        QuoteDetail quoteDetail = quoteDetailService.findDetailByqID(qid);
        //合同编号
        String number = contractService.getContractNumber();
        mv.addObject("quote",quote);
        mv.addObject("quoteDetail",quoteDetail);
        mv.addObject("number",number);
        mv.addObject("imId",imId);
        return mv;
    }

}
