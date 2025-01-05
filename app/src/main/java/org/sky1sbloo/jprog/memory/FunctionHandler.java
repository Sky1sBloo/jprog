package org.sky1sbloo.jprog.memory;

import org.sky1sbloo.jprog.syntaxtree.ParseNodes;

import java.util.HashMap;
import java.util.List;

/**
 * Class for handling function call and definitions
 */
public class FunctionHandler {
    private final ProgramMemory memory;
    HashMap<String, Integer> functionMap;

    public FunctionHandler(ProgramMemory memory) {
        this.memory = memory;
        this.functionMap = new HashMap<>();
    }

    public void defineFunction(String identifier, List<String> paramId, List<ParseNodes.Expr> body) {
        if (functionMap.containsKey(identifier)) {
            throw new IllegalArgumentException("Tried to define existing function: " + identifier);
        }
        int address = memory.allocateMemoryCell(MemoryCells.buildFunction(identifier, paramId, body));
        functionMap.put(identifier, address);
    }

    /**
     * Retrieves the function expression from memory
     */
    public ParseNodes.FunctionDefinitionExpr getFunction(String identifier, List<String> paramValues) throws WrongTypeException {
        if (!functionMap.containsKey(identifier)) {
            throw new IllegalArgumentException("Tried to call non-existing function: " + identifier);
        }
        int address = functionMap.get(identifier);
        MemoryCell function = memory.getMemoryCell(address);
        ParseNodes.FunctionDefinitionExpr functionDefinitionExpr = MemoryCells.visitFunctionDefinition(function);

        if (functionDefinitionExpr.argumentId().size() != paramValues.size()) {
            throw new IllegalArgumentException("Function: " + identifier + " called with wrong number of arguments");
        }

        return functionDefinitionExpr;
    }
}
