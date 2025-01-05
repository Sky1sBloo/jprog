package org.sky1sbloo.jprog.syntaxtree;

import java.util.function.Function;

/**
 * Class for visiting parse nodes
 */
public class ParseNodesVisitor {
    public static <R> R visitValueExpr(ParseNodes.Expr expr,
                                       Function<ParseNodes.LiteralExpr, R> literalExprFunction,
                                       Function<ParseNodes.IdentifierExpr, R> identifierExprFunction,
                                       Function<ParseNodes.BinaryExpr, R> binaryExprFunction,
                                       Function<ParseNodes.AssignmentExpr, R> assignmentExprFunction,
                                       Function<ParseNodes.FunctionCallExpr, R> functionCallExprFunction,
                                       Function<ParseNodes.FunctionDefinitionExpr, R> functionDefinitionExprFunction) {
        if (expr instanceof ParseNodes.LiteralExpr literalExpr) {
            return literalExprFunction.apply(literalExpr);
        }
        if (expr instanceof ParseNodes.IdentifierExpr identifierExpr) {
            return identifierExprFunction.apply(identifierExpr);
        }
        if (expr instanceof ParseNodes.BinaryExpr binaryExpr) {
            return binaryExprFunction.apply(binaryExpr);
        }
        if (expr instanceof ParseNodes.AssignmentExpr assignmentExpr) {
            return assignmentExprFunction.apply(assignmentExpr);
        }
        if (expr instanceof ParseNodes.FunctionCallExpr functionCallExpr) {
            return functionCallExprFunction.apply(functionCallExpr);
        }
        if (expr instanceof ParseNodes.FunctionDefinitionExpr functionDefinitionExpr) {
            return functionDefinitionExprFunction.apply(functionDefinitionExpr);
        }
        throw new ParseNodesException("Expression is not a valid parse node");
    }
}
