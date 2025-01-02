package org.sky1sbloo.jprog.Lexer;

public class TokenBuilder {
    /**
     * Builds a token with the given primary and secondary types.
     */
    public static Token build(TokenTypes.Primary primaryType, TokenTypes.Secondary secondaryType) {
        return new Token(primaryType, secondaryType);
    }

    /**
     * Builds a token with the given secondary type and identifies the primary type.
     */
    public static Token build(TokenTypes.Secondary secondaryType) {
        return new Token(secondaryType.getPrimaryType(), secondaryType);
    }

    /**
     * Builds a token with the given primary type and secondary type is ANY
     */
    public static Token build(TokenTypes.Primary primaryType) {
        return new Token(primaryType, TokenTypes.Secondary.ANY);
    }
}
