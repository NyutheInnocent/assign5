package assign5.ast;

import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class StatementNode extends Node {

    public Statements stmts;
    public Node stmt;

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
