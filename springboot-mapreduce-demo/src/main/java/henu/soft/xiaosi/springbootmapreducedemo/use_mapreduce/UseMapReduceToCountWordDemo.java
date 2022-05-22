package henu.soft.xiaosi.springbootmapreducedemo.use_mapreduce;

import henu.soft.xiaosi.springbootmapreducedemo.mapreduce.WordCountMapper;
import henu.soft.xiaosi.springbootmapreducedemo.mapreduce.WordCountReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UseMapReduceToCountWordDemo {

    public static void main(String[] args) throws Exception {
        // 1 创建一个配置对象
        Configuration conf = new Configuration();
        // 2 通过配置对象创建一个job
        Job job = Job.getInstance(conf);
        // 3 设置job的mr的路径(jar包的位置)
        job.setJarByClass(UseMapReduceToCountWordDemo.class);
        // 4 设置job的mapper类  reduce类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);
        // 5 设置job的mapper类的keyout，valueout
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 6 设置job的最终输出的keyout，valueout
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // 7 设置job的输入数据的路径
        FileInputFormat.setInputPaths(job,new Path("E:\\项目\\spring-demo\\springboot-mapreduce-demo\\src\\main\\resources\\input"));
        // 8 设置job的输出数据的路径 得保证，输出目录不能事先存在，否则报错，
        FileSystem fs = FileSystem.get(conf);
        Path pp = new Path("E:\\项目\\spring-demo\\springboot-mapreduce-demo\\src\\main\\resources\\output");
        if(fs.exists(pp)){
            //递归删除
            fs.delete(pp, true);
        }
        FileOutputFormat.setOutputPath(job,pp);

        // 9 提交job到yarn集群
        boolean b = job.waitForCompletion(true);
        System.out.println("是否运行成功："+b);
    }

}
