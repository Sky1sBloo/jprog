package org.sky1sbloo.jprog.memory;

import java.util.function.Function;

public class MemoryCellVisitor {
    public static <R> R visit(MemoryCell cell,
                              Function<Integer, R> intVisitor,
                              Function<Double, R> numberVisitor,
                              Function<Boolean, R> boolVisitor,
                              Function<String, R> stringVisitor) throws WrongTypeException {
        if (cell.value() instanceof MemoryCellTypes.Int(int value)) {
            return intVisitor.apply(value);
        }
        if (cell.value() instanceof MemoryCellTypes.Number(Double value)) {
            return numberVisitor.apply(value);
        }
        if (cell.value() instanceof MemoryCellTypes.Bool(boolean value)) {
            return boolVisitor.apply(value);
        }
        if (cell.value() instanceof MemoryCellTypes.StringType(String value)) {
            return stringVisitor.apply(value);
        }
        throw new WrongTypeException("Cell is at unknown type");
    }
}
