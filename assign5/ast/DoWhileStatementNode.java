package assign5.ast;

import assign5.visitor.*;

//////////////////////////////////////
// stmt --> do stmt while ( bool ) ;
//////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class DoWhileStatementNode extends StatementNode {
   
    public StatementNode stmt;
    public boolean bool;

    public DoWhileStatementNode() { }
    public DoWhileStatementNode(boolean bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
