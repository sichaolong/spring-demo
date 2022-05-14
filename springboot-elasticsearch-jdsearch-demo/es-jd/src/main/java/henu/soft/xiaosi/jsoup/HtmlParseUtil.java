package henu.soft.xiaosi.jsoup;

import henu.soft.xiaosi.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public  class HtmlParseUtil {

    public static void main(String[] args) throws IOException {


        System.out.println(parseJD("jvm"));


    }

    public static List<Content> parseJD(String keywords) throws IOException {

        String urlKeywords = URLEncoder.encode(keywords, "UTF-8");
        String url = "https://search.jd.com/Search?keyword=" + urlKeywords + "&enc=utf-8";

        // 跳过登录验证
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN) AppleWebKit/535.12 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/535.12").timeout(30000).get();
        // 所有商品div
        Element goodsList = document.getElementById("J_goodsList");
        //System.out.println(goodsList);

        // 获取所有的li
        Elements goodsLis = goodsList.getElementsByTag("li");

        List<Content> allGoodsList = new ArrayList<>();


        // 遍历每个li
        for (Element li : goodsLis) {
            //System.out.println(li);

            String imgUrl = li.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = li.getElementsByClass("p-price").eq(0).text();
            String name = li.getElementsByClass("p-name").eq(0).text();
            //String commit = li.getElementsByClass("p-commit").tagName("a").text(); 拿不到

            /*
            System.out.println("===============");
            System.out.println(imgUrl);
            System.out.println(price);
            System.out.println(name);
            */

            allGoodsList.add(new Content(name,price,imgUrl));




        }



        return allGoodsList;



    }
}
