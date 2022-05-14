package henu.soft.xiaosi.springbootorderrabbitmqproducer;

import henu.soft.xiaosi.springbootorderrabbitmqproducer.service.DirectOrderService;
import henu.soft.xiaosi.springbootorderrabbitmqproducer.service.FanoutOrderService;
import henu.soft.xiaosi.springbootorderrabbitmqproducer.service.TopicOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootOrderRabbitmqProducerApplicationTests {


    @Autowired
    FanoutOrderService fanoutOrderService;

    @Autowired
    DirectOrderService directOrderService;

    @Autowired
    TopicOrderService topicOrderService;


    @Test
    void contextLoads() {
    }

    @Test
    void testFanout(){
        fanoutOrderService.makeOrder("1","1",6);
        // 输出 订单生成成功5a229eda-da45-4f3b-b9e9-92c3184f6265
    }

    @Test
    void testDirect(){
        directOrderService.makeOrder("1","1",6,"email");
        // 输出 订单生成成功c5ff0e9d-f5e3-4b19-8594-cabd40fec108
    }

    @Test
    void testTopic(){
        topicOrderService.makeOrder("1","1",6,"xiaosi.email.2222");
        // 输出 订单生成成功c5ff0e9d-f5e3-4b19-8594-cabd40fec108
    }



}
