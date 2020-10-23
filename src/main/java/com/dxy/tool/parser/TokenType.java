package com.dxy.tool.parser;

/**
 * We split the json string to several tokens to help to build a parsing tree of
 * the json string.
 */
public enum TokenType {
  STRING,
  NUMBER,
  LEFT_CURLY_BRACKET,
  RIGHT_CURLY_BRACKET,
  LEFT_BRACKET,
  RIGHT_BRACKET,
  COLON,
  COMMA,
  TRUE,
  FALSE,
  NULL
}
