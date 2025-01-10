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

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("2.5");
        MemoryCell numberRight = MemoryCells.build("4.5");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.ADD);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(7.0));
        MemoryCell numberCombinedResult = MemoryCells.performBinaryOperation(left, numberRight, BinaryOperators.ADD);
        MemoryCell expectedCombinedResult = new MemoryCell(new MemoryCellTypes.Number(6.5));
        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));
        Assertions.assertTrue(MemoryCells.isEqual(expectedCombinedResult, numberCombinedResult));

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

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(left, wrongType, BinaryOperators.ADD));
    }

    @Test
    public void subtractionTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("5");
        MemoryCell right = MemoryCells.build("3");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.SUBTRACT);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Int(2));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("5.5");
        MemoryCell numberRight = MemoryCells.build("3.0");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.SUBTRACT);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(2.5));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));
        MemoryCell combinedResult = MemoryCells.performBinaryOperation(left, numberRight, BinaryOperators.SUBTRACT);
        MemoryCell expectedCombinedResult = new MemoryCell(new MemoryCellTypes.Number(2.0));
        Assertions.assertTrue(MemoryCells.isEqual(expectedCombinedResult, combinedResult));

        MemoryCell boolType = MemoryCells.build("\"true\"");
        MemoryCell boolType2 = MemoryCells.build("\"false\"");
        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, stringType, BinaryOperators.SUBTRACT));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(boolType, boolType2, BinaryOperators.SUBTRACT));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(nullType, nullType, BinaryOperators.SUBTRACT));
    }

    @Test
    public void multiplicationTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("5");
        MemoryCell right = MemoryCells.build("3");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.MULTIPLY);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Int(15));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("5.5");
        MemoryCell numberRight = MemoryCells.build("3.0");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.MULTIPLY);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(16.5));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));
        MemoryCell combinedResult = MemoryCells.performBinaryOperation(left, numberRight, BinaryOperators.MULTIPLY);
        MemoryCell expectedCombinedResult = new MemoryCell(new MemoryCellTypes.Number(15.0));
        Assertions.assertTrue(MemoryCells.isEqual(expectedCombinedResult, combinedResult));

        MemoryCell boolType = MemoryCells.build("\"true\"");
        MemoryCell boolType2 = MemoryCells.build("\"false\"");
        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, stringType, BinaryOperators.MULTIPLY));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(boolType, boolType2, BinaryOperators.MULTIPLY));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(nullType, nullType, BinaryOperators.MULTIPLY));
    }

    @Test
    public void divisionTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("4");
        MemoryCell right = MemoryCells.build("2");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.DIVIDE);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Int(2));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("3.5");
        MemoryCell numberRight = MemoryCells.build("1.75");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.DIVIDE);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(2.0));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));

        MemoryCell boolType = MemoryCells.build("\"true\"");
        MemoryCell boolType2 = MemoryCells.build("\"false\"");
        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, stringType, BinaryOperators.DIVIDE));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(boolType, boolType2, BinaryOperators.DIVIDE));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(nullType, nullType, BinaryOperators.DIVIDE));
    }

    @Test
    public void moduloTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("3");
        MemoryCell right = MemoryCells.build("2");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.MODULO);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Int(1));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("7.5");
        MemoryCell numberRight = MemoryCells.build("2");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.MODULO);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Number(1.5));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));

        MemoryCell boolType = MemoryCells.build("\"true\"");
        MemoryCell boolType2 = MemoryCells.build("\"false\"");
        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, stringType, BinaryOperators.MODULO));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(boolType, boolType2, BinaryOperators.MODULO));
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(nullType, nullType, BinaryOperators.MODULO));
    }

    @Test
    public void equalityTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("3");
        MemoryCell right = MemoryCells.build("3");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.EQUALS);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("3.0");
        MemoryCell numberRight = MemoryCells.build("3.0");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.EQUALS);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));

        MemoryCell boolLeft = MemoryCells.build("true");
        MemoryCell boolRight = MemoryCells.build("false");
        MemoryCell boolResult = MemoryCells.performBinaryOperation(boolLeft, boolRight, BinaryOperators.EQUALS);
        MemoryCell expectedBoolResult = new MemoryCell(new MemoryCellTypes.Bool(false));

        Assertions.assertTrue(MemoryCells.isEqual(expectedBoolResult, boolResult));

        MemoryCell stringLeft = MemoryCells.build("\"Hello\"");
        MemoryCell stringRight = MemoryCells.build("\"Hello\"");
        MemoryCell stringResult = MemoryCells.performBinaryOperation(stringLeft, stringRight, BinaryOperators.EQUALS);
        MemoryCell expectedStringResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedStringResult, stringResult));

        MemoryCell nullLeft = MemoryCells.build("null");
        MemoryCell nullRight = MemoryCells.build("null");
        MemoryCell nullResult = MemoryCells.performBinaryOperation(nullLeft, nullRight, BinaryOperators.EQUALS);
        MemoryCell expectedNullResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedNullResult, nullResult));

        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, nullType, BinaryOperators.EQUALS));
    }

    @Test
    public void inequalityTest() throws WrongTypeException {
        MemoryCell left = MemoryCells.build("3");
        MemoryCell right = MemoryCells.build("4");
        MemoryCell result = MemoryCells.performBinaryOperation(left, right, BinaryOperators.NOT_EQUALS);
        MemoryCell expectedResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedResult, result));

        MemoryCell numberLeft = MemoryCells.build("3.0");
        MemoryCell numberRight = MemoryCells.build("3.01");
        MemoryCell numberResult = MemoryCells.performBinaryOperation(numberLeft, numberRight, BinaryOperators.NOT_EQUALS);
        MemoryCell expectedFloatResult = new MemoryCell(new MemoryCellTypes.Bool(true));

        Assertions.assertTrue(MemoryCells.isEqual(expectedFloatResult, numberResult));

        MemoryCell boolLeft = MemoryCells.build("false");
        MemoryCell boolRight = MemoryCells.build("false");
        MemoryCell boolResult = MemoryCells.performBinaryOperation(boolLeft, boolRight, BinaryOperators.NOT_EQUALS);
        MemoryCell expectedBoolResult = new MemoryCell(new MemoryCellTypes.Bool(false));

        Assertions.assertTrue(MemoryCells.isEqual(expectedBoolResult, boolResult));

        MemoryCell stringLeft = MemoryCells.build("\"Hello\"");
        MemoryCell stringRight = MemoryCells.build("\"Hello\"");
        MemoryCell stringResult = MemoryCells.performBinaryOperation(stringLeft, stringRight, BinaryOperators.NOT_EQUALS);
        MemoryCell expectedStringResult = new MemoryCell(new MemoryCellTypes.Bool(false));

        Assertions.assertTrue(MemoryCells.isEqual(expectedStringResult, stringResult));

        MemoryCell nullLeft = MemoryCells.build("null");
        MemoryCell nullRight = MemoryCells.build("null");
        MemoryCell nullResult = MemoryCells.performBinaryOperation(nullLeft, nullRight, BinaryOperators.NOT_EQUALS);
        MemoryCell expectedNullResult = new MemoryCell(new MemoryCellTypes.Bool(false));

        Assertions.assertTrue(MemoryCells.isEqual(expectedNullResult, nullResult));

        MemoryCell nullType = MemoryCells.build("null");
        MemoryCell stringType = MemoryCells.build("\"2\"");
        Assertions.assertThrows(WrongTypeException.class, () -> MemoryCells.performBinaryOperation(stringType, nullType, BinaryOperators.NOT_EQUALS));
    }
}
