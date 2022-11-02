package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

/////////////////////////////////////
// Type --> Type [num] | basic ;
/////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class TypeNode extends Node {

    public Type basic; // basic type (e.g. Type.Int, etc...)
    public ArrayTypeNode array = null; // By default, array type is null

    public TypeNode() { }
    public TypeNode(Type basic, ArrayTypeNode array) {
        this.basic = basic;
        this.array = array;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
