package org.sky1sbloo.jprog.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTests {
    @Test
    public void tokenizerTests() {
        String tokenValues = """
                var testValue;
                func testFunc() {
                    return testValue;
                }
                """;
        Token[] expectedTokens = {
                TokenBuilder.build(TokenTypes.Secondary.KEYWORD, "var", 1),
                TokenBuilder.build(TokenTypes.Secondary.IDENTIFIER, "testValue", 1),
                TokenBuilder.build(TokenTypes.Secondary.STATEMENT_TERMINATE, ";", 1),
                TokenBuilder.build(TokenTypes.Secondary.KEYWORD, "func", 2),
                TokenBuilder.build(TokenTypes.Secondary.IDENTIFIER, "testFunc", 2),
                TokenBuilder.build(TokenTypes.Secondary.BRACE, "(", 2),
                TokenBuilder.build(TokenTypes.Secondary.BRACE, ")", 2),
                TokenBuilder.build(TokenTypes.Secondary.BRACE, "{", 2),
                TokenBuilder.build(TokenTypes.Secondary.KEYWORD, "return", 3),
                TokenBuilder.build(TokenTypes.Secondary.IDENTIFIER, "testValue", 3),
                TokenBuilder.build(TokenTypes.Secondary.STATEMENT_TERMINATE, ";", 3),
                TokenBuilder.build(TokenTypes.Secondary.BRACE, "}", 4)
        };

        assertDoesNotThrow(() -> {
            Tokenizer tokenizer = new Tokenizer(tokenValues);
            Token[] token = tokenizer.getTokens().toArray(new Token[0]);
            assertEquals(expectedTokens.length, token.length);

            for (int i = 0; i < token.length; i++) {
                assertEquals(expectedTokens[i].primaryType(), token[i].primaryType());
                assertEquals(expectedTokens[i].secondaryType(), token[i].secondaryType());
                assertEquals(expectedTokens[i].value(), token[i].value());
                assertEquals(expectedTokens[i].line(), token[i].line());
            }
        });
    }
}
