package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.loginInfo;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class myinfoControll {
    @Autowired
    loginInfo nowloginInfo;
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    Logger logger = LoggerFactory.getLogger(loginControll.class);
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String login(@RequestParam("token") String token)
    {
        String[] myList = {token};
        JSONObject result = new JSONObject();
        result.put("roles", token);
        result.put("name", nowloginInfo.getMyusername());
        result.put("avatar", nowloginInfo.getMyimge());
        result.put("introduction", "我是管理员");
        return result.toJSONString();

    }
}
