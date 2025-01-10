package org.sky1sbloo.jprog.interpreter;

import org.sky1sbloo.jprog.memory.*;
import org.sky1sbloo.jprog.syntaxtree.ParseNodes;
import org.sky1sbloo.jprog.syntaxtree.ParseNodesVisitor;

/**
 * Class for interpreting the syntax tree
 */
public class Interpreter {
    private final VariableHandler variableHandler;
    private final FunctionHandler functionHandler;

    public Interpreter(ParseNodes.Expr root, VariableHandler variableHandler, FunctionHandler functionHandler) {
        this.variableHandler = variableHandler;
        this.functionHandler = functionHandler;
    }

    private void performVariableInitialization(ParseNodes.VariableInitializationExpr expr) {
        variableHandler.allocateVariable(expr.identifier());
    }

    private void performVariableAssignment(ParseNodes.AssignmentExpr expr) {
        try {
            variableHandler.setVariable(expr.identifier(),
                    visitValueExpr(expr.value()));
        } catch (RuntimeException e) {
            throw new InterpreterException("Failed to interpret ParseTree: " + e.getMessage());
        }
    }

    private MemoryCell visitValueExpr(ParseNodes.Value expr) throws RuntimeException {
        return ParseNodesVisitor.visitValue(expr,
                (literalExpr) -> {
                    try {
                        return MemoryCells.build(literalExpr.value());
                    } catch (WrongTypeException e) {
                        throw new RuntimeException(e);
                    }
                },
                (identifierExpr) -> variableHandler.getVariable(identifierExpr.value()),
                (binaryExpr) -> {
                    try {
                        return MemoryCells.performBinaryOperation(visitValueExpr(binaryExpr.left()),
                                visitValueExpr(binaryExpr.right()), binaryExpr.operator());
                    } catch (WrongTypeException e) {
                        throw new RuntimeException(e);
                    }
                },
                (functionCallExpr) -> {
                    // Todo: add implementation
                    return null;
                });
    }

    private void visitTerminalExpr(ParseNodes.Terminal expr) {
        ParseNodesVisitor.visitTerminal(expr,
                this::performVariableInitialization,
                this::performVariableAssignment,
                (functionDefinitionExpr) -> {
                    throw new InterpreterException("Cannot interpret a terminal expression as a value expression");
                });
    }
}
