## B站黑马JavaWeb增删改查小案例

by:henu.soft.xiaosi

### 一、案例简单介绍



![项目整体流程](C:\Users\司超龙\Desktop\项目整体流程.png)





![](C:\Users\司超龙\Desktop\案例概况.png)

### 1.1所用技术：

Servlet、JSP、MySQL、JDBCTemplete、Durid、BeanUtils、tomcat、EL、JSTL等

###  

### 1.2设计思想



1. 案例设计基本符合MVC模式，三层架构设计UserDao接口、UserDaoImpl实现子类、UserService接口、UserServiceImpl实现子类

2. 列表简单查询（查询数据库的数据到前端通过Servlet处理，然后JSP实现展示）

   

   ![](C:\Users\司超龙\Desktop\简单查询功能目录.png)

   

   

   首先点击访问的是UserListServlet容器，然后我们需要：

- 控制层service包下的 实现接口UserService的 子类UserServiceImpl对象 来调用findAll()方法（其实就是创建UserDaoImpl对象来调用findAll()方法）
- 数据操作层dao包下的 实现接口UserDao 的 子类UserDaoImpl对象在findAll()方法中编写sql语句，完成数据查询并封装成一个list集合返回 给 UserServiceImpl对象

3. 登录、增、删、改、查

![](C:\Users\司超龙\Desktop\目录.png)





4. 复杂功能

- 删除选中：删除指定记录需要用户id, 这些id就来自 复选框checkbox

  表单中，有多少勾选的记录，checkbox就能返回多少个对应的id，配合JS实现

- 分页查询：分页查询稍复杂，需要jsp提供每页显示的记录数，和开始查询的索引

  然后Dao需要查询总记录数、总页数、总页码、查询到的记录 显示到jsp页面

- 复杂条件查询：不定参数的处理不太容易，设置sql初始语句 where 1 = 1然后根据条件map中的value是否有值，有就添加到sql中

## 二、详细 实现过程、实现的截图、一些小错误等记录在CSDN:



https://blog.csdn.net/qq_24654501/article/details/109501708







