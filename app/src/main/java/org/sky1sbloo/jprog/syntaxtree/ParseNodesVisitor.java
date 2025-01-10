package org.sky1sbloo.jprog.syntaxtree;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class for visiting parse nodes
 */
public class ParseNodesVisitor {
    /**
     * Visits all valid expr types and runs the corresponding function
     *
     * @param expr Expression node to be checked
     */
    public static <R> R visit(ParseNodes.Expr expr,
                              Function<ParseNodes.LiteralExpr, R> literalExprFunction,
                              Function<ParseNodes.IdentifierExpr, R> identifierExprFunction,
                              Function<ParseNodes.BinaryExpr, R> binaryExprFunction,
                              Function<ParseNodes.VariableInitializationExpr, R> variableInitializationExprFunction,
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
        if (expr instanceof ParseNodes.VariableInitializationExpr variableInitializationExpr) {
            return variableInitializationExprFunction.apply(variableInitializationExpr);
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

    /**
     * Visits all valid value expr and calls the corresponding function
     * @param expr Expression to checked
     */
    public static <R> R visitValue(ParseNodes.Value expr,
                                   Function<ParseNodes.LiteralExpr, R> literalExprFunction,
                                   Function<ParseNodes.IdentifierExpr, R> identifierExprFunction,
                                   Function<ParseNodes.BinaryExpr, R> binaryExprFunction,
                                   Function<ParseNodes.FunctionCallExpr, R> functionCallExprFunction) {
        if (expr instanceof ParseNodes.LiteralExpr literalExpr) {
            return literalExprFunction.apply(literalExpr);
        }
        if (expr instanceof ParseNodes.IdentifierExpr identifierExpr) {
            return identifierExprFunction.apply(identifierExpr);
        }
        if (expr instanceof ParseNodes.BinaryExpr binaryExpr) {
            return binaryExprFunction.apply(binaryExpr);
        }
        if (expr instanceof ParseNodes.FunctionCallExpr functionCallExpr) {
            return functionCallExprFunction.apply(functionCallExpr);
        }
        throw new ParseNodesException("Expression is not a valid value parse node");
    }

    /**
     * Visits all valid terminal expr types and runs the corresponding function
     *
     * @param expr Expression to be checked
     */
    public static void visitTerminal(ParseNodes.Terminal expr,
                              Consumer<ParseNodes.VariableInitializationExpr> variableInitializationExprConsumer,
                              Consumer<ParseNodes.AssignmentExpr> assignmentExprConsumer,
                              Consumer<ParseNodes.FunctionDefinitionExpr> functionDefinitionExprConsumer) {
        if (expr instanceof ParseNodes.VariableInitializationExpr variableInitializationExpr) {
            variableInitializationExprConsumer.accept(variableInitializationExpr);
        }
        if (expr instanceof ParseNodes.AssignmentExpr assignmentExpr) {
            assignmentExprConsumer.accept(assignmentExpr);
        }
        if (expr instanceof ParseNodes.FunctionDefinitionExpr functionDefinitionExpr) {
            functionDefinitionExprConsumer.accept(functionDefinitionExpr);
        }
        throw new ParseNodesException("Expression is not a valid terminal parse node");
    }
}
