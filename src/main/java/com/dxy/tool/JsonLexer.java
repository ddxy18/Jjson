package com.dxy.tool;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Split a json string to a serious of tokens by whitespace characters.
 */
interface JsonLexer {
    /**
     * Check and consume the next token.
     *
     * @return a token
     */
    String next();

    /**
     * Only check the next token.
     *
     * @return a token
     */
    String peek();

}

/**
 * Json is represented in a stream. It is usually used for json from Internet
 * or second storage or a very long json.
 */
class StreamJsonLexer implements JsonLexer {
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
}

/**
 * Json is represented in a string.
 */
class StrJsonLexer extends StreamJsonLexer implements JsonLexer {
    public StrJsonLexer(String json) {
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