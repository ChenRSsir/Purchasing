package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Enquire;

import java.text.ParseException;
import java.util.List;

public interface EnquireService {

    /**
     * 添加
     * @param enquire
     * @return
     */
    public int addEnquire(Enquire enquire);

    /**
     * 获取编码
     * @return
     */
    public String getEnquirePlanNumber() throws ParseException;

    /**
     * 询价书列表
     * @param cusPage
     * @param pageSize
     * @return
     */
    public EasyUIDataGrid getEnquireList(Integer cusPage,Integer pageSize);

    /**
     * 通过id查询询价书
     * @param eid
     * @return
     */
    public Enquire findEnquireById(Integer eid);

    /**
     * 修改
     * @param enquire
     * @return
     */
    public int updateEnquire(Enquire enquire);


    /**
     * 删除
     * @param eid
     * @return
     */
    public int delEnquire(Integer eid);

    /**
     * 查询询价书报价信息
     * @param cusPage
     * @param pageSize
     * @return
     */
    public EasyUIDataGrid findForEnquire(Integer cusPage,Integer pageSize);


    /**
     * 将过时的询价改变状态
     * @return
     */
    public List<Enquire> updateEnquireStatus();
}
