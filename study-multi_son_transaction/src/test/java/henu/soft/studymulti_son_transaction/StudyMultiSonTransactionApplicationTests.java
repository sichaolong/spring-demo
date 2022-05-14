package henu.soft.studymulti_son_transaction;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
class StudyMultiSonTransactionApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    DataSourceTransactionManager transactionManager;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    void testMySQLConnection(){
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            System.out.println(connection);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
        }

    }
    @Test
    @Transactional
    void testUpdate(){

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int update = jdbcTemplate.update("update  `user` set age = 1 where id = 1");
                    System.out.println("线程1更新结果===>" + update);

                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int update = jdbcTemplate.update("update  `user_money` set money = 100000 where id = 1");
                    System.out.println("线程2更新结果===> " + update);

                }
            });
            t1.start();
            t2.start();
            int update = jdbcTemplate.update("update  `user` set age = 2 where id = 2");

        System.out.println("主线程更新结果===> " + update);



    }
    @Test
    @Transactional
    void testMultiThreadUpdate() throws ExecutionException, InterruptedException {

        FutureTask<Integer> task1 = new FutureTask<>(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                System.out.println("任务1的Connection:" + jdbcTemplate.getDataSource().getConnection().toString());
                return jdbcTemplate.update("update  `user` set age = 1 where id = 1");

            }
        });

        FutureTask<Integer> task2 = new FutureTask<>(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                System.out.println("任务2的Connection:" + jdbcTemplate.getDataSource().getConnection().toString());
                return jdbcTemplate.update("update  `user_money` set money = 1 where id = 1");
            }
        });


        executor.execute(task1);
        executor.execute(task2);
        System.out.println("任务1更新结果===> " + task1.get());
        System.out.println("任务2更新结果===> " + task2.get());



        int update = jdbcTemplate.update("update  `user` set age = 20 where id = 4");
        System.out.println("主线程手动创造异常"+ 1/0);
        System.out.println("主线程更新结果===> " + update);






    }

    @Test
    @Transactional
    void testCompletableFutureMultiThread() throws ExecutionException, InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println(jdbcTemplate.getDataSource().getConnection().toString());
            }
        },executor);

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(jdbcTemplate.getDataSource().getConnection().toString());
            }
        },executor);

        task1.get();
        task2.get();


    }
    @Test
    void testTransactionManagerMultiThread() throws ExecutionException, InterruptedException, BrokenBarrierException {



        // 所有线程执行的结果
        AtomicBoolean result = new AtomicBoolean(true);

        // 子任务的数量
        int subTaskCount = 2;

        // 栅栏，保证所有子线程任务执行完成
        CyclicBarrier endLine = new CyclicBarrier(subTaskCount +1);

        // 子任务1
        executor.execute(()->{
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            try {
                System.out.println("子任务1开始执行！当前线程===> " + Thread.currentThread().getName());
                // 子任务1执行
                jdbcTemplate.update("update  `user` set age = 100 where id = 1");
                System.out.println("子事务1的con===> " + jdbcTemplate.getDataSource().getConnection().toString());

                // 子事务1模拟异常
//                int byZero = 1 / 0;
//                System.out.println("子任务1发生异常！");


            } catch (Exception e) {

                // 设置全局结果
                result.set(false);
                System.out.println("子任务1执行失败！");

            }
            // 等待全局结果
            try {
                endLine.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            // 等待全局结果才能提交
            if(result.get() == true){
                transactionManager.commit(status);
                System.out.println("子事务1中根据整体结果提交事务！");
            }else {
                transactionManager.rollback(status);
                System.out.println("子事务1中根据整体结果回滚事务！");
            }




        });

        // 子任务2
        executor.execute(()->{
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            try {
                System.out.println("子任务2开始执行！当前线程===> " + Thread.currentThread().getName());

                // 子任务2执行
                jdbcTemplate.update("update  `user_money` set money = 10000 where id = 1");
                System.out.println("子事务2的con===> " + jdbcTemplate.getDataSource().getConnection().toString());


            } catch (Exception e) {
                result.set(false);
                System.out.println("子任务2执行失败！");
            }

            // 等待全局结果
            try {
                endLine.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            // 等待全局结果才能提交
            if(result.get()){
                transactionManager.commit(status);
                System.out.println("子事务2根据整体结果提交事务！");
            }else{
                transactionManager.rollback(status);
                System.out.println("子事务2根据整体结果回滚事务！");

            }

        });

        // 主线程执行
        endLine.await();
        System.out.println("整体执行的结果===> "  + result);


    }

}
