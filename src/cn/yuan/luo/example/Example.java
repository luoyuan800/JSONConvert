package cn.yuan.luo.example;

import cn.yuan.luo.json.JSON;
import cn.yuan.luo.json.SimpleToken;

/**
 * Created by gluo on 7/7/2016.
 */
public class Example {
    public static void main(String...args){
        //Create JSON String
        SimpleToken token = new SimpleToken();
        token.setValue("name", "test");
        token.setValue("location", "cn");
        token.setValue("price", 10000);
        token.setValue("change", 0.1);
        System.out.println(token.toJSONString());
        //Parse JSON String
        String content = "{result:{{\"name\":\"test\",\"location\":\"cn\",\"price\":100},{\"location\":\"cn\",\"price\":100,\"name\":\"test\"}}}";
        JSON json = new JSON(content);
        json.parse();
        System.out.println(json.getTokens().size());
        System.out.println(json);

    }
}
