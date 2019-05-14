package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.*;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import four.kjgz.logistics.repository.SuperAdministratorsReposity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class updateUserCntorll {
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    @Autowired
    SuperAdministratorsReposity superAdministratorsReposity;
    Logger logger = LoggerFactory.getLogger(updateUserCntorll.class);
    @MyLog(value = "通过的num更新用户")  //这里添加了AOP的自定义注解
    @Transactional
    @Modifying
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @CrossOrigin(origins = "http://localhost:8080")    //注解用于存储数据时的跨域问题
    public Object updateUser(@RequestParam("num") String num,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("email") String email,
                        @RequestParam("sex") String sex)
    {
        char first = num.charAt(0);
        if (first=='0')//表示为管理员
        {
            if(superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).size()==0)
            {
                logger.error("没有该用户，无法更新");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户，无法更新");
                return result.toJSONString();
            }
            SuperAdministrators SuperAdministratorsone = (SuperAdministrators) superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num).get(0);
            SuperAdministrators superAdministrators =new SuperAdministrators();
            superAdministrators.setId(SuperAdministratorsone.getId());
            superAdministrators.setImage(SuperAdministratorsone.getImage());
            superAdministrators.setSuperAdministratorsNum(num);
            superAdministrators.setEmail(email);
            superAdministrators.setPassword(password);
            superAdministrators.setSex(sex);
            superAdministrators.setUsername(username);
            if(superAdministratorsReposity.save(superAdministrators)==null)
            {
                logger.error("更新失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "更新失败");
                return result.toJSONString();
            }
            else
            {

                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "更新成功");
                result.put("ourdata", superAdministratorsReposity.save(superAdministrators));
                return result.toJSONString();
            }

        }
        else if (first=='1')//表示为管理员
        {
            System.out.println(administratorsReposity.findByAdministratorsNum(num));
            if(administratorsReposity.findByAdministratorsNum(num).size()==0)
            {
                logger.error("没有该用户，无法更新");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户，无法更新");
                return result.toJSONString();
            }
            Administrators administratorsone = (Administrators) administratorsReposity.findByAdministratorsNum(num).get(0);
            Administrators administrators =new Administrators();
            administrators.setId(administratorsone.getId());
            administrators.setImage(administratorsone.getImage());
            administrators.setAdministratorsNum(num);
            administrators.setEmail(email);
            administrators.setPassword(password);
            administrators.setSex(sex);
            administrators.setUsername(username);
            if(administratorsReposity.save(administrators)==null)
            {
                logger.error("更新失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "更新失败");
                return result.toJSONString();
            }
            else
            {

                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "更新成功");
                result.put("ourdata", administratorsReposity.save(administrators));
                return result.toJSONString();
            }

        }
        else if(first=='2')//表示工作人员
        {
            if(staffReposity.findByStaffNum(num).size()==0)
            {
                logger.error("没有该用户，无法更新");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户，无法更新");
                return result.toJSONString();
            }
            Staff staffone = (Staff) staffReposity.findByStaffNum(num).get(0);
            Staff staff =new Staff();
            staff.setImage(staffone.getImage());
            staff.setId(staffone.getId());
            staff.setStaffNum(num);
            staff.setEmail(email);
            staff.setPassword(password);
            staff.setSex(sex);
            staff.setUsername(username);
            if(staffReposity.save(staff)==null)
            {
                logger.error("更新失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "更新失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "更新成功");
                result.put("ourdata",  staffReposity.save(staff));
                return result.toJSONString();
            }

        }
        else{
            if(customerReposity.findByCustomerNum(num).size()==0)
            {
                logger.error("没有该用户，无法更新");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "没有该用户，无法更新");
                return result.toJSONString();
            }
            Customer customerone = (Customer) customerReposity.findByCustomerNum(num).get(0);
            Customer customer =new Customer();
            customer.setId(customerone.getId());
            customer.setImage(customerone.getImage());
            customer.setCustomerNum(num);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setSex(sex);
            customer.setUsername(username);
            customer.setAddress(customerone.getAddress());
            if(customerReposity.save(customer)==null)
            {
                logger.error("更新失败");
                JSONObject result = new JSONObject();
                result.put("sts", "0");
                result.put("msg", "更新失败");
                return result.toJSONString();
            }
            else
            {
                JSONObject result = new JSONObject();
                result.put("sts", "1");
                result.put("msg", "更新成功");
                result.put("ourdata",  customerReposity.save(customer));
                return result.toJSONString();
            }
        }
    }
}
