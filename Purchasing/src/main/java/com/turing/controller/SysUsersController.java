package com.turing.controller;

import com.turing.entity.Employee;
import com.turing.entity.SysUsers;
import com.turing.service.EmployeeService;
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
            //获取员工
            Employee emp = employeeService.findEmpByUserId(user.getId().intValue());
            session.setAttribute("emp",emp);
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
           return employee.getEmpName();
       }else{
           return "";
       }
    }
}
