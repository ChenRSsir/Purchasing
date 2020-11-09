package com.turing.serviceImpl;

import com.turing.dao.ContractApplyMapper;
import com.turing.entity.ContractApply;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Enquire;
import com.turing.service.ContractApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 合同申请业务类
 */
@Service
public class ContractApplyServiceImpl implements ContractApplyService {

    @Resource
    private ContractApplyMapper contractApplyMapper;

    @Override
    public int addContractApply(ContractApply contractApply) {
        return contractApplyMapper.insert(contractApply);
    }

    @Override
    public int updateContractApply(ContractApply contractApply) {
        return contractApplyMapper.updateByPrimaryKeySelective(contractApply);
    }

    @Override
    public String getContractApplyNumber() throws ParseException {
        ///获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
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
        ContractApply recentlyForInsert = contractApplyMapper.findForInsert();
        //获取需求计划编号的日期
        String num = recentlyForInsert.getContAppNum();
        String da = num.substring(3, 11);
        //System.out.println("流水号日期"+da);
        if (mad.getTime() == mad2.getTime() && !da.equals(date2)) {
            return "400" + nowDate + 00001;
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
            num = "400" + year + m + d + num.substring(11);
            Long nums = Long.parseLong(num) + 1;
            //System.out.println(num+":"+nums);
            return nums + "";
        }
    }

    @Override
    public ContractApply findContractApplyByIDI(Integer cid) {
        return contractApplyMapper.selectByPrimaryKey(new Long(cid));
    }

    @Override
    public EasyUIDataGrid findContractApplyList(Integer cusPage, Integer pageSize) {
        List<ContractApply> applyList = contractApplyMapper.findContractApplys((cusPage - 1) * pageSize, pageSize);
        int total = contractApplyMapper.findContractApplysTotal();
        EasyUIDataGrid easyUIDataGrid=new EasyUIDataGrid();
        easyUIDataGrid.setRows(applyList);
        easyUIDataGrid.setTotal(total);
        return easyUIDataGrid;
    }
}
