package org.sky1sbloo.jprog.memory;

import com.google.common.math.DoubleMath;
import org.sky1sbloo.jprog.syntaxtree.BinaryOperators;
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


    // Added separate visitor call for function definition
    public static ParseNodes.FunctionDefinitionExpr visitFunctionDefinition(MemoryCell cell) throws WrongTypeException {
        if (cell.value() instanceof MemoryCellTypes.FunctionDefinition(
                ParseNodes.FunctionDefinitionExpr functionDefinitionExpr
        )) {
            return functionDefinitionExpr;
        }
        throw new WrongTypeException("Cell is not a function definition");
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
    public static MemoryCell buildFunction(String identifier, List<String> paramId, List<ParseNodes.Expr> body) {
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

    /**
     * Identifies and performs binary operation on memory cells
     */
    public static MemoryCell performBinaryOperation(MemoryCell left, MemoryCell right, BinaryOperators operation) throws WrongTypeException {
        return switch (operation) {
            case BinaryOperators.ADD -> addCells(left, right);
            case BinaryOperators.SUBTRACT -> subtractCells(left, right);
            case BinaryOperators.MULTIPLY -> multiplyCells(left, right);
            case BinaryOperators.DIVIDE -> divideCells(left, right);
            case BinaryOperators.MODULO -> moduloCells(left, right);
            case BinaryOperators.EQUALS ->
                    MemoryCells.build(String.valueOf(isEqual(left, right))); // Note ensure the value of true is "true" and false is "false"
            case BinaryOperators.NOT_EQUALS -> MemoryCells.build(String.valueOf(!isEqual(left, right)));
            case BinaryOperators.GREATER_THAN -> compareGreaterThanCells(left, right);
            case BinaryOperators.LESS_THAN -> compareLessThanCells(left, right);
            case BinaryOperators.GREATER_THAN_OR_EQUAL -> {
                MemoryCell leftGreaterThanRight = compareGreaterThanCells(left, right);
                MemoryCell isEqual = MemoryCells.build(String.valueOf(isEqual(left, right)));
                if (MemoryCells.isEqual(isEqual, MemoryCells.build("true"))) {
                    yield isEqual;
                }
                if (MemoryCells.isEqual(leftGreaterThanRight, MemoryCells.build("true"))) {
                    yield leftGreaterThanRight;
                }
                yield isEqual;
            }
            case BinaryOperators.LESS_THAN_OR_EQUAL -> {
                MemoryCell leftGreaterThanRight = compareLessThanCells(left, right);
                MemoryCell isEqual = MemoryCells.build(String.valueOf(isEqual(left, right)));
                if (MemoryCells.isEqual(isEqual, MemoryCells.build("true"))) {
                    yield isEqual;
                }
                if (MemoryCells.isEqual(leftGreaterThanRight, MemoryCells.build("true"))) {
                    yield leftGreaterThanRight;
                }
                yield isEqual;
            }
            case AND -> performAndOperationCells(left, right);
            case OR -> performOrOperationCells(left, right);
        };
    }

    /**
     * Adds two memory cells
     * Note: Only works on primitives and string
     */
    public static MemoryCell addCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue + rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue + rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue + rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue + rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.StringType(String leftValue)) {
            if (right.value() instanceof MemoryCellTypes.StringType(String rightValue)) {
                return MemoryCells.build("\"" + leftValue + rightValue + "\"");
            }
        }
        throw new WrongTypeException("Cannot perform addition on memory cells of invalid cell types");
    }

    /**
     * Subtracts two memory cells
     * Note: Only works on primitives
     */
    public static MemoryCell subtractCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue - rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue - rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue - rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue - rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform subtraction on memory cells of invalid cell types");
    }


    /**
     * Multiplies two memory cells
     * Note: Only works on primitives
     */
    public static MemoryCell multiplyCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue * rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue * rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue * rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue * rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform multiplication on memory cells of invalid cell types");
    }

    /**
     * Divides two memory cells
     * Note: Only works on primitives
     */
    public static MemoryCell divideCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue / rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue / rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue / rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue / rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform division on memory cells of invalid cell types");
    }


    /**
     * Modulo two memory cells
     * Note: Only works on primitives
     */
    public static MemoryCell moduloCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue % rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue % rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue % rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue % rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform modulo on memory cells of invalid cell types");
    }

    /**
     * Compares if left memory cell is greater than right memory cell
     */
    public static MemoryCell compareGreaterThanCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue > rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue > rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue > rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue > rightValue));
            }
        }
        throw new WrongTypeException("Cannot compare > memory cells of invalid cell types");
    }

    public static MemoryCell compareLessThanCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Int(int leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue < rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue < rightValue));
            }
        }
        if (left.value() instanceof MemoryCellTypes.Number(Double leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Int(int rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue < rightValue));
            }
            if (right.value() instanceof MemoryCellTypes.Number(Double rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue < rightValue));
            }
        }
        throw new WrongTypeException("Cannot compare < memory cells of invalid cell types");
    }

    public static MemoryCell performAndOperationCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Bool(boolean leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Bool(boolean rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue && rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform AND operation on non boolean memory cells");
    }

    public static MemoryCell performOrOperationCells(MemoryCell left, MemoryCell right) throws WrongTypeException {
        if (left.value() instanceof MemoryCellTypes.Bool(boolean leftValue)) {
            if (right.value() instanceof MemoryCellTypes.Bool(boolean rightValue)) {
                return MemoryCells.build(String.valueOf(leftValue || rightValue));
            }
        }
        throw new WrongTypeException("Cannot perform OR operation on non boolean memory cells");
    }
}
