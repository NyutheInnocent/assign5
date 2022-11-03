package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////////
// stmt --> do stmt while ( bool ) ;
//////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class DoWhileStatementNode extends Statements {
   
    public Word bool;
    public IdentifierNode id1;
    public IdentifierNode id2;
    public Statements stmts;

    public DoWhileStatementNode() { }
    public DoWhileStatementNode(Word bool, Statements stmts) {
        this.bool = bool;
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
