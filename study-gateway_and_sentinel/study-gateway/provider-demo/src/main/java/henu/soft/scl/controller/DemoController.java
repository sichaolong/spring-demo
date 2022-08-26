package henu.soft.scl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sichaolong
 * @date 2022/8/18 18:42
 *
 */
@RestController
public class DemoController {

    @RequestMapping(value = "/test/hello", method = RequestMethod.GET)
    public String demo(){
        return "hello,provider";
    }

    /**
     * name : DemoController.java
     * creator : sichaolong
     * date : 2022/8/19 16:40
     * descript :
    **/


    @RequestMapping(value = "/loadbalance/hello", method = RequestMethod.GET)
    public String loadbalanceByYml(){
        return "hello,provider1,port:80";
    }

    @RequestMapping(value = "/lbtest/hello", method = RequestMethod.GET)
    public String loadbalanceByConfigClass(){
        return "hello,provider1,port:80,this is config class ..";
    }
}
