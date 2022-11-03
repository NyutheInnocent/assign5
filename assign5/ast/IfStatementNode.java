package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////
// stmt --> if ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class IfStatementNode extends StatementNode {
    
    public Word bool;
    public Node id1;
    public Node id2;
    public StatementNode stmt;

    public IfStatementNode() { }
    public IfStatementNode(Word bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }
    
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
