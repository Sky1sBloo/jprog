package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MemoryCellTests {
    @Test
    public void memoryCellBuilderTests() {
        String[] testValues = {"true", "1.0534", "20", "20.0", "\"This is a string\"", "false"};
        List<MemoryCell> cells = new ArrayList<>();
        MemoryCell[] expectedCells = {
                new MemoryCell(new MemoryCellTypes.Bool(true)),
                new MemoryCell(new MemoryCellTypes.Number(1.0534)),
                new MemoryCell(new MemoryCellTypes.Int(20)),
                new MemoryCell(new MemoryCellTypes.Number(20.0)),
                new MemoryCell(new MemoryCellTypes.StringType("This is a string")),
                new MemoryCell(new MemoryCellTypes.Bool(false))
        };

        for (String testValue : testValues) {
            Assertions.assertDoesNotThrow(() -> {
                cells.add(MemoryCells.build(testValue));
            });
        }

        if (cells.size() != expectedCells.length) {
            Assertions.fail();
        }

        Assertions.assertDoesNotThrow(() -> {
            for (int i = 0; i < cells.size(); i++) {
                Assertions.assertTrue(MemoryCells.isEqual(cells.get(i), expectedCells[i]));
            }
        });
    }
}
