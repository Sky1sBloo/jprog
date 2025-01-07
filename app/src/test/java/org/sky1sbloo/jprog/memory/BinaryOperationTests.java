package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Test;
import org.sky1sbloo.jprog.syntaxtree.BinaryOperators;

public class BinaryOperationTests {
    @Test
    public void additionTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("2");
        MemoryCell right = MemoryCells.build("3");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.ADD);
    }
}
