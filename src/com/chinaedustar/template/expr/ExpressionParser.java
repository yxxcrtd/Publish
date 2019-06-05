package com.chinaedustar.template.expr;

import java.util.Stack;

import com.chinaedustar.template.token.Token;
import com.chinaedustar.template.token.TokenParser;

/**
 * 表达式解析器。
 * 
 * @author liujunxing
 *
 */
public class ExpressionParser implements ExpressionConstant {
	/** 所使用的符号解析器。 */
	private final TokenParser token_parser;
	
	/** 当前正在分析的符号。 */
	private Token curr_token;
	
	/** 表达式根节点。 */
	private AbstractExpressionNode expr_root_node;
	
	/**
	 * 使用指定的字符串构造一个表达式解析器。
	 * @param expr
	 */
	public ExpressionParser(String expr) {
		this(new TokenParser(expr, 0, expr.length()));
	}
	
	/**
	 * 使用指定的字符串构造一个表达式解析器。
	 * @param expr
	 */
	public ExpressionParser(String expr, int start, int end) {
		this(new TokenParser(expr, start, end));
	}
	
	/**
	 * 使用指定的符号解析器构造一个 ExpressionParser 的实例。
	 * @param token_parser
	 */
	public ExpressionParser(TokenParser token_parser) {
		this.token_parser = token_parser;
	}
	
	/**
	 * 解析一个表达式。
	 * @return - 返回解析出来的表达式。
	 */
	public Expression parse() {
		advanceToken();
		
		this.expr_root_node = parseExpression();
		
		return new Expression(expr_root_node);
	}
	
	/**
	 * 解析一个参数列表，参数列表定义为 params = null | expr [, params] 
	 *
	 */
	public ParameterList parseParameters() {
		this.advanceToken();
		return parseParameterList();
	}
	
	/**
	 * 获得当前扫描到的最后位置，该位置表达式结束，或遇到不可识别的符号。
	 * @return
	 */
	public int getCurrScanPos() {
		if (curr_token.isEOT()) 
			return this.token_parser.getCurrScanPos();
		return curr_token.getStartPos();
	}
	
	/**
	 * 获得当前符号代表的操作符，如果当前符号不是一个操作符，则返回 null。
	 * @return
	 */
	private Operator getOperator() {
		if (curr_token.isSymbolToken() == false) return null;
		return Operator.fromSymbol(curr_token.getSymbol());
	}
	
	/**
	 * 内部解析表达式。
	 * @return
	 */
	private AbstractExpressionNode parseExpression() {
		try {
			// 解析第一个单项。
			AbstractExpressionNode term = parseUnionTerm();
	
			// 解析下一个可能的操作符，如果没有则直接返回，此时不需要建立复杂的堆栈结构。
			Operator oper = getOperator();
			if (oper == null) return term;
			advanceToken();					// accept - 操作子。
	
			// 构造堆栈结构: 值栈和操作子栈。
			// 堆栈中将保留所有操值和操作子，如果遇到一个新的操作子，则会根据操作子优先级
			//   更新堆栈。
			Stack<AbstractExpressionNode> term_stack = new Stack<AbstractExpressionNode>();
			Stack<Operator> oper_stack = new Stack<Operator>();
			term_stack.push(term);
			oper_stack.push(oper);
			
			// 读取后续的值和操作子。
			while (true) {
				// 读入一个值，并压入值栈。
				AbstractExpressionNode next_term = parseUnionTerm();
				term_stack.push(next_term);
				
				// 读入一个操作子，如果没有操作子了，则表达式结束。
				Operator next_oper = getOperator();
				if (next_oper == null) break; 
				advanceToken();					// accept - 操作子。
				
				// 根据当前操作子更新堆栈。
				pushOperator(term_stack, oper_stack, next_oper);
			}
			
			return popAllTerm(term_stack, oper_stack);
		} catch (ExpressionException ex) {
			throw new ExpressionException(ex.getMessage(), ex.getCause());
		}
	}

