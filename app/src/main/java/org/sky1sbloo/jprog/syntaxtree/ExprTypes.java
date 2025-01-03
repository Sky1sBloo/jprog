package org.sky1sbloo.jprog.syntaxtree;

public class ExprTypes {
    /**
     * Interface containing all the expression types
     */
    public sealed interface Expr permits Terminal, Value {
    }

    /**
     * Interface containing all the terminal expression types
     */
    public non-sealed interface Terminal extends Expr {
    }

    /**
     * Interface containing all the returnable expression types
     */
    public non-sealed interface Value extends Expr {
    }
}
