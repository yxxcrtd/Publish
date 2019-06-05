package com.chinaedustar.publish.action;

import com.chinaedustar.publish.model.Label;
import com.chinaedustar.publish.model.LabelCollection;

/**
 * 标签管理操作集合。
 * 
 * @author liujunxing
 *
 */
public class LabelAction extends AbstractAction {
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.action.AbstractAction#execute()
	 */
	@Override public void execute() throws Exception {
		String command = param_util.safeGetStringParam("command");
		if ("save".equalsIgnoreCase(command)) 
			save();
		else if ("delete".equalsIgnoreCase(command))
			delete();
		else
			unknownCommand(command);
	}
	
	/** 新建或更新一个标签。 */
	private void save() {
		// 收集数据。
		Label label = collectLabel();
		
		// 执行操作。
		LabelCollection label_coll = pub_ctxt.getSite().getLabelCollection();
		if (label.getId() == 0)
			pub_ctxt.getTransactionProxy().insertLabel(label_coll, label);
		else
			pub_ctxt.getTransactionProxy().updateLabel(label_coll, label);
		
		// 提示信息。
		messages.add("创建或更新标签成功完成。");
		links.add(getBackActionLink());
	}
	
	/** 删除指定标签。 */
	private void delete() {
		int labelId = param_util.safeGetIntParam("labelId");
		
		// 执行操作，也许以后我们应该提供更丰富的操作信息。
		LabelCollection label_coll = pub_ctxt.getSite().getLabelCollection();
		pub_ctxt.getTransactionProxy().deleteLabel(label_coll, labelId);
		
		messages.add("删除成功。");
		links.add(getBackActionLink());
	}
	
	// 从提交的标单中收集数据。
	private Label collectLabel() {
		int id = param_util.safeGetIntParam("labelId");
		String name = param_util.safeGetStringParam("name");
		int labelType = param_util.safeGetIntParam("labelType");
		String description = param_util.safeGetStringParam("description");
		int priority = param_util.safeGetIntParam("priority");
		String content = param_util.safeGetStringParam("content");
		
		Label label = new Label();
		label.setId(id);
		label.setName(name);
		label.setLabelType(labelType);
		label.setDescription(description);
		label.setPriority(priority);
		label.setContent(content);

		return label;
	}

	// 获得返回的 ActionLink 对象。
	@Override protected ActionLink getBackActionLink() {
		return new ActionLink("返回", "admin_label_list.jsp");
	}
}

