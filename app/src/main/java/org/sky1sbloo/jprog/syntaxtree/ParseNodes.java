package org.sky1sbloo.jprog.syntaxtree;

import org.sky1sbloo.jprog.syntaxtree.ExprTypes.*;

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

    public record FunctionDefinitionExpr(ExprTypes.Value identifier, List<ExprTypes.Value> arguments, List<ExprTypes.Expr> body) implements ExprTypes.Terminal {
    }
}
