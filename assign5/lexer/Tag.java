package assign5.lexer;

/**
 * @author Chase Dodge
 */
public class Tag {
    public static final int BASIC = 250;
    public static final int ID = 257;
    
    // Reserved Words
    public static final int TRUE = 258;
    public static final int FALSE = 259;
    public static final int WHILE = 260;
    public static final int DO = 261;
    public static final int INT = 262;
    public static final int FLOAT = 263;
    public static final int IF = 264;
    public static final int BREAK = 265;
    public static final int BLOCK = 266;
    public static final int ELSE = 267;

    // Operators
    public static final int AND = 366;
    public static final int OR = 367;
    public static final int EQ = 368;
    public static final int NE = 369;
    public static final int LE = 370;
    public static final int GE = 371;
    public static final int MINUS = 372;
    public static final int TEMP = 373;
}
