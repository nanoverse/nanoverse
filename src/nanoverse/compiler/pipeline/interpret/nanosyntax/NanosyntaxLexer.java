// Generated from D:/Users/dbborens/IdeaProjects/nanoverse/src\Nanosyntax.g4 by ANTLR 4.5.1
package nanoverse.compiler.pipeline.interpret.nanosyntax;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NanosyntaxLexer extends Lexer {
    public static final int
        T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, BOOLEAN = 5, STRING = 6, FLOAT = 7, INTEGER = 8,
        ID = 9, WS = 10, COMMENT = 11, LINE_COMMENT = 12;
    public static final String[] ruleNames = {
        "T__0", "T__1", "T__2", "T__3", "BOOLEAN", "STRING", "FLOAT", "INTEGER",
        "INT", "EXP", "ID", "WS", "COMMENT", "LINE_COMMENT"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
        "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u008c\b\1\4\2" +
            "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
            "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3" +
            "\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\61\n\6\3\7\3\7\7\7\65\n" +
            "\7\f\7\16\78\13\7\3\7\3\7\3\b\5\b=\n\b\3\b\3\b\3\b\6\bB\n\b\r\b\16\bC" +
            "\3\b\5\bG\n\b\3\b\5\bJ\n\b\3\b\3\b\3\b\5\bO\n\b\3\t\5\tR\n\t\3\t\3\t\3" +
            "\n\3\n\3\n\7\nY\n\n\f\n\16\n\\\13\n\5\n^\n\n\3\13\3\13\5\13b\n\13\3\13" +
            "\3\13\3\f\3\f\7\fh\n\f\f\f\16\fk\13\f\3\r\6\rn\n\r\r\r\16\ro\3\r\3\r\3" +
            "\16\3\16\3\16\3\16\7\16x\n\16\f\16\16\16{\13\16\3\16\3\16\3\16\3\16\3" +
            "\16\3\17\3\17\3\17\3\17\7\17\u0086\n\17\f\17\16\17\u0089\13\17\3\17\3" +
            "\17\3y\2\20\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\2\25\2\27\13\31\f\33" +
            "\r\35\16\3\2\13\4\2$$^^\3\2\62;\3\2\63;\4\2GGgg\4\2--//\4\2C\\c|\5\2\62" +
            ";C\\c|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\u0098\2\3\3\2\2\2\2\5\3\2\2\2" +
            "\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3" +
            "\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2" +
            "\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\60\3\2\2\2\r\62\3\2\2\2\17N\3\2" +
            "\2\2\21Q\3\2\2\2\23]\3\2\2\2\25_\3\2\2\2\27e\3\2\2\2\31m\3\2\2\2\33s\3" +
            "\2\2\2\35\u0081\3\2\2\2\37 \7=\2\2 \4\3\2\2\2!\"\7<\2\2\"\6\3\2\2\2#$" +
            "\7}\2\2$\b\3\2\2\2%&\7\177\2\2&\n\3\2\2\2\'(\7V\2\2()\7t\2\2)*\7w\2\2" +
            "*\61\7g\2\2+,\7H\2\2,-\7c\2\2-.\7n\2\2./\7u\2\2/\61\7g\2\2\60\'\3\2\2" +
            "\2\60+\3\2\2\2\61\f\3\2\2\2\62\66\7$\2\2\63\65\n\2\2\2\64\63\3\2\2\2\65" +
            "8\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\679\3\2\2\28\66\3\2\2\29:\7$\2\2" +
            ":\16\3\2\2\2;=\7/\2\2<;\3\2\2\2<=\3\2\2\2=>\3\2\2\2>?\5\23\n\2?A\7\60" +
            "\2\2@B\t\3\2\2A@\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2EG\5\25" +
            "\13\2FE\3\2\2\2FG\3\2\2\2GO\3\2\2\2HJ\7/\2\2IH\3\2\2\2IJ\3\2\2\2JK\3\2" +
            "\2\2KL\5\23\n\2LM\5\25\13\2MO\3\2\2\2N<\3\2\2\2NI\3\2\2\2O\20\3\2\2\2" +
            "PR\7/\2\2QP\3\2\2\2QR\3\2\2\2RS\3\2\2\2ST\5\23\n\2T\22\3\2\2\2U^\7\62" +
            "\2\2VZ\t\4\2\2WY\t\3\2\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[^\3" +
            "\2\2\2\\Z\3\2\2\2]U\3\2\2\2]V\3\2\2\2^\24\3\2\2\2_a\t\5\2\2`b\t\6\2\2" +
            "a`\3\2\2\2ab\3\2\2\2bc\3\2\2\2cd\5\23\n\2d\26\3\2\2\2ei\t\7\2\2fh\t\b" +
            "\2\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\30\3\2\2\2ki\3\2\2\2ln\t" +
            "\t\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\b\r\2\2r\32" +
            "\3\2\2\2st\7\61\2\2tu\7,\2\2uy\3\2\2\2vx\13\2\2\2wv\3\2\2\2x{\3\2\2\2" +
            "yz\3\2\2\2yw\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7,\2\2}~\7\61\2\2~\177\3\2" +
            "\2\2\177\u0080\b\16\3\2\u0080\34\3\2\2\2\u0081\u0082\7\61\2\2\u0082\u0083" +
            "\7\61\2\2\u0083\u0087\3\2\2\2\u0084\u0086\n\n\2\2\u0085\u0084\3\2\2\2" +
            "\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a" +
            "\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\b\17\3\2\u008b\36\3\2\2\2\22" +
            "\2\60\66<CFINQZ]aioy\u0087\4\2\3\2\b\2\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
        null, "';'", "':'", "'{'", "'}'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, null, null, null, null, "BOOLEAN", "STRING", "FLOAT", "INTEGER",
        "ID", "WS", "COMMENT", "LINE_COMMENT"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public NanosyntaxLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String getGrammarFileName() {
        return "Nanosyntax.g4";
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }
}