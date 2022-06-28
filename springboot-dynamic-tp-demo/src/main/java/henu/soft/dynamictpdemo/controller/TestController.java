package henu.soft.dynamictpdemo.controller;

import com.dtp.core.DtpRegistry;
import com.dtp.core.support.NamedRunnable;
import com.dtp.core.thread.DtpExecutor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 模拟多线程任务
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/dtp-nacos-example/test")
    public String test() throws InterruptedException {
        task();
        return "success";
    }


    public void task() throws InterruptedException {
        DtpExecutor dtpExecutor2 = DtpRegistry.getDtpExecutor("dtpExecutor2");
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            dtpExecutor1.execute(() -> {
                log.info("当前线程name : {} ====> i am dynamic-tp-test-1 task",Thread.currentThread().getName());
            });
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
}
