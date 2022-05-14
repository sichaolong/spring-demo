package study_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo1 {
    public static void main(String[] args) {

        User user1 = new User("xiaosi1",20,18000);
        User user2 = new User("xiaosi2",21,19000);
        User user3 = new User("xiaosi3",23,20000);
        User user4 = new User("xiaosi4",24,21000);
        User user5 = new User("xiaosi5",25,22000);
        User user6 = new User("xiaosi6",26,23000);

        List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6);

        // 1. forEach

        list.stream().forEach((user)->{
            System.out.println(user);

        });

        list.stream().close();

        System.out.println("===================");


        //2.  filter、Predicate判定式函数接口
        // 筛选年龄在21之上，薪水在20000以上的user

        list.stream().
                filter((user)->{ return user.getSalary() > 20000; }).
                filter((user)->{return user.getAge()>= 21; }).
                forEach(System.out::println);


        list.stream().close();

        System.out.println("==================");

        // 3. map(Function<? super T,? extends R> mapper)
        //返回由给定函数应用于此流的元素的结果组成的流。
        // salary排序

        list.stream().
                filter((user)->{return user.getSalary() < 20000; }).
                map((user)->{ return user.getSalary() + 2000; }).
                sorted((u1,u2)->{ return u1.compareTo(u1); }).
                forEach(System.out::println);


        list.stream().close();

    }




}



class User{
    private String name;
    private int age;
    private int salary;

    public User(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
