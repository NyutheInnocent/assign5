package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

/**
 * Represents integer values in the grammar. 
 * @author Chase Dodge
 */
public class IntNode extends Node {

    public int value;
    public Int v;

    public IntNode() { }
    public IntNode(Int v) {
        this.value = v.value;
        this.v = v;
    }

    public void printNode() {
        System.out.println("IntNode: " + value);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