	/**
	 * 压入一个操作子，如果要压入的操作子等级比以前的低，则调整堆栈。
	 * @param term_stack
	 * @param oper_stack
	 * @param oper
	 */
	private void pushOperator(Stack<AbstractExpressionNode> term_stack,
			Stack<Operator> oper_stack,
			Operator new_oper) {
		while (oper_stack.isEmpty() == false) {
			Operator prev_oper = oper_stack.peek();
			// 如果前一个操作子等级 >= 当前操作子等级，则产生操作子节点。
			if (prev_oper.getLevel() >= new_oper.getLevel()) {
				oper_stack.pop();		// accept
				AbstractExpressionNode right_term = term_stack.pop();	// accept
				AbstractExpressionNode left_term = term_stack.pop();	// accept
				term_stack.push(new OperatorNode(prev_oper, left_term, right_term));
			} else
				break;
		}
		oper_stack.push(new_oper);
	}

	/**
	 * 弹出当前所有的操作值和操作子。
	 * @return
	 */
	private AbstractExpressionNode popAllTerm(Stack<AbstractExpressionNode> term_stack,
			Stack<Operator> oper_stack) {
		assert (term_stack.isEmpty() == false);
		AbstractExpressionNode result = term_stack.pop();
		
		while (oper_stack.isEmpty() == false) {
			Operator oper = oper_stack.pop();
			AbstractExpressionNode left_node = term_stack.pop();
			result = new OperatorNode(oper, left_node, result);
		}
		assert (term_stack.isEmpty());		// 必须都弹空了。
		return result;
	}
	
	/**
	 * 解析一元操作符的项目。
	 * @return
	 */
	private AbstractExpressionNode parseUnionTerm() { 
		if (isUnionOper(curr_token) == false)
			return parseTerm();
		
		// 处理 !, -, + 的一元操作符。
		Stack<Operator> oper_stack = new Stack<Operator>();
		while (true) {
			Operator unio_oper = getUnionOperator();
			if (unio_oper == null) break;
			advanceToken();		// accept the union-operator
			oper_stack.push(unio_oper);
			if (isUnionOper(curr_token) == false) break;
		}
		
		// 将一元操作符作用到解析出来的项目上面。
		AbstractExpressionNode term = parseTerm();
		while (oper_stack.isEmpty() == false) {
			Operator unio_oper = oper_stack.pop();
			term = new UnionOperatorNode(unio_oper, term);
		}
		return term;
	}
	
