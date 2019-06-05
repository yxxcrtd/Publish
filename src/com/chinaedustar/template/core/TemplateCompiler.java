package com.chinaedustar.template.core;

import com.chinaedustar.template.expr.Expression;
import com.chinaedustar.template.expr.ExpressionException;
import com.chinaedustar.template.expr.ExpressionParser;
import com.chinaedustar.template.expr.ParameterList;
import com.chinaedustar.template.token.Token;
import com.chinaedustar.template.token.TokenParser;

/**
 * 模板编译器。
 * 
 * <p>本来模板编译器是语义无关的，但是对于一些执行指令如 if, foreach 等，包含语义
 *   能够让用户写的语法更直观，所以还是做成语义有关的了。</p>
 * 
 * @author liujunxing
 */
public class TemplateCompiler {
	/** 工厂类。 */ 
	@SuppressWarnings("unused")
	private final TemplateFactoryImpl factory;
	
	/** 标签开始的标记，${} 被 JSP EL 语言使用了，{$ } 容易和它混淆，当前使用 #{ } 或 {# }。 */
	private final String LABEL_BEGIN =   "#{";
	private final String LABEL_BEGIN_2 = "{#"; 	// '{#' 也支持，用户万一写错顺序也行。
	
	/** 标签结束的标记。 */
	private final String LABEL_END = "}";
	
	/** 用于字符串包围的字符，当前支持 ', " 。 */
	@SuppressWarnings("unused")
	private final String STRING_QUOTE = "\'\"";
	
	/** 位置计算对象。 */
	private PositionProvider pos;
	
	/** 选项：跳过标记之后的空格(含回车、换行、制表) */
	private boolean skip_tag_blank = true;
	
	/** 根元素。 */
	private PlaceHolderElement root_elem;
	
	/** 当前插入元素位置，新节点都是插入到该元素的子节点，所以其必然是一个容器节点。 */
	private AbstractContainerElement cur_elem;

	/** 所使用的符号分析器。 */
	private TokenParser token_parser;
	
	/** 模板的正文 (final)。 */
	private String template_content;
	
	/** 模板的长度 (final)。 */
	private int template_length;
	
	/** 未处理的文本内容开始位置。 */
	private int text_begin_pos = 0;
	
	/** 当前正在分析的一个标签的开始位置。 */
	private int label_begin_pos = 0;
	
	/** 当前分析位置。 */
	private int scan_ptr = 0;
	
	/**
	 * 使用指定的模板工厂构造一个 TemplateCompiler 的实例。
	 * 
	 * @param factory
	 */
	public TemplateCompiler(TemplateFactoryImpl factory) {
		this.factory = factory;
	}
	
	/**
	 * 编译具有指定内容的模板。
	 * @return
	 */
	public AbstractTemplateElement compileTemplate(String template_content) {
		if (template_content == null) throw new IllegalArgumentException("tmpl is null");
		
		// 设置好编译初始变量。
		this.template_content = template_content;
		this.template_length = template_content.length();
		this.text_begin_pos = 0;
		this.scan_ptr = 0;
		this.token_parser = new TokenParser(template_content);
		this.pos = new PositionProvider(template_content);
		
		// 我们用一个占位元素做为开始，这样能够节省很多判定代码和构建代码。
		root_elem = new PlaceHolderElement();
		cur_elem = root_elem;

		// 开始编译。
		internalCompile();
		
		assert (root_elem.getNextElement() == null);	// 必须为空。
		assert (root_elem.getParent() == null);			// 必须为空。
		
		dumpElementTree("", root_elem);
		
		return root_elem;
	}
	
	/**
	 * 内部实现编译处理。
	 *
	 */
	private final void internalCompile() {
		// 查找 Label 符号。
		while (this.scan_ptr < this.template_length) {
			this.scan_ptr = findNextLabel();
			if (this.scan_ptr < 0) {
				// 没有元素了，剩下部分都做为文字来处理。
				break;
			} else {
				// 发现一个 Label 开始符号, '#{' 或 '{#'。
				analyzeLabel();
			}
		}
		
		// 如果有未分析完的部分，则都做为文本看待。
		if (this.text_begin_pos < this.template_length) {
			insertTextElement(template_content.substring(text_begin_pos));
		}
		
		// TODO: 验证当前插入点是 root_elem, 否则该插入点不正确。
	}
	
