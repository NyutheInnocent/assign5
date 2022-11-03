package assign5.parser;

import assign5.lexer.*;
import assign5.lexer.Float;
import assign5.visitor.*;
import assign5.ast.*;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author Chase Dodge
 */
public class Parser extends ASTVisitor {

    private Lexer lexer = null;
    public CompilationUnit cunit;

    private Token lookahead = null;

    int level = 0;
    String indent = "...";

    public Parser() {
        cunit = new CompilationUnit();
        move();
        visit(cunit);
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        cunit = new CompilationUnit();
        move();
        visit(cunit);
    }

    public void visit(CompilationUnit n) {
        println("Parser starts ...");
        // println("CompilationUnit");

        n.block = new BlockStatementNode();

        level++;
        n.block.accept(this);
        level--;

        println("Parser finished.\n");
    }

    public void visit(BlockStatementNode n) {
        printIndentation();
        // println("BlockStatementNode");

        match('{');

        if (lookahead.tag == Tag.BASIC) {
            n.decls = new Declarations();
            level++;
            n.decls.accept(this);
            level--;
        }

        n.stmts = new Statements();
        n.stmts.accept(this);

        match('}');
    }

    public void visit(Declarations n) {
        printIndentation();
        println("Declarations");

        if (lookahead.tag == Tag.BASIC) {
            n.decl = new DeclarationNode();
            level++;
            n.decl.accept(this);
            level--;

            n.decls = new Declarations();
            n.decls.accept(this);
        }
    }
    
    // decl --> type id ;
    public void visit(DeclarationNode n) {
        printIndentation();
        println("DeclarationNode");

        n.type = new TypeNode();
        level++;
        n.type.accept(this);
        level--;

        n.id = new IdentifierNode();
        level++;
        n.id.accept(this);
        level--;

        match(';');
    }

