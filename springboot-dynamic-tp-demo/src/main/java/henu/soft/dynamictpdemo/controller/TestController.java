package henu.soft.dynamictpdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.dromara.dynamictp.core.DtpRegistry;
import org.dromara.dynamictp.core.support.task.runnable.NamedRunnable;
import org.dromara.dynamictp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Redick01
 */
@Slf4j
@RestController
@SuppressWarnings("all")
public class TestController {

    @Resource
    private ThreadPoolExecutor dtpExecutor1;

    @Resource
    private ThreadPoolExecutor dtpExecutor2;

    @Resource
    private DtpExecutor dtpNacosExecutor1;



    /**
     * 模拟多线程任务，只有一个线程
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/dtp-nacos-example/test1")
    public String test1() throws InterruptedException {
        dtpExecutor1Task();
        return "success";
    }

    /**
     * 模拟多线程任务
     * @throws InterruptedException
     */

    public void dtpExecutor1Task() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            dtpExecutor1.execute(() -> {
                log.info("当前线程name : {} ====> i am dynamic-tp-test-1 task",Thread.currentThread().getName());
            });
        }
    }



    /**
     * 模拟多线程任务，注解定义的线程池
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/dtp-nacos-example/test2")
    public String test2() throws InterruptedException {
        dtpExecutor2Task();
        return "success";
    }

    /**
     * 注解配置的线程池测试任务
     * @throws InterruptedException
     */

    public void dtpExecutor2Task() throws InterruptedException {
        // DtpExecutor dtpExecutor2 = DtpRegistry.getDtpExecutor("dtpExecutor2");
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            dtpExecutor2.execute(NamedRunnable.of(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("当前线程name : {} ====> i am dynamic-tp-test-2 task",Thread.currentThread().getName());

            }, "task-" + i));
        }
    }


    /**
     * 模拟多线程任务，nacos yml 配置的线程池
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/dtp-nacos-example/test3")
    public String test3() throws InterruptedException {
        dtpNacosExecutor3Task();
        return "success";
    }

    /**
     * 注解配置的线程池测试任务
     * @throws InterruptedException
     */

    public void dtpNacosExecutor3Task() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            dtpNacosExecutor1.execute(NamedRunnable.of(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("当前线程name : {} ====> i am dynamic-tp-test-3 task",Thread.currentThread().getName());

            }, "task-" + i));
        }
    }


}
