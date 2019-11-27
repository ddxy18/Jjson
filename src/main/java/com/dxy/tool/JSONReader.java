package com.dxy.tool;

import com.dxy.tool.exception.ElementExistException;
import com.dxy.tool.exception.IllegalEntryException;
import com.dxy.tool.exception.KeyExistException;

import java.util.Stack;

public class JSONReader {

    /*
     * @return json字符串不合法时打印具体错误信息并且返回null
     */
    public static Object toJavaModel(String jsonString) {
        try {
            if (jsonString.charAt(0) == '{' && jsonString.charAt(jsonString.length() - 1) == '}') {
                return toJSONObject(jsonString);
            }
            if (jsonString.charAt(0) == '[' && jsonString.charAt(jsonString.length() - 1) == ']') {
                return toJSONArray(jsonString);
            }
            return null;
        } catch (ElementExistException | IllegalEntryException | KeyExistException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*上级函数调用时只会根据首尾'{''}'来确认是一个对象,不会进行内部键值对的合法性检查;
     *该函数必须判断json对象字符串的合法性
     * json字符串中':'前后可以有空格,','后可以有'\n','{'后可以有'\n',最后一个entry后不能有','
     */
    private static JSONObject toJSONObject(String jsonString) throws IllegalEntryException, KeyExistException, ElementExistException {
        JSONObject jsonObject = new JSONObject();
        //pointer[0]和pointer[1]为取出子串的首尾指针
        int[] pointer = {1, 1};

        skip(jsonString, pointer);
        //空对象
        if (jsonString.charAt(pointer[1]) == '}') {
            return jsonObject;
        }
        pointer[0] = pointer[1];
        while (pointer[1] < jsonString.length()) {
            String key = "";
            Object value = null;

            //寻找键
            if (jsonString.charAt(pointer[0]) == '"') {
                key = getKey(jsonString, pointer);
                skip(jsonString, pointer);
                if (jsonString.charAt(pointer[1]) != ':') {
                    throw new IllegalEntryException("lack of ':' after key");
                }
                pointer[1]++;
                skip(jsonString, pointer);
                pointer[0] = pointer[1];
            }
            value = getValue(jsonString, pointer);
            jsonObject.put(key, value);
            skip(jsonString, pointer);
            if (jsonString.charAt(pointer[1]) == '}') {
                return jsonObject;
            }
            if (jsonString.charAt(pointer[1]) != ',') {
                throw new IllegalEntryException("cannot find ',' after one entry");
            }
            pointer[1]++;
            skip(jsonString, pointer);
            pointer[0] = pointer[1];
        }
        return jsonObject;
    }

    /*上级函数调用时只会根据首尾'['']'来确认是一个数组,不会进行内部元素的合法性检查;
     *该函数必须判断json数组字符串的合法性
     */
    private static JSONArray toJSONArray(String jsonString) throws KeyExistException, IllegalEntryException, ElementExistException {
        JSONArray jsonArray = new JSONArray();
        //pointer[0]和pointer[1]为取出子串的首尾指针
        int[] pointer = {1, 1};

        skip(jsonString, pointer);
        pointer[0] = pointer[1];
        //每一次循环取出一个元素
        while (pointer[1] < jsonString.length()) {
            if (jsonString.charAt(pointer[0]) == '{') {
                if (isPairs(jsonString, pointer, "{}")) {
                    jsonArray.put(toJSONObject(jsonString.substring(pointer[0], pointer[1])));
                } else {
                    throw new IllegalEntryException("cannot find the correct element");
                }
            } else if (jsonString.charAt(pointer[0]) == '[') {
                if (isPairs(jsonString, pointer, "[]")) {
                    jsonArray.put(toJSONArray(jsonString.substring(pointer[0], pointer[1])));
                }
            } else {
                throw new IllegalEntryException("cannot find the correct type of element,json only support array and object");
            }
            skip(jsonString, pointer);
            if (jsonString.charAt(pointer[1]) != ',' && jsonString.charAt(pointer[1]) != ']') {
                throw new IllegalEntryException("cannot find ',' between elements");
            }
            if (jsonString.charAt(pointer[1]) == ']') {
                return jsonArray;
            }
            pointer[1]++;
            skip(jsonString, pointer);
            pointer[0] = pointer[1];
        }
        return jsonArray;
    }

    private static String getKey(String jsonString, int[] pointer) throws IllegalEntryException {
        pointer[1]++;
        while (pointer[1] < jsonString.length() && jsonString.charAt(pointer[1]) != '\"') {
            pointer[1]++;
        }
        //键为空
        if (pointer[1] == pointer[0] + 1) {
            throw new IllegalEntryException("key cannot be empty");
        }
        if (pointer[1] == jsonString.length()) {
            throw new IllegalEntryException("cannot find corresponding right '\"' in key");
        }
        pointer[1]++;
        return jsonString.substring(pointer[0] + 1, pointer[1] - 1);
    }

    /*
     * 在查找到对应的value后pointer[0]为value的头,pointer[1]为value的尾后第一个字符
     */
    private static Object getValue(String jsonString, int[] pointer) throws IllegalEntryException, KeyExistException, ElementExistException {
        //value是string类型
        if (jsonString.charAt(pointer[0]) == '"') {
            while (pointer[1] < jsonString.length() && jsonString.charAt(pointer[1]) != ',') {
                pointer[1]++;
            }
            if (pointer[1] == jsonString.length()) {
                pointer[1] -= 2;
                back(jsonString, pointer);
                if (jsonString.charAt(pointer[1]) == '\"') {
                    int tmp = pointer[1];
                    pointer[1] = jsonString.length() - 1;
                    return jsonString.substring(pointer[0] + 1, tmp);
                }
                throw new IllegalEntryException("cannot find corresponding value");
            }
            return jsonString.substring(pointer[0] + 1, pointer[1] - 1);
        }
        //value是null类型
        if (jsonString.charAt(pointer[0]) == 'n') {
            if (jsonString.substring(pointer[0], pointer[0] + 4).equals("null")) {
                pointer[1] += 4;
                return null;
            }
        }
        //value是boolean类型
        if (jsonString.charAt(pointer[0]) == 't') {
            if (jsonString.substring(pointer[0], pointer[0] + 4).equals("true")) {
                pointer[1] += 4;
                return true;
            }
        }
        if (jsonString.charAt(pointer[0]) == 'f') {
            if (jsonString.substring(pointer[0], pointer[0] + 5).equals("false")) {
                pointer[1] += 5;
                return false;
            }
        }
        //value是number类型
        if (isNumber(jsonString.charAt(pointer[0]))) {
            //pointer[0]和pointer[1]之间包含的是number值
            while (pointer[1] < jsonString.length() && jsonString.charAt(pointer[1]) != ',') {
                pointer[1]++;
            }
            if (jsonString.charAt(pointer[1] - 1) == '}') {
                pointer[1] -= 2;
                back(jsonString, pointer);
                int tmp = pointer[1];
                pointer[1] = jsonString.length() - 1;
                return getNumber(jsonString.substring(pointer[0], tmp + 1));
            }
            return getNumber(jsonString.substring(pointer[0], pointer[1]));
        }
        //value是object类型
        if (jsonString.charAt(pointer[0]) == '{') {
            if (isPairs(jsonString, pointer, "{}")) {
                return toJSONObject(jsonString.substring(pointer[0], pointer[1]));
            }
        }
        //value是array类型
        if (jsonString.charAt(pointer[0]) == '[') {
            if (isPairs(jsonString, pointer, "[]")) {
                return toJSONArray(jsonString.substring(pointer[0], pointer[1]));
            }
        }
        throw new IllegalEntryException("unsupported value type,json only supports 'null','number','array','object','boolean'");
    }

    /*
     * 用于检测'{}','[]'是否成对出现
     */
    private static boolean isPairs(String jsonString, int[] pointer, String symbol) {
        Stack<Character> symbolStack = new Stack<>();
        symbolStack.push(jsonString.charAt(pointer[0]));
        pointer[1]++;

        while (pointer[1] < jsonString.length()) {
            if (symbolStack.empty()) {
                return true;
            }
            if (jsonString.charAt(pointer[1]) == symbol.charAt(0)) {
                symbolStack.push(jsonString.charAt(pointer[1]));
            } else if (jsonString.charAt(pointer[1]) == symbol.charAt(1)) {
                if (!symbolStack.empty()) {
                    symbolStack.pop();
                }
            }
            pointer[1]++;
        }
        return false;
    }

    /*
     * 如果是整数用Integer表示,如果是小数用Double表示
     * number参数并未经过合法性检查,当数字表示方式不合法时对应包装器类的parse函数会抛出异常
     * @param number 传来的数字字符串
     */
    private static Number getNumber(String number) {
        if (number.contains(".")) {
            return Double.parseDouble(number);
        } else {
            return Integer.parseInt(number);
        }
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /*
     * 跳过空格和换行
     */
    private static void skip(String jsonString, int[] pointer) {
        while (jsonString.charAt(pointer[1]) == ' ' || jsonString.charAt(pointer[1]) == '\n' || jsonString.charAt(pointer[1]) == '\t') {
            pointer[1]++;
        }
    }

    /*
     * 处理对象中最后一个value时向前回溯到值的最后一位
     * @param pointer[1] 指向jsonString中的最后一个'}'
     */
    private static void back(String jsonString, int[] pointer) {
        while (jsonString.charAt(pointer[1]) == ' ' || jsonString.charAt(pointer[1]) == '\n' || jsonString.charAt(pointer[1]) == '\t') {
            pointer[1]--;
        }
    }
}
