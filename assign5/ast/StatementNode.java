package assign5.ast;

import assign5.visitor.*;

public class StatementNode extends Node {

    @Override
    public void accept(ASTVisitor visitor) {
        // TODO Auto-generated method stub
        visitor.visit(this);
    }
    
}
