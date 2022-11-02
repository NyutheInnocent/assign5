package assign5.lexer;

/**
 * @author Chase Dodge
 */
public class Word extends Token {
    public String lexeme = "";

    public static final Word True = new Word("true", Tag.TRUE);
    public static final Word False = new Word("false", Tag.FALSE);
    public static final Word While = new Word("while", Tag.WHILE);
    public static final Word Do = new Word("do", Tag.DO);
    public static final Word If = new Word("if", Tag.IF);
    public static final Word Else = new Word("else", Tag.ELSE);
    public static final Word Break = new Word("break", Tag.BREAK);


    public static final Word And = new Word("&&", Tag.AND);
    public static final Word Or = new Word("||", Tag.OR);
    public static final Word Eq = new Word("==", Tag.EQ);
    public static final Word Ne = new Word("!=", Tag.NE);
    public static final Word Le = new Word("<=", Tag.LE);
    public static final Word Ge = new Word(">=", Tag.GE);
    public static final Word Minus = new Word("minus", Tag.MINUS);
    public static final Word Temp = new Word("t", Tag.TEMP);

    public Word(String s, int t) {
        super(t); lexeme = s;
    }
    public String toString() {
        return lexeme;
    }
}