	/**
	 * 从当前 scan_ptr 位置开始分析一个标签。 scan_ptr 指向 '#{' 开始的位置。 
	 *
	 */
	private void analyzeLabel() {
		// 如果前一个符号是 '\' 则忽略此 '#{' 符号，继续找下一个。
		this.label_begin_pos = this.scan_ptr;		// label_begin_pos -> '#{'
		if (this.label_begin_pos >= 1 && template_content.charAt(this.label_begin_pos - 1) == '\\') {
			insertEscapedStart();
			return;
		}
		
		// 向前移动扫描指针，准备开始扫描 label。
		scan_ptr += LABEL_BEGIN.length();
		if (scan_ptr >= this.template_length) return;

		// 从 scan_ptr 解析第一个符号。
		Token token = token_parser.getFirstToken(scan_ptr, this.template_length);
		boolean result = false;
		if (token.isIdentifier()) {
			// 如果是一个标识符，则可能是 namespace 部分，也可能是标签名字，或者一个表达式的开始。
			result = analyzeFirstToken(token);
		} else if (token.isSymbolToken("/")) {
			// '/' 符号，则应该是一个结束标签。
			result = analyzeEndLabel();
		} else {
			// 不是标识符，则整个我们做为一个表达式来看待。
			result = analyzeExpression(token.getStartPos());
		}
		
		if (result) {
			this.text_begin_pos = this.scan_ptr;
			// 如果选项为跳过空格，则跳过标记之后的空格。
			if (skip_tag_blank)
				skipTagBlank();
		} else {
			// 尝试跳过不合法的标记。
			skipIlleageTag();
		}
	}
	
	/**
	 * 尝试跳过一个标记之后的空格、回车、换行、制表符。
	 *
	 */
	private void skipTagBlank() {
		while (this.scan_ptr < this.template_length) {
			char cur_ch = this.template_content.charAt(this.scan_ptr);
			if (cur_ch == ' ' || cur_ch == '\r' || cur_ch == '\n' || cur_ch == '\t') {
				++this.scan_ptr;
			} else {
				break;
			}
		}
		this.text_begin_pos = this.scan_ptr;
	}
	
	/**
	 * 尝试跳过不合法的标记。
	 *
	 */
	private void skipIlleageTag() {
		char label_end = LABEL_END.charAt(0);
		while (this.scan_ptr < this.template_length) {
			char cur_ch = this.template_content.charAt(this.scan_ptr);
			if (cur_ch == label_end) break;
			++this.scan_ptr;
		}
	}
	
	/**
	 * 遇到一个标识符，尝试做为标签来解析。
	 * @param token - 遇到的这个标识符。
	 */
	private boolean analyzeFirstToken(Token token) {
		// 处理特殊指令的符号。
		int instru_id = InstructionElement.getInstructionId(token.getIdentifier());
		if (instru_id != 0)
			return processStartInstruction(token, instru_id);
		
		// 尝试读取下一个 token - EOT, '}', ':' 我们进行处理。
		Token next_token = this.token_parser.getNextToken();
		
		// 遇到 EOT - 不合法，期待 '}' 符号。
		if (next_token.isEOT()) {
			return false;
		}
		
		// 遇到 '}' (LABEL_END) - 做为一个没有命名空间的标签来处理。格式为  #{iden}
		if (next_token.isSymbolToken(LABEL_END))  {
			this.scan_ptr = token_parser.getCurrScanPos();
			appendLabelElement(token.getIdentifier(), null, null);
			return true;
		}
		
		// 遇到 ':' - 命名空间分隔符号，做为标签来处理，格式为 #{ns:iden ...}
		if (next_token.isSymbolToken(":")) {
			return analyzeNamespaceLabel(token);
		}
		
		// 遇到另一个 iden - #{label attr, 我们认为是属性，开始解析属性。
		if (next_token.isIdentifier()) {
			return analyzeLabelWithAttr(token.getIdentifier(), null, next_token.getStartPos());
		}
		
		// 整个做为表达式来处理，请求单独的表达式解析器进行解析。
		return analyzeExpression(token.getStartPos());
	}
	
