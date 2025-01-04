package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ProgramMemoryTests {
    @Test
    public void globalStackFrameTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        MemoryCell cell = MemoryCells.build("10");
        int address = memory.allocateMemoryCell(cell);
        MemoryCell retrievedCell = memory.getMemoryCell(address);
        Assertions.assertTrue(MemoryCells.isEqual(cell, retrievedCell));

        memory.popStackFrame();
        Assertions.assertThrows(IllegalStateException.class, () -> memory.getMemoryCell(address));
    }

    @Test
    public void stackFrameTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        MemoryCell lowerCell = MemoryCells.build("13.2");
        int lowerAddress = memory.allocateMemoryCell(lowerCell);
        memory.allocateStackFrame();

        MemoryCell upperCell = MemoryCells.build("true");
        int upperAddress = memory.allocateMemoryCell(upperCell);
        Assertions.assertEquals(lowerAddress, upperAddress);

        MemoryCell retrievedUpperCell = memory.getMemoryCell(upperAddress);
        Assertions.assertTrue(MemoryCells.isEqual(upperCell, retrievedUpperCell));
        memory.popStackFrame();
        MemoryCell retrievedLowerCell = memory.getMemoryCell(lowerAddress);
        Assertions.assertTrue(MemoryCells.isEqual(lowerCell, retrievedLowerCell));
    }
}
