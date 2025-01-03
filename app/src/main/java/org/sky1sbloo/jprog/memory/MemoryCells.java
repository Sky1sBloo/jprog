package org.sky1sbloo.jprog.memory;

import com.google.common.math.DoubleMath;

import java.util.function.Function;
import java.util.Optional;

/**
 * Utility class for memory cell
 */
public class MemoryCells {
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

    public static boolean isEqual(MemoryCell cell1, MemoryCell cell2) throws WrongTypeException {
        return visit(cell1,
                (Integer cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Int(int cell2Value)) {
                        return cell1Value == cell2Value;
                    }
                    return false;
                },
                (Double cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Number(Double cell2Value)) {
                        return DoubleMath.fuzzyEquals(cell1Value, cell2Value, 0.000001d);
                    }
                    return false;
                },
                (Boolean cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Bool(boolean cell2Value)) {
                        return cell1Value == cell2Value;
                    }
                    return false;
                },
                (String cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.StringType(String cell2Value)) {
                        return cell1Value.equals(cell2Value);
                    }
                    return false;
                });
    }

    // Identifies datatype based on value
    public static MemoryCell build(String value) throws WrongTypeException {
        Optional<Integer> intValue = buildInteger(value);
        if (intValue.isPresent()) {
            return new MemoryCell(new MemoryCellTypes.Int(intValue.get()));
        }

        Optional<Double> numberValue = buildNumber(value);
        if (numberValue.isPresent()) {
            return new MemoryCell(new MemoryCellTypes.Number(numberValue.get()));
        }

        Optional<Boolean> boolValue = buildBoolean(value);
        if (boolValue.isPresent()) {
            return new MemoryCell(new MemoryCellTypes.Bool(boolValue.get()));
        }

        Optional<String> stringValue = buildString(value);
        if (stringValue.isPresent()) {
            return new MemoryCell(new MemoryCellTypes.StringType(stringValue.get()));
        }

        throw new WrongTypeException("Value: " + value + " is of unknown type");
    }

    // Functions for identifying type
    private static Optional<Integer> buildInteger(String value) {
        try {
            int convertedValue = Integer.parseInt(value);
            return Optional.of(convertedValue);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Double> buildNumber(String value) {
        try {
            double convertedValue = Double.parseDouble(value);
            return Optional.of(convertedValue);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Boolean> buildBoolean(String value) {
        // Todo: Make this a global setting between tokenizer and memory packages
        if (value.equals("true")) {
            return Optional.of(true);
        }
        if (value.equals("false")) {
            return Optional.of(false);
        }
        return Optional.empty();
    }

    // Note: Removes the quotes
    private static Optional<String> buildString(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return Optional.of(value.substring(1, value.length() - 1));
        }
        return Optional.empty();
    }
}
