package com.dxy.tool.parser;

import java.io.ByteArrayInputStream;

/**
 * Json is represented in a string.
 */
public class StringJsonLexer extends StreamJsonLexer implements JsonLexer {
    public StringJsonLexer(String json) {
        super(new ByteArrayInputStream(json.getBytes()));
    }

    @Override
    public String next() {
        return super.next();
    }

    @Override
    public String peek() {
        return super.peek();
    }
}
