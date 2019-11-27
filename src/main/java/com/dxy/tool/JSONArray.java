package com.dxy.tool;

import com.dxy.tool.exception.ElementExistException;

import java.util.LinkedList;

public class JSONArray {

    private LinkedList<Object> jsonArray;

    public JSONArray() {
        jsonArray = new LinkedList<>();
    }

    public Object get(int index) {
        return jsonArray.get(index);
    }

    public void put(Object element) throws ElementExistException {
        //当前数组元素已存在时抛出ElementExistException异常
        if (jsonArray.contains(element)) {
            throw new ElementExistException(element + " exist");
        }
        jsonArray.add(element);
    }

    public int size() {
        return jsonArray.size();
    }

    @Override
    public String toString() {
        String s = "[";
        StringBuilder builder=new StringBuilder(s);
        jsonArray.forEach((value)->{
            builder.append(value);
            builder.append(",");
        });
        builder.deleteCharAt(builder.length()-1);
        s=builder.toString();
        s += "]";
        return s;
    }
}