	/**
	 * 将 '#{ }' 之间的都做为表达式处理。
	 * @param expr_start_pos
	 * @return
	 */
	private boolean analyzeExpression(int expr_start_pos) {
		try {
			// 解析表达式。
			TokenParser expr_token_parser = new TokenParser(this.template_content, expr_start_pos, this.template_length);
			ExpressionParser expr_parser = new ExpressionParser(expr_token_parser);
			Expression expr = expr_parser.parse();	// throws ExpressionException
			
			// 结束之后必须是 '}'
			Token end_token = token_parser.getFirstToken(expr_parser.getCurrScanPos(), this.template_length);
			if (end_token.isSymbolToken(LABEL_END) == false) {
				return false;
			}
			this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token
			
			// 如果前面有文本，则先构建前面的文本元素。
			this.insertRemainTextElement();
			
			// 创建表达式节点并插入到当前插入点。
			ExpressionElement expr_elem = new ExpressionElement(expr);
			insertToCurrentElement(expr_elem, null);
			return true;
		} catch (ExpressionException ex) {
			return false;
		}
	}
	
	/**
	 * 解析具有属性的标签。
	 * @param label - 标签的名字。
	 * @param label_namespace - 标签的命名空间。
	 * @return
	 */
	private boolean analyzeLabelWithAttr(String label, String label_namespace, int attr_start_pos) {
		// 解析属性部分，从 attr_start_pos 开始。
		AttributeParser attr_parser = new AttributeParser(this.template_content);
		AttributeCollection attr_list = attr_parser.parse(attr_start_pos, this.template_content.length());
		this.scan_ptr = attr_parser.getCurrScanPos();
		
		// 此后的符号一定要是 "}" (LABEL_END)
		Token end_token = token_parser.getFirstToken(this.scan_ptr, this.template_length);
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		
		// 一个标签已经完整的读入了，创建这个 LabelElement。
		this.scan_ptr = token_parser.getCurrScanPos();
		this.appendLabelElement(label, label_namespace, attr_list);
		return true;
	}
	
	/**
	 * 解析带有命名空间的标签。格式 #{ns: label ...}
	 * @param label_start
	 * @param namespace
	 * @return
	 */
	private boolean analyzeNamespaceLabel(Token namespace) {
		// 读取名字部分。
		Token label_token = token_parser.getNextToken();
		if (label_token.isIdentifier() == false) {
			return false;
		}
		
		// 读取属性列表
		return analyzeLabelWithAttr(label_token.getIdentifier(), namespace.getIdentifier(), token_parser.getCurrScanPos());
	}
	
	/**
	 * 创建一个标签元素，并添加到当前插入点。
	 * @param label - 标签的名字。
	 * @param label_namespace - 标签的命名空间。
	 * @param attr_list - 属性列表。
	 */
	private void appendLabelElement(String label, String label_namespace, AttributeCollection attr_list) {
		// 如果前面有剩余的文本元素，则先构造文本元素。
		insertRemainTextElement();

		// 构造标签元素部分。
		LabelElement label_elem = new LabelElement(label, label_namespace, attr_list);
		// 附加上位置信息。
		pos.getPosition(this.label_begin_pos, this.scan_ptr, label_elem);
		
		insertToCurrentElement(label_elem, null);
	}
	
	/**
	 * 遇到一个结束标签，其以 '/' 开始。语法为 #{/[ns:]label}
	 *
	 */
	private boolean analyzeEndLabel() {
		// 可能是命名空间名字或者标签名字。
		Token ns_or_label = token_parser.getNextToken();
		if (ns_or_label.isIdentifier() == false) {
			return false;
		}
		
		// 如果是结束指令。
		int instru_id = InstructionElement.getInstructionId(ns_or_label.getIdentifier());
		if (instru_id != 0)
			return processEndInstruction(ns_or_label, instru_id);
		
		// 可能是 ':' or '}'
		Token colon_or_end = token_parser.getNextToken();
		if (colon_or_end.isSymbolToken(LABEL_END)) {
			// 遇到 '}'， 则前面的 token 是标签名字。
			return handleEndLabelElement(ns_or_label.getIdentifier());
		}
		
		if (colon_or_end.isSymbolToken(":")) {
			// 遇到 ':'，则前面的 token 是命名空间。
			Token label_token = token_parser.getNextToken();
			if (label_token.isIdentifier() == false) {
				return false;
			}
			
			// 必须是 '}' LABEL_END
			Token end_label = token_parser.getNextToken();
			if (end_label.isSymbolToken(LABEL_END) == false) {
				return false;
			}
			
			return handleEndLabelElement(ns_or_label.getIdentifier() + ":" + label_token.getIdentifier()); 
		}
		
		// 如上情况都不是，则语法错误。
		return false;
	}
	
