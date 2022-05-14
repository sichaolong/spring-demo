package study_rw_lock;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * ReadWriteLock
 */

public class Demo1_ReadWriteLock {

    public static void main(String[] args) {

       // MyCacheWriteReadLock cache = new MyCacheWriteReadLock();
        MyCache_Synchronized cache = new MyCache_Synchronized();

        // 模拟4个线程写入缓存
        for (int i = 1; i <= 4; i++) {
            final int temp = i;
            new Thread(()->{

                cache.save(temp +"",temp+"");

            },String.valueOf(i)).start();

        }

        // 模拟4个线程读取缓存
        for (int i = 1; i <= 4; i++) {
            final int temp = i;
            new Thread(()->{

                cache.get(temp +"");

            },String.valueOf(i)).start();

        }

    }
}



/**
 * 1. 模拟缓存 不加锁 写入、读取
 */
class MyCache {

   private volatile Map<String,String>  cache = new HashMap<>();

    public void  save(String key,String value){
        System.out.println(Thread.currentThread().getName() + "===》开始写入数据");
        cache.put(key,value);
        System.out.println(Thread.currentThread().getName() + "===》写入成功了 " + key + "---" + value);

    }
    public void get(String key){
        System.out.println(Thread.currentThread().getName() + "===》开始获取数据");
        String result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + "===》获取成功了 " + result);

    }
}
/**
 * 3. 模拟缓存 加读写锁 写入、读取
 */
class MyCacheWriteReadLock {

    private volatile Map<String,String>  cache = new HashMap<>();

    // 读写锁对象
    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void save(String key,String value){

        // 加入写锁,保证只能同时有一个线程来写
        reentrantReadWriteLock.writeLock().lock();


        try {
            System.out.println(Thread.currentThread().getName() + "===》开始写入数据");
            cache.put(key,value);
            System.out.println(Thread.currentThread().getName() + "===》写入成功了 " + key + "---" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放写锁
            reentrantReadWriteLock.writeLock().unlock();
        }

    }
    public void get(String key){
        // 加上读锁，保证读的时候不能有其他线程写
        reentrantReadWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "===》开始获取数据");
            String result = cache.get(key);
            System.out.println(Thread.currentThread().getName() + "===》获取成功了 " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            reentrantReadWriteLock.readLock().unlock();

        }

    }
}


/**
 * 4. 模拟缓存 加synchronized 写入、读取
 */
class MyCache_Synchronized {

    private volatile Map<String,String>  cache = new HashMap<>();

    public  synchronized void  save(String key,String value){
        System.out.println(Thread.currentThread().getName() + "===》开始写入数据");
        cache.put(key,value);
        System.out.println(Thread.currentThread().getName() + "===》写入成功了 " + key + "---" + value);

    }
    public synchronized void  get(String key){
        System.out.println(Thread.currentThread().getName() + "===》开始获取数据");
        String result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + "===》获取成功了 " + result);

    }
}
/**
 * 3. 模拟缓存 加Lock锁 写入、读取
 */
class MyCacheLock {

    private volatile Map<String,String>  cache = new HashMap<>();

    Lock lock = new ReentrantLock();

    public  void   save(String key,String value){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "===》开始写入数据");
            cache.put(key,value);
            System.out.println(Thread.currentThread().getName() + "===》写入成功了 " + key + "---" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void  get(String key){

        System.out.println(Thread.currentThread().getName() + "===》开始获取数据");
        String result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + "===》获取成功了 " + result);

    }
}


