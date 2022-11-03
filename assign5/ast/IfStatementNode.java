package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////
// stmt --> if ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class IfStatementNode extends Statements {
    
    public Word bool;
    public Node id1;
    public Node id2;
    public Statements stmts;

    public IfStatementNode() { }
    public IfStatementNode(Word bool, Statements stmts) {
        this.bool = bool;
        this.stmts = stmts;
    }
    
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
