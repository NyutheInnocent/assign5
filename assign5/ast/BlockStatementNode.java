package assign5.ast;

import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class BlockStatementNode extends StatementNode {

    public Declarations decls;
    public Statements   stmts;

    public BlockStatementNode() { }
    public BlockStatementNode(Declarations decls) {
        this.decls = decls;
    }
    public BlockStatementNode(Statements stmts) {
        this.stmts = stmts;
    }
    public BlockStatementNode(Declarations decls, Statements stmts) {
        this.decls = decls;
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
