package com.chinaedustar.template.core;

/**
 * 系统内建指令节点的基类。
 * 
 * @author liujunxing
 */
public abstract class InstructionElement extends AbstractContainerElement {
	/** 指令名字。 */
	private final String instruction;
	
	// 指令常量。
	
	/** if 指令。 */
	public static final int INSTRU_IF = 1;
	
	/** elseif 指令。 */
	public static final int INSTRU_ELSEIF = 2;
	
	/** else 指令。 */
	public static final int INSTRU_ELSE = 3;
	
	/** endif (/if) 指令。 */
	public static final int INSTRU_ENDIF = 4;
	
	/** foreach 指令。 */
	public static final int INSTRU_FOREACH = 5;
	
	/** switch 指令。 */
	public static final int INSTRU_SWITCH = 6;
	
	/** case 指令。 */
	public static final int INSTRU_CASE = 7;
	
	/** default 指令。 */
	public static final int INSTRU_DEFAULT = 8;
	
	/** break 指令。 */
	public static final int INSTRU_BREAK = 9;
	
	/** return 指令。 */
	public static final int INSTRU_RETURN = 10;
	
	/** call 指令。 */
	public static final int INSTRU_CALL = 11;
	
	/** param 指令。 */
	public static final int INSTRU_PARAM = 12;
	
	/** assign 指令。 */
	public static final int INSTRU_ASSIGN = 13;
	
	/**
	 * 返回指定标识符是否是一个指令，如果是返回指令标识。
	 * @param iden
	 * @return 如果不是一个指令，则返回 0。
	 */
	public static final int getInstructionId(String iden) {
		if (iden.equals("if")) return INSTRU_IF;
		if (iden.equals("elseif")) return INSTRU_ELSEIF;
		if (iden.equals("else")) return INSTRU_ELSE;
		if (iden.equals("endif")) return INSTRU_ENDIF;
		if (iden.equals("foreach")) return INSTRU_FOREACH;
		if (iden.equals("switch")) return INSTRU_SWITCH;
		if (iden.equals("case")) return INSTRU_CASE;
		if (iden.equals("default")) return INSTRU_DEFAULT;
		if (iden.equals("break")) return INSTRU_BREAK;
		if (iden.equals("return")) return INSTRU_RETURN;
		if (iden.equals("call")) return INSTRU_CALL;
		if (iden.equals("param")) return INSTRU_PARAM;
		if (iden.equals("assign")) return INSTRU_ASSIGN;
		
		return 0;
	}
	
	/**
	 * 使用指定指令名字构造一个指令节点。
	 *
	 */
	public InstructionElement(String instruction) {
		super();
		this.instruction = instruction;
	}
	
	/**
	 * 获得指令的名字，如 if, switch, foreach 等。
	 * @return
	 */
	public String getInstruction() {
		return this.instruction;
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public abstract int accept(InternalProcessEnvironment env);
}
