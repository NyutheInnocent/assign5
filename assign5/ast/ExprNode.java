package assign5.ast;

import assign5.visitor.*;

/////////////////////////////////////////////////////////
//
// expr --> || | && | == | != | < | <= | > | >= | 
//          +  | -  | /  | !  | unnary - |
//          id | num | real | true | false | 
//          ( expr )
//
/////////////////////////////////////////////////////////

/**
 * @author Chase Dodge
 */
public class ExprNode extends Node {

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
