# Jjson

install jar to your own local repository and use dependency as below:
```
<dependency>
    <groupId>com.dxy.tool</groupId>
    <artifactId>Jjson</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
a parser to turn json to java objects and turn java objects to json
- json to java:<br>
 ```JSONUtil.getJavaObject()```
- java to json:<br>
 ```JSONUtil.getJSONString()```
 ***
java objects are divided into two structures:<br>
 - `JSONObject` represents object in json
 - `JSONArray` represents array in json
 
when you get json from java objects you should make sure parameter mast be objects of `JSONObject` or `JSONArray`
 
JSONObject uses HashMap to store json object structures,key is String type and represents key in json and value uses different types to represent different type value in json.
here is the relationships:<br>

| json type | java type |
| :-------: | :-------: |
| null | null | 
| string | String | 
| boolean | Boolean |
| number | Double/Integer |
| array | JSONArray |
| object | JSONObject |

JSONArray uses LinkedList to store array structures,every element is objects of JSONObject or JSONArray
 