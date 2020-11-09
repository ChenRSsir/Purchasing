package com.turing.serviceImpl;

import com.turing.dao.QuoteMapper;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Quote;
import com.turing.entity.QuoteExample;
import com.turing.entity.Stock;
import com.turing.service.QuoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 报价业务类
 */
@Service
public class QuoteServiceImpl implements QuoteService {

    @Resource
    private QuoteMapper quoteMapper;

    @Override
    public int addQuote(Quote quote) {
        return quoteMapper.insert(quote);
    }

    @Override
    public String getQuotePlanNumber() throws ParseException {
        ///获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();


        //获取月份和日期
        String nowDAM = sdf3.format(date);
        Date mad = sdf3.parse(nowDAM);

        Date mad2 = sdf3.parse("01-01");

        //获取年份
        Integer year = cal.get(Calendar.YEAR);

        String date2 = year+"0101";
        //System.out.println(date2);
        //获取当前时间
        String nowDate = sdf1.format(date);

        //获取需求计划的最近更新的一列
        Quote recentlyForInsert = quoteMapper.findForInsert();
        //获取需求计划编号的日期
        String num = recentlyForInsert.getQuoteNum();
        String da = num.substring(3,11);
        //System.out.println("流水号日期"+da);
        if(mad.getTime() == mad2.getTime()&&!da.equals(date2)){
            return "500"+nowDate+00001;
        }else {
            //获取当前月份
            String m = String.valueOf(cal.get(Calendar.MONTH) + 1);
            if (cal.get(Calendar.MONTH) + 1 < 10) {
                m = String.valueOf("0" + cal.get(Calendar.MONTH) + 1);
            }

            //获取当前日期
            String d = String.valueOf(cal.get(Calendar.DATE));
            if (cal.get(Calendar.DATE) < 10) {
                d = String.valueOf("0" + cal.get(Calendar.DATE));
            }
            //获取最后五位数
            num = "500" + year + m + d + num.substring(11);
            Long nums = Long.parseLong(num) + 1;
            //System.out.println(num + ":" + nums);
            return nums + "";
        }
    }

    @Override
    public EasyUIDataGrid findQuote(Integer cusPage,Integer pageSize) {
        List<Quote> quoteList = quoteMapper.findQuote((cusPage - 1) * pageSize, pageSize);
        int total = quoteMapper.findQuoteTotal();
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(quoteList);
        easyUIDataGrid.setTotal(total);
        return easyUIDataGrid;
    }

    @Override
    public Quote findQuoteById(Integer qid) {
        return quoteMapper.selectByPrimaryKey(new Long(qid));
    }
}
