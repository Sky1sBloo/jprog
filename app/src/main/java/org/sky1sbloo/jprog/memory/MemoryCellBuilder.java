package org.sky1sbloo.jprog.memory;


import java.util.Optional;

/**
 * Class used to build memory cell
 */
public class MemoryCellBuilder {
    // Identifies datatype based on value
    public MemoryCell build(String value) throws WrongTypeException {
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
