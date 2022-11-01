package assign5.ast;

import assign5.visitor.*;

//////////////////////////////
//                          //
//   Stmts --> Stmts Stmt   //
//   Stmt -->  id = expr    //
//                          //
//////////////////////////////

public class Statements extends Node {

    public Statements stmts;
    public Node stmt;

    public Statements() { }
    public Statements(Statements stmts, Node stmt) {
        this.stmts = stmts;
        this.stmt = stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);        
    }
    
}
