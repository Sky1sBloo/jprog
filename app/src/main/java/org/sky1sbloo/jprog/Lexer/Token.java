package org.sky1sbloo.jprog.Lexer;

import org.sky1sbloo.jprog.Lexer.TokenTypes.Primary;
import org.sky1sbloo.jprog.Lexer.TokenTypes.Secondary;

/**
 * Class representing a token.
 */
public class Token {
    private TokenTypes.Primary primaryType;

    private TokenTypes.Secondary secondaryType;

    public Token(Primary primaryType, Secondary secondaryType) {
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }

    public TokenTypes.Primary getPrimaryType() {
        return primaryType;
    }

    public TokenTypes.Secondary getSecondaryType() {
        return secondaryType;
    }

    public boolean isEqual(Token other) {
        if (other.secondaryType == TokenTypes.Secondary.ANY || this.secondaryType == TokenTypes.Secondary.ANY) {
            return this.primaryType == other.primaryType;
        } else {
            return this.primaryType == other.primaryType && this.secondaryType == other.secondaryType;
        }
    }
}
