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
        Assertions.assertThrows(IllegalStateException.class, () -> memory.freeMemoryCell(address));
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

    /**
     * Test for freeing and reusing the cell pointer
     */
    @Test
    public void cellPtrFreeAndReuseTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        MemoryCell cell1 = MemoryCells.build("10");
        MemoryCell cell2 = MemoryCells.build("true");
        MemoryCell cell3 = MemoryCells.build("\"String\"");
        MemoryCell cell4 = MemoryCells.build("false");

        memory.allocateMemoryCell(cell1);
        int address2 = memory.allocateMemoryCell(cell2);
        int address3 = memory.allocateMemoryCell(cell3);
        int address4 = memory.allocateMemoryCell(cell4);

        memory.freeMemoryCell(address2);
        memory.freeMemoryCell(address3);
        MemoryCell cellReused = MemoryCells.build("20.23003");
        MemoryCell cellReused2 = MemoryCells.build("1");
        MemoryCell cellReused3 = MemoryCells.build("false");
        int addressReused = memory.allocateMemoryCell(cellReused);
        int addressReused2 = memory.allocateMemoryCell(cellReused2);
        int addressNew = memory.allocateMemoryCell(cellReused3);
        Assertions.assertEquals(address2, addressReused);
        Assertions.assertEquals(address3, addressReused2);
        Assertions.assertEquals(addressNew, address4 + 1);
    }
}
