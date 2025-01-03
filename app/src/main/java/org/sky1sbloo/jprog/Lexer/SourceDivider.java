package org.sky1sbloo.jprog.Lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for dividing strings so the tokenizer can easily tokenize them
 */
public class SourceDivider {
    private final List<String> tokenValues;

    public SourceDivider(String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        tokenValues = new ArrayList<>();

        while (matcher.find()) {
            String token = matcher.group();
            if (!token.isEmpty()) {
                tokenValues.add(token);
            }
        }
    }

    public List<String> getTokenValues() {
        return tokenValues;
    }


    // Splits a single string into dividable tokens
    private static final String regex = "\\b(var|func|return)\\b" +  // Keywords
            "|[(){}\\[\\];]" +  // Delimiters
            "|(\\+\\+|\\+=|\\+|-|\\*|/|,|=)" +  // Operations
            "|\\d+(?:\\.\\d+)?" +  // Number Literal
            "|\"([^\"]+)\"" +  // Strings
            "|\\b(true|false)\\b" + // Booleans
            "|\\r?\\n" +
            "|\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";  // Identifiers
}
