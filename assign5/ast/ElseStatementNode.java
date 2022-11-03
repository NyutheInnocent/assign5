package assign5.ast;

import javax.swing.plaf.nimbus.State;

import assign5.visitor.*;

////////////////////////////////////////
// stmt --> if ( bool ) stmt else stmt
////////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class ElseStatementNode extends Statements {
    
    public boolean bool;
    public Statements stmts;

    public ElseStatementNode() { }
    public ElseStatementNode(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
