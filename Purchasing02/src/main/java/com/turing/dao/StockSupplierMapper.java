package com.turing.dao;

import com.turing.entity.StockSupplier;
import com.turing.entity.StockSupplierExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockSupplierMapper {
    long countByExample(StockSupplierExample example);

    int deleteByExample(StockSupplierExample example);

    int insert(StockSupplier record);

    int insertSelective(StockSupplier record);

    List<StockSupplier> selectByExample(StockSupplierExample example);

    int updateByExampleSelective(@Param("record") StockSupplier record, @Param("example") StockSupplierExample example);

    int updateByExample(@Param("record") StockSupplier record, @Param("example") StockSupplierExample example);
}