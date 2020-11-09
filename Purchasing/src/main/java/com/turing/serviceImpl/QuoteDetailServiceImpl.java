package com.turing.serviceImpl;

import com.turing.dao.QuoteDetailMapper;
import com.turing.entity.QuoteDetail;
import com.turing.entity.QuoteDetailExample;
import com.turing.service.QuoteDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报价详情业务类
 */
@Service
public class QuoteDetailServiceImpl implements QuoteDetailService {

    @Resource
    private QuoteDetailMapper quoteDetailMapper;

    @Override
    public int addQuoteDetail(QuoteDetail quoteDetail) {
        return quoteDetailMapper.insert(quoteDetail);
    }

    @Override
    public QuoteDetail findDetailByqID(Integer qid) {
        QuoteDetailExample example=new QuoteDetailExample();
        example.createCriteria().andQuoteIdEqualTo(new Long(qid));
        List<QuoteDetail> detailList = quoteDetailMapper.selectByExample(example);
        if(detailList.size()>0){
            return detailList.get(0);
        }
        return null;
    }
}
