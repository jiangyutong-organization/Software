package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.Administrators;
import four.kjgz.logistics.bean.Customer;
import four.kjgz.logistics.bean.MyLog;
import four.kjgz.logistics.bean.Staff;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import four.kjgz.logistics.repository.SuperAdministratorsReposity;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class deleteControll {
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    SuperAdministratorsReposity superAdministratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    Logger logger = LoggerFactory.getLogger(deleteControll.class);

    @MyLog(value = "删除用户")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/delUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public  String delUser(@RequestParam("num") String num) {
        char first = num.charAt(0);
        if(first=='0')//表示为超级管理员
        {
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("删除超级管理员失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "删除超级管理员失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "删除管理员成功");
                result.put("ourdata", superAdministratorsReposity.deleteSuperAdministratorsBySuperAdministratorsNum(num));
                return  result.toJSONString();
            }
        }
        else if (first=='1')//表示为管理员
        {
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("删除管理员失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "删除管理员失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "删除管理员成功");
                result.put("ourdata", administratorsReposity.deleteByAdministratorsNum(num));
                return  result.toJSONString();
            }

        }
        else if(first=='2')//表示工作人员
        {
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("删除工作人员失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "删除工作人员失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "删除工作人员成功");
                result.put("ourdata",staffReposity.deleteByStaffNum(num));
                return  result.toJSONString();
            }
        }
        else{
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("删除客户失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "删除客户失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "删除客户成功");
                result.put("ourdata",customerReposity.deleteByCustomerNum(num));
                return  result.toJSONString();
            }

        }
    }
}
