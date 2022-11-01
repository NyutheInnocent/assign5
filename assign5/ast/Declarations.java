package assign5.ast;

import assign5.visitor.*;

//////////////////////////
// Decls --> Decls decl 
// Decl --> type[num] | basic

/**
 * @author Chase Dodge
 */
public class Declarations extends Node {

    public Declarations decls;
    public DeclarationNode decl;

    public Declarations() { }
    public Declarations(Declarations decls, DeclarationNode decl) {
        this.decls = decls;
        this.decl = decl;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
