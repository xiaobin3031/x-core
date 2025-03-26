package com.xiaobin.core.json.path;

/**
 * created by xuweibin at 2025/3/26
 */
public enum TokenType {

    LEFT_BRACE   // {
    ,RIGHT_BRACE  // }
    ,LEFT_SQUARE  // [
    ,RIGHT_SQUARE  // ]
    ,COMMA  // ,
    ,COLON  // :
    ,MINUS  // -
    ,PLUS   // +
    ,E_E    // E
    ,E_e    // e

    ,TRUE   // true
    ,FALSE   // false
    ,NULL   // null

    , STRING
    , OBJECT
    , ARRAY
    , NUMBER
    , LITERAL


    , EOF
}
