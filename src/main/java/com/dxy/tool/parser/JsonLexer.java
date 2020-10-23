package com.dxy.tool.parser;

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

  static TokenType tokenType(String token) {
    return null;
  }
}