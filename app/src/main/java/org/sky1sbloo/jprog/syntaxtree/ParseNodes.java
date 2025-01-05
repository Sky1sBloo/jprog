package org.sky1sbloo.jprog.syntaxtree;

import java.util.List;

/**
 * Class containing all the parse nodes
 */
public class ParseNodes {
    /**
     * Interface containing all the expression types
     */
    public sealed interface Expr permits Terminal, Value {
    }

    /**
     * Interface containing all the terminal expression types
     */
    public non-sealed interface Terminal extends Expr {
    }

    /**
     * Interface containing all the returnable expression types
     */
    public non-sealed interface Value extends Expr {
    }

    public record LiteralExpr(String value) implements Value{
    }

    public record IdentifierExpr(String value) implements Value {
    }

    public record BinaryExpr(Value left, Value right, BinaryOperators operator) implements Value {
    }

    public record AssignmentExpr(Value identifier, Value value) implements Terminal {
    }

    public record FunctionCallExpr(Value identifier, List<Value> arguments) implements Value {
    }

    public record FunctionDefinitionExpr(String identifier, List<String> argumentId, List<Expr> body) implements Terminal {
    }
}
