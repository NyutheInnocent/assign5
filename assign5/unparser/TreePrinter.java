package assign5.unparser;

import assign5.ast.*;
import assign5.parser.*;
import assign5.visitor.*;

/**
 * @author Chase Dodge
 */
public class TreePrinter extends ASTVisitor{
    public Parser parser = null;
    
    int level = 0;
    String indent = "...";
    
    public TreePrinter() {
        // this.parser = new Parser();
        // visit(this.parser.cunit);
    }

    public TreePrinter(Parser parser) {
        this.parser = parser;
        visit(this.parser.cunit);
    }

    /////////////////////
    //  Visit Methods  //
    /////////////////////

    public void visit(CompilationUnit n) {
        println("Tree Printer starts ...\n");
        println("CompilationUnit");

        indentUp();
        n.block.accept(this);
        indentDown();

        println("Tree Printer finshed\n");
    }

    public void visit(BlockStatementNode n) {
        printIndent(indent);
        println("BlockStatementNode");

        if (n.decls != null) {
            indentUp();
            n.decls.accept(this);
            indentDown();
        }

        if (n.stmts != null) {
            indentUp();
            n.stmts.accept(this);
            indentDown();
        }
    }

    public void visit(Statements n) {
        if (n.stmts != null) {
            n.stmt.accept(this);
            n.stmts.accept(this);
        }
    }

    public void visit(StatementNode n) {
        if (n.stmts != null) {
            indentUp();
            n.stmt.accept(this);
            indentDown();
            n.stmts.accept(this);
        }
    }

    public void visit(Declarations n) {
        if (n.decls != null) {
            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit(DeclarationNode n) {
        printIndent(indent);
        print("DeclarationNode: " + n.type.basic);
        
        if (n.type.array != null) {
            n.type.accept(this);       
        } else {
            println("");
        }

        indentUp();
        n.id.accept(this);
        indentDown();
    }

    public void visit(AssignmentNode n) {
        printIndent(indent);
        println("AssignmentNode");

        indentUp();
        n.left.accept(this);

        printIndent(indent);
        println("operator: =");

        acceptNode(n.right);
        indentDown();
    }

    public void visit(BinaryExprNode n) {
        printIndent(indent);
        println("BinaryExprNode: " + n.op);

        indentUp();

        acceptNode(n.left);

        // Right Hand Side
        if (n.right != null) {
            acceptNode(n.right);
        }
        indentDown();
    }

    public void visit(IfStatementNode n) {
        printIndent(indent);
        println("IfStatementNode: " + n.bool.lexeme);
        
        if (n.stmts != null) {
            indentUp();
            n.stmts.accept(this);
            indentDown();
        }
    }

    public void visit(ElseStatementNode n) {
        printIndent(indent);
        println("ElseStatementNode");

        if (n.stmts != null) {
            indentUp();
            n.stmts.accept(this);
            indentDown();
        }
    }

    public void visit(WhileStatementNode n) {
        printIndent(indent);
        println("WhileStatementNode: " + n.bool.lexeme);

        indentUp();
        if (n.id1 != null && n.id2 != null) {
            indentUp();
            n.id1.accept(this);
            indentDown();

            indentUp();
            n.id2.accept(this);
            indentDown();
        }
        indentDown();

        if (n.stmts != null) {
            n.stmts.accept(this);
        }
    }

    public void visit(DoWhileStatementNode n) {
        printIndent(indent);
        println("DoWhileStatementNode");
        
        if (n.stmts != null) {
            n.stmts.accept(this);
        }
    }

    public void visit(BreakStatementNode n) {
        printIndent(indent);
        println("BreakStatementNode");
    }

    public void visit(TypeNode n) {
        if (n.array != null) {
            n.array.accept(this);
        }
    }

    public void visit(ArrayTypeNode n) {
        println("[" + (n.id == null ? n.size : n.id.id) + "]");

        if (n.type != null) {
            n.type.accept(this);
        }
    }

    public void visit(IntNode n) {
        printIndent(indent);
        println("IntNode: " + n.value);
    }

    public void visit(FloatNode n) {
        printIndent(indent);
        println("FloatNode: " + n.value);
    }

    public void visit(IdentifierNode n) {
        printIndent(indent);
        print("IdentifierNode: " + n.id);

        if (n.array != null) {
            n.array.accept(this);
        } else {
            println("");
        }
    }

    /////////////////////
    // Utility Methods //
    /////////////////////

    void acceptNode(Node n) {
        if (n instanceof IdentifierNode) {
            ((IdentifierNode)n).accept(this);
        }
        else if (n instanceof IntNode) {
            ((IntNode)n).accept(this);
        }
        else if (n instanceof FloatNode) {
            ((FloatNode)n).accept(this);
        }
        else {
            ((BinaryExprNode)n).accept(this);
        }
    }

    void print(String s) {
        System.out.print(s);
    }

    void println(String s) {
        System.out.println(s);
    }

    void printSpace() {
        System.out.print(" ");
    }

    int indent_level = 0;

    void indentUp() {
        indent_level++;
    }

    void indentDown() {
        indent_level--;
    }

    void printIndent() {
        String s = "";
        for (int i = 0; i < indent_level; i++) {
            s += "  ";
        }
        print(s);
    }
    void printIndent(String indent) {
        String s = "";
        for (int i = 0; i < indent_level; i++) {
            s += indent;
        }
        print(s);
    }
    void printIndent(String indent, int j) {
        String s = "";
        for (int i = 0; i < indent_level - j; i++) {
            s += indent;
        }
        print(s);
    }
}
