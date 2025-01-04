package org.sky1sbloo.jprog.memory;

import java.util.*;

/**
 * Class for handling the program memory
 */
public class ProgramMemory {
    private final Deque<HashMap<Integer, MemoryCell>> memoryStack;
    private final Deque<Queue<Integer>> unusedPtrs;  // Represents the deallocated pointers
    private final Deque<Integer> nextPtr;  // Represents the ptr that will be allocated next based on the stack frame

    public ProgramMemory() {
        this.memoryStack = new ArrayDeque<>();
        this.nextPtr = new ArrayDeque<>();
        this.unusedPtrs = new ArrayDeque<>();
        allocateStackFrame();
    }

    /**
     * Allocates a new stack frame
     */
    public void allocateStackFrame() {
        memoryStack.addFirst(new HashMap<>());
        memoryStack.push(new HashMap<>());
        unusedPtrs.push(new LinkedList<>());
        nextPtr.push(0);
    }

    /**
     * Deallocates the current stack frame
     */
    public void popStackFrame() {
        if (memoryStack.size() == 1) {
            throw new IllegalStateException("Tried to deallocate the global stack frame");
        }
        memoryStack.pop();
        unusedPtrs.pop();
        nextPtr.pop();
    }

    /**
     * Returns the number of stack frames
     */
    public int getStackFrameCount() {
        return memoryStack.size();
    }

    /**
     * Allocates a new memory cell
     * @return The memory address of the cell
     */
    public int allocateMemoryCell(MemoryCell cell) {
        if (memoryStack.isEmpty() || nextPtr.isEmpty() || unusedPtrs.isEmpty()) {
            throw new IllegalStateException("Tried to allocate on empty stack frame");
        }

        if (!unusedPtrs.peek().isEmpty()) {
            Integer address = unusedPtrs.peek().poll();
            if (address == null) {
                throw new IllegalStateException("Address on allocation is null");
            }
            setMemoryCell(address, cell);
            return address;
        }

        int address = nextPtr.peek();
        setMemoryCell(address, cell);
        nextPtr.pop();
        nextPtr.push(address + 1);
        return address;
    }

    public MemoryCell getMemoryCell(int address) {
        if (memoryStack.isEmpty()) {
            throw new IllegalStateException("Tried to allocate on empty stack frame");
        }
        for (HashMap<Integer, MemoryCell> frame : memoryStack) {
            if (frame.containsKey(address)) {
                return frame.get(address);
            }

        }
        throw new IllegalStateException("Tried to access an undefined cell");
    }

    /**
     * Sets the memory cell at the given address
     */
    public void setMemoryCell(int address, MemoryCell cell) {
        if (memoryStack.isEmpty()) {
            throw new IllegalStateException("Tried to allocate on empty stack frame");
        }
        memoryStack.peek().put(address, cell);
    }
}
