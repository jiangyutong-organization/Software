package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.*;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class loginControll {
    @Autowired
    loginInfo nowloginInfo;
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    Logger logger = LoggerFactory.getLogger(loginControll.class);
    @MyLog(value = "用户登录")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestParam("num") String num,
                        @RequestParam("password") String password)
    {
        nowloginInfo.setMyusernum(num);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(num, password);
        //3、执行登录方法
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
            JSONObject result = new JSONObject();
            result.put("sts", "1");
            result.put("msg", "成功");

            Object obj = subject.getPrincipal();
            System.out.println(obj);
            char first = num.charAt(0);
            if (first=='0')//表示为超级管理员
            {
                result.put("token", "superadmin");
                SuperAdministrators superAdministrators = (SuperAdministrators)subject.getPrincipal();
                nowloginInfo.setMyimge(superAdministrators.getImage());
                nowloginInfo.setMyusername(superAdministrators.getUsername());
                result.put("ourdata", subject.getPrincipal());
                return result.toJSONString();
            }
            else if (first=='1')//表示为管理员
            {    result.put("token", "admin");
                Administrators admin = (Administrators)subject.getPrincipal();
                nowloginInfo.setMyimge(admin.getImage());
                nowloginInfo.setMyusername(admin.getUsername());
                result.put("ourdata", subject.getPrincipal());
                return result.toJSONString();
            }
            else if(first=='2')//表示工作人员
            {
                result.put("token", "Staff");
                //System.out.println("staff");
                Staff staff = (Staff)subject.getPrincipal();
                nowloginInfo.setMyimge(staff.getImage());
                nowloginInfo.setMyusername(staff.getUsername());
                result.put("ourdata", subject.getPrincipal());
                return result.toJSONString();
            }
            else{
                result.put("token", "customer");
                Customer customer = (Customer)subject.getPrincipal();
                nowloginInfo.setMyimge(customer.getImage());
                nowloginInfo.setMyusername(customer.getUsername());
                result.put("ourdata", subject.getPrincipal());
                return result.toJSONString();
            }

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            //登录失败:用户名不存在
            logger.error("用户名错误");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "用户名错误");
            return result.toJSONString();
        } catch (IncorrectCredentialsException e) {
            logger.error("密码错误");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "密码错误");
            return result.toJSONString();
        }
    }

    @GetMapping("/unLogin")
    public String unLogin(){
        return "unLogin";
    }
}