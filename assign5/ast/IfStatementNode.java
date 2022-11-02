package assign5.ast;

import assign5.visitor.*;

//////////////////////////////////
// stmt --> if ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class IfStatementNode extends StatementNode {
    
    public boolean bool;
    public StatementNode stmt;

    public IfStatementNode() { }
    public IfStatementNode(boolean bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }
    
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
