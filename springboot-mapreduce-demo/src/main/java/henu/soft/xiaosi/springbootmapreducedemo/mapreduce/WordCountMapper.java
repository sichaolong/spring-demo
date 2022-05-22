package henu.soft.xiaosi.springbootmapreducedemo.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;


/// 因为我们将框架给我们送过来的数据处理完毕后，要组装kv对输出，我们想要将单词作为key输出，用Text表示
// 将单词出现的次数，即1，作为value输出，用IntWritable表示  <单词，1>
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text k = new Text();
    IntWritable v = new IntWritable(1);
    Logger logger = LogManager.getLogger(WordCountMapper.class);

    // map方法什么时候调用一次？框架每读取一行数据，封装一次kv对，调用一次map方法
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 业务逻辑
        // 1 将每行内容转化为字符串，hello world
        String line = value.toString();
        // 2 将每行内容按照空格切割，[hello,world]
        String[] words = line.split(" ");
        // 3 遍历数组，取出每个单词 ,hello,
        for (String word : words) {
            // 4 按照<单词，1> 的样式，组装kv对
//            logger.info("hhhhhhhhhhhh: "+word);
//            System.out.println("hahahaha");
            k.set(word);
            // v.set(1);
            // 5 将kv对写出
            context.write(k,v);
        }
    }
}

