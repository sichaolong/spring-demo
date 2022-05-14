package henu.soft.xiaosi.controller;

import org.springframework.web.bind.annotation.GetMapping;


/**
 * 访问首页
 */
public class IndexController {

    @GetMapping({"/","index"})
    public String index(){
        return "index";
    }

}
