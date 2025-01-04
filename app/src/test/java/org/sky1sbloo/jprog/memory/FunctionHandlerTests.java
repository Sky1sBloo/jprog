package org.sky1sbloo.jprog.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sky1sbloo.jprog.syntaxtree.ParseNodes;

import java.util.List;

public class FunctionHandlerTests {
    @Test
    public void testFunctionDefinition() throws WrongTypeException {
        ProgramMemory memory = new ProgramMemory();
        FunctionHandler functionHandler = new FunctionHandler(memory);

        ParseNodes.FunctionDefinitionExpr functionDefinitionExpr = new ParseNodes.FunctionDefinitionExpr("test",
                List.of("a", "b"),
                List.of(new ParseNodes.LiteralExpr("a"), new ParseNodes.LiteralExpr("b")));
        functionHandler.defineFunction(functionDefinitionExpr.identifier(), functionDefinitionExpr.argumentId(), functionDefinitionExpr.body());

        ParseNodes.FunctionDefinitionExpr retrievedFunction = functionHandler.getFunction("test", List.of("a", "b"));
        Assertions.assertEquals(functionDefinitionExpr.identifier(), retrievedFunction.identifier());
        Assertions.assertEquals(functionDefinitionExpr.argumentId(), retrievedFunction.argumentId());
        Assertions.assertEquals(functionDefinitionExpr.body(), retrievedFunction.body());

        Assertions.assertThrows(IllegalArgumentException.class, () -> functionHandler.getFunction("test", List.of("a")));
    }
}
