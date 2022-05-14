package henu.soft.controller;

import henu.soft.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeptConsumerController {

    //    RestTemplate 我们需要注入直接调用，无@Bean,需要自己config配置

    //    三个主要的参数（url,实体Map,Class responseType)


    @Autowired
    RestTemplate restTemplate;// 模板，提供多种便捷访问远程http服务的方式

    private static final String REST_URL_PREFIX = "http://localhost:8001";

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable Long id){
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id,Dept.class);
    }






}