    // a = b + c;
    // d = e - f;
    // x = y + z;
    // int i ;
    // float j;
    // Recursively create declation and assignment nodes
    public void visit(Statements n) {
        if (!lookahead.toString().equals("}")) {

            // Check if lookahead.tag is ID | IF | WHILE | DO | BREAK | BLOCK
            switch (lookahead.tag) {
                case Tag.ID: {
                    n.stmt = new AssignmentNode();
                    level++;
                    ((AssignmentNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case Tag.IF: {
                    n.stmt = new IfStatementNode();
                    level++;
                    ((IfStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case Tag.ELSE: {
                    n.stmt = new ElseStatementNode();
                    level++;
                    ((ElseStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case Tag.DO: {
                    n.stmt = new DoWhileStatementNode();
                    level++;
                    ((DoWhileStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case Tag.WHILE: {
                    n.stmt = new WhileStatementNode();
                    level++;
                    ((WhileStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case Tag.BREAK: {
                    n.stmt = new BreakStatementNode();
                    level++;
                    ((BreakStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }
                case '{':
                case Tag.BLOCK: {
                    n.stmt = new BlockStatementNode();
                    level++;
                    ((BlockStatementNode)n.stmt).accept(this);
                    level--;

                    n.stmts = new Statements();
                    level++;
                    n.stmts.accept(this);
                    level--;

                    break;
                }

                default: 
                    break;
            }
        }
    }

    public void visit(AssignmentNode n) {
        // printIndentation();
        // println("AssignmentNode");

        // Left Hand Side 
        n.left = new IdentifierNode();
        level++;
        n.left.accept(this);
        level--;

        match('=');
        // printIndentation();
        // println("operator: =");

        // Right Hand Side
        Node rhs_assign = null;

        if (lookahead.tag == Tag.ID) {
            rhs_assign = new IdentifierNode();
            level++;
            ((IdentifierNode)rhs_assign).accept(this);
            level--;
        } 
        else if (lookahead.tag == Tag.INT) {
            rhs_assign = new IntNode();
            level++;
            ((IntNode)rhs_assign).accept(this);
            level--;
        }
        else if (lookahead.tag == Tag.FLOAT) {
            rhs_assign = new FloatNode();
            level++;
            ((FloatNode)rhs_assign).accept(this);
            level--;
        }
        // else if (lookahead.tag == Tag.)

        // a = b + c;
        if (lookahead.tag == ';') { // e.g. a = 19;
            n.right = rhs_assign;
        }
        else { // e.g. b = a + b * c;
            // printIndentation();
            // println("operator: " + lookahead);

            level++;
            // Build AST for binary expressions with operator precedence
            n.right = (BinaryExprNode) parseBinaryExprNode(rhs_assign, 0);
            level--;

            // println("**** Root Node operator: " + ((BinaryExprNode)n.right).op);
        }

        match(';');
    }

    // a = 1; 
    // b = c;
    // d = e + f;
    // x = y - 2; 
    // v = 5 - z + 1;
    public void visit(BinaryExprNode n) {
       // Building BinaryExprNode in AssignmentNode so nothing needs to go here.
    }

    public void visit(IfStatementNode n) {
        match(Tag.IF);
        match('(');

        if (lookahead.tag == Tag.ID || lookahead.tag == Tag.INT || lookahead.tag == Tag.FLOAT) {
            if (lookahead.tag == Tag.ID) {
                n.id1 = new IdentifierNode();
                level++;
                ((IdentifierNode)n.id1).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.INT) {
                n.id1 = new IntNode();
                level++;
                ((IntNode)n.id1).accept(this);
                level--;
            }
            else {
                n.id1 = new FloatNode();
                level++;
                ((FloatNode)n.id1).accept(this);
                level--;
            }

            n.bool = ((Word)lookahead);
            match(lookahead.tag);
            
            if (lookahead.tag == Tag.ID) {
                n.id2 = new IdentifierNode();
                level++;
                ((IdentifierNode)n.id2).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.INT) {
                n.id2 = new IntNode();
                level++;
                ((IntNode)n.id2).accept(this);
                level--;
            }
            else {
                n.id2 = new FloatNode();
                level++;
                ((FloatNode)n.id2).accept(this);
                level--;
            }
        } else if (lookahead.tag == Tag.TRUE || lookahead.tag == Tag.FALSE) {
            n.bool = ((Word)lookahead);
            match(lookahead.tag);
        } else {
            System.out.println("Error: Expected bool value in If statment but got ->" + lookahead.toString());
        }

        match(')');
        
        n.stmts = new Statements();
        level++;
        n.stmts.accept(this);
        level--;
    }

    public void visit(ElseStatementNode n) {
        match(Tag.ELSE);

        n.stmts = new Statements();
        level++;
        n.stmts.accept(this);
        level--;
    }

    public void visit(WhileStatementNode n) {
        match(Tag.WHILE);
        match('(');

        if (lookahead.tag == Tag.ID || lookahead.tag == Tag.INT || lookahead.tag == Tag.FLOAT) {
            if (lookahead.tag == Tag.ID) {
                n.id1 = new IdentifierNode();
                level++;
                ((IdentifierNode)n.id1).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.INT) {
                n.id1 = new IntNode();
                level++;
                ((IntNode)n.id1).accept(this);
                level--;
            }
            else {
                n.id1 = new FloatNode();
                level++;
                ((FloatNode)n.id1).accept(this);
                level--;
            }

            n.bool = ((Word)lookahead);
            match(lookahead.tag);
            
            if (lookahead.tag == Tag.ID) {
                n.id2 = new IdentifierNode();
                level++;
                ((IdentifierNode)n.id2).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.INT) {
                n.id2 = new IntNode();
                level++;
                ((IntNode)n.id2).accept(this);
                level--;
            }
            else {
                n.id2 = new FloatNode();
                level++;
                ((FloatNode)n.id2).accept(this);
                level--;
            }
        } else if (lookahead.tag == Tag.TRUE || lookahead.tag == Tag.FALSE) {
            n.bool = ((Word)lookahead);
            match(lookahead.tag);
        } else {
            System.out.println("Error: Expected bool value in If statment but got ->" + lookahead.toString());
        }
    
        match(')');
        
        if (lookahead.tag == ';') {
            match(';');
            return;
        }

        n.stmts = new Statements();
        level++;
        n.stmts.accept(this);
        level--;
    }

    public void visit(DoWhileStatementNode n) {
        match(Tag.DO);

        n.stmts = new Statements();
        level++;
        n.stmts.accept(this);
        level--;
    }

    public void visit(BreakStatementNode n) {
        printIndentation();
        match(Tag.BREAK);
        match(';');
    }

    // int i ; || int[2] j ;
    public void visit(TypeNode n) {
        printIndentation();
        println("TypeNode");
        println("*********** lookahead: " + lookahead);


        String temp = lookahead.toString();            
        if (temp.equals("int")) {
            n.basic = Type.Int;
        }
        else if (temp.equals("float")) {
            n.basic = Type.Float;
        }

        match(Tag.BASIC);

        // If lookahead is '[', this type should be array type.
        if (lookahead.toString().equals("[")) {
            n.array = new ArrayTypeNode();
            level++;
            n.array.accept(this);
            level--;
        }
    }

    public void visit(ArrayTypeNode n) {
        printIndentation();
        println("ArrayTypeNode");

        match('[');

        // Size could be ID or integer...
        if (lookahead.tag == Tag.INT) {
            n.size = ((Int)lookahead).value;
            printIndentation();
            println("Array Dimension: " + ((Int)lookahead).value);
            match(Tag.INT);
        }
        else if (lookahead.tag == Tag.ID) {
            n.id = new IdentifierNode();
            level++;
            n.id.accept(this);
            level--;
            printIndentation();
            println("Array Dimension: " + n.id.id);
        }

        // num between '[' and ']' is a IntNode
        // Do I have to visit IntNode?

        // For int[2],
        // ArrayTypeNode(2, null) vs. ArrayTypeNode(IntNode(), null)

        match(']');

        if (lookahead.toString().equals("[")) {
            n.type = new ArrayTypeNode();
            level++;
            n.type.accept(this);
            level--;
        }
    }

    public void visit(IntNode n) {
        n.value = ((Int)lookahead).value;

        match(Tag.INT);
        
        // printIndentation();
        // n.printNode();
    }

    public void visit(FloatNode n) {
        n.value = ((Float)lookahead).value;

        match(Tag.FLOAT);

        // printIndentation();
        // n.printNode();
    }

    public void visit(IdentifierNode n) {
        n.id = lookahead.toString();

        match(Tag.ID);

        if (lookahead.tag == '[') {
            n.array = new ArrayTypeNode();
            level++;
            n.array.accept(this);
            level--;
        }
        
        // printIndentation();
        // n.printNode();
    }

    /////////////////////
    // Utility Methods //
    /////////////////////
    void move() {
        try {
            lookahead = lexer.scan();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void error(String s) {
        throw new Error("near line " + lexer.line + ": " + s);
    }

    void match(int t) {
        try {
            if (lookahead.tag == t) {
                move();
            } else {
                throw new Error(MessageFormat.format("Syntax Error: t: \"{0}\", lookahead: \"{1}\"", (char) t, (char) lookahead.tag));
            }
        } catch (Error ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    void print(String s) {
        System.out.print(s);
    }

    void println(String s) {
        System.out.println(s);
    }

    void printIndentation() {
        // Print indentation
        for (int i = 0; i < level; i++) System.out.print(indent);
    }

    /**
     * Operators are listed top to bottom in ascending precedence.
     * 14 -> Highest Precedence, 01 -> Lowest Precedence.
     * 
     * 01. assignment =, +=, -=, *=, /=, %=, &=, ^=, \=, <<=, >>=, >>>=
     * 02. tenary ? :
     * 03. logical OR ||
     * 04. logical AND &&
     * 05. bitwise inclusive OR |
     * 06. bitwise exclusive OR ^
     * 07. bitwise AND &
     * 08. equality ==, !=
     * 09. relational <, >, <=, >=
     * 10. shift <<, >>, >>>
     * 11. additive +, -
     * 12. multiplicative *, /, %
     * 13. unary ++expr, --expr, +expr, -expr
     * 14. postfix expr++, expr--        
     */
    int getPrecedence(int op) {
        switch (op) {
            // case '+' + '+':
            // case '-' + '-': return 14;
            
            case '*': 
            case '/':
            case '%': return 12; // Multiplicative

            case '+': 
            case '-': return 11; // Additive
            
            case '<':
            case '>':
            case '<' + '=':
            case '>' + '=': return 9; // Relational
            
            case '=' + '=': 
            case '!' + '=': return 8; // Equality

            case '&' + '&': return 4; // Logical AND
            case '|' + '|': return 3; // Logical OR
            

            default:
                return -1; // ';'
        }
    }

    // Eventually lhs should be a ExpressionNode
    // But, for a while, it can be a Node.

    // Already moved to the next token, which should
    // be binary operator, in LiteralNode || IdentifierNode
    // It is stored in lookahead (look now)
    Node parseBinaryExprNode(Node lhs, int precedence) {
        // If the current op's precedence is higher than that of 
        // the previous, then keep traversing down to create a new
        // BinaryExprNode for binary expressions with higher precedence
        // Otherwise, create a new BinaryExprNode for current lhs and rhs.
        while (getPrecedence(lookahead.tag) >= precedence) {
            Token token_op = lookahead;
            int op = getPrecedence(lookahead.tag);
            
            move();
            
            // printIndentation();
            
            Node rhs = null;
            
            // Check if lookahead is LiteralNode || IdentifierNode
            if (lookahead.tag == Tag.ID) {
                rhs = new IdentifierNode();
                level++;
                ((IdentifierNode)rhs).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.INT) {
                rhs = new IntNode();
                level++;
                ((IntNode)rhs).accept(this);
                level--;
            }
            else if (lookahead.tag == Tag.FLOAT) {
                rhs = new FloatNode();
                level++;
                ((FloatNode)rhs).accept(this);
                level--;
            }
            
            // printIndentation();
            // println("operator " + lookahead);
            
            // Whenever the next op's precedence is higher than that
            // of the current operator, keep recursively calling itself.
            while (getPrecedence(lookahead.tag) > op) {
                rhs = parseBinaryExprNode(rhs, getPrecedence(lookahead.tag));
            }
            
            lhs = new BinaryExprNode(token_op, lhs, rhs);
        }
        return lhs;
    }
}
