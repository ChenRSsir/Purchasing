package com.turing.serviceImpl;

import com.turing.dao.ContractDetailMapper;
import com.turing.entity.ContractDetail;
import com.turing.entity.ContractDetailExample;
import com.turing.service.ContractDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合同详情业务
 */
@Service
public class ContractDetailServiceImpl implements ContractDetailService {

    @Resource
    private ContractDetailMapper contractDetailMapper;

    @Override
    public int addContractDetail(ContractDetail contractDetail) {
        return contractDetailMapper.insert(contractDetail);
    }

    @Override
    public ContractDetail findDetailByCid(Integer cid) {

        ContractDetailExample example=new ContractDetailExample();
        example.createCriteria().andContIdEqualTo(new Long(cid));
        List<ContractDetail> details = contractDetailMapper.selectByExample(example);
        if(details.size()>0){
            return details.get(0);
        }
        return null;
    }
}
