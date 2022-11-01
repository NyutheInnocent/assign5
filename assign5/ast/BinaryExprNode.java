package assign5.ast;

import assign5.lexer.*;
import assign5.visitor.*;

/**
 * @author Chase Dodge
 * @examples: 
 *      a = 1; 
 *      b = c;
 *      d = e + f;
 *      x = y - 2; 
 */
public class BinaryExprNode extends Node {
    public Node left;
    public Node right;
    public Token op;

    public BinaryExprNode() { }
    public BinaryExprNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
    public BinaryExprNode(Token op, Node left, Node right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
