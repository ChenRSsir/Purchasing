package com.turing.dao;

import com.turing.entity.Enquire;
import com.turing.entity.Quote;
import com.turing.entity.QuoteExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuoteMapper {
    long countByExample(QuoteExample example);

    int deleteByExample(QuoteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Quote record);

    int insertSelective(Quote record);

    List<Quote> selectByExample(QuoteExample example);

    Quote selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Quote record, @Param("example") QuoteExample example);

    int updateByExample(@Param("record") Quote record, @Param("example") QuoteExample example);

    int updateByPrimaryKeySelective(Quote record);

    int updateByPrimaryKey(Quote record);

     //获取最新的一条数据
    Quote  findForInsert();

    //报价揭示
    List<Quote> findQuote(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

    //报价揭示条数
    int findQuoteTotal();
}