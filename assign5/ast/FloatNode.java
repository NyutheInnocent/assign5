package assign5.ast;

import assign5.visitor.*;
import assign5.lexer.Float;

/**
 * @author Chase Dodge
 */
public class FloatNode extends Node {

    public float value;
    public Float v;

    public FloatNode() {
        super();
    }
    public FloatNode(Float v) {
        super();
        this.value = v.value;
        this.v = v;
    }

    public void printNode() {
        System.out.println("FloatNode: " + this.value);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
