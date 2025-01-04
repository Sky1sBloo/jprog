package org.sky1sbloo.jprog.memory;

import com.google.common.math.DoubleMath;
import org.sky1sbloo.jprog.syntaxtree.ExprTypes;
import org.sky1sbloo.jprog.syntaxtree.ParseNodes;

import java.util.List;
import java.util.function.Function;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class for memory cell
 */
public class MemoryCells {
    /**
     * Visits all primitive types
     */
    public static <R> R visit(MemoryCell cell,
                              Function<Integer, R> intVisitor,
                              Function<Double, R> numberVisitor,
                              Function<Boolean, R> boolVisitor,
                              Function<String, R> stringVisitor,
                              Function<ParseNodes.FunctionDefinitionExpr, R> functionVisitor,
                              Supplier<R> nullVisitor) throws WrongTypeException {
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
        if (cell.value() instanceof MemoryCellTypes.Null) {
            return nullVisitor.get();
        }
        if (cell.value() instanceof MemoryCellTypes.FunctionDefinition(
                ParseNodes.FunctionDefinitionExpr functionDefinitionExpr
        )) {
            return functionVisitor.apply(functionDefinitionExpr);
        }
        throw new WrongTypeException("Cell is at unknown type");
    }

    public static boolean isEqual(MemoryCell cell1, MemoryCell cell2) throws WrongTypeException {
        Optional<Boolean> result = visit(cell1,
                (Integer cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Int(int cell2Value)) {
                        return Optional.of(cell1Value == cell2Value);
                    }
                    return Optional.empty();
                },
                (Double cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Number(Double cell2Value)) {
                        return Optional.of(DoubleMath.fuzzyEquals(cell1Value, cell2Value, 0.000001d));
                    }
                    return Optional.empty();
                },
                (Boolean cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.Bool(boolean cell2Value)) {
                        return Optional.of(cell1Value == cell2Value);
                    }
                    return Optional.empty();
                },
                (String cell1Value) -> {
                    if (cell2.value() instanceof MemoryCellTypes.StringType(String cell2Value)) {
                        return Optional.of(cell1Value.equals(cell2Value));
                    }
                    return Optional.empty();
                },
                (ParseNodes.FunctionDefinitionExpr expr) -> Optional.empty(),
                () -> Optional.of(cell2.value() instanceof MemoryCellTypes.Null));

        if (result.isEmpty()) {
            throw new WrongTypeException("Equality check between different types or function definition");
        }
        return result.get();
    }

    /**
     * Identifies datatype based on value
     * Note: Only works on primitives
     */
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

        Optional<String> nullValue = buildNull(value);
        if (nullValue.isPresent()) {
            return new MemoryCell(new MemoryCellTypes.Null());
        }

        throw new WrongTypeException("Value: " + value + " is of unknown type");
    }

    /**
     * Builds a memory cell for function definition
     */
    public static MemoryCell buildFunction(String identifier, List<String> paramId, List<ExprTypes.Expr> body) {
        return new MemoryCell(
                new MemoryCellTypes.FunctionDefinition(
                        new ParseNodes.FunctionDefinitionExpr(identifier, paramId, body))
        );
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

    private static Optional<String> buildNull(String value) {
        if (value.equals("null")) {
            return Optional.of(value);
        }
        return Optional.empty();
    }
}
