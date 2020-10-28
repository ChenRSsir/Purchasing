package com.turing.controller;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.IdMapping;
import com.turing.entity.Orders;
import com.turing.service.IdMappingService;
import com.turing.service.OrdersService;
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
 * 需求控制层
 */
@Controller
public class OrdersController {

    //需求业务
    @Autowired
    private OrdersService ordersService;

    //需求状态
    @Autowired
    private IdMappingService idMappingService;

    //保存需求
    @RequestMapping("/addOrders")
    @ResponseBody
    public String addOrders(Orders orders,String startDate01, HttpSession session) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<Orders> ordersList=null;
        //因为有多条数据，判断session是否为空
        if(session.getAttribute("ordersList")==null){
            ordersList=new ArrayList<>();
            orders.setStartDate(sdf.parse(startDate01));
            ordersList.add(orders);
        }else{
           ordersList=(List<Orders>) session.getAttribute("ordersList");
           orders.setStartDate(sdf.parse(startDate01));
           ordersList.add(orders);
        }
        session.setAttribute("ordersList",ordersList);
         if(ordersList.size()>0){
             return "true";
         }
             return "false";
    }

    //重新选择物资
    @RequestMapping("/roback")
    public ModelAndView roback(HttpSession session){
          ModelAndView mv=new ModelAndView("redirect:planman/pclass_select.html");
          //清空session
          session.removeAttribute("ordersList");
          return mv;
    }

    //全部需求录入后跳转需求查询页
    @RequestMapping("/saveOrders")
    public ModelAndView saveOrders(HttpSession session){
        ModelAndView mv=new ModelAndView("redirect:/planman/Order_ytb_list.html");
        if(session.getAttribute("ordersList")!=null){
            //获取session中的对象
            List<Orders> ordersList =(List<Orders>)session.getAttribute("ordersList");
            //循环添加需求信息
            for (Orders order:ordersList){
                ordersService.addOrders(order);
            }
        }
        //清空session
        session.removeAttribute("ordersList");
        return mv;
    }

    //需求信息查询
    @RequestMapping("/findOrders")
    @ResponseBody
    public EasyUIDataGrid findOrders(String materialCode, String materialName, String status, @RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
        //存储map
        List<Map<String,String>> list=new ArrayList<>();
       if(materialCode==null){
           materialCode="";
       }
       if(materialName==null){
           materialName="";
       }
       if(status=="0"){
           status=null;
       }
       //查询到的集合
        EasyUIDataGrid ordersByEasy = ordersService.findOrdersByEasy(materialCode, materialName, status, cusPage, pageSize);

       //取出处理
        List<Orders> orders =(List<Orders>) ordersByEasy.getRows();
        for(Orders o:orders){
            //存储需求信息
            Map<String,String> map=new HashMap<>();
            map.put("id",o.getId().toString());//需求id
            map.put("sid",o.getIdMapping().getId().toString());//状态id
           map.put("materialCode",o.getMaterialCode());//物资编码
           map.put("materialName",o.getMaterialName());//物资名称
           map.put("amount",o.getAmount());//数量
           map.put("pType","制造中心采购公开求购");
           map.put("status",o.getIdMapping().getStatus());//状态

            //将map存起来返回出去
            list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setTotal(ordersByEasy.getTotal());
        easyUIDataGrid.setRows(list);
        return  easyUIDataGrid;
    }

    //改变状态
    @RequestMapping("/updateOrdersStatus")
    @ResponseBody
    public ModelAndView updateOrdersStatus(String ids){
        ModelAndView mv=new ModelAndView("redirect:planman/Order_ytb_list.html");
        //截取
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            //强转
            int id=Integer.parseInt(split[i]);
            //修改状态
            IdMapping idMapping=new IdMapping();
            idMapping.setId(new Long(id));
            idMapping.setStatus("C001-20");
            idMappingService.updOrderStatus(idMapping);
        }
        return mv;
    }

    @RequestMapping("/findOrderById")
    @ResponseBody
    public Orders findOrderById(Integer oid){
        Orders order = ordersService.findOrderById(oid);
        return order;
    }


    @RequestMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(Orders orders,String sDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        orders.setStartDate(sdf.parse(sDate));
        int i = ordersService.updateOrderById(orders);
         if(i>0){
             return "true";
         }
        return "false";
    }


    //删除需求
    @RequestMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(Integer oid,Integer sid){
        int r=0;
        //删除需求状态信息
        int i = idMappingService.deleteIdMapping(sid);
        if(i>0){
            //删除需求信息
           r= ordersService.deleteOrderById(oid);
        }
        if(r>0){
            return "true";
        }
        return "false";
    }


    //未编采购计划的需求一览
    @RequestMapping("/findUnPurchaseOrders")
    @ResponseBody
    public EasyUIDataGrid findUnPurchaseOrders(String materialCode, String materialName, String status, @RequestParam(value = "page",defaultValue = "1") Integer cusPage,@RequestParam(value = "rows",defaultValue = "2") Integer pageSize){

        if(materialCode==null){
            materialCode="";
        }
        if(materialName==null){
            materialName="";
        }
        if(status==null){
            status="C001-20";
        }
        EasyUIDataGrid ordersByEasy = ordersService.findOrdersByEasy(materialCode, materialName, status, cusPage, pageSize);
        return  ordersByEasy;
    }

}
