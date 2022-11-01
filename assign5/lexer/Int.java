package assign5.lexer;

/**
 * Represents an Integer token.
 * @author Chase Dodge
 */
public class Int extends Token {
    public final int value;
    public Int(int v) {super(Tag.INT); value = v;}
    public String toString() {
        return "" + value;
    }
}

