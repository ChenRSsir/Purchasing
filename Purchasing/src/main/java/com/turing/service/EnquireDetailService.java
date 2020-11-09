package com.turing.service;

import com.turing.entity.EnquireDetail;

public interface EnquireDetailService {

    /**
     * 添加询价详情
     * @param enquireDetail
     * @return
     */
    public int addEnquireDetail(EnquireDetail enquireDetail);


    /**
     * 通过询价书id查询询价详情
     * @param eid
     * @return
     */
    public EnquireDetail findEnquireDetailByEid(Integer eid);


    /**
     * 修改
     * @param detail
     * @return
     */
    public int updateEnquireDetail(EnquireDetail detail);

    /**
     * 删除
     * @param edid
     * @return
     */
    public int delEnquireDetail(Integer edid);
}
