package org.sky1sbloo.jprog.memory;

import java.util.*;

/**
 * Class for handling the program memory
 */
public class ProgramMemory {
    private final Deque<HashMap<Integer, MemoryCell>> memoryStack;

    public ProgramMemory() {
        this.memoryStack = new ArrayDeque<>();
        allocateStackFrame();
    }

    /**
     * Allocates a new stack frame
     */
    public void allocateStackFrame() {
        memoryStack.addFirst(new HashMap<>());
        memoryStack.push(new HashMap<>());
    }

    /**
     * Deallocates the current stack frame
     */
    public void popStackFrame() {
        if (memoryStack.size() == 1) {
            throw new IllegalStateException("Tried to deallocate the global stack frame");
        }
        memoryStack.pop();
    }

    /**
     * Sets the memory cell at the given address
     */
    public void setMemoryCell(int address, MemoryCell cell) {
        if (memoryStack.peek() == null) {
            throw new IllegalStateException("Tried to allocate on empty stack frame");
        }
        memoryStack.peek().put(address, cell);
    }

    /**
     * Frees the memory cell at the given address
     */
    public void freeMemoryCell(int address) {
        if (memoryStack.peek() == null) {
            throw new IllegalStateException("Tried to deallocate on empty stack frame");
        }
        try {
            memoryStack.peek().remove(address);
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Tried to deallocate an undefined cell");
        }
    }
}
