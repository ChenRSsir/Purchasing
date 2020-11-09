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
 * 报价书控制层
 */
@Controller
public class QuoteController {

//报价
@Autowired
private QuoteService quoteService;

//报价详情
@Autowired
private QuoteDetailService quoteDetailService;

//询价
@Autowired
private EnquireService enquireService;

//询价详情
@Autowired
private EnquireDetailService enquireDetailService;

//编号对照
@Autowired
private IdMappingService idMappingService;

@RequestMapping("/addQuoteAndDetail")
@ResponseBody
public String addQuoteAndDetail(HttpSession session, Quote quote, QuoteDetail quoteDetail, String p_bjExDate, String QsDate0, String JSDate0) throws ParseException {
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    int row=0;
     //获取询价详情
    EnquireDetail enquireDetail = enquireDetailService.findEnquireDetailByEid(quote.getEnquireId().intValue());
    //添加开始结束时间
    enquireDetail.setStartDate(sdf.parse(QsDate0));
    enquireDetail.setEndDate(sdf.parse(JSDate0));
    enquireDetailService.updateEnquireDetail(enquireDetail);

    //添加报价信息

    quote.setQuoteNum(quoteService.getQuotePlanNumber());//报价书编号
    //供应商信息,从登录信息中获取
    Supplier supplier=(Supplier)session.getAttribute("supplier");
    quote.setSupplierId(supplier.getId());//供应商序号
    quote.setQuoCompany(supplier.getCompany());//报价单位
    quote.setQuoAddress(supplier.getAddress());//报价单位地址
    quote.setQuoLinkman(supplier.getContact());//联系人
    quote.setQuoPhone(supplier.getPhone());//电话
    quote.setQuoFax(supplier.getFax());//传真
    quote.setQuoEmail(supplier.getEmail());//邮箱
    quote.setQueDate(new Date());//报价时间
    quote.setEndDate(sdf.parse(p_bjExDate));
    quote.setStatus("B001-1");//状态 结果未发

    //添加
    int i = quoteService.addQuote(quote);
    if(i>0){
        quoteDetail.setQuoteId(quote.getId());//报价书id
        quoteDetail.setStartDate(sdf.parse(QsDate0));//开始到货期
        quoteDetail.setEndDate(sdf.parse(JSDate0));//结束到货期
       quoteDetailService.addQuoteDetail(quoteDetail);

        IdMapping idMapping = idMappingService.findMdMappingByOid(quoteDetail.getOrderId().intValue());
        idMapping.setStatus("C001-100");//报价状态
        idMapping.setQuoteId(quote.getId());//报价id
        row=  idMappingService.updateIdMapping(idMapping);
    }
    if(row>0){
         return "true";
    }else {
         return "false";
    }
}


//报价揭示
@RequestMapping("/offerToReveal")
@ResponseBody
public EasyUIDataGrid offerToReveal(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    EasyUIDataGrid dataGrid=new EasyUIDataGrid();
    List<Map<String,String>> list=new ArrayList<>();
    EasyUIDataGrid easyUIDataGrid = quoteService.findQuote(cusPage, pageSize);
    List<Quote> quoteList =(List<Quote>)easyUIDataGrid.getRows();
    for(Quote quote:quoteList){
        Map<String,String> map=new HashMap<>();
        Enquire enquire = enquireService.findEnquireById(quote.getEnquireId().intValue());
        map.put("enquireName",enquire.getEnquireName());//询价书名称
        map.put("endTime",sdf.format(quote.getEndDate()));//揭示时间
        map.put("MaxPrice",quote.getOverallPrice().toString());//最高报价
        map.put("MinPrice",quote.getOverallPrice().toString());//最低报价
        map.put("eid",quote.getEnquireId().toString());//询价id
        map.put("qid",quote.getId().toString());//报价id
        list.add(map);
    }
      dataGrid.setRows(list);
      dataGrid.setTotal(easyUIDataGrid.getTotal());
     return dataGrid;
}

//合同申请编制信息查看
  @RequestMapping("/selQuoteDetail")
  public ModelAndView selQuoteDetail(Integer qid){
    ModelAndView mv=new ModelAndView("Apply_bianji");
     //获取报价信息
      Quote quote = quoteService.findQuoteById(qid);
      //报价详情
      QuoteDetail quoteDetail = quoteDetailService.findDetailByqID(qid);
      //询价信息
      Enquire enquire = enquireService.findEnquireById(quote.getEnquireId().intValue());
      mv.addObject("quote",quote);
      mv.addObject("quoteDetail",quoteDetail);
      mv.addObject("enquire",enquire);
      return  mv;
  }

}
