package com.turing.controller;

import com.turing.entity.Employee;
import com.turing.entity.Supplier;
import com.turing.entity.SysUserRole;
import com.turing.entity.SysUsers;
import com.turing.service.EmployeeService;
import com.turing.service.SupplierService;
import com.turing.service.SysUserRoleService;
import com.turing.service.SysUsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户控制层
 */
@Controller
public class SysUsersController {

    //用户业务
    @Autowired
    private SysUsersService sysUsersService;
    //员工业务
    @Autowired
    private EmployeeService employeeService;
    //角色
    @Autowired
    private SysUserRoleService sysUserRoleService;
    //供应商
    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/loginUser")
    public String loginUser(String loginId, String password, ServletResponse response, HttpSession session) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //通过Shiro提供的验证方式
        //1，获取subject
        Subject subject= SecurityUtils.getSubject();
        //2，把用户提供的账号密码封装到Shiro提供的一个对象中
        UsernamePasswordToken token=new UsernamePasswordToken(loginId,password);
        //执行认证
        try {
            subject.login(token);
            //登录成功
            //将用户员工存储
            SysUsers user = sysUsersService.findUserByName(loginId);

            //判断是员工还是供应商
            SysUserRole role = sysUserRoleService.findUserRoleByUserId(user.getId().intValue());
            if(role.getRoleId()==6){
                //供应商
                Supplier supplier = supplierService.findSupplierByUserId(user.getId().intValue());
                session.setAttribute("supplier",supplier);
            }else {
                //获取员工
                Employee emp = employeeService.findEmpByUserId(user.getId().intValue());
                session.setAttribute("emp", emp);
            }
            return "/index";
        } catch (UnknownAccountException e) {
            out.print("<script>alert('用户名不存在!');</script>");
            return "/login";
        }catch (IncorrectCredentialsException e){
            out.print("<script>alert('密码错误!');</script>");
            return "/login";
        }
    }

    //主页显示姓名
    @RequestMapping("/showUserName")
    @ResponseBody
    public String showUserName(HttpSession session){
       if(session.getAttribute("emp")!=null){
           Employee employee=(Employee)session.getAttribute("emp");
           return employee.getEmpName()+"(株洲南车时代电气股份有限公司)";
       }else if(session.getAttribute("supplier")!=null){
          Supplier supplier=(Supplier)session.getAttribute("supplier");
           return supplier.getContact()+"(供应商)";
       }else{
           return "";
       }
    }


    @RequestMapping("/loginOut1")
    @ResponseBody
    public String loginOut1(HttpSession session){
        if(session.getAttribute("emp")!=null){
            session.removeAttribute("emp");
        }else if(session.getAttribute("supplier")!=null){
            session.removeAttribute("supplier");
        }else{
            session.invalidate();
        }
        return "login";
    }
}
