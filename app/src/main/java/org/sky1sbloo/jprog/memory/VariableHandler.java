package org.sky1sbloo.jprog.memory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * Class for handling variables
 */
public class VariableHandler {
    private final ProgramMemory memory;
    private final Deque<HashMap<String, Integer>> variableStack;  // Map itself to program memory addresses

    public VariableHandler(ProgramMemory memory) {
        this.memory = memory;
        this.variableStack = new ArrayDeque<>();
        variableStack.push(new HashMap<>());
    }

    /**
     * Allocates a new variable to memory
     */
    public void allocateVariable(String identifier, MemoryCell value) {
        if (variableStack.isEmpty()) {
            throw new IllegalStateException("Tried to allocate variable: " + identifier + " on an empty stack");
        }
        HashMap<String, Integer> topStack = variableStack.peek();

        if (topStack.containsKey(identifier)) {
            throw new IllegalArgumentException("Tried to allocate existing variable: " + identifier);
        }
        int address = memory.allocateMemoryCell(value);
        topStack.put(identifier, address);
    }

    /**
     * Allocates a null variable
     */
    public void allocateVariable(String identifier) {
        allocateVariable(identifier, new MemoryCell(new MemoryCellTypes.Null()));
    }

    /**
     * Updates the value starting from top of stack to end
     */
    public void setVariable(String identifier, MemoryCell value) {
        if (variableStack.isEmpty()) {
            throw new IllegalStateException("Tried to allocate variable: " + identifier + " on an empty stack");
        }
        HashMap<String, Integer> topStack = variableStack.peek();

        for (HashMap<String, Integer> stack : variableStack) {
            if (stack.containsKey(identifier)) {
                memory.updateMemoryCell(stack.get(identifier), value);
                return;
            }
        }
        throw new IllegalArgumentException("Tried to set non-existing variable: " + identifier);
    }

    /**
     * Retrieves the value from starting from top of stack to end
     */
    public MemoryCell getVariable(String identifier) {
        if (variableStack.isEmpty()) {
            throw new IllegalStateException("Tried to get variable: " + identifier + " on an empty stack");
        }
        for (HashMap<String, Integer> stack : variableStack) {
            if (stack.containsKey(identifier)) {
                return memory.getMemoryCell(stack.get(identifier));
            }
        }
        throw new IllegalArgumentException("Tried to get non-existing variable: " + identifier);
    }

    /**
     * Allocates stack frame on memory and variable handler
     * NOTE: Call this rather than calling using program memory's allocateStackFrame
     */
    public void allocateStackFrame() {
        variableStack.push(new HashMap<>());
    }

    /**
     * Allocates stack frame on memory and variable handler
     * NOTE: Call this rather than calling using program memory's allocateStackFrame
     */
    public void popStackFrame() {
        variableStack.pop();
    }
}
