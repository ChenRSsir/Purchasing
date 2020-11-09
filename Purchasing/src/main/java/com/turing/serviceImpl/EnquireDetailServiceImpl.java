package com.turing.serviceImpl;

import com.turing.dao.EnquireDetailMapper;
import com.turing.entity.EnquireDetail;
import com.turing.entity.EnquireDetailExample;
import com.turing.service.EnquireDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 询价详情业务类
 */
@Service
public class EnquireDetailServiceImpl implements EnquireDetailService {

    @Resource
    private EnquireDetailMapper enquireDetailMapper;

    @Override
    public int addEnquireDetail(EnquireDetail enquireDetail) {
        return enquireDetailMapper.insert(enquireDetail);
    }

    @Override
    public EnquireDetail findEnquireDetailByEid(Integer eid) {
        EnquireDetailExample example=new EnquireDetailExample();
        example.createCriteria().andEnquireIdEqualTo(new Long(eid));
        List<EnquireDetail> detailList = enquireDetailMapper.selectByExample(example);
        if(detailList.size()>0){
            return detailList.get(0);
        }
        return null;
    }

    @Override
    public int updateEnquireDetail(EnquireDetail detail) {
        return enquireDetailMapper.updateByPrimaryKeySelective(detail);
    }


    @Override
    public int delEnquireDetail(Integer edid) {
        return enquireDetailMapper.deleteByPrimaryKey(new Long(edid));
    }
}
