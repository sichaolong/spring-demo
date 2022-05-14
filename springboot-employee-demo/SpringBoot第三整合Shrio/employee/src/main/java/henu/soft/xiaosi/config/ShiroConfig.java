package henu.soft.xiaosi.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * Shiro认证、授权
     * @param defaultWebSecurityManager
     * @return
     */

    //3.用户
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加内置过滤器

        Map<String,String> filterMap = new LinkedHashMap<>();

        //设置未登录认证拦截器
        filterMap.put("/emps","authc");
        filterMap.put("/add","authc");
        filterMap.put("/edit/*","authc");
        filterMap.put("/main.html","authc");
        filterMap.put("/getDepartmentById","authc");
        filterMap.put("/getDepartments/*","authc");


        //设置权限拦截器

        /*
        下面这些权限只能实现单资源单用户(因为Map的Key唯一)，对于单资源多用户需要设置角色)
         */

        filterMap.put("/add","perms[user:root]");



        filterMap.put("/add/*","perms[user:root]");


        filterMap.put("/edit","perms[user:root]");


        filterMap.put("/edit/*","perms[user:root]");

        filterMap.put("/delete/*","perms[user:root]");



        //设置登录的请求,转到自己写的认证页面
        bean.setLoginUrl("/toLogin");

        //未授权访问跳转页面，点击有权限的页面，会自动指定UserRealm的方法
        bean.setUnauthorizedUrl("/limit");

        bean.setFilterChainDefinitionMap(filterMap);


        return bean;
    }


    //2. 管理
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm对象
        securityManager.setRealm(userRealm);
        return securityManager;

    }

    //1. 创建Realm对象，用于处理数据
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }


    /**
     * Shiro整合Thymeleaf
     *
     * 	配置 ShiroDialect（Shiro 方言） 对象
     *
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
