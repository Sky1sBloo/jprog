package org.sky1sbloo.jprog.Lexer;

public class Token {
    public enum MainTypes {
        VALUE,
        TERMINAL,
        INVALID
    }

    public enum SubTypes {
        ANY,
        KEYWORD,
        IDENTIFIER,
        OPERATOR,
        BRACE,
        ASSIGN,
        STATEMENT_TERMINATE
    }
}
