package org.sky1sbloo.jprog.syntaxtree;

/**
 * Class containing all the parse nodes
 */
public class ParseNodes {
    public record LiteralExpr(String value) implements ValueExpr {
    }

    public record IdentifierExpr(String name) implements ValueExpr {
        @Override
        public String value() {
            return name;
        }
    }

    public record AssignmentExpr(ValueExpr identifier, ValueExpr value) implements TerminalExpr {
    }
}
