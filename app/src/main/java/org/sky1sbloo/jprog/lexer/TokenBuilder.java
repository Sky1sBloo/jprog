package org.sky1sbloo.jprog.lexer;

public class TokenBuilder {
    /**
     * Builds a token with the given primary and secondary types.
     */
    public static Token build(TokenTypes.Primary primaryType, TokenTypes.Secondary secondaryType, String value, int line) {
        return new Token(primaryType, secondaryType, value, line);
    }

    /**
     * Builds a token with the given secondary type and identifies the primary type.
     */
    public static Token build(TokenTypes.Secondary secondaryType, int line) {
        return new Token(secondaryType.getPrimaryType(), secondaryType, "", line);
    }

    /**
     * Builds a token with the given secondary type and identifies the primary type.
     */
    public static Token build(TokenTypes.Secondary secondaryType, String value, int line) {
        return new Token(secondaryType.getPrimaryType(), secondaryType, value, line);
    }

    /**
     * Builds a token with the given primary type and secondary type is ANY
     */
    public static Token build(TokenTypes.Primary primaryType, int line) {
        return new Token(primaryType, TokenTypes.Secondary.ANY, "", line);
    }
}
