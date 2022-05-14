package henu.soft.xiaosi.config;

import henu.soft.xiaosi.mapper.UserMapper;
import henu.soft.xiaosi.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        //System.out.println("debug===>" + currentUser.getLimits());

        //拿出来user表中limits字段
        info.addStringPermission(currentUser.getLimits());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String username = "scl";
//        String password = "123456";


        //刚才的token是全局的，可以在这里取出认证
       UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userMapper.findLoginUserByName(token.getUsername());

        if(user == null){
           //用户名认证，用户名不同会自动抛出用户不存在异常
           return null;
       }

        System.out.println("认证方法执行了debug===>" + user.getLimits());
       //密码认证Shiro自己做,如果还需要设置权限授予，需要将user参数传递给SimpleAuthenticationInfo
       return new SimpleAuthenticationInfo(user,user.getPassword(),"");

    }
}
