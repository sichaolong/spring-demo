package service.impl;

import org.springframework.stereotype.Component;
import service.HelloService;

@Component
public class HelloServiceImpl implements HelloService {
    public void sayHello() {
        System.out.println("你好呀！");
    }
}
