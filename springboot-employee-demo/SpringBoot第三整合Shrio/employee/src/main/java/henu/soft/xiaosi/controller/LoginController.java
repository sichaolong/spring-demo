package henu.soft.xiaosi.controller;

import javax.servlet.http.HttpSession;

import henu.soft.xiaosi.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.thymeleaf.util.StringUtils;



@Controller
public class LoginController {
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model){

        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        //在UserRelam内实现验证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
            //这里登陆成功设置Session,Thymeleaf整合Shiro

            //当前登录用户
            User loginUser = (User) subject.getPrincipal();
            //获取Session
            Session LoginUserSession = subject.getSession();
            //待会可以取出来

            LoginUserSession.setAttribute("loginSession",loginUser);
            return "redirect:/main.html";

        } catch(UnknownAccountException e){
            model.addAttribute("msg","用户名错误！");
            return "index";
        } catch(IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误！");
            return "index";
        }
    }

    /*
    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model, HttpSession session){
        //具体的业务
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){

            //设置登录成功的Session
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }
        else{
            model.addAttribute("msg","用户名或者密码错误!");
            return "index";
        }



    }
    */
    @RequestMapping("/logout")
    public String logout(){
        //注销功能
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.html";
    }
}
