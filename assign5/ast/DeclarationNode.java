package assign5.ast;

import assign5.visitor.*;

//////////////////////
// Decl --> Type id ;
//////////////////////

/**
 * @author Chase Dodge
 */
public class DeclarationNode extends Node{

    public TypeNode type;
    public IdentifierNode id;

    public DeclarationNode() { }
    public DeclarationNode(TypeNode type, IdentifierNode id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);        
    }
}
