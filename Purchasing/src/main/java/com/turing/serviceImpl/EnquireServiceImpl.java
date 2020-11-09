package com.turing.serviceImpl;

import com.turing.dao.EnquireMapper;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Enquire;
import com.turing.service.EnquireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 询价信息业务
 */
@Service
public class EnquireServiceImpl implements EnquireService {

    @Resource
    private EnquireMapper enquireMapper;

    @Override
    public int addEnquire(Enquire enquire) {
        return enquireMapper.insert(enquire);
    }

    @Override
    public String getEnquirePlanNumber() throws ParseException {
        ///获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();


        //获取月份和日期
        String nowDAM = sdf3.format(date);
        Date mad = sdf3.parse(nowDAM);

        Date mad2 = sdf3.parse("01-01");

        //获取年份
        Integer year = cal.get(Calendar.YEAR);

        String date2 = year + "0101";
        //System.out.println(date2);
        //获取当前时间
        String nowDate = sdf1.format(date);

        //获取需求计划的最近更新的一列
        Enquire recentlyForInsert = enquireMapper.findForInsert();
        //获取需求计划编号的日期
        String num = recentlyForInsert.getEnquireNum();
        String da = num.substring(3, 11);
        //System.out.println("流水号日期"+da);
        if (mad.getTime() == mad2.getTime() && !da.equals(date2)) {
            return "300" + nowDate + 00001;
        } else {
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
            num = "300" + year + m + d + num.substring(11);
            Long nums = Long.parseLong(num) + 1;
            //System.out.println(num+":"+nums);
            return nums + "";
        }
    }

    @Override
    public EasyUIDataGrid getEnquireList(Integer cusPage, Integer pageSize) {
        List<Enquire> enquireList = enquireMapper.findEnquireList((cusPage - 1) * pageSize, pageSize);
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(enquireList);
        easyUIDataGrid.setTotal(enquireMapper.findEnquireListTotal());
        return easyUIDataGrid;
    }

    @Override
    public Enquire findEnquireById(Integer eid) {
        return enquireMapper.selectByPrimaryKey(new Long(eid));
    }

    @Override
    public int updateEnquire(Enquire enquire) {
        return enquireMapper.updateByPrimaryKeySelective(enquire);
    }

    @Override
    public int delEnquire(Integer eid) {
        return enquireMapper.deleteByPrimaryKey(new Long(eid));
    }

    @Override
    public EasyUIDataGrid findForEnquire(Integer cusPage, Integer pageSize) {
        List<Enquire> enquireList = enquireMapper.ouotationForinquiry((cusPage - 1) * pageSize, pageSize);
        int i = enquireMapper.ouotationForinquiryTatol();
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(enquireList);
        easyUIDataGrid.setTotal(i);
        return easyUIDataGrid;
    }

    @Override
    public List<Enquire> updateEnquireStatus() {

        return enquireMapper.unOuotationForinquiry();
    }
}
