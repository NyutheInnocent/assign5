package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////////
// stmt --> do stmt while ( bool ) ;
//////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class DoWhileStatementNode extends StatementNode {
   
    public Word bool;
    public IdentifierNode id1;
    public IdentifierNode id2;
    public StatementNode stmt;

    public DoWhileStatementNode() { }
    public DoWhileStatementNode(Word bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
