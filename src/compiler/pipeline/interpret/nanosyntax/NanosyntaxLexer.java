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
		T__0=1, T__1=2, T__2=3, T__3=4, ID=5, STRING=6, FLOAT=7, INTEGER=8, BOOLEAN=9, 
		WS=10, COMMENT=11, LINE_COMMENT=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "ID", "STRING", "FLOAT", "INTEGER", "INT", 
		"EXP", "BOOLEAN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "ID", "STRING", "FLOAT", "INTEGER", "BOOLEAN", 
		"WS", "COMMENT", "LINE_COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u008c\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\7\6*\n\6\f\6\16\6-\13\6\3\7\3\7\7\7\61\n\7\f\7\16\7\64"+
		"\13\7\3\7\3\7\3\b\5\b9\n\b\3\b\3\b\3\b\6\b>\n\b\r\b\16\b?\3\b\5\bC\n\b"+
		"\3\b\5\bF\n\b\3\b\3\b\3\b\5\bK\n\b\3\t\5\tN\n\t\3\t\3\t\3\n\3\n\3\n\7"+
		"\nU\n\n\f\n\16\nX\13\n\5\nZ\n\n\3\13\3\13\5\13^\n\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fk\n\f\3\r\6\rn\n\r\r\r\16\ro\3\r\3\r\3"+
		"\16\3\16\3\16\3\16\7\16x\n\16\f\16\16\16{\13\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\7\17\u0086\n\17\f\17\16\17\u0089\13\17\3\17\3"+
		"\17\3y\2\20\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\2\25\2\27\13\31\f\33"+
		"\r\35\16\3\2\13\4\2C\\c|\5\2\62;C\\c|\4\2$$^^\3\2\62;\3\2\63;\4\2GGgg"+
		"\4\2--//\5\2\13\f\16\17\"\"\4\2\f\f\17\17\u0098\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2"+
		"\2\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\'\3\2\2\2\r.\3\2\2\2\17J\3\2"+
		"\2\2\21M\3\2\2\2\23Y\3\2\2\2\25[\3\2\2\2\27j\3\2\2\2\31m\3\2\2\2\33s\3"+
		"\2\2\2\35\u0081\3\2\2\2\37 \7=\2\2 \4\3\2\2\2!\"\7<\2\2\"\6\3\2\2\2#$"+
		"\7}\2\2$\b\3\2\2\2%&\7\177\2\2&\n\3\2\2\2\'+\t\2\2\2(*\t\3\2\2)(\3\2\2"+
		"\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\f\3\2\2\2-+\3\2\2\2.\62\7$\2\2/\61\n"+
		"\4\2\2\60/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2"+
		"\2\2\64\62\3\2\2\2\65\66\7$\2\2\66\16\3\2\2\2\679\7/\2\28\67\3\2\2\28"+
		"9\3\2\2\29:\3\2\2\2:;\5\23\n\2;=\7\60\2\2<>\t\5\2\2=<\3\2\2\2>?\3\2\2"+
		"\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2AC\5\25\13\2BA\3\2\2\2BC\3\2\2\2CK\3\2"+
		"\2\2DF\7/\2\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\5\23\n\2HI\5\25\13\2IK\3"+
		"\2\2\2J8\3\2\2\2JE\3\2\2\2K\20\3\2\2\2LN\7/\2\2ML\3\2\2\2MN\3\2\2\2NO"+
		"\3\2\2\2OP\5\23\n\2P\22\3\2\2\2QZ\7\62\2\2RV\t\6\2\2SU\t\5\2\2TS\3\2\2"+
		"\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2YQ\3\2\2\2YR\3\2\2"+
		"\2Z\24\3\2\2\2[]\t\7\2\2\\^\t\b\2\2]\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\5"+
		"\23\n\2`\26\3\2\2\2ab\7V\2\2bc\7t\2\2cd\7w\2\2dk\7g\2\2ef\7H\2\2fg\7c"+
		"\2\2gh\7n\2\2hi\7u\2\2ik\7g\2\2ja\3\2\2\2je\3\2\2\2k\30\3\2\2\2ln\t\t"+
		"\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\b\r\2\2r\32\3"+
		"\2\2\2st\7\61\2\2tu\7,\2\2uy\3\2\2\2vx\13\2\2\2wv\3\2\2\2x{\3\2\2\2yz"+
		"\3\2\2\2yw\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7,\2\2}~\7\61\2\2~\177\3\2\2"+
		"\2\177\u0080\b\16\2\2\u0080\34\3\2\2\2\u0081\u0082\7\61\2\2\u0082\u0083"+
		"\7\61\2\2\u0083\u0087\3\2\2\2\u0084\u0086\n\n\2\2\u0085\u0084\3\2\2\2"+
		"\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a"+
		"\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\b\17\2\2\u008b\36\3\2\2\2\22"+
		"\2+\628?BEJMVY]joy\u0087\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}