package four.kjgz.logistics.config;

import four.kjgz.logistics.Realm.MyShiroRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /*
    创建ShiorFilterFactoryBean
    //Filter工厂，设置对应的过滤条件和跳转条件
    * */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/unLogin");
        // 设置无权限时跳转的 url;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/logout");
        /*
        Shiro内置过滤器，可以实现权限相关的的拦截器
        常用的过滤器
            anon :无需认证（登录）可以访问
            authc ：必须认证才可以访问
            user ：如果使用rememberMe的功能可以直接访问
            perms : 该资源必须得到资源才可以访问
            role :该资源报错得到角色权限才可以访问
        * */
        Map<String,String> filterMap=new LinkedHashMap<String, String>();



        filterMap.put("/login","anon");
        filterMap.put("/info","anon");
        filterMap.put("/unLogin","anon");



        //授权过滤器
//        filterMap.put("/create","perms[user:create]");

       filterMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
        //
        //顾客，开发权限
//        filterMap.put("/guest/**", "roles[user]");
//        //，需要角色权限 “user”
//        filterMap.put("/user/**", "roles[user]");
//        //管理员，需要角色权限 “admin”
//        filterMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
//        filterMap.put("/loginn", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
//        filterMap.put("/**", "authc");
    }

    /*
    创建DefaultWebSecurityManager
    //权限管理，配置主要是Realm的管理认证
    * */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        return  securityManager;
    }
    /*
    创建Realm
    * */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm getRealm()
    {
        return  new MyShiroRealm();
    }

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new MyExceptionHandler();
    }
}