	/**
	 * 处理一个结束标签。
	 * @param label_name
	 */
	private boolean handleEndLabelElement(String label_name) {
		// 移动扫描指针。
		this.scan_ptr = token_parser.getCurrScanPos();	// accept last-token '}'
		
		// 向上查找与此匹配的标签。
		AbstractLabelElement start_label = findStartLabel(label_name);
		if (start_label == null) {
			return false;
		}
		
		// 如果前面有剩余的文本元素，则先构造文本元素。
		insertRemainTextElement();
		
		// 将 start_label 之后的所有元素都改变为其子元素，并设置已经有了匹配结束标签。
		start_label.xferSiblingToChild(true);
		
		// TODO: 由于 start_label 已经结束，所以将 start_label 的父元素作为当前元素。
		// this.cur_elem = start_label.getParent();

		return true;
	}

	
	// label 辅助函数。
	
	/**
	 * 从当前插入点元素的最后一个子节点开始向上查找具有指定标签名字的开始标签。
	 * @param label_name
	 * @return
	 */
	private final AbstractLabelElement findStartLabel(String label_name) {
		AbstractTemplateElement iter_elem = this.cur_elem.getLastChild();
		while (iter_elem != null) {
			// 是一个标签？没有匹配结束？名字符合？
			if (iter_elem.isLabelElement()) {
				AbstractLabelElement label_elem = (AbstractLabelElement)iter_elem;
				if (label_elem.hasMatchEndLabel() == false && label_name.equals(label_elem.getLabelName()))
					return label_elem;
			}
			// 继续找前一个。
			iter_elem = iter_elem.getPrevElement();
		}
		// 没有找到匹配的。
		return null;
	}
	
	/**
	 * 从当前扫描位置 (scan_ptr) 查找下一个 Label 的位置。
	 * @return
	 */
	private final int findNextLabel() {
		int next_1 = this.template_content.indexOf(LABEL_BEGIN, this.scan_ptr);
		int next_2 = this.template_content.indexOf(LABEL_BEGIN_2, this.scan_ptr);
		if (next_1 < 0 && next_2 < 0) return -1;	// 未找到。
		if (next_1 < 0) return next_2;
		if (next_2 < 0) return next_1;
		return (next_1 < next_2) ? next_1 : next_2;	// 先找到哪个用哪个。
	}

	
	// 插入节点帮助函数。
	
	/**
	 * 在当前插入点插入一个文本子节点，插入点不变。
	 */
	private final void insertTextElement(String text) {
		insertToCurrentElement(new TextElement(text), null);
	}
	
	/**
	 * 在当前插入点插入一个子节点，然后设置当前插入点为指定节点。
	 * @param new_elem - 要插入的新节点。
	 * @param new_insert_pos - 新的插入点元素，如果给出为空，则插入点不变。
	 * 
	 */
	private final void insertToCurrentElement(AbstractTemplateElement new_elem, AbstractContainerElement new_insert_pos) {
		this.cur_elem.appendChild(new_elem);
		if (new_insert_pos != null)
			this.cur_elem = new_insert_pos;
	}

	/**
	 * 在指定插入点插入一个子节点，然后设置 new_insert_pos 为新的插入节点。
	 * @param cont_elem - 插入点。
	 * @param new_elem - 要插入的新节点。
	 * @param new_insert_pos - 新的插入点元素，必须不能为空。
	 * 
	 */
	private final void insertToSpecialElement(AbstractContainerElement cont_elem, 
			AbstractTemplateElement new_elem, AbstractContainerElement new_insert_pos) {
		cont_elem.appendChild(new_elem);
		this.cur_elem = new_insert_pos;
	}

	/**
	 * 在当前 label 位置之前如果还未构造 TextElement 则构造并添加上。
	 *
	 */
	private void insertRemainTextElement() {
		// 如果前面有剩余的文本元素，则先构造文本元素。
		if (this.text_begin_pos < this.label_begin_pos) {
			insertTextElement(template_content.substring(text_begin_pos, label_begin_pos));
			
			// 移动 text_begin_pos 到最后解析的扫描位置。
			this.text_begin_pos = this.scan_ptr;
		}
	}

	/**
	 * 遇到 ... \#{ 序列，其中 scan_ptr 指向 # 位置，我们忽略此 #{ 序列，将其做为正文看待。
	 */
	private final void insertEscapedStart() {
		// 取得从 text_begin_pos 开始到 \ 符号前面的正文做为正文。跳过 \ 符号。
		if (this.text_begin_pos < this.scan_ptr-1) {
			insertTextElement(this.template_content.substring(this.text_begin_pos, this.scan_ptr-1));
		}
		
		// 设置新的正文开始位置和扫描开始位置。
		this.text_begin_pos = this.scan_ptr;
		this.scan_ptr += LABEL_BEGIN.length();
	}

