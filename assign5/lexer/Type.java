package assign5.lexer;

/**
 * @author Chase Dodge
 */
public class Type extends Word {

    public int width = 0; // Width is used for storage allocation

    public static final Type Int   = new Type("int",   Tag.BASIC, 4);
    public static final Type Float = new Type("float", Tag.BASIC, 8);
    public static final Type Char  = new Type("char",  Tag.BASIC, 1);
    public static final Type Bool  = new Type("bool",  Tag.BASIC, 1);

    public Type(String s, int t, int w) {
        super(s, t);
        this.width = w;
    }

    public static boolean numeric(Type p) {
        if (p != Type.Bool) {
            return true;
        } else {
            return false;
        }
    }
    
    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) {
            return null;
        } 
        else if (p1 == Type.Float || p2 == Type.Float) {
            return Type.Float;
        } 
        else if (p1 == Type.Int || p2 == Type.Int) {
            return Type.Int;
        } 
        else {
            return Type.Char;
        }
    }
}
