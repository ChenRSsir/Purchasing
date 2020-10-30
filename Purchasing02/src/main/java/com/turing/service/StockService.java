package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Stock;

import java.text.ParseException;

public interface StockService {

    /**
     * 添加
     * @param stock
     * @return
     */
    public int addStock(Stock stock);

    /**
     * 获取编码
     * @return
     */
    public String getRequirementPlanNumber() throws ParseException;


    /**
     * 分页查询采购信息
     * @param cusPage
     * @param pageSize
     * @return
     */
    public EasyUIDataGrid findStockList(Integer cusPage,Integer pageSize);
}
