package com.dxy.tool;

import org.junit.Assert;
import org.junit.Test;

public class ToJavaTest {

    @Test
    public void testString() {
        String json = "{\"content-type\":\"application/json\",\"status\":\"200\"}";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.getValue("content-type"), "application/json");
        Assert.assertEquals(jsonObject.getType("content-type"), "string");
    }

    @Test
    public void testBoolean() {
        String json = "{\"isSuccess\":true,\"isAlive\":false}";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.getValue("isSuccess"), true);
        Assert.assertEquals(jsonObject.getType("isAlive"), "boolean");
        Assert.assertEquals(jsonObject.getValue("isAlive"), false);
    }

    @Test
    public void testNull() {
        String json = "{\"isSuccess\":null,\"isAlive\":null}";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject.getValue("isSuccess"));
        Assert.assertEquals(jsonObject.getType("isAlive"), "null");
        Assert.assertNull(jsonObject.getValue("isAlive"));
    }

    @Test
    public void testObject() {
        String json = "{\"isSuccess\":true,\"isAlive\":false,\"http\":{\"content-type\":\"application/json\",\"status\":\"200\"}}";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(((JSONObject) jsonObject.getValue("http")).getValue("content-type"), "application/json");
        Assert.assertEquals(jsonObject.getType("http"), "object");
    }

    @Test
    public void testArray() {
        String json = """
        [
            {
                "isSuccess":  true,
                "isAlive": false
            },
            {
                "isSuccess": false,
                "isAlive" : null
            }
        ]""";
        JSONArray jsonArray = (JSONArray) JSONUtil.getJavaObject(json);
        Assert.assertEquals(((JSONObject) jsonArray.get(0)).getValue("isSuccess"), true);
        Assert.assertNull(((JSONObject) jsonArray.get(1)).getValue("isAlive"));
        Assert.assertEquals(jsonArray.size(), 2);
    }

    @Test
    public void testNumber() {
        String json = "{\"id\"  :  12,\"name\":\"dxy\",\"age\":20.0}";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.getValue("id"), 12);
        Assert.assertEquals(jsonObject.getValue("name"), "dxy");
        Assert.assertEquals(jsonObject.getValue("age"), 20.0);
    }

    @Test
    public void testEnter() {
        String json = """
        {
            \"id\":12,
            \"name\":\"dxy\",
            \"age\":20.0
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.getValue("id"), 12);
        Assert.assertEquals(jsonObject.getValue("name"), "dxy");
        Assert.assertEquals(jsonObject.getValue("age"), 20.0);
    }

    @Test
    public void testComplex() {
        String json = """
        {
            "id": 1,
            "address": {
            "city": "Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany",
            "employee": [
                {
                    "name": "a",
                    "wage": 123.5
                },
                {
                    "name": "b",
                    "wage": 124.5
                }
            ]
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.getValue("id"),1);
        JSONArray employee=(JSONArray)jsonObject.getValue("employee");
        Assert.assertEquals(((JSONObject)employee.get(0)).getValue("wage"),123.5);
        Assert.assertEquals(employee.size(),2);
        Assert.assertEquals(jsonObject.getType("employee"),"array");
    }

    @Test
    public void testArrayContainsArray() {
        String json="""
        {
            "id": 1,
            "sort": [
                [
                    {
                        "name": "a"
                    },
                    {
                        "name": "b"
                    }
                ],
                [
                    {
                        "name": "c"
                    },
                    {
                        "name": "d"
                    }
                ]
            ]
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        JSONArray sort=(JSONArray)jsonObject.getValue("sort");
        JSONArray sort1=(JSONArray)sort.get(0);
        Assert.assertEquals(((JSONObject)sort1.get(1)).getValue("name"),"b");
        Assert.assertEquals(sort.size(),2);
        Assert.assertEquals(sort1.size(),2);
        Assert.assertEquals(jsonObject.getType("id"),"number");
    }

    @Test
    public void testLackCurlyBrackets() {
        String json = """
        {
            "id": 1,
            "address": {
            "city": "Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany",
            "employee": [
                {
                    "name": "a",
                    "wage": 123.5
                },
                {
                    "name": "b",
                    "wage": 124.5

            ]
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testSameKey() {
        String json = """
        {
            "id": 1,
            "address": {
            "city": "Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany",
            "address": {
            "city": "Taizhou",
            "district": "Hailing"
            }
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testLackColon() {
        String json = """
        {

            "id": 1,
            "address": {
            "city""Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany"
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testLackQuotationMark() {
        String json = """
        {
            "id": 1,
            "address": {
            "city": "Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testUnexpectedJSONFormat() {
        String json = """
        {
            "id": 1,
            "address": {
            "city": "Shanghai",
            "district": "Minhang"
            },
            "name": "dCompany""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testLackCommaBetweenEntry() {
        String json = """
        {
            "id": 1,
            "isSuccess":false
            "status":200
        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testNullObject() {
        String json = """
        {

        }""";
        JSONObject jsonObject = (JSONObject) JSONUtil.getJavaObject(json);
        Assert.assertEquals(jsonObject.toString(),"{}");
    }

    @Test
    public void testLackCommaBetweenElements() {
       String json="""
       {
        "employee": [
            [
                {
                    "name": "a",
                    "wage": 123.5
                }
                {
                    "name": "b",
                    "wage": 124.5
                }
            ]
        ]
       }""";
       JSONObject jsonObject=(JSONObject)JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testLackKey() {
        String json="""
       {
        "": 200
       }""";
        JSONObject jsonObject=(JSONObject)JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testKeyLackQuotationMarks() {
        String json="""
       {
        "status
       }""";
        JSONObject jsonObject=(JSONObject)JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testUnsupportedValueType() {
        String json="""
       {
        "status": @
       }""";
        JSONObject jsonObject=(JSONObject)JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testUnsupportedElementType() {
        String json="""
       {
        "employee": [
            "name": "b",
            "wage": 124.5
        ]
       }""";
        JSONObject jsonObject=(JSONObject)JSONUtil.getJavaObject(json);
        Assert.assertNull(jsonObject);
    }
}