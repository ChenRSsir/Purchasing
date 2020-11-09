package com.turing.serviceImpl;

import com.turing.dao.StockMapper;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Stock;
import com.turing.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 采购业务类
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockMapper stockMapper;



    @Override
    public int addStock(Stock stock) {
        return stockMapper.insert(stock);
    }


    /**
     * 获取采购计划编号
     * @return
     * @throws ParseException
     */
    @Override
    public String getRequirementPlanNumber() throws ParseException {

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
        Stock recentlyForInsert = stockMapper.findForInsert();
        //获取需求计划编号的日期
        String num = recentlyForInsert.getStockNum();
        String da = num.substring(3,11);
        //System.out.println("流水号日期"+da);
        if(mad.getTime() == mad2.getTime()&&!da.equals(date2)){
            return "200"+nowDate+00001;
        }else{
            //获取当前月份
            String m = String.valueOf(cal.get(Calendar.MONTH) + 1);
            if(cal.get(Calendar.MONTH) + 1<10){
                m=String.valueOf("0"+cal.get(Calendar.MONTH) + 1);
            }

            //获取当前日期
            String d =String.valueOf(cal.get(Calendar.DATE));
            if(cal.get(Calendar.DATE)<10){
                d =String.valueOf("0"+cal.get(Calendar.DATE));
            }
            //获取最后五位数
            num = "200"+year+m+d+num.substring(11);
            Long nums = Long.parseLong(num)+1;
            //System.out.println(num+":"+nums);
            return nums+"";
        }
    }

    @Override
    public EasyUIDataGrid findStockList(Integer cusPage, Integer pageSize) {
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        List<Stock> stockList = stockMapper.findStockEnquire((cusPage - 1) * pageSize, pageSize);
        easyUIDataGrid.setRows(stockList);
        easyUIDataGrid.setTotal(stockMapper.findStockTotal());
        return easyUIDataGrid;
    }

    @Override
    public Stock findStockById(Integer sid) {
        return stockMapper.selectByPrimaryKey(new Long(sid));
    }

    @Override
    public int updateStock(Stock stock) {
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    @Override
    public EasyUIDataGrid purchaseRequest(String status, Integer cusPage, Integer pageSize) {

        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        List<Stock> purchaseRequest = stockMapper.findPurchaseRequest(status, (cusPage-1)*pageSize, pageSize);
         easyUIDataGrid.setRows(purchaseRequest);
         easyUIDataGrid.setTotal(stockMapper.findPurchaseRequestTotal(status));
        return easyUIDataGrid;
    }

    @Override
    public EasyUIDataGrid unPurchaseRequest(String[] statues, Integer cusPage, Integer pageSize) {
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        List<Stock> purchaseRequest = stockMapper.findUnPurchaseRequest(statues, (cusPage-1)*pageSize, pageSize);
        easyUIDataGrid.setRows(purchaseRequest);
        easyUIDataGrid.setTotal(stockMapper.findUnPurchaseRequestTotal(statues));
        return easyUIDataGrid;
    }

    @Override
    public int deleteStock(Integer sid) {
        return stockMapper.deleteByPrimaryKey(new Long(sid));
    }

}