	// 读取表达式辅助函数。
	
	/**
	 * 从当前符号开始读取后面的表达式部分，并验证结束必须是 '}'。
	 * @return
	 */
	private Expression readRemainExpression() {
		// 从下一个 token 处解析表达式。
		ExpressionParser expr_parser = new ExpressionParser(this.token_parser);
		Expression expr = expr_parser.parse();
		
		// 当表达式解析完成之后必须是 '}' 符号。
		Token end_token = token_parser.getFirstToken(expr_parser.getCurrScanPos(), this.template_length);
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return null;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		return expr;
	}

	
	// 指令读取处理辅助函数。
	
	/**
	 * 判定是否是一个模板指令，并读取这些指令。
	 * @param token
	 * @return 返回 true 表示这是一个指令，并做了处理；否则不是一个指令。
	 */
	private boolean processStartInstruction(Token token, int instru_id) {
		switch (instru_id) {
		case InstructionElement.INSTRU_IF:
			return readIfInstruction();
		case InstructionElement.INSTRU_ELSEIF:
			return readElseIfInstruction();
		case InstructionElement.INSTRU_ELSE:
			return readElseInstruction();
		case InstructionElement.INSTRU_ENDIF:
			return readEndIfInstruction();
		case InstructionElement.INSTRU_FOREACH:
			return readForeachInstruction();
		case InstructionElement.INSTRU_SWITCH:
			return readSwitchInstruction();
		case InstructionElement.INSTRU_CASE:
			return readCaseInstruction();
		case InstructionElement.INSTRU_DEFAULT:
			return readDefaultInstruction();
		case InstructionElement.INSTRU_BREAK:
			return readBreakInstruction();
		case InstructionElement.INSTRU_RETURN:
			return readReturnInstruction();
		case InstructionElement.INSTRU_CALL:
			return readCallInstruction();
		case InstructionElement.INSTRU_PARAM:
			return readParamInstruction();
		case InstructionElement.INSTRU_ASSIGN:
			return readAssignInstruction();
		default:
			throw new RuntimeException("unknown instruction: " + token + ", it's template internal error");
		}
	}
	
	/**
	 * 判定是否是一个结束指令，并读取这些指令。
	 * @param token
	 * @return 返回 true 表示这是一个指令，并做了处理；否则不是一个指令。
	 */
	private boolean processEndInstruction(Token token, int instru_id) {
		switch (instru_id) {
		case InstructionElement.INSTRU_IF:
			return readEndIfInstruction();
		case InstructionElement.INSTRU_FOREACH:
			return readEndForeachInstruction();
		case InstructionElement.INSTRU_SWITCH:
			return readEndSwitchInstruction();
		default:
			throw new RuntimeException("internal error");
		}		
	}
	

	// if-elseif-else-endif 读取帮助函数。
	
	/**
	 * 得到当前 if 指令节点，用于 elseif, else, /if 节点构建时。
	 * 此时 当前插入点 cur_elem 一定是一个 ConditionElement, 且其父节点是一个 if instruction,
	 *   否则就是 elseif, else, /if 节点出现的位置不正确。
	 * @return 找到了则返回，否则返回空。
	 */
	private IfInstructionElement getCurrIfInstruction() {
		// 当前插入点必须是 ConditionElement.
		if ((cur_elem instanceof ConditionElement) == false) return null;
		
		// 其一定有父节点。
		AbstractTemplateElement if_elem = cur_elem.getParent();
		if (if_elem == null) return null;
		
		// 父节点一定是 IfInstructionElement.
		if ((if_elem instanceof IfInstructionElement) == false) return null;
		
		return (IfInstructionElement)if_elem;
	}

	/**
	 * 读取 if 指令。 if 语法 #{if expr}
	 * @return
	 */
	private boolean readIfInstruction() {
		// 读取 if 的表达式。
		Expression expr = readRemainExpression();
		if (expr == null) {
			return false;
		}
		
		// 构造 if instruction element, 加到 cur_elem, 切换插入点为第一个 cond 块。
		IfInstructionElement if_elem = new IfInstructionElement(expr);
		AbstractContainerElement if_cond = (AbstractContainerElement)if_elem.getFirstChild();
		this.insertRemainTextElement();
		this.insertToCurrentElement(if_elem, if_cond);
		return true;
	}
	
