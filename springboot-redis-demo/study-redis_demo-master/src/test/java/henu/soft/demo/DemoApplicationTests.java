package henu.soft.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import henu.soft.demo.pojo.User;
import henu.soft.demo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class DemoApplicationTests {


    @Autowired
    @Qualifier("redisTemplate")// 重名时，指定
    private RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;




    @Test
    void contextLoads() {
    }


    @Test
    void test1(){
        redisTemplate.opsForValue().set("name","xiaosi");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);

    }

    @Test
    void test2() throws JsonProcessingException {
        User user = new User("亚索",20);
        // 一般使用json传递数据
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("jsonUser",jsonUser);
        redisTemplate.opsForValue().set("User",user);
        System.out.println(redisTemplate.opsForValue().get("user"));

    }

    @Test
    void testRedisUtil(){

        redisUtil.set("redisUtil","redisUtil");
        Object s = this.redisUtil.get("redisUtil");
        System.out.println(s);





    }




}
