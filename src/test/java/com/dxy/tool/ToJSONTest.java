package com.dxy.tool;

import com.dxy.tool.exception.IllegalJavaObjectException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ToJSONTest {

    @Test
    public void testObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            JSONObject employee = new JSONObject();
            employee.put("city", "Shanghai");
            employee.put("district", "Minhang");
            jsonObject.put("employee", employee);
            jsonObject.put("name", "dCompany");
            String json = JSONUtil.getJSONString(jsonObject);
            Assert.assertTrue(json.contains("employee"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testArray() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            JSONArray employee = new JSONArray();
            JSONObject e1 = new JSONObject();
            e1.put("name", "a");
            e1.put("wage", 123.5);
            employee.put(e1);
            JSONObject e2 = new JSONObject();
            e2.put("name", "b");
            e2.put("wage", 124.5);
            employee.put(e2);
            jsonObject.put("employee", employee);
            jsonObject.put("name", "dCompany");
            String json = JSONUtil.getJSONString(jsonObject);
            Assert.assertTrue(json.contains("employee"));
            Assert.assertTrue(json.contains("\"name\":\"b\",\"wage\":124.5"));
            String jsonArray = JSONUtil.getJSONString(employee);
            Assert.assertTrue(jsonArray.contains("\"name\":\"b\",\"wage\":124.5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSameElement() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            JSONArray employee = new JSONArray();
            JSONObject e1 = new JSONObject();
            e1.put("name", "a");
            e1.put("wage", 123.5);
            employee.put(e1);
            JSONObject e2 = new JSONObject();
            e2.put("name", "b");
            e2.put("wage", 124.5);
            employee.put(e2);
            employee.put(e2);
            jsonObject.put("employee", employee);
            jsonObject.put("name", "dCompany");
            String json = JSONUtil.getJSONString(jsonObject);
            Assert.assertTrue(json.contains("employee"));
            Assert.assertTrue(json.contains("\"name\":\"b\",\"wage\":124.5"));
            String jsonArray = JSONUtil.getJSONString(employee);
            Assert.assertTrue(jsonArray.contains("\"name\":\"b\",\"wage\":124.5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectWithObjectParameter() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 1);
            jsonObject.put("name", "dCompany");
            String json = JSONUtil.getJSONString((Object) jsonObject);
            Assert.assertTrue(json.contains("\"name\":\"dCompany\""));
            Assert.assertTrue(json.contains("\"id\":1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testArrayWithObjectParameter() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject o1=new JSONObject();
            o1.put("name","a");
            JSONObject o2=new JSONObject();
            o2.put("name","b");
            jsonArray.put( o1);
            jsonArray.put( o2);
            String json = JSONUtil.getJSONString((Object) jsonArray);
            Assert.assertTrue(json.contains("\"name\":\"a\""));
            Assert.assertTrue(json.contains("\"name\":\"b\""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalJavaObjectException.class)
    public void testIllegalJavaObjectException() throws IllegalJavaObjectException {
        Map<String,Object> jsonObject = new HashMap<>();
            jsonObject.put("id", 1);
            jsonObject.put("name", "dCompany");
            String json = JSONUtil.getJSONString(jsonObject);
            Assert.assertTrue(json.contains("\"name\":\"dCompany\""));
            Assert.assertTrue(json.contains("\"id\":1"));
        }
}
