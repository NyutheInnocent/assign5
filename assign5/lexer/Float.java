package assign5.lexer;

/**
 * Represents a Float token.
 * @author Chase Dodge
 */
public class Float extends Token {

    public final float value;

    public Float(float f) {
        super(Tag.FLOAT);
        this.value = f;
    }
    
    public String toString() {
        return "" + this.value;
    }
}
