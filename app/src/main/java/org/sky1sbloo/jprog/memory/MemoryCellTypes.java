package org.sky1sbloo.jprog.memory;

import org.sky1sbloo.jprog.syntaxtree.ParseNodes;


public class MemoryCellTypes {
    /**
     * Holder for variant
     */
    public sealed interface Cell permits Int, Number, Bool, StringType, Null, FunctionDefinition {
    }

    public record Int(int value) implements Cell {
    }

    public record Number(Double value) implements Cell {
    }

    public record Bool(boolean value) implements Cell {
    }

    public record StringType(String value) implements Cell {
    }

    public record Null() implements Cell {
    }

    public record FunctionDefinition(ParseNodes.FunctionDefinitionExpr functionDefinitionExpr) implements Cell {
    }
}
