package org.sky1sbloo.jprog.Lexer;

public class TokenTypes {
    public enum Primary {
        VALUE,
        TERMINAL,
        INVALID
    }

    public enum Secondary {
        ANY(Primary.INVALID),
        KEYWORD(Primary.TERMINAL),
        LITERAL(Primary.VALUE),
        IDENTIFIER(Primary.VALUE),
        OPERATOR(Primary.TERMINAL),
        BRACE(Primary.TERMINAL),
        STATEMENT_TERMINATE(Primary.TERMINAL),;

        private final Primary primaryType;

        Secondary(Primary type) {
            this.primaryType = type;
        }

        public Primary getPrimaryType() {
            return primaryType;
        }
    }
}
