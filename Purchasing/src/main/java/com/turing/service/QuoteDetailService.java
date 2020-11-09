package com.turing.service;

import com.turing.entity.QuoteDetail;

public interface QuoteDetailService {

    /**
     * 添加报价详情
     * @param quoteDetail
     * @return
     */
    public int addQuoteDetail(QuoteDetail quoteDetail);


    /**
     * 通过报价id查询详情
     * @param qid
     * @return
     */
    public QuoteDetail findDetailByqID(Integer qid);
}
