package assign5.ast;

import assign5.visitor.*;

/////////////////////
// stmt --> break ; 
/////////////////////

/**
 * @author Chase Dodge
 */
public class BreakStatementNode extends StatementNode {
    

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
