package com.turing.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan("com.turing.dao")
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("login");
       registry.addViewController("/index").setViewName("index");
      registry.addViewController("/Order_newform").setViewName("Order_newform");//需求计划
      registry.addViewController("/bianzhicaigoujihua").setViewName("/bianzhicaigoujihua");//编制采购计划
      registry.addViewController("/xjfatz_xjfamx").setViewName("/xjfatz_xjfamx");//采购详情查看
      registry.addViewController("/Apply_list_do").setViewName("Apply_list_do");//采购审批查看
      registry.addViewController("/Apply_caiwushenpi").setViewName("Apply_caiwushenpi");//采购审批
      registry.addViewController("/Enquire_bianzhi").setViewName("Enquire_bianzhi");//编制询价书
      registry.addViewController("/Enquire_update").setViewName("Enquire_update");//修改询价书
      registry.addViewController("/tianjiabaojiashu").setViewName("tianjiabaojiashu");//添加报价书
      registry.addViewController("Apply_xjsmx").setViewName("Apply_xjsmx");//询价书明细
      registry.addViewController("Apply_bianji").setViewName("Apply_bianji");//编辑合同申请
      registry.addViewController("Apply_list_do22").setViewName("Apply_list_do22");//合同审批查看
      registry.addViewController("Apply_caiwuhetongshenpi").setViewName("Apply_caiwuhetongshenpi");//财务审批
      registry.addViewController("Apply_jihuashenpi").setViewName("Apply_jihuashenpi");//计划审批
      registry.addViewController("Apply_changzhangshenpi").setViewName("Apply_changzhangshenpi");//厂长审批
      registry.addViewController("bianzhihetong").setViewName("bianzhihetong");//编制合同
      registry.addViewController("contract_view").setViewName("contract_view");//合同归档详情
    }
}