	/**
	 * 读取 elseif 指令，语法 #{elseif expr}.
	 * @return
	 */
	private boolean readElseIfInstruction() {
		// 读取 elseif 的表达式。
		Expression expr = readRemainExpression();
		if (expr == null) return false;
		
		// 构造一个 condition element, 添加到前一个 if 块中，切换插入点为此 elseif cond 块。
		ConditionElement elseif_cond = new ConditionElement("elseif", expr);
		IfInstructionElement if_elem = getCurrIfInstruction();
		if (if_elem == null) {
			return false;
		}
		
		// 将 elseif 条件节点插入到 if 节点做为其子节点，并设置当前插入点为 elseif 节点。
		this.insertRemainTextElement();
		if_elem.appendChild(elseif_cond);
		this.cur_elem = elseif_cond;
		
		return true;
	}

	/**
	 * 读取 else 指令，语法 #{else}.
	 * @return
	 */
	private boolean readElseInstruction() {
		// else 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		// 构造一个 else condition element, 添加到前一个 if 块中，切换插入点为此 else cond 块。
		ConditionElement else_cond = new ConditionElement("else", null);
		IfInstructionElement if_elem = getCurrIfInstruction();
		if (if_elem == null) {
			return false;
		}
		
		// 将 else 节点插入到 if 节点做为其子节点，并设置当前插入点为 else 节点。
		this.insertRemainTextElement();
		if_elem.appendChild(else_cond);
		this.cur_elem = else_cond;
		
		return true;
	}
	
	/**
	 * 读取 endif, /if 指令，语法 #{endif} 或 #{/if}.
	 * @return
	 */
	private boolean readEndIfInstruction() {
		// endif, /if 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		// 结束当前的 if 节点。
		IfInstructionElement if_elem = getCurrIfInstruction();
		if (if_elem == null) {
			return false;
		}
		
		// 设置当前插入点为 if 节点的父节点，其一定是一个容器。
		this.insertRemainTextElement();
		this.cur_elem = (AbstractContainerElement)if_elem.getParent();
		return true;
	}
	
	
	// foreach-/foreach 读取帮助函数。
	
	/**
	 * 读取 foreach 指令。foreach 语法 #{foreach var_name in expr}
	 */
	private boolean readForeachInstruction() {
		// 1. 读取 var_name
		Token var_token = this.token_parser.getNextToken();
		if (var_token.isIdentifier() == false) {
			return false;
		}
		
		// 2. 读取 in 关键字。
		Token in_token = this.token_parser.getNextToken();	// accept var_token
		if (in_token.isIdentifier("in") == false) {
			return false;
		}
		
		// 3. 读取后面的表达式。
		Expression coll_expr = this.readRemainExpression();
		if (coll_expr == null) {
			return false;
		}
		
		// 构造 foreach 节点，插入到当前插入点，并做为新的插入点。
		ForeachInstructionElement foreach_elem = new ForeachInstructionElement(var_token.getIdentifier(), coll_expr);
		this.insertRemainTextElement();
		this.insertToCurrentElement(foreach_elem, foreach_elem);
		
		return true;
	}
	
	/**
	 * 读取 /foreach 指令，语法 #{/foreach}
	 * @return
	 */
	private boolean readEndForeachInstruction() {
		// /foreach 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}

		// 结束当前的 foreach 块。
		ForeachInstructionElement foreach_elem = getCurrForeachInstruction();
		if (foreach_elem == null) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		// 转换插入点到父节点就结束了这个 foreach 指令。
		this.insertRemainTextElement();
		this.cur_elem = foreach_elem.getParent();
		return true;
	}
	
	/**
	 * 获取当前 foreach 指令的开始节点。
	 * @return
	 */
	private ForeachInstructionElement getCurrForeachInstruction() {
		// 当前插入点必须是 ForeachInstructionElement.
		if ((cur_elem instanceof ForeachInstructionElement) == false) return null;
		
		return (ForeachInstructionElement)cur_elem;
	}
	
	
	// switch - case - default - /switch 读取帮助函数。
	
	/**
	 * 读取 switch 指令。 switch 语法 #{switch expr}.
	 * @return
	 */
	private boolean readSwitchInstruction() {
		// 读取 switch 的表达式。
		Expression expr = readRemainExpression();
		if (expr == null) return false;
		
		// 构造一个 SwitchInstructionElement 并准备读取 case, default 条件语句。
		SwitchInstructionElement switch_elem = new SwitchInstructionElement(expr);
		this.insertRemainTextElement();
		this.insertToCurrentElement(switch_elem, switch_elem);
		
		return true;
	}
	
