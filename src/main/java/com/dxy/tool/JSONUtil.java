package com.dxy.tool;

import com.dxy.tool.exception.IllegalJavaObjectException;

public class JSONUtil {

    public static Object getJavaObject(String jsonString) {
        return JSONReader.toJavaModel(jsonString);
    }

    public static String getJSONString(Object javaObject) throws IllegalJavaObjectException {
        if (javaObject instanceof JSONObject) {
            return getJSONString((JSONObject) javaObject);
        } else if (javaObject instanceof JSONArray) {
            return getJSONString((JSONArray) javaObject);
        }
        throw new IllegalJavaObjectException(null);
    }

    public static String getJSONString(JSONObject javaObject) {
        return javaObject.toString();
    }

    public static String getJSONString(JSONArray javaObject) {
        return javaObject.toString();
    }
}
