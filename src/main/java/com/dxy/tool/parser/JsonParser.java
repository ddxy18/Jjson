package com.dxy.tool.parser;

import com.dxy.tool.JsonArray;
import com.dxy.tool.JsonObject;
import com.dxy.tool.JsonElement;

/**
 * Parse json strings to corresponding JsonElement.
 */
public class JsonParser {
  public JsonParser(JsonLexer jsonLexer) {
    this.jsonLexer = jsonLexer;
  }

  public JsonElement parseJson() {
    return null;
  }

  public JsonObject parseObject() {
    return null;
  }

  public JsonArray parseArray() {
    return null;
  }

  private JsonLexer jsonLexer;
}
