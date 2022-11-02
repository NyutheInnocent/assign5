package assign5.visitor;

import assign5.ast.*;

/**
 * @author Chase Dodge
 */
public class ASTVisitor {
    public void visit(CompilationUnit n) {
        n.block.accept(this);
    }
    public void visit(BlockStatementNode n) {
        n.decls.accept(this);
        n.stmts.accept(this);
    }

    public void visit(Declarations n) {
        if (n.decls != null) {
            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit(DeclarationNode n) {
        n.type.accept(this);
        n.id.accept(this);
    }

    

    public void visit(Statements n) {
        if (n.stmts != null) {
            n.stmt.accept(this);
            n.stmts.accept(this);
        }
    }
    
    public void visit(StatementNode n) {

    }

    public void visit(AssignmentNode n) {
        n.left.accept(this);
        // n.right.accept(this);

        if (n.right instanceof IdentifierNode) {
            ((IdentifierNode)n.right).accept(this);
        } else if (n.right instanceof IntNode) {
            ((IntNode)n.right).accept(this);
        } else if (n.right instanceof FloatNode) {
            ((FloatNode)n.right).accept(this);
        } else {
            ((BinaryExprNode)n.right).accept(this);
        }
    }
    
    public void visit(ExprNode n) {

    }

    public void visit(BinaryExprNode n) {
        // n.left.accept(this);
        // n.right .accept(this);
    }

    public void visit(IfStatementNode n) {

    }

    public void visit(ElseStatementNode n) {

    }

    public void visit(WhileStatementNode n) {

    }

    public void visit(DoWhileStatementNode n) {

    }

    public void visit(BreakStatementNode n) {
        
    }
    
    public void visit(TypeNode n) {

    }

    public void visit(ArrayTypeNode n) {
        
    }

    public void visit(IdentifierNode n) {
        // Leaf Node
    }

    public void visit(IntNode n) {
        // Leaf Node
    }

    public void visit(FloatNode n) {
        // Leaf Node
    }
}