	/**
	 * 读取 case 指令。 case 语法 #{case expr}.
	 * @return
	 */
	private boolean readCaseInstruction() {
		// 读取 case 的表达式。
		Expression case_expr = readRemainExpression();
		if (case_expr == null) return false;
		
		// 查找所在的 switch 指令。
		SwitchInstructionElement switch_elem = getCurrSwitchInstruction();
		if (switch_elem == null) {
			return false;
		}
		ConditionElement case_elem = new ConditionElement("case", case_expr);
		if (this.cur_elem != switch_elem) {
			this.insertRemainTextElement();
		} // 否则前面的文字部分被忽略掉。
		// 将 case 插入到 switch 下面，并把 case 做为新的插入点。
		this.insertToSpecialElement(switch_elem, case_elem, case_elem);
		return true;
	}
	
	/**
	 * 读取 default 指令。 default 语法 #{default}.
	 * @return
	 */
	private boolean readDefaultInstruction() {
		// default 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'

		// 查找所在的 switch 指令。
		SwitchInstructionElement switch_elem = getCurrSwitchInstruction();
		if (switch_elem == null) {
			return false;
		}
		ConditionElement def_elem = new ConditionElement("default", null);
		this.insertRemainTextElement();
		// 将 default 插入到 switch 下面，并把 default 做为新的插入点。
		this.insertToSpecialElement(switch_elem, def_elem, def_elem);
		return true;
	}
	
	/**
	 * 读取 /switch 结束。语法 #{/switch}.
	 * @return
	 */
	private boolean readEndSwitchInstruction() {
		// #/switch 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}

		// 结束当前的 switch 块。
		SwitchInstructionElement switch_elem = getCurrSwitchInstruction();
		if (switch_elem == null) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		// 转换插入点到父节点就结束了这个 foreach 指令。
		this.insertRemainTextElement();
		this.cur_elem = switch_elem.getParent();
		return true;
	}
	
	/**
	 * 获取当前 switch 指令的开始节点。
	 * @return
	 */
	private SwitchInstructionElement getCurrSwitchInstruction() {
		// 当前插入点是 SwitchInstructionElement 则直接返回
		if (this.cur_elem instanceof SwitchInstructionElement) 
			return (SwitchInstructionElement)this.cur_elem;
		
		// 否则当前插入点必须是 case 或 default 节点。其 parent 是 switch 节点。
		AbstractTemplateElement parent_insert = this.cur_elem.getParent();
		if (parent_insert == null) return null;
		if ((parent_insert instanceof SwitchInstructionElement) == false) return null;
		
		return (SwitchInstructionElement)parent_insert;
	}
	
	// break, return 读取帮助函数。
	
	/**
	 * 读取 break 指令。 break 语法 #{break}
	 */
	private boolean readBreakInstruction() {
		// #/switch 后面必须就是 '}' 结束了。
		Token end_token = token_parser.getNextToken();
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'

		// 构造 break 指令并插入到当前插入节点。
		BreakInstructionElement break_elem = new BreakInstructionElement();
		this.insertRemainTextElement();
		this.insertToCurrentElement(break_elem, null);
		return true;
	}
	
	/**
	 * 读取 return 指令。 return 语法 #{return expr} 或 #{return}
	 * @return
	 */
	private boolean readReturnInstruction() {
		// 读取 return 的表达式，可能没有。
		Token next_token = this.token_parser.getNextToken();
		Expression ret_expr = null;
		if (next_token.isSymbolToken(LABEL_END)) {
			// #{return} 无返回表达式。
			this.scan_ptr = token_parser.getCurrScanPos();	// accept next_token '}'
		} else {
			// 后面必须是一个表达式。
			/*Token ignore_token MUST be 'return' */ 
			this.token_parser.getFirstToken(this.scan_ptr, this.template_length);
			ExpressionParser expr_parser = new ExpressionParser(token_parser);
			ret_expr = expr_parser.parse();
			if (ret_expr == null) {
				return false;
			}
			
			// 再后面必须是 '}' LABEL_END
			Token end_token = token_parser.getFirstToken(expr_parser.getCurrScanPos(), this.template_length);
			if (end_token.isSymbolToken(LABEL_END) == false) {
				return false;
			}
			this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		}
		
		// 构造 return 指令并插入到当前插入节点。
		ReturnInstructionElement return_elem = new ReturnInstructionElement(ret_expr);
		this.insertRemainTextElement();
		this.insertToCurrentElement(return_elem, null);
		return true;
	}


