package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

//////////////////////////////////
// stmt --> while ( bool ) stmt 
//////////////////////////////////

/**
 * @author Chase Dodge
 */
public class WhileStatementNode extends Statements {
    
    public Word bool; 
    public Node id1;
    public Node id2;
    public Statements stmts;

    public WhileStatementNode() { }
    public WhileStatementNode(Word bool, Statements stmt) {
        this.bool = bool;
        this.stmt = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
