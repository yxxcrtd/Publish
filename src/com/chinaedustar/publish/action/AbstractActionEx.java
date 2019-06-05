package com.chinaedustar.publish.action;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.comp.StringResources;
import com.chinaedustar.publish.model.Log;

/**
 * 提供有 Log, Messages, Links 支持的 Action 基类。
 * 
 * <p>为使用此类，派生类需要：
 * <li>  1、提供正确的名字，如 'SiteAction' 除了 'Action' 名字之外的部分被做为对象名字看待。
 * <li>  2、提供命令注册，使用 registerCommand 注册其支持的命令。
 *          命令注册之后，实现该命令，标准格式为 public ActionResult command_name()
 * <li>  3、在 strings.property 中记录各种操作对应的字符串。日志和消息都使用它。
 * </p>
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public class AbstractActionEx extends AbstractAction {
	
	/** 所有可用命令集合，以 ActionCommand.operation 为键。 */
	protected static final Map<String, ActionCommand> command_map = 
			new java.util.HashMap<String, ActionCommand>();
	
	/** 使用的资源字符串。 */
	private static StringResources str_res;
	
	/**
	 * 注册一个命令。
	 * @param clazz
	 * @param command
	 */
	protected static final void registerCommand(Class clazz, ActionCommand command) {
		String key = clazz.getName() + "." + command.getCommand();
		command_map.put(key, command);
	}
	
	/**
	 * 注册一个命令。
	 * @param clazz
	 * @param command
	 */
	protected static final void registerCommand(Class clazz, String command) {
		registerCommand(clazz, new ActionCommand(command));
	}
	
	/** 当前要执行的命令。 */
	protected ActionCommand action_command;
	
	/** 操作的结果。 */
	protected ActionResult action_result;
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	@Override public void execute() throws Exception {
		// 标准 action 分步实施
		ActionResult result = UNKNOWN_COMMAND;
		try {
			// 1、翻译命令对象，从 command -> ActionCommand 对象。
			if (xlateActionCommand() == false) return;
			
			result = NOT_LOGIN;
			// 2、验证是否有管理员。此步骤可以由子类覆盖而不进行，或以别的方式进行。
			if (checkHasAdmin() == false) return;
			
			// 这里也许需要有检查权限这一步骤，但是很多地方权限和命令有关，所以暂时不加独立的这一步。
			// 如果需要做，可以在 executeAction() 里面实现。 
			
			// 3、执行命令。
			result = FAIL;
			result = executeAction();
		} finally {
			// 4、善后处理。根据返回值：记录日志、选择模板、信息提示
			finalExecute(result);
		}
	}

	/**
	 * 翻译命令字符串。
	 *
	 */
	protected boolean xlateActionCommand() {
		String command = param_util.safeGetStringParam("command");
		this.action_command = xlateActionCommand(command);
		if (this.action_command == null) {
			this.action_command = EMPTY_COMMAND;
			super.unknownCommand(command);
			return false;
		}
		return true;
	}
	
	/**
	 * 翻译命令字符串。
	 *
	 */
	protected ActionCommand xlateActionCommand(String command) {
		Class clazz = this.getClass();
		while (clazz != null && clazz != AbstractAction.class) {
			// 标准 ActionCommand.operation = 'com.chinaedustar.publish.XxxAction' + '.' + command
			String key = getClass().getName() + "." + command;
			
			// 从 Map 中找。
			ActionCommand action_command = command_map.get(key);
			if (command != null) return action_command;
			clazz = clazz.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 检查是否有管理员。
	 * @return
	 */
	protected boolean checkHasAdmin() {
		if (this.admin == null || this.admin.getId() == 0) {
			messages.add("当前未登录，或登录超时，请登录之后进行操作。");
			links.add(new ActionLink("[登录]", site.resolveUrl("admin/admin_login.jsp")));
			links.add(new ActionLink("[返回主页]", site.getUrl()));
			
			this.createWriteLog("security", "验证登录", Log.STATUS_NOT_LOGIN); // createLog("security", Log.STATUS_ACCESS_DENIED);
			return false;
		}
		return true;
	}

	/**
	 * 实际执行命令。派生类可以重载不使用内部的 dispatchCommand 方式。
	 * @return 
	 */
	protected ActionResult executeAction() {
		return dispatchCommand();
	}
	
	/**
	 * 内部派发命令。
	 * @return
	 */
	protected ActionResult dispatchCommand() {
		try {
			// 根据命令查找同名函数。如 'save()'
			String command = this.action_command.getCommand();
			Method method = this.getClass().getDeclaredMethod(command, new Class[]{});
			if (method == null) return NO_SUCH_METHOD;
			
			// 设置命令我们能执行，派生类能够定义命令为 protected 或 internal
			method.setAccessible(true);
			Object result = method.invoke(this, new Object[]{});
			
			if (result == null) return FAIL;
			if (result instanceof ActionResult) return (ActionResult)result;
			
			// 最好返回 ActionResult 类型，否则返回结果未知。从而无法产生日志。
			return UNKNOWN;
		} catch (NoSuchMethodException ex) {
			return NO_SUCH_METHOD;
		} catch (Exception ex) {
			return new ActionResult("internal_error", ex);
		}
	}
	
	/**
	 * 根据返回值：记录日志、选择模板、信息提示。
	 * @param result
	 */
	protected void finalExecute(ActionResult result) throws Exception {
		// 确保有结果。
		if (result == null) result = FAIL;

		// 写入日志。
		String desc = createResultLogString(result);
		if (result.getWriteLog()) {
			super.createWriteLog(getLogCommand(), desc, result.getStatus());
		}

		// 确保有信息和链接。
		if (desc != null && desc.length() > 0) {
			messages.add(desc);
		}
		
		if (links.isEmpty())
			links.add(getBackActionLink());
		
		if (result.getException() != null)
			throw result.getException();
	}
	
	// 获得日志中写入的命令形式，格式为 'site_save', 'channel_delete' 等。
	protected String getLogCommand() {
		if (action_command == null)
			return getObjectName() + "_unknown";
		return getObjectName() + "_" + action_command.getCommand();
	}
	
	/**
	 * 根据结果创建日志记录字符串。
	 * @param result
	 * @return
	 */
	protected String createResultLogString(ActionResult result) {
		// 1. 得到 pattern.
		String pattern = getPattern(action_command, result);
		
		// 2. 格式化这个字符串。
		String desc = result.formatString(pattern, action_command);
		
		// 3. 如果有 ex 则附加上。
		if (result.getException() != null) {
			desc += result.getException();
		}
		
		return desc;
	}
	
	/**
	 * 根据当前执行的命令、类、结果获得描述字符串，其从 'strings.property' 文件中读取。
	 * @param command
	 * @param result
	 * @return
	 */
	protected String getPattern(ActionCommand command, ActionResult result) {
		// 1. 全 key = 'site.save.success'
		String key = getPatternKey(result);
		String pattern = getStringResource(key);
		if (pattern != null && pattern.length() > 0) return pattern;
		
		// 1.5 部分 key1 = 'site.*.success'
		key = getObjectName() + ".*." + result.getCode();
		pattern = getStringResource(key);
		if (pattern != null && pattern.length() > 0) return pattern;
		
		// 2. 部分 key1 = 'save.success'
		if (command != null) {
			key = command.getCommand() + "." + result.getCode();
			pattern = getStringResource(key);
			if (pattern != null && pattern.length() > 0) return pattern;
		}
		
		// 3. 再最后一部分 key 'success'
		key = result.getCode();
		pattern = getStringResource(key);
		if (pattern != null && pattern.length() > 0) return pattern;
		
		// 4. 实在没有 ??
		return "执行命令 " + command + " 结果为 " + result.getCode() + 
			"(未提供详细信息，请检查 strings.property 是否配置正确。)";
	}
	
	/**
	 * 得到指定键的属性值，其配置在 'strings.property' 文件中。
	 * @param key
	 * @return
	 */
	protected String getStringResource(String key) {
		if (str_res == null) {
			synchronized (this) {
				str_res = new StringResources();
				String root_dir = pub_ctxt.getRootDir();
				String file_name = root_dir + "/WEB-INF/conf/strings.property";
				System.err.println("try load strings.property from " + file_name);
				try {
					str_res.init(file_name, "GB2312");
				} catch (java.io.IOException ex) {
					System.err.println("不能加载字符串资源文件 " + file_name + ", 请确定该文件存在");
					System.err.println("  ex = " + ex);
					backdoorInitStrings(str_res);
				}
			}
		}
		return str_res.get(key);
	}

	// 走后门初始化一些基本错误信息字符串。
	private static final void backdoorInitStrings(StringResources str_res) {
		// 添加一些基本的。
		str_res.backdoorPut("success", "操作成功完成。");
		str_res.backdoorPut("fail", "操作失败。");
		str_res.backdoorPut("not_login", "管理员未登录，权限被拒绝。");
		str_res.backdoorPut("unknown", "不支持的命令 {0}。");
		str_res.backdoorPut("no_such_method", "不支持当前命令 {0}，内部未实现方法。");
		str_res.backdoorPut("access_denied", "当前操作权限被拒绝。");
		str_res.backdoorPut("invalid_param", "参数错误，请确定您填写的数据正确。");
		str_res.backdoorPut("internal_error", "内部错误。");
	}
	
	// 获得此 action 此命令的格式化字符串键，其对应到 'strings.property' 文件中。
	// 例如对 SiteAction.save 命令返回为 'site.save.result'
	private String getPatternKey(ActionResult result) {
		if (action_command == null)
			return getObjectName() + "." + result.getCode();
		else
			return getObjectName() + "." + action_command.getCommand() + "." + result.getCode();
	}
	
	// 得到此 Action 操作的对象名字，如 'site', 'channel'
	protected String getObjectName() {
		// 如 'SiteAction'
		String object_name = this.getClass().getSimpleName();
		// 去掉后面的 'Action'
		if (object_name.endsWith("Action"))
			object_name = object_name.substring(0, object_name.length() - "Action".length());
		// uncapFirst
		return StringHelper.uncapFirst(object_name);
	}
	
	// === 辅助类 =======================================================================
	
	/** 成功 - 会记录日志。 = 'success'  */
	public static final ActionResult SUCCESS = new ActionResult(ActionResult.SUCCESS, null, true);
	
	/** 一般性失败。 = 'fail' */
	public static final ActionResult FAIL = new ActionResult("fail");
	
	/** 未知命令，不支持的命令 = 'unknown' */
	public static final ActionResult UNKNOWN_COMMAND = new ActionResult("unknown");
	
	/** 未登录。 */
	public static final ActionResult NOT_LOGIN = new ActionResult("not_login", null, true);
	
	/** 未知。 */
	public static final ActionResult UNKNOWN = new ActionResult("unknown");
	
	/** 没有此命令。 */
	public static final ActionResult NO_SUCH_METHOD = new ActionResult("no_such_method");
	
	/** 缺省权限被拒绝的结果 - 会记录日志。 */
	public static final ActionResult ACCESS_DENIED = new ActionResult("access_denied", null, true);
	
	/** 参数不合法。 */
	public static final ActionResult INVALID_PARAM = new ActionResult("invalid_param");
	
	/** 频道参数不合法。 */
	public static final ActionResult INVALID_CHANNEL = new ActionResult("invalid_channel");
	
	/* internal_error - 一般带有 exception 信息。 */
	public static final ActionResult INTERNAL_ERROR = new ActionResult("internal_error");
	
	/**
	 * 运行结果。
	 * @author liujunxing
	 *
	 */
	protected static final class ActionResult {
		public static final String SUCCESS = "success";
		/** 状态字符串。 */
		private final String code;
		/** 如果是异常，则表示异常值。 */
		private final Exception ex;
		/** 是否写入日志。 */
		private final boolean write_log;
		/** 格式化字符串时候使用的参数。 */
		private final Object[] params;
		public ActionResult(String code) {
			this.code = code;
			this.ex = null;
			this.write_log = false;
			this.params = null;
		}
		
		public ActionResult(String code, Exception ex) {
			this.code = code;
			this.ex = ex;
			this.write_log = false;
			this.params = null;
		}

		public ActionResult(String code, Exception ex, boolean write_log) {
			this.code = code;
			this.ex = ex;
			this.write_log = write_log;
			this.params = null;
		}

		public ActionResult(String code, Exception ex, boolean write_log, Object param1) {
			this.code = code;
			this.ex = ex;
			this.write_log = write_log;
			this.params = new Object[] {param1};
		}

		public ActionResult(String code, Exception ex, boolean write_log, Object param1, Object param2) {
			this.code = code;
			this.ex = ex;
			this.write_log = write_log;
			this.params = new Object[] {param1, param2};
		}

		public ActionResult(String code, Exception ex, boolean write_log, Object param1, Object param2, Object param3) {
			this.code = code;
			this.ex = ex;
			this.write_log = write_log;
			this.params = new Object[] {param1, param2, param3};
		}

		public ActionResult(String code, Exception ex, boolean write_log, Object[] params) {
			this.code = code;
			this.ex = ex;
			this.write_log = write_log;
			this.params = params;
		}

		public int getStatus() {
			if ("success".equals(code)) return 0;
			if ("access_denied".equals(code)) return Log.STATUS_ACCESS_DENIED;
			return Log.STATUS_FAIL;
		}
		
		public String getCode() { return this.code; }
		
		public Exception getException() { return this.ex; }
		
		public boolean getWriteLog() { return this.write_log; }
		
		public Object[] getParams() { return this.params; }
	
		// 使用 pattern, params, action_command 格式化字符串。
		public String formatString(String pattern, ActionCommand action_command) {
			try {
				if (params != null)
					return MessageFormat.format(pattern, params);
				else
					return MessageFormat.format(pattern, new Object[]{action_command.getCommand()});
			} catch (IllegalArgumentException ex) {
				return pattern;
			}
		}
	}
	
	// === 命令处理 ===================================================================
	
	public static final ActionCommand EMPTY_COMMAND = new ActionCommand("");
	
	/**
	 * 定义一个命令对象描述，用于产生 message 和 log。
	 */
	protected static final class ActionCommand {
		/** 要执行的命令，如 'save', 'delete'。 */
		private final String command;
		
		/**
		 * 构造。
		 * @param command
		 */
		public ActionCommand(String command) {
			this.command = command;
		}
		@Override public String toString() { return this.command; }
		public String getCommand() { return this.command; }
	}
	
	// === 辅助函数 =====================================================================
	
	/**
	 * 添加一条消息。
	 */
	protected void addMessage(String message) {
		this.messages.add(message);
	}

	/**
	 * 使用指定的 Object[] 数组对象创建一个 success 的 ActionResult. 
	 * @param params
	 * @return
	 */
	protected ActionResult success(Object... params) {
		return new ActionResult(ActionResult.SUCCESS, null, true, params);
	}

	/**
	 * 使用指定的 Object[] 数组对象创建一个一般的 ActionResult, 记录日志，没有异常。
	 * 相当于 new ActionResult(code, null, true, params);
	 * @param code
	 * @param params
	 * @return
	 */
	protected ActionResult access_denied(String code, Object... params) {
		if (code == null || code.length() == 0) code = "access_denied";
		return new ActionResult(code, null, true, params);
	}
	
	/**
	 * 使用指定的 Object[] 数组对象创建一个 INVALID_PARAM 的 ActionResult, 不记录日志，没有异常。
	 * 相当于 new ActionResult(code, null, false, params);
	 * @param code
	 * @param params
	 * @return
	 */
	protected ActionResult invalid_param(Object... params) {
		return new ActionResult("invalid_param", null, false, params);
	}
	
	/**
	 * 使用指定的 Object[] 数组对象创建一个一般的 ActionResult, 不记录日志，没有异常。
	 * 相当于 new ActionResult(code, null, false, params);
	 * @param code
	 * @param params
	 * @return
	 */
	protected ActionResult result(String code, Object... params) {
		return new ActionResult(code, null, false, params);
	}

	/**
	 * 返回 false 并设置 action_result = result;
	 * @param result
	 * @return
	 */
	protected boolean false_with_result(ActionResult result) {
		this.action_result = result;
		return false;
	}
}