	// call, param 读取帮助函数。
	
	/**
	 * 读取 call 指令。 call 语法 #{call child_template_name(param-list)}.
	 */
	private boolean readCallInstruction() {
		// 读取子模板名字.
		Token name_token = this.token_parser.getNextToken();
		if (name_token.isIdentifier() == false) {
			return false;
		}
		
		// 读取后面的可选的参数列表。
		Token next_token = this.token_parser.getNextToken();
		ParameterList params = ParameterList.EMPTY_PARAMETER_LIST;
		if (next_token.isSymbolToken("(")) {
			ExpressionParser expr_parser = new ExpressionParser(this.token_parser);
			params = expr_parser.parseParameters();		// accept '('
			// 读取完成之后必须是 ')'
			Token para_end = this.token_parser.getFirstToken(expr_parser.getCurrScanPos(), this.template_length);
			if (para_end.isSymbolToken(")")== false) {
				return false;
			}
			next_token = this.token_parser.getNextToken();		// accept ')'
		}
		
		if (next_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = next_token.getEndPos();		// accept '}'
		
		// 构造 CallInstructionElement, 并插入到当前插入点。
		CallInstructionElement call_elem = new CallInstructionElement(name_token.getIdentifier(), params);
		this.insertRemainTextElement();
		this.insertToCurrentElement(call_elem, null);
		return true;
	}
	
	/**
	 * 读取 param 指令。 param 语法 #{param para_name, para2_name ...}
	 * @return
	 */
	private boolean readParamInstruction() {
		java.util.ArrayList<String> para_coll = new java.util.ArrayList<String>();
		Token name_token = this.token_parser.getNextToken();
		if (name_token.isIdentifier() == false) {
			return false;
		}
		para_coll.add(name_token.getSymbol());
		
		while (true) {
			Token next_token = this.token_parser.getNextToken();	// accept name_token
			if (next_token.isSymbolToken(LABEL_END) == true) break;
			if (next_token.isSymbolToken(",")) {
				name_token = this.token_parser.getNextToken();		// accept ','
				if (name_token.isIdentifier() == false) {
					return false;
				}
				para_coll.add(name_token.getSymbol());
			} else {
				return false;
			}
		}
		
		this.scan_ptr = this.token_parser.getCurrScanPos();	  // accept '}'
		
		this.insertRemainTextElement();
		for (int i = 0; i < para_coll.size(); ++i) {
			String para_name = para_coll.get(i);
			// 构造 ParamInstructionElement 并插入到当前插入点。
			ParamInstructionElement para_elem = new ParamInstructionElement(para_name, null);
			this.insertToCurrentElement(para_elem, null);
		}
		return true;
	}
	
	
	// assign 读取帮助函数
	
	/**
	 * 读取 #{assign var = expr}
	 */
	private boolean readAssignInstruction() {
		// 读取变量名。
		Token varname_token = this.token_parser.getNextToken();
		if (varname_token.isIdentifier() == false) {
			return false;
		}

		// 读取 '=' 号
		Token equal_token = this.token_parser.getNextToken();
		if (equal_token.isSymbolToken("=") == false) return false;
		
		// 读取后面的表达式。
		ExpressionParser expr_parser = new ExpressionParser(token_parser);
		Expression assign_expr = expr_parser.parse();
		if (assign_expr == null) {
			return false;
		}
		
		// 再后面必须是 '}' LABEL_END
		Token end_token = token_parser.getFirstToken(expr_parser.getCurrScanPos(), this.template_length);
		if (end_token.isSymbolToken(LABEL_END) == false) {
			return false;
		}
		this.scan_ptr = token_parser.getCurrScanPos();	// accept end_token '}'
		
		// 构造 assign instruction 并插入到当前节点。
		AssignInstructionElement assign_elem = new AssignInstructionElement(varname_token.getIdentifier(), assign_expr);
		this.insertRemainTextElement();
		this.insertToCurrentElement(assign_elem, null);
		return true;
	}
	
	// 调试用。
	
	/** 调试输出 ElementTree */
	private final void dumpElementTree(String indent, AbstractTemplateElement elem) {
		while (elem != null) {
			AbstractTemplateElement child = elem.getFirstChild();
			if (child != null) {
				dumpElementTree(indent + "  ", child);
			}

			elem = elem.getNextElement();
		}
	}
}
