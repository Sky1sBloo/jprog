package org.sky1sbloo.jprog.Lexer;

import org.sky1sbloo.jprog.Lexer.TokenTypes.*;

/**
 * Class representing a token.
 */
public record Token(Primary primaryType, Secondary secondaryType, String value, int line) {

    public boolean isEqual(Token other) {
        if (other.secondaryType == Secondary.ANY || this.secondaryType == Secondary.ANY) {
            return this.primaryType == other.primaryType;
        } else {
            return this.primaryType == other.primaryType && this.secondaryType == other.secondaryType;
        }
    }
}
