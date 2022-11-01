package assign5.ast;

import assign5.visitor.*;

public class ArrayTypeNode extends TypeNode{
    
    public TypeNode type; // Array of type
    public int size = 1; // Number of elements (size of array)

    public ArrayTypeNode() { }
    public ArrayTypeNode(int size, TypeNode type) {
        this.size = size;
        this.type = type;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
