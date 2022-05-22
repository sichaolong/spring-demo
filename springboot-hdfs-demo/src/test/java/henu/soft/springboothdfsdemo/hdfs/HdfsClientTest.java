package henu.soft.springboothdfsdemo.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@SpringBootTest
public class HdfsClientTest {

    /**
     * 1. 创建目录
     *
     * @throws Exception
     */
    @Test
    void mkdirTest() throws Exception {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), conf, "root");
        boolean mkdirs = fs.mkdirs(new Path("/user/sichaolong/"));
        System.out.println("是否成功：" + mkdirs);
        fs.close();

    }

    @Test // 上传文件
    public void uploadTest() throws Exception {
        // 1 创建配置对象
        Configuration conf = new Configuration();
        // 对文件创建两个副本
        // 配置优先级是参数优先级排序：（1）客户端代码中设置的值 >（2）ClassPath下的用户自定义配置文件 >（3）然后是服务器的默认配置
        conf.set("dfs.replication", "2");
        // 2 获取文件系统对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), conf, "root");
        // 3 执行上传
        fs.copyFromLocalFile(new Path("E:\\项目\\spring-demo\\springboot-hdfs-demo\\src\\main\\resources\\uploadFile.txt"), new Path("/uploadFile.txt"));
        System.out.println("上传成功");
        // 关闭资源
        fs.close();
    }

    @Test // 下载文件
    public void download() throws Exception {
        // 1 创建配置对象
        Configuration conf = new Configuration();
        // 2 获取文件系统对象
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), conf, "root");
        // 3 执行上传
        // 第一个参数：是否要删除源文件
        // 第二个参数：要下载的文件
        // 第三个参数：下载到哪个位置
        // 第四个参数：是否使用本地文件系统（要不要进行校验）
        fs.copyToLocalFile(false, new Path("/uploadFile.txt"), new Path("E:\\项目\\spring-demo\\springboot-hdfs-demo\\src\\main\\resources\\uploadFileTestDownload.txt"), true);
        System.out.println("下载成功");
        // 关闭资源
        fs.close();
    }

    @Test
    public void delete() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");

        // 2 执行删除
        //参数1：要删除的路径
        //参数2：是否递归删除，如果删除的是个目录，则需要置为true
        fs.delete(new Path("/user/xiaosi"), true);
        System.out.println("删除成功");
        // 3 关闭资源
        fs.close();


    }

    @Test
    public void rename() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://bd-offcn-01:8020"), configuration, "offcn");
        // 2 重命名目录
        boolean rename = fs.rename(new Path("/bigdata/0309/test.jpg"), new Path("/bigdata/0309/newtest.jpg"));
        System.out.println("是否重命名成功：" + rename);
        // 3 关闭资源
        fs.close();
    }

    @Test
    public void showDetail() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");

        // 2 查看详情
        RemoteIterator<LocatedFileStatus> iter = fs.listFiles(new Path("/uploadFile.txt"), true);
        while (iter.hasNext()) {
            LocatedFileStatus fileStatus = iter.next();
            // 获取文件名
            String name = fileStatus.getPath().getName();
            System.out.println(name);
            // 获取权限
            FsPermission permission = fileStatus.getPermission();
            System.out.println(permission.toString());
            // 获取拥有者
            String owner = fileStatus.getOwner();
            System.out.println(owner);
            // 获取组
            String group = fileStatus.getGroup();
            System.out.println(group);
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println("块位置信息" + Arrays.toString(blockLocations));
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println("host:" + host);
                }
            }
            System.out.println("---------------------------------------");
        }
        // 3 关闭资源
        fs.close();

    }

    @Test
    public void isFileOrdirectory()throws IOException,InterruptedException,URISyntaxException{
        // 1 获取文件系统
        Configuration configuration=new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        // 2 查看详情
        //boolean directory = fs.isDirectory(new Path("/0622"));
        //System.out.println("是否是目录："+directory);
        FileStatus[]fileStatuses=fs.listStatus(new Path("/user"));
        for(FileStatus fileStatus:fileStatuses){
            if(fileStatus.isFile()){
                System.out.println(fileStatus.getPath()+"是文件");
            }else{
                System.out.println(fileStatus.getPath()+"不是文件");

            }
        }
        // 3 关闭资源
        fs.close();
    }

    @Test
    public void ioUpload() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        // 2 读取本地磁盘数据到内存  FileInputStream
        FileInputStream fis = new FileInputStream("d:/test/test.jpg");
        // 3 将内存的数据用输出流写出到hdfs  输出流
        FSDataOutputStream fsdos = fs.create(new Path("/bigdata/test.jpg"));
        // 4 流对接
        IOUtils.copyBytes(fis,fsdos,configuration);
        // 5 关闭资源
        fsdos.close();
        fis.close();
        fs.close();
    }

    @Test
    public void ioDownload2() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        // 2 创建输入流，将数据从hdfs读取进内存
        FSDataInputStream fsdis = fs.open(new Path("/bigdata/jdk-8u144-linux-x64.tar.gz"));
        // 3 创建输出流，将内存中的数据写出到本地磁盘
        FileOutputStream fos = new FileOutputStream("d:/test/0623-1.tar.gz");
        // 4 流对接
        byte[] buffer = new byte[512]; // 1k
        for (int i = 0; i < 2*1024 * 128; i++) {
            fsdis.read(buffer);
            fos.write(buffer);
        }
        // 5 关闭资源
        fos.close();
        fsdis.close();
        fs.close();
    }

    @Test
    public void ioDownload3() throws IOException, InterruptedException, URISyntaxException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        // 2 创建输入流，将数据从hdfs读取进内存
        FSDataInputStream fsdis = fs.open(new Path("/bigdata/jdk-8u144-linux-x64.tar.gz"));
        // 3 创建输出流，将内存中的数据写出到本地磁盘
        FileOutputStream fos = new FileOutputStream("d:/test/0623-2.tar.gz");
        fsdis.seek(1024*1024*128);
        // 4 流对接
        IOUtils.copyBytes(fsdis,fos,configuration);
        // 5 关闭资源
        fos.close();
        fsdis.close();
        fs.close();
    }


}
