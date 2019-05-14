package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.MyLog;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import four.kjgz.logistics.repository.SuperAdministratorsReposity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class selectUserByNumContorll {
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    @Autowired
    SuperAdministratorsReposity superAdministratorsReposity;
    Logger logger = LoggerFactory.getLogger(selectUserByNumContorll.class);
    @MyLog(value = "通过的num查找用户")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/selectUserByNum", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @CrossOrigin(origins = "http://localhost:8080")    //注解用于存储数据时的跨域问题
    public String  selectUserByNum(@RequestParam("num") String num)
    {
        char first = num.charAt(0);
        if (first=='0')//表示为超级管理员
        {
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("没有该用户");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "查找成功");
                result.put("ourdata",superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num));
                return  result.toJSONString();
            }
        }
        else if (first=='1')//表示为管理员
        {
            if(administratorsReposity.findByAdministratorsNum(num).size()==0)
            {
                logger.error("没有该用户");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "查找成功");
                result.put("ourdata", administratorsReposity.findByAdministratorsNum(num));
                return  result.toJSONString();
            }
        }
        else if(first=='2')//表示工作人员
        {
            if(staffReposity.findByStaffNum(num).size()==0)
            {
                logger.error("没有该用户");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "查找成功");
                result.put("ourdata",staffReposity.findByStaffNum(num));
                return  result.toJSONString();
            }


        }
        else{
            if(customerReposity.findByCustomerNum(num).size()==0)
            {
                logger.error("没有该用户");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户");
                return result.toJSONString();

            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "查找成功");
                result.put("ourdata", customerReposity.findByCustomerNum(num));
                return  result.toJSONString();
            }
        }
    }
}
