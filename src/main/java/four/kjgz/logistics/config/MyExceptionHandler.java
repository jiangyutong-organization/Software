package four.kjgz.logistics.config;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class MyExceptionHandler implements HandlerExceptionResolver {
    /**
     * 全局捕获异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String,Object> map = new HashMap<>();

        if (e instanceof UnauthenticatedException) {
            map.put("code", "1000001");
            map.put("msg", "token错误");
        } else if (e instanceof UnauthorizedException) {
            //没有角色权限
            map.put("code", "1000002");
            System.out.println(e.getMessage());
            map.put("msg", "用户无权限");
        }
        e.printStackTrace();
//        else {
//            map.put("code", "1000003");
//            map.put("msg", e.getMessage());
//            System.out.println(e.getMessage());
//    }
        view.setAttributesMap(map);
        modelAndView.setView(view);
        return modelAndView;
    }
}
