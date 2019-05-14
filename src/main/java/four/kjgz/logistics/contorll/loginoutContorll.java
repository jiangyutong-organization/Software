package four.kjgz.logistics.contorll;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/logout")
public class LoginoutContorll {
    @RequestMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.toString());
        try {
            subject.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
