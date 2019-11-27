package com.dxy.tool;

import com.dxy.tool.exception.KeyExistException;

import java.util.HashMap;

public class JSONObject {

    private HashMap<String, Object> jsonObject;

    public JSONObject() {
        jsonObject = new HashMap<>();
    }

    /*
     * 如果键不存在则抛出NullPointerException,如果值为null则返回null
     */
    public Object getValue(String key) throws NullPointerException {
        Object value = jsonObject.get(key);
        if (value == null) {
            if (jsonObject.containsKey(key)) {
                return null;
            } else {
                throw new NullPointerException(key + " not exist");
            }
        }
        return value;
    }

    public String getType(String key) {
        Object value = getValue(key);
        if (value == null) {
            return "null";
        } else if (value instanceof Number) {
            return "number";
        } else if (value instanceof JSONObject) {
            return "object";
        } else if (value instanceof JSONArray) {
            return "array";
        } else if (value instanceof String) {
            return "string";
        } else {
            return "boolean";
        }
    }

    public void put(String key, Object value) throws KeyExistException {
        //对已经存在的key抛出异常KeyExistException
        try {
            Object v = getValue(key);
            throw new KeyExistException(key + " exist");
        } catch (NullPointerException ignored) {
        }
        jsonObject.put(key, value);
    }

    @Override
    public String toString() {
        String s = "{";
        StringBuilder builder=new StringBuilder(s);
        jsonObject.forEach((key,value)->{
            builder.append("\"");
            builder.append(key);
            builder.append("\":");
            if(value instanceof String) {
                builder.append("\"");
                builder.append(value);
                builder.append("\"");
            }else {
                builder.append(value);
            }
            builder.append(",");
        });
        if(jsonObject.size()!=0) {
            builder.deleteCharAt(builder.length()-1);
        }
        s=builder.toString();
        s += "}";
        return s;
    }
}
