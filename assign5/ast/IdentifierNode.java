package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class IdentifierNode extends Node {
    public String id;
    public Word w;

    public ArrayTypeNode array = null;

    public IdentifierNode() { }
    public IdentifierNode(Word w) {
        this.id = w.lexeme;
        this.w = w;
    }

    public void printNode() {
        System.out.println("IdentifierNode: " + id);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
