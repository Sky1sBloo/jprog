package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ProgramMemoryTests {
    @Test
    public void allocationTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        MemoryCell cell = MemoryCells.build("10");
        int address = memory.allocateMemoryCell(cell);
        MemoryCell retrievedCell = memory.getMemoryCell(address);
        Assertions.assertTrue(MemoryCells.isEqual(cell, retrievedCell));

        memory.deallocateMemoryCell(address);
        Assertions.assertThrows(IllegalStateException.class, () -> memory.getMemoryCell(address));
    }

    @Test
    public void updateCellTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        MemoryCell oldCell = MemoryCells.build("true");
        MemoryCell newCell = MemoryCells.build("10");
        int address = memory.allocateMemoryCell(oldCell);
        memory.updateMemoryCell(address, newCell);
        Assertions.assertTrue(MemoryCells.isEqual(newCell, memory.getMemoryCell(address)));
        memory.deallocateMemoryCell(address);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                memory.updateMemoryCell(address, oldCell)
        );
    }

    @Test
    public void reuseDeallocatedTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();

        MemoryCell value1 = MemoryCells.build("9.0");
        MemoryCell value2 = MemoryCells.build("\"Test\"");
        MemoryCell value3 = MemoryCells.build("true");
        MemoryCell value4 = MemoryCells.build("false");

        memory.allocateMemoryCell(value1);
        int address2 = memory.allocateMemoryCell(value2);
        int address3 = memory.allocateMemoryCell(value3);
        int address4 = memory.allocateMemoryCell(value4);

        memory.deallocateMemoryCell(address2);
        memory.deallocateMemoryCell(address3);
        int reallocatedAddress1 = memory.allocateMemoryCell(value1);
        int reallocatedAddress2 = memory.allocateMemoryCell(value4);

        int address5 = memory.allocateMemoryCell(value1);
        Assertions.assertEquals(address2, reallocatedAddress1);
        Assertions.assertEquals(address3, reallocatedAddress2);
        Assertions.assertEquals(address4 + 1, address5);
    }
}
