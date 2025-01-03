package org.sky1sbloo.jprog.lexer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final List<Token> tokens;

    /**
     * Tokenizes the source code
     */
    public Tokenizer(String source) throws TokenizerException {
        // To divide the source code into tokens (but without identifying it yet)
        SourceDivider sourceDivider = new SourceDivider(source);
        tokens = new ArrayList<>();
        int currentLine = 1;

        for (String tokenValue : sourceDivider.getTokenValues()) {
            if (tokenValue.equals("\n")) {
                currentLine++;
                continue;
            }

            Token token = identifyToken(tokenValue, currentLine);
            if (token.primaryType() == TokenTypes.Primary.INVALID) {
                throw new TokenizerException("Token is invalid at line: " + token.line() + " with value: " + tokenValue);
            }
            tokens.add(token);
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private Token identifyToken(String tokenValue, int line) {
        if (isKeyword(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.KEYWORD, tokenValue, line);
        }
        if (isOperation(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.OPERATOR, tokenValue, line);
        }
        if (isLiteral(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.LITERAL, tokenValue, line);
        }
        if (isIdentifier(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.IDENTIFIER, tokenValue, line);
        }
        if (isBrace(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.BRACE, tokenValue, line);
        }
        if (isStatementTerminate(tokenValue)) {
            return TokenBuilder.build(TokenTypes.Secondary.STATEMENT_TERMINATE, tokenValue, line);
        }

        return TokenBuilder.build(TokenTypes.Primary.INVALID, TokenTypes.Secondary.ANY,"", line);
    }

    private boolean isKeyword(String token) {
        for (String word : keywords) {
            if (token.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOperation(String token) {
        for (String word : operationKeywords) {
            if (token.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLiteral(String token) {
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            return false;
        }

        boolean isStringLiteral = token.startsWith("\"") && token.endsWith("\"");
        if (isStringLiteral) {
            return true;
        }

        return token.equals(booleanKeywords[0]) || token.equals(booleanKeywords[1]);
    }

    private boolean isBrace(String token) {
        for (String word : braceKeywords) {
            if (token.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private boolean isStatementTerminate(String token) {
        return token.equals(statementTerminate);
    }

    // Rulesets
    private static final String[] keywords = {"var", "func", "return"};
    private static final String[] operationKeywords = {"+", "-", "*", "/", ",", "="};
    private static final String[] booleanKeywords = {"true", "false"};
    private static final String[] braceKeywords = {"(", ")", "{", "}"};
    private static final String statementTerminate = ";";
}
