package assign5.ast;

import assign5.visitor.*;

//////////////////////////////////
// stmt --> while ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class WhileStatementNode extends StatementNode {
    
    public boolean bool;
    public StatementNode stmt;

    public WhileStatementNode() { }
    public WhileStatementNode(boolean bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
