package assign5.ast;

import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class CompilationUnit extends Node {
    
    // Node ast;
    // public AssignmentNode assign;
    public BlockStatementNode block;

    public CompilationUnit() { }
    public CompilationUnit(BlockStatementNode block) {
        this.block = block;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
