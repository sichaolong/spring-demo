package henu.soft.xiaosi.springbootmapreducedemo.use_map;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class UseMapToCountWordDemo {
    // 定义一个
    private static BufferedReader br;

    static {
        // 加载单词文件
        try {
            br = new BufferedReader(new FileReader(new File("E:\\项目\\spring-demo\\springboot-hdfs-demo\\src\\main\\resources\\wordCountContent.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述
     * @param args 运行参数
     * @throws IOException 读取文件时的异常
     */
    public static void main(String[] args) throws IOException {
        // 1,创建一个Map集合保存统计结果
        TreeMap<String, Integer> result_Map = new TreeMap<>();
        // 2, 逐行读取单词文件的内容
        String line;
        while ((line = br.readLine())!=null){
            // 3, 拆分每一行的单词
            String[] words = line.split("\t");//每行单词之间使用tab键分隔
            // 4, 遍历每一个单词
            for (String word : words) {
                // 5, 过滤空白字符单词
                if (!isEmpty(word)){
                    // 6, 查询Map集合中是否已经记录了该单词
                    Integer count = result_Map.get(word);
                    // 7, 如果返回的count是null,代表之前未存入该单词,应存入单词和对应的次数1
                    // 如果返回的count不是null,代表之前已经存入该单词,应该更新单词的个数,即count+1
                    Integer res = count == null ? result_Map.put(word, 1) : result_Map.put(word, count + 1);
                }
            }
        }
        // 8, 遍历result_Map,查看统计结果
        for (Map.Entry<String, Integer> entry : result_Map.entrySet()) {
            System.out.println(entry.getKey()+"->"+entry.getValue());
        }
    }
    /**
     * 功能描述
     * @param string 传入的字符串
     * @return true:传入的字符串为空 , false:传入的字符串不为空
     */
    private static boolean isEmpty(String string){
        if (string == null || string.length() == 0 || string.replace(" ","").length() == 0){
            return true;
        }
        return false;
    }

}




