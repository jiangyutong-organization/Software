package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.*;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        char first = num.charAt(0);
        if (first=='1')//表示为管理员
        {
            if(administratorsReposity.findByAdministratorsNum(num).size()==0)
            {
                logger.error("用户名错误");
                JSONObject result = new JSONObject();
                result.put("msg", "用户名错误");
                return result.toJSONString();
            }
            else
            {
                if(administratorsReposity.findByAdministratorsNumAndPassword(num,password).size()==0)
                {
                    logger.error("密码错误");
                    JSONObject result = new JSONObject();
                    result.put("msg", "密码错误");
                    return result.toJSONString();
                }
                else
                {
                    Administrators administrators=administratorsReposity.findByAdministratorsNumAndPassword(num,password).get(0);
                    nowloginInfo.setMyusername(administrators.getUsername());
                    nowloginInfo.setMyimge(administrators.getImage());
                    JSONObject result = new JSONObject();
                    result.put("msg", "成功");
                    result.put("token", "admin");
                    result.put("ourdata", administratorsReposity.findByAdministratorsNumAndPassword(num,password));
                    return result.toJSONString();

                }
            }

        }
        else if(first=='2')//表示工作人员
        {
            if(staffReposity.findByStaffNum(num).size()==0)
            {
                logger.error("用户名错误");
                JSONObject result = new JSONObject();
                result.put("msg", "用户名错误");
                return result.toJSONString();
            }
            else
            {
                if(staffReposity.findByStaffNumAndPassword(num,password).size()==0)
                {
                    logger.error("密码错误");
                    JSONObject result = new JSONObject();
                    result.put("msg", "密码错误");
                    return result.toJSONString();
                }
                else
                {
                    Staff staff=staffReposity.findByStaffNumAndPassword(num,password).get(0);
                    nowloginInfo.setMyusername(staff.getUsername());
                    nowloginInfo.setMyimge(staff.getImage());
                    JSONObject result = new JSONObject();
                    result.put("msg", "成功");
                    result.put("token", "staff");
                    result.put("ourdata", staffReposity.findByStaffNumAndPassword(num,password));
                    return result.toJSONString();
                }
            }

        }
        else{
            if(customerReposity.findByCustomerNum(num).size()==0)
            {
                logger.error("用户名错误");
                JSONObject result = new JSONObject();
                result.put("msg", "用户名错误");
                return result.toJSONString();
            }
            else
            {
                if(customerReposity.findByCustomerNumAndPassword(num,password).size()==0)
                {
                    logger.error("密码错误");
                    JSONObject result = new JSONObject();
                    result.put("msg", "密码错误");
                    return result.toJSONString();
                }
                else
                {
                    Customer customer=customerReposity.findByCustomerNumAndPassword(num,password).get(0);
                    nowloginInfo.setMyusername(customer.getUsername());
                    nowloginInfo.setMyimge(customer.getImage());
                    JSONObject result = new JSONObject();
                    result.put("msg", "成功");
                    result.put("token", "customer");
                    result.put("ourdata", customerReposity.findByCustomerNumAndPassword(num,password));
                    return result.toJSONString();
                }
            }

        }
    }
}