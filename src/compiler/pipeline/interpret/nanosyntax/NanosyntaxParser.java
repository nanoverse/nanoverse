// Generated from C:/Users/dbborens/IdeaProjects/nanoverse/src\Nanosyntax.g4 by ANTLR 4.5
package compiler.pipeline.interpret.nanosyntax;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NanosyntaxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ID=5, STRING=6, FLOAT=7, INTEGER=8, BOOLEAN=9, 
		WS=10, COMMENT=11, LINE_COMMENT=12;
	public static final int
		RULE_root = 0, RULE_statement = 1, RULE_assignment = 2, RULE_block = 3, 
		RULE_singleton = 4, RULE_id = 5, RULE_primitive = 6, RULE_stringPrimitive = 7, 
		RULE_floatPrimitive = 8, RULE_intPrimitive = 9, RULE_boolPrimitive = 10;
	public static final String[] ruleNames = {
		"root", "statement", "assignment", "block", "singleton", "id", "primitive", 
		"stringPrimitive", "floatPrimitive", "intPrimitive", "boolPrimitive"
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

	@Override
	public String getGrammarFileName() { return "Nanosyntax.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public NanosyntaxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << ID) | (1L << STRING) | (1L << FLOAT) | (1L << INTEGER) | (1L << BOOLEAN))) != 0)) {
				{
				{
				setState(22); 
				statement();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public PrimitiveContext primitive() {
			return getRuleContext(PrimitiveContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(40);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(28); 
				id();
				setState(29); 
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(31); 
				primitive();
				setState(32); 
				match(T__0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(34); 
				block();
				setState(35); 
				match(T__0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(37); 
				assignment();
				setState(38); 
				match(T__0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SingletonContext singleton() {
			return getRuleContext(SingletonContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignment);
		try {
			setState(49);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42); 
				id();
				setState(43); 
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45); 
				id();
				setState(46); 
				match(T__1);
				setState(47); 
				singleton();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); 
			match(T__2);
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << ID) | (1L << STRING) | (1L << FLOAT) | (1L << INTEGER) | (1L << BOOLEAN))) != 0)) {
				{
				{
				setState(52); 
				statement();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58); 
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingletonContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public PrimitiveContext primitive() {
			return getRuleContext(PrimitiveContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public SingletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleton; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitSingleton(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingletonContext singleton() throws RecognitionException {
		SingletonContext _localctx = new SingletonContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_singleton);
		try {
			setState(63);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(60); 
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61); 
				primitive();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(62); 
				assignment();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(NanosyntaxParser.ID, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); 
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveContext extends ParserRuleContext {
		public StringPrimitiveContext stringPrimitive() {
			return getRuleContext(StringPrimitiveContext.class,0);
		}
		public FloatPrimitiveContext floatPrimitive() {
			return getRuleContext(FloatPrimitiveContext.class,0);
		}
		public IntPrimitiveContext intPrimitive() {
			return getRuleContext(IntPrimitiveContext.class,0);
		}
		public BoolPrimitiveContext boolPrimitive() {
			return getRuleContext(BoolPrimitiveContext.class,0);
		}
		public PrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveContext primitive() throws RecognitionException {
		PrimitiveContext _localctx = new PrimitiveContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primitive);
		try {
			setState(71);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(67); 
				stringPrimitive();
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(68); 
				floatPrimitive();
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(69); 
				intPrimitive();
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 4);
				{
				setState(70); 
				boolPrimitive();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringPrimitiveContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(NanosyntaxParser.STRING, 0); }
		public StringPrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringPrimitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitStringPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringPrimitiveContext stringPrimitive() throws RecognitionException {
		StringPrimitiveContext _localctx = new StringPrimitiveContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stringPrimitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); 
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FloatPrimitiveContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(NanosyntaxParser.FLOAT, 0); }
		public FloatPrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatPrimitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitFloatPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatPrimitiveContext floatPrimitive() throws RecognitionException {
		FloatPrimitiveContext _localctx = new FloatPrimitiveContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_floatPrimitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); 
			match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntPrimitiveContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(NanosyntaxParser.INTEGER, 0); }
		public IntPrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intPrimitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitIntPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntPrimitiveContext intPrimitive() throws RecognitionException {
		IntPrimitiveContext _localctx = new IntPrimitiveContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_intPrimitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); 
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolPrimitiveContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(NanosyntaxParser.BOOLEAN, 0); }
		public BoolPrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolPrimitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NanosyntaxVisitor ) return ((NanosyntaxVisitor<? extends T>)visitor).visitBoolPrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolPrimitiveContext boolPrimitive() throws RecognitionException {
		BoolPrimitiveContext _localctx = new BoolPrimitiveContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_boolPrimitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); 
			match(BOOLEAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\16T\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\5\3+\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\64\n\4\3\5\3"+
		"\5\7\58\n\5\f\5\16\5;\13\5\3\5\3\5\3\6\3\6\3\6\5\6B\n\6\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\5\bJ\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\2\2\r\2\4\6"+
		"\b\n\f\16\20\22\24\26\2\2S\2\33\3\2\2\2\4*\3\2\2\2\6\63\3\2\2\2\b\65\3"+
		"\2\2\2\nA\3\2\2\2\fC\3\2\2\2\16I\3\2\2\2\20K\3\2\2\2\22M\3\2\2\2\24O\3"+
		"\2\2\2\26Q\3\2\2\2\30\32\5\4\3\2\31\30\3\2\2\2\32\35\3\2\2\2\33\31\3\2"+
		"\2\2\33\34\3\2\2\2\34\3\3\2\2\2\35\33\3\2\2\2\36\37\5\f\7\2\37 \7\3\2"+
		"\2 +\3\2\2\2!\"\5\16\b\2\"#\7\3\2\2#+\3\2\2\2$%\5\b\5\2%&\7\3\2\2&+\3"+
		"\2\2\2\'(\5\6\4\2()\7\3\2\2)+\3\2\2\2*\36\3\2\2\2*!\3\2\2\2*$\3\2\2\2"+
		"*\'\3\2\2\2+\5\3\2\2\2,-\5\f\7\2-.\5\b\5\2.\64\3\2\2\2/\60\5\f\7\2\60"+
		"\61\7\4\2\2\61\62\5\n\6\2\62\64\3\2\2\2\63,\3\2\2\2\63/\3\2\2\2\64\7\3"+
		"\2\2\2\659\7\5\2\2\668\5\4\3\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3"+
		"\2\2\2:<\3\2\2\2;9\3\2\2\2<=\7\6\2\2=\t\3\2\2\2>B\5\f\7\2?B\5\16\b\2@"+
		"B\5\6\4\2A>\3\2\2\2A?\3\2\2\2A@\3\2\2\2B\13\3\2\2\2CD\7\7\2\2D\r\3\2\2"+
		"\2EJ\5\20\t\2FJ\5\22\n\2GJ\5\24\13\2HJ\5\26\f\2IE\3\2\2\2IF\3\2\2\2IG"+
		"\3\2\2\2IH\3\2\2\2J\17\3\2\2\2KL\7\b\2\2L\21\3\2\2\2MN\7\t\2\2N\23\3\2"+
		"\2\2OP\7\n\2\2P\25\3\2\2\2QR\7\13\2\2R\27\3\2\2\2\b\33*\639AI";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}