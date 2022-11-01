package assign5.ast;

import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class AssignmentNode extends Node{
    public IdentifierNode left;
    public Node right;

    public AssignmentNode() { }
    public AssignmentNode(IdentifierNode id, Node binary) {
        this.left = id;
        this.right = binary;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
