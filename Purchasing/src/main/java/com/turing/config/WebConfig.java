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
    }
}
