package com.turing.dao;

import com.turing.entity.Orders;
import com.turing.entity.OrdersExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersMapper {
    long countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    Orders selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    //获取最新一条需求信息
    Orders findRecentlyForInsert();
    //分页模糊查询
    List<Orders> getOrdersByEasy(@Param("materialCode") String materialCode,@Param("materialName") String materialName,@Param("status") String status,@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

    //总条数
    int getOrdersTotalCount(@Param("materialCode") String materialCode,@Param("materialName") String materialName,@Param("status") String status);
}