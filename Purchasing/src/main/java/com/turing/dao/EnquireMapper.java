package com.turing.dao;

import com.turing.entity.Enquire;
import com.turing.entity.EnquireExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnquireMapper {
    long countByExample(EnquireExample example);

    int deleteByExample(EnquireExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Enquire record);

    int insertSelective(Enquire record);

    List<Enquire> selectByExample(EnquireExample example);

    Enquire selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Enquire record, @Param("example") EnquireExample example);

    int updateByExample(@Param("record") Enquire record, @Param("example") EnquireExample example);

    int updateByPrimaryKeySelective(Enquire record);

    int updateByPrimaryKey(Enquire record);
    //获取最新的一条数据
    Enquire findForInsert();
    //询价书列表
    List<Enquire> findEnquireList(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);
    //询价书总数
    int findEnquireListTotal();

    //询价书报价
    List<Enquire> ouotationForinquiry(@Param("cusPage") Integer cusPage,@Param("pageSize") Integer pageSize);

    //询价书报价总数
    int ouotationForinquiryTatol();

    //过时询价书
    List<Enquire> unOuotationForinquiry();
}