package henu.soft.xiaosi.springbootmapreducedemo.mapreduce;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class WordCountReduce extends Reducer<Text, IntWritable,Text,IntWritable> {

    //reduce方法什么使用调用一次？分组调用，框架会对所有的map输出的kv根据k进行分组，k相同的kv们为一组，对这一组kv们调用一次reduce方法
    // 会将k封装进key，将v们封装进迭代器values变量
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // <hello,1>
        // <hello,1>
        // <hello,1>
        //遍历values，将每个v进行累加
        int sum = 0;
        for (IntWritable value : values) {
            int i = value.get();
            sum = sum + i;
        }
        context.write(key, new IntWritable(sum));


    }
}

