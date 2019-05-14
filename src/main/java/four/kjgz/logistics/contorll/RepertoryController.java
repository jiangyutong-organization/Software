package four.kjgz.logistics.contorll;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.MyLog;
import four.kjgz.logistics.bean.Repertory;
import four.kjgz.logistics.mapper.RepertoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepertoryController {
    @Autowired
    private RepertoryMapper repertoryMapper;
    Logger logger = LoggerFactory.getLogger(RepertoryController.class);
    @MyLog(value = "通过id查找仓库")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/selectRepertoryById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String selectRepertoryById(@RequestParam("id") Integer id){
        Repertory repertory = repertoryMapper.selectRepertoryById(id);
        if(repertory==null)
        {
            logger.error("通过id查找仓库失败");
            JSONObject result = new JSONObject();
            result.put("sts", "0");
            result.put("msg", "通过id查找仓库失败");
            return result.toJSONString();
        }
        else
        {
            JSONObject result = new JSONObject();
            result.put("sts", "1");
            result.put("msg", "通过id查找仓库成功");
            result.put("ourdata",repertory);
            return result.toJSONString();
        }

    }
}
