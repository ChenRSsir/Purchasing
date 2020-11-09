package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Quote;

import java.text.ParseException;

public interface QuoteService {

    /**
     * 添加报价书
     * @param quote
     * @return
     */
    public int addQuote(Quote quote);

    /**
     * 获取编码
     * @return
     */
    public String getQuotePlanNumber() throws ParseException;


    /**
     * 查询所有报价书
     * @return
     */
    public EasyUIDataGrid findQuote(Integer cusPage,Integer pageSize);


    /**
     * 通过id查询
     * @param qid
     * @return
     */
    public Quote findQuoteById(Integer qid);
}
