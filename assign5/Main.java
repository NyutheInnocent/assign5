package assign5;

import assign5.lexer.*;
import assign5.parser.*;
import assign5.unparser.*;

/**
 * @author Chase Dodge
 */
public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        TreePrinter tree = new TreePrinter(parser);
        Unparser unparser = new Unparser(parser);
    }
}