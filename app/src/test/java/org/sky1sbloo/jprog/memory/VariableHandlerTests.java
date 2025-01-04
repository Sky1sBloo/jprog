package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariableHandlerTests {
    @Test
    public void variableAllocationTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        VariableHandler variableHandler = new VariableHandler(memory);

        String var1Identifier = "var1";
        String varUnallocated = "test";
        MemoryCell var1Value = MemoryCells.build("50");
        variableHandler.allocateVariable(var1Identifier, var1Value);

        Assertions.assertTrue(MemoryCells.isEqual(var1Value, variableHandler.getVariable(var1Identifier)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> variableHandler.getVariable(varUnallocated));
    }

    @Test
    public void variableStackTest() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        VariableHandler variableHandler = new VariableHandler(memory);

        String reusableIdentifier = "reusable";
        String lowerLevelIdentifier = "lowlevel";
        String highLevelIdentifier = "highlevel";
        MemoryCell reusableOriginalValue = MemoryCells.build("25.0");
        MemoryCell reusableStackValue = MemoryCells.build("true");
        MemoryCell reusableStackNewValue = MemoryCells.build("false");
        MemoryCell lowLevelValue = MemoryCells.build("10");
        MemoryCell highLevelValue = MemoryCells.build("15");

        variableHandler.allocateVariable(reusableIdentifier, reusableOriginalValue);
        variableHandler.allocateVariable(lowerLevelIdentifier, lowLevelValue);

        variableHandler.allocateStackFrame();

        variableHandler.allocateVariable(reusableIdentifier, reusableStackValue);
        Assertions.assertTrue(MemoryCells.isEqual(reusableStackValue, variableHandler.getVariable(reusableIdentifier)));
        variableHandler.setVariable(reusableIdentifier, reusableStackNewValue);
        Assertions.assertTrue(MemoryCells.isEqual(reusableStackNewValue, variableHandler.getVariable(reusableIdentifier)));

        variableHandler.allocateVariable(highLevelIdentifier, highLevelValue);
        Assertions.assertTrue(MemoryCells.isEqual(highLevelValue, variableHandler.getVariable(highLevelIdentifier)));

        Assertions.assertTrue(MemoryCells.isEqual(lowLevelValue, variableHandler.getVariable(lowerLevelIdentifier)));
        variableHandler.popStackFrame();

        Assertions.assertTrue(MemoryCells.isEqual(reusableOriginalValue, variableHandler.getVariable(reusableIdentifier)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> variableHandler.getVariable(highLevelIdentifier));
        Assertions.assertTrue(MemoryCells.isEqual(lowLevelValue, variableHandler.getVariable(lowerLevelIdentifier)));
    }
}
