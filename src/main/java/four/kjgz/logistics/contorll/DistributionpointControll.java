package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.*;
import four.kjgz.logistics.repository.DistributionpointReposity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DistributionpointControll {
    Logger logger = LoggerFactory.getLogger(DistributionpointControll.class);
    @Autowired
    loginInfo nowloginInfo;
    @Autowired
    DistributionpointReposity distributionpointReposity;

    @MyLog(value = "通过名字查找配送点")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/selectDpByName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String selectDpByName(@RequestParam("name") String name)
    {
        if(distributionpointReposity.findByName(name).size()==0)
        {
            logger.error("通过名字查找配送点失败");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "通过名字查找配送点失败");
            return result.toJSONString();
        }
        else
        {
            JSONObject result = new JSONObject();
            result.put("sts", "1");
            result.put("msg", "通过名字查找配送点成功");
            result.put("ourdata", distributionpointReposity.findByName(name));
            return  result.toJSONString();
        }
    }
    //插入的第一种方法
    @MyLog(value = "插入配送点")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/addDp", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addDp(
            @RequestParam("name") String name,
            @RequestParam("location") String location
    )
    {
       // System.out.println("现在登陆的账号是"+nowloginInfo.getMyusernum());
       // Mycheck mycheck = new Mycheck();
        // if(nowloginInfo.getResult()!="成功")
        // {
         //   return nowloginInfo.getResult();
        // }
      //  else
         //{
             Distributionpoint distributionpoint= new Distributionpoint();
             distributionpoint.setLocation(location);
             distributionpoint.setName(name);
             if(distributionpointReposity.save(distributionpoint)==null)
             {
                 logger.error("插入配送点失败");
                 JSONObject result = new JSONObject();
                 result.put("sts", "0");
                 result.put("msg", "插入配送点失败");
                 return  result.toJSONString();

             }
             else
             {

                 JSONObject result = new JSONObject();
                 result.put("sts", "1");
                 result.put("msg", "插入配送点成功");
                 result.put("ourdata", distributionpointReposity.save(distributionpoint));
                 return  result.toJSONString();

             }
       //  }

    }
    @MyLog(value = "通过位置查找配送点")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/selectDpByLocation", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object selectDpByLocation(
            @RequestParam("location") String location
    )
    {
        if(distributionpointReposity.findByLocation(location).size()==0)
        {
            logger.error("查找失败，没有该配送点");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "查找失败，没有该配送点");
            return   result.toJSONString();
        }
        else
        {
            JSONObject result = new JSONObject();
            result.put("sts", "1");
            result.put("msg", "通过地址查询配送点");
            result.put("ourdata", distributionpointReposity.findByLocation(location));
            return result.toJSONString();
        }

    }
    @MyLog(value = "删除配送点")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/delDp", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object delDp(
            @RequestParam("name") String name,
            @RequestParam("location") String location
    )
    {
        if(distributionpointReposity.findByNameAndLocation(name,location).size()==0)
        {
            logger.error("删除配送点识别");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "删除配送点失败");
            return   result.toJSONString();

        }
        else
        {
            JSONObject result = new JSONObject();
            result.put("sts", "1");
            result.put("msg", "删除配送点成功");
            result.put("ourdata",distributionpointReposity.deleteByNameAndLocation(name,location));
            return result.toJSONString();
        }

    }
}
