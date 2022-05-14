package henu.soft.xiaosi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserLimitController {

    @RequestMapping("/limit")
    @ResponseBody
    public String limit(){
        return "权限不足，请联系管理员获取权限！";
    }
}
