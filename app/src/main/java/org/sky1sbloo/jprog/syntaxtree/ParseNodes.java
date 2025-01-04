package org.sky1sbloo.jprog.syntaxtree;

import java.util.List;

/**
 * Class containing all the parse nodes
 */
public class ParseNodes {
    public record LiteralExpr(String value) implements ExprTypes.Value{
    }

    public record IdentifierExpr(String value) implements ExprTypes.Value {
    }

    public record BinaryExpr(ExprTypes.Value left, ExprTypes.Value right, BinaryOperators operator) implements ExprTypes.Value {
    }

    public record AssignmentExpr(ExprTypes.Value identifier, ExprTypes.Value value) implements ExprTypes.Terminal {
    }

    public record FunctionCallExpr(ExprTypes.Value identifier, List<ExprTypes.Value> arguments) implements ExprTypes.Value {
    }

    public record FunctionDefinitionExpr(String identifier, List<String> argumentId, List<ExprTypes.Expr> body) implements ExprTypes.Terminal {
    }
}
