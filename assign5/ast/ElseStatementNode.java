package assign5.ast;

import assign5.visitor.*;

////////////////////////////////////////
// stmt --> if ( bool ) stmt else stmt
////////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class ElseStatementNode extends StatementNode {
    
    public boolean bool;
    public StatementNode stmt;

    public ElseStatementNode() { }
    public ElseStatementNode(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
