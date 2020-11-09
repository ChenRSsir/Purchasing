package com.turing.serviceImpl;

import com.turing.dao.OrdersMapper;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.IdMapping;
import com.turing.entity.Orders;
import com.turing.service.IdMappingService;
import com.turing.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    //需求映射
    @Resource
    private OrdersMapper ordersMapper;

    //订单对照业务
    @Autowired
    private IdMappingService idMappingService;

    @Override
    public int addOrders(Orders orders) {
        try {
            //需求编码
            String number = getRequirementPlanNumber();
            orders.setOrderNum(number);
            //计算小计
            //数量
            String amount = orders.getAmount();
            //单价
            BigDecimal price = orders.getUnitPrice();
            int count=Integer.parseInt(amount);
            BigDecimal sum=new BigDecimal(count*price.intValue());
            orders.setSumPrice(sum);
            //编制人



        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = ordersMapper.insert(orders);
        //编号对照
        IdMapping idMapping=new IdMapping();
        idMapping.setOrderId(orders.getId());
        idMapping.setStatus("C001-10");
        idMappingService.addIdMapping(idMapping);
        return i;
    }

    @Override
    public EasyUIDataGrid findOrdersByEasy(String materialCode, String materialName, String status, Integer cusPage, Integer pageSize) {
  EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        List<Orders> list = ordersMapper.getOrdersByEasy("%"+materialCode+"%", "%"+materialName+"%", status, (cusPage - 1) * pageSize, pageSize);
        int count = ordersMapper.getOrdersTotalCount("%"+materialCode+"%", "%"+materialName+"%", status);
        easyUIDataGrid.setTotal(count);
        easyUIDataGrid.setRows(list);
        return easyUIDataGrid;
    }

    @Override
    public Orders findOrderById(Integer id) {

        return ordersMapper.selectByPrimaryKey(new Long(id));
    }

    @Override
    public int updateOrderById(Orders orders) {

        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    @Override
    public int deleteOrderById(Integer id) {
        return ordersMapper.deleteByPrimaryKey(new Long(id));
    }

    /**
     * 获取需求计划编号
     * @return
     * @throws ParseException
     */
    private String getRequirementPlanNumber() throws ParseException {

        ///获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();


        //获取月份和日期
        String nowDAM = sdf3.format(date);
        Date mad = sdf3.parse(nowDAM);

        Date mad2 = sdf3.parse("01-01");

        //获取年份
        Integer year = cal.get(Calendar.YEAR);

        String date2 = year+"0101";
        //System.out.println(date2);
        //获取当前时间
        String nowDate = sdf1.format(date);

        //获取需求计划的最近更新的一列
        Orders recentlyOrders = ordersMapper.findRecentlyForInsert();
        //获取需求计划编号的日期
        String num = recentlyOrders.getOrderNum();
        String da = num.substring(3,11);
        //System.out.println("流水号日期"+da);
        if(mad.getTime() == mad2.getTime()&&!da.equals(date2)){
            return "100"+nowDate+00001;
        }else{
            //获取当前月份
            String m = String.valueOf(cal.get(Calendar.MONTH) + 1);
            if(cal.get(Calendar.MONTH) + 1<10){
                m=String.valueOf("0"+cal.get(Calendar.MONTH) + 1);
            }

            //获取当前日期
            String d =String.valueOf(cal.get(Calendar.DATE));
            if(cal.get(Calendar.DATE)<10){
                d =String.valueOf("0"+cal.get(Calendar.DATE));
            }
            //获取最后五位数
            num = "100"+year+m+d+num.substring(11);
            Long nums = Long.parseLong(num)+1;
           // System.out.println(num+":"+nums);
            return nums+"";
        }
    }
}
