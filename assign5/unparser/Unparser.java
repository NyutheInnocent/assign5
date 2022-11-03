package assign5.unparser;

import java.io.File;
import java.io.FileWriter;

import assign5.ast.*;
import assign5.parser.*;
import assign5.visitor.*;

/**
 * The Unparser class.
 * Used to write a formatted version of the input.txt file to a new file called output.txt
 * @author Chase Dodge
 */
public class Unparser extends ASTVisitor {
    public Parser parser = null;
    StringBuilder fileContent = new StringBuilder();
    int lineNumber = 1;
    int indent = 0;

    public Unparser() { }
    public Unparser(Parser parser) {
        this.parser = parser;
        visit(parser.cunit);
    }

    public void visit(CompilationUnit n) {
        println("Unparser starts ...");

        n.block.accept(this);

        try {
            File output = new File("output.txt");
            output.createNewFile();

            FileWriter writer = new FileWriter(output);
            writer.write(fileContent.toString());
            writer.close();
            println("Successfully wrote to file.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        println("Unparser finished.\n");
    }

    public void visit(BlockStatementNode n) {
        printIndent();
        println("{");
        write(addIndent() + "{", true);

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

        printIndent();
        println("}");
        write(addIndent() + "}", true);
    }

    public void visit(Declarations n) {
        if (n.decls != null) {
            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit(DeclarationNode n) {
        n.type.accept(this);

        print(" ");
        write(" ");

        n.id.accept(this);

        println(" ;");
        write(" ;", true);
    }

    public void visit(Statements n) {
        if (n.stmts != null) {
            n.stmt.accept(this);
            n.stmts.accept(this);
        }
    }

    // Superclass for newly introduced stmts
    public void visit(StatementNode n ) {
        n.stmt.accept(this);
    }

    public void visit(AssignmentNode n) {
        printIndent();
        write(addIndent());

        indentUp();
        n.left.accept(this);
        indentDown();

        print(" = ");
        write(" = ");

        indentUp();
        n.right.accept(this);
        indentDown();

        println(" ;");
        write(" ;", true);
    }

    public void visit(BinaryExprNode n) {
        
        acceptNode(n.left);

        if (n.op != null) {
            String temp = " " + n.op.toString() + " ";
            print(temp);
            write(temp);
        }

        if (n.right != null) {
            acceptNode(n.right);
        }
    }

    public void visit(IfStatementNode n) {
        printIndent();
        print("if (");
        write(addIndent() + "if (");
        if (n.id1 != null) {
            n.id1.accept(this);
        }
        print(" " + n.bool.lexeme + " ");
        write(" " + n.bool.lexeme + " ");
        if (n.id2 != null) {
            n.id2.accept(this);
        }

        println(")");
        write(")", true);
        
        indentUp();
        n.stmt.accept(this);
        indentDown();
    }

    public void visit(ElseStatementNode n) {
        printIndent();
        print("else");
        write(addIndent() + "else");

        indentUp();
        n.stmt.accept(this);
        indentDown();
    }

    public void visit(WhileStatementNode n) {
        printIndent();
        print("while (");
        write(addIndent()  + "while (");
        if (n.id1 != null) {
            indentUp();
            n.id1.accept(this);
            indentDown();
        }
        print(" " + n.bool.lexeme + " ");
        write(" " + n.bool.lexeme + " ");
        if (n.id2 != null) {
            indentUp();
            n.id2.accept(this);
            indentDown();
        }
        

        if (n.stmt != null) {
            println(")");
            write(")", true);
            n.stmt.accept(this);
        } else {
            println(") ;\n");
            write(") ;\n", true);
        }
    }

    public void visit(DoWhileStatementNode n) {
        printIndent();
        println("do");
        write(addIndent() + "do", true);

        indentUp();
        n.stmt.accept(this);
        indentDown();
    }

    public void visit(BreakStatementNode n) {
        printIndent();
        println("break ;\n");
        write(addIndent() + "break ;\n", true);
    }

    public void visit(TypeNode n) {
        printIndent();
        print(n.basic.toString());
        write(addIndent() + n.basic.toString());

        if (n.array != null) {
            n.array.accept(this);
        }
    }

    public void visit(ArrayTypeNode n) {
        

        print("[" + (n.id != null ? n.id.id : n.size) + "]");
        write("[" + (n.id != null ? n.id.id : n.size) + "]");

        if (n.type != null) {
            n.type.accept(this);
        }
    } 

    public void visit(IntNode n) {
        print("" + n.value);
        write("" + n.value);
    }

    public void visit(FloatNode n) {
        print("" + n.value);
        write("" + n.value);
    }

    public void visit(IdentifierNode n) {
        // Leaf node
        print(n.id);
        write(n.id);

        if (n.array != null) {
            n.array.accept(this);
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

    void indentUp() {
        indent++;
    }

    void indentDown() {
        indent--;
    }

    void printIndent() {
        String s = "";
        for (int i = 0; i < indent; i++) {
            s += "  ";
        }
        print(s);
    }

    String addIndent() {
        String s = "";
        for (int i = 0; i < indent; i++) {
            s += "  ";
        }
        return s;
    }

    void write(String s) {
        fileContent.append(s);
    }
    void write(String s, boolean includeNewLine) {
        fileContent.append(s + (includeNewLine ? '\n' : ""));
    }
}
