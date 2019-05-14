package four.kjgz.logistics.contorll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import four.kjgz.logistics.bean.Menu;
import four.kjgz.logistics.mapper.MenuMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuControll {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取菜单列表
     * 登陆后才可以访问
     * 未登录访问的话返回未登录
     * @param num
     * @return
     * @throws JsonProcessingException
     */
    @RequiresPermissions("select")
    @PostMapping("/showMenu")
    public String showMenu(@RequestParam("num") String num)throws JsonProcessingException {
        Integer rid = num.charAt(0) - '0';
        System.out.println(rid);
        List<Menu> menuList = menuMapper.findByRid(rid);
        Map<String,Object> map = new HashMap<>();
        map.put("menus",menuList);
        map.put("rid",rid);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(map);
        return s;
    }
}
