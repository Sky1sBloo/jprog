package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sky1sbloo.jprog.syntaxtree.BinaryOperators;

public class BinaryOperationTests {
    @Test
    public void additionTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("2");
        MemoryCell right = MemoryCells.build("3");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.ADD);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Int(5));

        Assertions.assertEquals(expectedResult, result);

        MemoryCell numberLeft = MemoryCells.build("2.5");
        MemoryCell numberRight = MemoryCells.build("4.5");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.ADD);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(7.0));
        MemoryCell numberCombinedResult = MemoryCells.performBinaryOperation(left, numberRight, BinaryOperators.ADD);
        MemoryCell expectedCombinedResult = new MemoryCell(new MemoryCellTypes.Number(6.5));
        Assertions.assertEquals(expectedFloatResult, numberResult);
        Assertions.assertEquals(expectedCombinedResult, numberCombinedResult);

        MemoryCell wrongType = MemoryCells.build("\"true\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(left, wrongType, BinaryOperators.ADD));
    }

    @Test
    public void stringConcatenationTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("\"Hello, \"");
        MemoryCell right = MemoryCells.build("\"World!\"");
        MemoryCell wrongType = MemoryCells.build("2");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.ADD);
        MemoryCell expectedResult = MemoryCells.build("\"Hello, World!\"");

        Assertions.assertEquals(expectedResult, result);
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(left, wrongType, BinaryOperators.ADD));
    }
}
