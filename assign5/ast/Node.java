package assign5.ast;

import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public abstract class Node {
    public abstract void accept(ASTVisitor visitor);
}
