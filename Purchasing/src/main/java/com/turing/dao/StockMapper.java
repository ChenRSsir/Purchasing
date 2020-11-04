package com.turing.dao;

import com.turing.entity.Stock;
import com.turing.entity.StockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMapper {
    long countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    //获取最新的一条数据
    Stock findForInsert();

    //分页查询采购计划
    List<Stock> findStockEnquire(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

    //总数
    int findStockTotal();

    //通过状态查询采购信息
   List<Stock> findPurchaseRequest(@Param("status")String status,@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

   //通过状态查询采购信息数量
   int findPurchaseRequestTotal(@Param("status")String status);

   //未通过审批采购信息
   List<Stock> findUnPurchaseRequest(@Param("statues")String [] statues,@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

    //通过状态查询未通过审批采购信息数量
    int findUnPurchaseRequestTotal(@Param("statues")String [] statues);
}