package cn.yuan.luo.json;

/**
 * Created by luoyuan on 2016/6/18.
 * SimpleToken keep data in a map
 */
import cn.yuan.luo.json.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleToken {
    private String content;
    private Map<String, JSONValue> data;

    public SimpleToken(String content) {
        this();
        this.content = content;
    }

    public SimpleToken() {
        data = new HashMap<>();
    }

    public void parse() {
        String[] array = StringUtils.split(content, ",(?=\")");
        for (int i =0 ; i< array.length; i++) {
            String[] entry = StringUtils.split(array[i],"(?<=\"):");
            if (entry.length > 1) {
                if(entry[1].startsWith("[") && !entry[1].endsWith("]")){
                    while(i < array.length) {
                        entry[1] = entry[1] + "," + array[++i];
                        if(i < array.length && array[i].endsWith("]")){
                            break;
                        }
                    }
                }
                if (entry[1].matches("^\\[.*\\]$")) {
                    JSONValue<List<JSONValue>> value = new JSONValue<>();
                    entry[1] = entry[1].replaceAll("\\[|\\]", "");
                    String[] listArray = StringUtils.split(entry[1], ",");
                    List<JSONValue> jsondata = new ArrayList<>();
                    for (String s : listArray) {
                        JSONValue value1 = buildJSONValue(s);
                        jsondata.add(value1);
                    }
                    value.setValue(jsondata);
                    data.put(entry[0].replaceAll("\"", ""), value);
                } else {
                    data.put(entry[0].replaceAll("\"", ""), buildJSONValue(entry[1]));
                }
            }
        }
    }

    private JSONValue buildJSONValue(String value) {
        if(value.matches("\\d+")) {
            //Number type
            if(value.indexOf(".") > 0){
                //Double
                JSONValue<Double> jsonValue = new JSONValue<>();
                jsonValue.setValue(StringUtils.toDouble(value));
                return jsonValue;
            }else{
                //Long
                JSONValue<Long> jsonValue = new JSONValue<>();
                jsonValue.setValue(StringUtils.toLong(value));
                return jsonValue;
            }

        }else{
            //String
            JSONValue<String> jsonValue = new JSONValue<>();
            jsonValue.setValue(value.replaceAll("\"", ""));
            return jsonValue;
        }
    }

    public String toString() {
        return data.toString();
    }

    public Map<String, JSONValue> getData() {
        return data;
    }

    public String toJSONString() {
        StringBuilder builder = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, JSONValue> entry : data.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\"").append(":").append(entry.getValue().toString());
            if (i < data.size() - 1) {
                builder.append(",");
            }
            i++;
        }
        builder.append("}");
        return builder.toString();
    }

    public <T> T getValue(String key) {
        JSONValue value = data.get(key);
        if (value != null && value.getValue() != null) {
            return (T) value.getValue();
        }
        return null;
    }

    public <T> void setValue(String key, T value) {
        JSONValue<T> jsonValue = new JSONValue<>();
        jsonValue.setValue(value);
        this.data.put(key, jsonValue);
    }

    public void removeValue(String key){
        data.remove(key);
    }
}