	/**
	 * 解析一个独立项目，独立项目指常量、变量、( 表达式 )、函数调用 xxx(vvv)、
	 *   属性方法访问 xxx.yyy, 内建函数访问 xxx@yyy, 索引访问 xxx[index] 等，以及其组合。
	 * 这些操作符都是右结合的。
	 * @return
	 */
	private AbstractExpressionNode parseTerm() {
		AbstractExpressionNode result = null;
		if (curr_token.isConstant()) {
			// const
			result = new ConstantNode(curr_token.getConstant());
			advanceToken();				// accept const
		} else if (curr_token.isIdentifier()) {
			// 特殊的量 true, false 做为常量处理。
			if ("true".equals(curr_token.getIdentifier())) {
				result = new ConstantNode(Boolean.TRUE);
				advanceToken();				// accept const
			} else if ("false".equals(curr_token.getIdentifier())) {
				result = new ConstantNode(Boolean.FALSE);
				advanceToken();				// accept const
			} else if ("null".equals(curr_token.getIdentifier())) {
				result = new ConstantNode(null);
				advanceToken();				// accept const
			} else {
				// iden
				Token iden_token = curr_token;
				advanceToken();				// accept iden
				
				if (curr_token.isSymbolToken("(")) {
					// 全局函数调用 - iden(params)
					advanceToken();			// accept '('
					ParameterList param_list = parseParameterList();	// 解析参数。
					expectSymbolToken(")"); // and accept ')'
					result = new GlobalMethodNode(iden_token.getIdentifier(), param_list);
				} else {
					// variable
					result = new VariableNode(iden_token.getIdentifier());
				}
			}
		} else if (curr_token.isSymbolToken("(")) {
			// ( expr )
			advanceToken();				// accept '('
			result = parseExpression();
			expectSymbolToken(")");		// and accept ')'
		} else
			throw new ExpressionException("缺少常量、变量或表达式");

		// 后面可能跟多个索引或属性访问操作符 '[]', '.', 如 user.name[index].to_string().format("xxx")
		// 常量、变量、表达式后面都可以跟的。如 (1 + user.age).to_lower()[3]
		while (true) {
			// index '[' or prop/method '.'
			if (curr_token.isSymbolToken("[")) {
				// index operator - iden[index]
				advanceToken();			// accept '['
				// 解析索引，索引有且只有一个参数。
				AbstractExpressionNode index_expr = this.parseExpression();
				expectSymbolToken("]");	// and accept ']'
				result = new IndexOperatorNode(result, index_expr);
			} else if (curr_token.isSymbolToken(".")) {
				// property, method operator - item.xxx, item.yyy()
				advanceToken();			// accept '.'
				if (curr_token.isIdentifier() == false) {
					throw new ExpressionException("期待一个标识符。");
				}
				Token prop_or_func = curr_token;
				advanceToken();			// accept token
				
				if (curr_token.isSymbolToken("(")) {
					// .method() - item.func(params)
					advanceToken();			// accept '('
					ParameterList param_list = parseParameterList();	// 解析参数。
					expectSymbolToken(")");	// and accept ')'
					// 产生一个方法访问节点。 
					result = new MethodAccessNode(result, prop_or_func.getIdentifier(), param_list);
				} else {
					// .property 产生一个属性访问节点。
					result = new PropertyAccessNode(result, prop_or_func.getIdentifier());
				}
			} else if (curr_token.isSymbolToken("@")) {
				// builtin operator - item@index, item@is_first() etc.
				advanceToken();			// accept '@'
				if (curr_token.isIdentifier() == false)
					throw new ExpressionException("期待一个标识符。");
				Token prop_or_func = curr_token;
				advanceToken();			// accept token
				
				if (curr_token.isSymbolToken("(")) {
					// .method() - item.func(params)
					advanceToken();			// accept '('
					ParameterList param_list = parseParameterList();	// 解析参数。
					expectSymbolToken(")");	// and accept ')'
					// 产生一个 builtin 访问节点。 
					result = new BuiltinAccessNode(result, prop_or_func.getIdentifier(), param_list);
				} else {
					// @index 产生一个 builtin 访问节点。
					result = new BuiltinAccessNode(result, prop_or_func.getIdentifier());
				}
			} else
				break;
		}
		
		return result;
	}
	
	/**
	 * 解析一个参数列表。
	 * @return
	 */
	private ParameterList parseParameterList() {
		// 及早判断没有参数的情况。
		if (curr_token.isSymbolToken(")"))
			return ParameterList.EMPTY_PARAMETER_LIST;
		
		ParameterList param_list = new ParameterList();
		while (true) {
			AbstractExpressionNode param_expr = this.parseExpression();
			param_list.add(new Parameter(param_expr));
			if (curr_token.isSymbolToken(",") == false) break;
			advanceToken();			// accept ',', 参数分隔符。
		}
		return param_list;
	}
	
	/**
	 * 判断是否是一个一元操作符。
	 * @param token
	 * @return
	 */
	private static boolean isUnionOper(Token token) {
		if (token.getTokenType() != Token.TOKEN_TYPE_SYMBOL) return false;
		String symbol = token.getSymbol();
		// +, -, ! 都是一元操作符。如 -var, !bool, +7
		if ("-".equals(symbol) || "!".equals(symbol) || "+".equals(symbol))
			return true;
		return false;
	}
	
	/**
	 * 获得当前一元操作符。
	 * @return
	 */
	private Operator getUnionOperator() {
		String symbol = curr_token.getSymbol();
		return Operator.fromSymbolUnion(symbol);
	}
	
	/**
	 * 期待当前符号是指定的符号，并且当是此符号的时候，向前推进一个符号。
	 * @param symbol
	 */
	private Token expectSymbolToken(String symbol) {
		if (curr_token.isSymbolToken(symbol) == false) {
			throw new ExpressionException("expr parse error");
		}
		return advanceToken();
	}
	
	/**
	 * 向前移动一个符号。
	 * @return
	 */
	private Token advanceToken() {
		this.curr_token = token_parser.getNextToken();
		return this.curr_token;
	}
	
	/**
	 * 调试输出表达式树。
	 *
	 */
	@SuppressWarnings("unused")
	private void dumpExpression() {
		if (expr_root_node != null)
			expr_root_node.dump("");
	}
}
