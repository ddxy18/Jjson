package com.dxy.tool.parser;

import java.io.InputStream;
import java.util.List;

/**
 * Json is represented in a stream. It is usually used for json from Internet
 * or second storage or a very long json.
 */
public class StreamJsonLexer implements JsonLexer {
  public StreamJsonLexer(InputStream jsonStream) {
    this.jsonStream = jsonStream;
  }

  @Override
  public String next() {
    return null;
  }

  @Override
  public String peek() {
    return null;
  }

  private InputStream jsonStream;

  private List<String> tokenBuffer;
}
