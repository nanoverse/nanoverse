// Generated from C:/Users/dbborens/IdeaProjects/nanoverse/src\Nanosyntax.g4 by ANTLR 4.5
package compiler.pipeline.interpret.nanosyntax;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NanosyntaxLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ID=5, STRING=6, FLOAT=7, INTEGER=8, WS=9, 
		COMMENT=10, LINE_COMMENT=11;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "ID", "STRING", "FLOAT", "INTEGER", "INT", 
		"EXP", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "ID", "STRING", "FLOAT", "INTEGER", "WS", 
		"COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	@NotNull
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public NanosyntaxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Nanosyntax.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\r\177\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3"+
		"\6\7\6(\n\6\f\6\16\6+\13\6\3\7\3\7\7\7/\n\7\f\7\16\7\62\13\7\3\7\3\7\3"+
		"\b\5\b\67\n\b\3\b\3\b\3\b\6\b<\n\b\r\b\16\b=\3\b\5\bA\n\b\3\b\5\bD\n\b"+
		"\3\b\3\b\3\b\5\bI\n\b\3\t\5\tL\n\t\3\t\3\t\3\n\3\n\3\n\7\nS\n\n\f\n\16"+
		"\nV\13\n\5\nX\n\n\3\13\3\13\5\13\\\n\13\3\13\3\13\3\f\6\fa\n\f\r\f\16"+
		"\fb\3\f\3\f\3\r\3\r\3\r\3\r\7\rk\n\r\f\r\16\rn\13\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\7\16y\n\16\f\16\16\16|\13\16\3\16\3\16\3l\2\17"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\2\25\2\27\13\31\f\33\r\3\2\13\4"+
		"\2C\\c|\5\2\62;C\\c|\4\2$$^^\3\2\62;\3\2\63;\4\2GGgg\4\2--//\5\2\13\f"+
		"\16\17\"\"\4\2\f\f\17\17\u008a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\27\3\2\2"+
		"\2\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t#\3"+
		"\2\2\2\13%\3\2\2\2\r,\3\2\2\2\17H\3\2\2\2\21K\3\2\2\2\23W\3\2\2\2\25Y"+
		"\3\2\2\2\27`\3\2\2\2\31f\3\2\2\2\33t\3\2\2\2\35\36\7=\2\2\36\4\3\2\2\2"+
		"\37 \7<\2\2 \6\3\2\2\2!\"\7}\2\2\"\b\3\2\2\2#$\7\177\2\2$\n\3\2\2\2%)"+
		"\t\2\2\2&(\t\3\2\2\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\f\3\2\2"+
		"\2+)\3\2\2\2,\60\7$\2\2-/\n\4\2\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60"+
		"\61\3\2\2\2\61\63\3\2\2\2\62\60\3\2\2\2\63\64\7$\2\2\64\16\3\2\2\2\65"+
		"\67\7/\2\2\66\65\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\5\23\n\29;\7\60\2"+
		"\2:<\t\5\2\2;:\3\2\2\2<=\3\2\2\2=;\3\2\2\2=>\3\2\2\2>@\3\2\2\2?A\5\25"+
		"\13\2@?\3\2\2\2@A\3\2\2\2AI\3\2\2\2BD\7/\2\2CB\3\2\2\2CD\3\2\2\2DE\3\2"+
		"\2\2EF\5\23\n\2FG\5\25\13\2GI\3\2\2\2H\66\3\2\2\2HC\3\2\2\2I\20\3\2\2"+
		"\2JL\7/\2\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MN\5\23\n\2N\22\3\2\2\2OX\7\62"+
		"\2\2PT\t\6\2\2QS\t\5\2\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UX\3\2"+
		"\2\2VT\3\2\2\2WO\3\2\2\2WP\3\2\2\2X\24\3\2\2\2Y[\t\7\2\2Z\\\t\b\2\2[Z"+
		"\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]^\5\23\n\2^\26\3\2\2\2_a\t\t\2\2`_\3\2"+
		"\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\b\f\2\2e\30\3\2\2\2fg\7"+
		"\61\2\2gh\7,\2\2hl\3\2\2\2ik\13\2\2\2ji\3\2\2\2kn\3\2\2\2lm\3\2\2\2lj"+
		"\3\2\2\2mo\3\2\2\2nl\3\2\2\2op\7,\2\2pq\7\61\2\2qr\3\2\2\2rs\b\r\2\2s"+
		"\32\3\2\2\2tu\7\61\2\2uv\7\61\2\2vz\3\2\2\2wy\n\n\2\2xw\3\2\2\2y|\3\2"+
		"\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\b\16\2\2~\34\3\2\2\2\21"+
		"\2)\60\66=@CHKTW[blz\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}