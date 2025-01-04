package org.sky1sbloo.jprog.memory;

import java.util.*;

/**
 * Class for handling the program memory
 */
public class ProgramMemory {
    private final HashMap<Integer, MemoryCell> memory;
    private final Queue<Integer> unusedPtrs;  // Represents the deallocated pointers
    private int nextPtr;  // Represents the ptr that will be allocated next based on the stack frame

    public ProgramMemory() {
        this.memory = new HashMap<>();
        this.nextPtr = 0;
        this.unusedPtrs = new LinkedList<>();
    }

    /**
     * Allocates a new memory cell
     * @return The memory address of the cell
     */
    public int allocateMemoryCell(MemoryCell cell) {
        if (!unusedPtrs.isEmpty()) {
            Integer address = unusedPtrs.poll();
            if (address == null) {
                throw new IllegalStateException("Address on allocation is null");
            }
            memory.put(address, cell);
            return address;
        }

        int address = nextPtr++;
        memory.put(address, cell);
        return address;
    }

    /**
     * Deallocates the memory cell
     */
    public void deallocateMemoryCell(int address) {
        if (!memory.containsKey(address)) {
            throw new IllegalArgumentException("Tried to deallocate unallocated memory cell");
        }
        memory.remove(address);
        unusedPtrs.add(address);
    }

    /**
     * Retrieves the value of memory cell
     */
    public MemoryCell getMemoryCell(int address) {
        if (memory.containsKey(address)) {
            return memory.get(address);
        }
        throw new IllegalStateException("Tried to access an undefined cell");
    }

    /**
     * Updates the memory cell at the given address
     */
    public void updateMemoryCell(int address, MemoryCell cell) {
        if (!memory.containsKey(address)) {
            throw new IllegalArgumentException("Tried to update memory cell on unallocated variable");
        }
        memory.put(address, cell);
    }
}
