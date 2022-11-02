package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////
// stmt --> while ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class WhileStatementNode extends StatementNode {
    
    public Word bool; 
    public IdentifierNode id1;
    public IdentifierNode id2;
    public StatementNode stmt;

    public WhileStatementNode() { }
    public WhileStatementNode(Word bool, StatementNode stmt) {
        this.bool = bool;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
