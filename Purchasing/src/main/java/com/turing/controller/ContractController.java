package com.turing.controller;

import com.turing.entity.Contract;
import com.turing.entity.ContractDetail;
import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.IdMapping;
import com.turing.service.ContractDetailService;
import com.turing.service.ContractService;
import com.turing.service.IdMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同控制层
 */
@Controller
public class ContractController {

    //合同业务类
    @Autowired
    private ContractService contractService;

    //合同详情
    @Autowired
    private ContractDetailService contractDetailService;

    //编号对照
    @Autowired
    private IdMappingService idMappingService;


    //编制合同
    @RequestMapping("/saveContract")
    @ResponseBody
    public String saveContract(Contract c, ContractDetail cd,Integer imId, String verifydate,String contdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int rows=0;
        c.setContDate(sdf.parse(contdate));
        c.setVerifyDate(sdf.parse(verifydate));
        //添加合同
        int i = contractService.addContract(c);
        if(i>0){
            //设置合同id
            //添加详情
            cd.setContId(new Long(c.getId()));
            int i1 = contractDetailService.addContractDetail(cd);
            if(i1>0){
                IdMapping mapping=new IdMapping();
                mapping.setId(new Long(imId));
                mapping.setContId(new Long(c.getId()));
                mapping.setStatus("C001-160");

                rows= idMappingService.updateIdMapping(mapping);
            }
        }
        if(rows>0){
            return "true";
        }else {
            return "false";
        }
    }


    //归档列表
    @RequestMapping("/findContracts")
    @ResponseBody
    public EasyUIDataGrid findContracts(@RequestParam(value = "page",defaultValue = "1") Integer cusPage, @RequestParam(value = "rows",defaultValue = "2") Integer pageSize){
     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String,String>> list=new ArrayList<>();
        EasyUIDataGrid uiDataGrid = contractService.findContractList(cusPage, pageSize);
        List<Contract> contractList =(List<Contract>) uiDataGrid.getRows();
        for (Contract c:
             contractList) {
            Map<String,String> map=new HashMap<>();
            map.put("contNum",c.getContNum());//合同编号
            map.put("contDate",sdf.format(c.getContDate()));//签订时间
            map.put("contPrice",c.getContPrice().toString());//总金额
            map.put("seller",c.getSeller());//供应商
            map.put("status",c.getIdMapping().getStatus());//状态
            map.put("qid",c.getIdMapping().getQuoteId().toString());//报价id
            map.put("imId",c.getIdMapping().getId().toString());//对照id
            map.put("cid",c.getId().toString());//合同id
         list.add(map);
        }
        EasyUIDataGrid easyUIDataGrid1=new EasyUIDataGrid();
        easyUIDataGrid1.setRows(list);
        easyUIDataGrid1.setTotal(uiDataGrid.getTotal());
        return easyUIDataGrid1;
    }


    //归档
    @RequestMapping("/pigeonhole")
    @ResponseBody
    public String pigeonhole(Integer imId){
        IdMapping idMapping=new IdMapping();
        idMapping.setId(new Long(imId));
        idMapping.setStatus("C001-170");//归档
        int i = idMappingService.updateIdMapping(idMapping);
        if(i>0){
            return "true";
        }else{
            return "false";
        }
    }


    //查看合同详情
    @RequestMapping("/showContractDetail")
    public ModelAndView showContractDetail(Integer cid){
    ModelAndView mv=new ModelAndView("contract_view");
        System.out.println(cid);
        //合同信息
        Contract contract = contractService.findContractById(cid);
        //详情
        ContractDetail detail = contractDetailService.findDetailByCid(cid);
        //编号对照信息
        IdMapping idMapping = contract.getIdMapping();
        mv.addObject("contract",contract);
        mv.addObject("detail",detail);
        mv.addObject("idMapping",idMapping);
        return mv;
    }
}
