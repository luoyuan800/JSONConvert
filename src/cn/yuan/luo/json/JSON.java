package cn.yuan.luo.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by luoyuan on 2016/6/18.
 * You an use this class to parse a json String, it will keep the data in SimpleToken
 */
public class JSON {
    private String content;
    private List<SimpleToken> tokens = new ArrayList<>();

    public JSON(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void parse() {
        if(content!=null) {
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < content.length(); i++) {
                char charAt = content.charAt(i);
                if (charAt != '}') {
                    stack.push((int) charAt);
                } else {
                    StringBuilder builder = new StringBuilder();
                    while (stack.peek() != '{') {
                        builder.append((char) stack.pop().intValue());
                    }
                    if (stack.peek() == '{') {
                        stack.pop();
                    }
                    builder.reverse();
                    if (!builder.toString().replaceAll("\\[|\\]|:|(results)|\"|,", "").isEmpty()) {
                        SimpleToken simpleToken = new SimpleToken(builder.toString());
                        simpleToken.parse();
                        if (!simpleToken.getData().isEmpty()) {
                            tokens.add(simpleToken);
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        return tokens.toString();
    }

    public List<SimpleToken> getTokens() {
        return tokens;
    }
}
