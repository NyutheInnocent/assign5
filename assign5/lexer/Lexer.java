package assign5.lexer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author Chase Dodge
 */
public class Lexer {
    public int line = 1;
    private char _peek = ' ';

    private BufferedInputStream bin;

    private Hashtable<String, Word> words = new Hashtable<>();

    public Lexer() {
        reserve(Word.If);
        reserve(Word.True);
        reserve(Word.False);
        reserve(Word.Do);
        reserve(Word.While);
        reserve(Word.Break);
        reserve(Word.Else);

        reserve(Type.Int);
        reserve(Type.Float);
        reserve(Type.Char);
        reserve(Type.Bool);

        setupIOStream();
    }
    void reserve(Word w) {
        words.put(w.lexeme, w); 
    }

    public Token scan() throws IOException {
        // Removes space tab and newline
        for (; ; readChar()) {
            if (_peek == ' ' || _peek == '\t') {
                continue;
            }
            else if (_peek == '\n') {
                line += 1;
            }
            else {
                break;
            }
        }

        switch (_peek) {
            case '&': {
                if (readChar('&')) {
                    return Word.And;
                } else {
                    return new Token('&');
                }
            }
            case '|': {
                if (readChar('|')) {
                    return Word.Or;
                } else {
                    return new Token('|');
                }
            }
            case '=': {
                if (readChar('=')) {
                    return Word.Eq;
                } else {
                    return new Token('=');
                }
            }
            case '!': {
                if (readChar('=')) {
                    return Word.Ne;
                } else {
                    return new Token('!');
                }
            }
            case '<': {
                if (readChar('=')) {
                    return Word.Le;
                } else {
                    return new Token('<');
                }
            }
            case '>': {
                if (readChar('=')) {
                    return Word.Ge;
                } else {
                    return new Token('>');
                }
            }
        }

        if (Character.isDigit(_peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(_peek, 10);
                readChar();
            } while (Character.isDigit(_peek));

            if (_peek != '.') {
                return new Int(v);
            }

            float x = v; float d = 10;

            for (;;) {
                readChar();
                if (!Character.isDigit(_peek)) break;
                x = x + Character.digit(_peek, 10) / d; d = d * 10;
            }

            return new Float(x);
        }

        if (Character.isLetter(_peek)) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(_peek);
                readChar();
            } while (Character.isLetterOrDigit(_peek));

            String s = sb.toString();
            Word w = words.get(s);

            if (w != null) {
                return w;
            }
            w = new Word(s, Tag.ID);
            words.put(s,w);
            return w;
        }

        Token t = new Token(_peek);
        _peek = ' ';
        return t;
    }

    public void setupIOStream() {
        try {
            FileInputStream in = new FileInputStream("input.txt");
            bin = new BufferedInputStream(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void readChar() throws IOException {
        _peek = (char) bin.read();
    }
    boolean readChar(char c) throws IOException {
        readChar();
        if (_peek != c) return false;
        _peek = ' ';
        return true;
    }

    public Word getWord(String id) {
        try {
            if (!this.words.containsKey(id)) {
                throw new Error("Words does not contain a key of value: " + id);
            }
            return this.words.get(id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

