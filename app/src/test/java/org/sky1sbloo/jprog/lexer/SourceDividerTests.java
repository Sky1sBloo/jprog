package org.sky1sbloo.jprog.lexer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class SourceDividerTests {
    @Test
    public void testSourceDivider() {
        String source =
                """
                        var x = 5.30;
                        func myFunction(z, y) {
                            return true;
                            var y = 10;
                            var z = x * y;
                            z += "wow";
                            y++;
                            x = y / 2;
                            return false;
                        }
                        """;

        List<String> expectedTokens = List.of(
                "var", "x", "=", "5.30", ";", "\n",
                "func", "myFunction", "(", "z", ",", "y", ")", "{", "\n",
                "return", "true", ";", "\n",
                "var", "y", "=", "10", ";", "\n",
                "var", "z", "=", "x", "*", "y", ";", "\n",
                "z", "+=", "\"wow\"", ";", "\n",
                "y", "++", ";", "\n",
                "x", "=", "y", "/", "2", ";", "\n",
                "return", "false", ";", "\n",
                "}", "\n"
        );

        SourceDivider sourceDivider = new SourceDivider(source);
        assertEquals(expectedTokens, sourceDivider.getTokenValues());
    }
}
