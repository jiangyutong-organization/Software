package four.kjgz.logistics.Realm;

import com.alibaba.fastjson.JSONObject;
import four.kjgz.logistics.bean.*;
import four.kjgz.logistics.mapper.PermissionMapper;
import four.kjgz.logistics.repository.AdministratorsReposity;
import four.kjgz.logistics.repository.CustomerReposity;
import four.kjgz.logistics.repository.StaffReposity;
import four.kjgz.logistics.repository.SuperAdministratorsReposity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
自定义的Realm
* */
public class MyShiroRealm extends AuthorizingRealm {
    //用于用户查询
    @Autowired
    AdministratorsReposity administratorsReposity;
    @Autowired
    CustomerReposity customerReposity;
    @Autowired
    StaffReposity staffReposity;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    SuperAdministratorsReposity superAdministratorsReposity;
    /*
    执行授权逻辑
    //角色权限和对应权限添加
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
         System.out.println("执行授权逻辑");
         //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        System.out.println("principal:"+principal.getClass());
        if (principal.getClass() == Administrators.class){
            System.out.println("admin");
            info.addRole("admin");
            List<Permission> permissions = permissionMapper.getPermByRid(1);
            for (Permission perm:permissions){
                info.addStringPermission(perm.getName());
            }
        }else if (principal.getClass() == Staff.class){
            System.out.println("staff");
            info.addRole("staff");
            List<Permission> permissions = permissionMapper.getPermByRid(2);
            for (Permission perm:permissions){
                info.addStringPermission(perm.getName());
            }
        }else{
            System.out.println("customer");
            info.addRole("customer");
            List<Permission> permissions = permissionMapper.getPermByRid(3);
            for (Permission perm:permissions){
                info.addStringPermission(perm.getName());
            }
        }
        System.out.println("授权结束");
        return info;
    }
    /*
    执行认证逻辑
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //1、判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String num = token.getUsername();
        char first = num.charAt(0);
        if (first=='0')//表示为超级管理员
        {
            List<SuperAdministrators> list = superAdministratorsReposity.findSuperAdministratorsBySuperAdministratorsNum(num);
            System.out.println("list:"+list.size());
            if (list.size() == 0){
                return null;
            }
            //2、判断密码
            //第一个参数是需要返回给login方法的一些数据，不返回可以留空
            //第二个参数就是数据库的密码
            //第三个参数是shiro的名字
            return new SimpleAuthenticationInfo(list.get(0),list.get(0).getPassword(),"");
        }
        else if (first=='1')//表示为管理员
        {
            List<Administrators> list = administratorsReposity.findByAdministratorsNum(num);
            System.out.println("list:"+list.size());
            if (list.size() == 0){
                return null;
            }
            //2、判断密码
            //第一个参数是需要返回给login方法的一些数据，不返回可以留空
            //第二个参数就是数据库的密码
            //第三个参数是shiro的名字
            return new SimpleAuthenticationInfo(list.get(0),list.get(0).getPassword(),"");
        }
        else if(first=='2')//表示工作人员
        {
            List<Staff> list = staffReposity.findByStaffNum(num);
            System.out.println("list:"+list.size());
            if (list.size() == 0){
                return null;
            }
            return new SimpleAuthenticationInfo(list.get(0),list.get(0).getPassword(),"");
        }
        else{
            List<Customer> list = customerReposity.findByCustomerNum(num);
            System.out.println("list:"+list.size());
            if (list.size() == 0){
                return null;
            }
            return new SimpleAuthenticationInfo(list.get(0),list.get(0).getPassword(),"");

        }

        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
//        if (authenticationToken.getPrincipal() == null) {
//            return null;
//        }
//        //获取用户信息
//        String name = authenticationToken.getPrincipal().toString();
//        //查找用户
//        User user = loginService.findByName(name);
//        if (user == null) {
//            //这里返回后会报出对应异常
//            return null;
//        } else {
//            //这里验证authenticationToken和simpleAuthenticationInfo的信息
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
//            return simpleAuthenticationInfo;
//        }
        //return null;
    }
}

