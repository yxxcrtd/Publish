package com.chinaedustar.publish.admin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.chinaedustar.publish.PublishContext;

/**
 * 提供软件数据选项。选项保存在 admin_soft_params.xml 文件中。
 * 
 * @author liujunxing
 *
 */
public class SoftParamProvider {
	/**
	 * 获得软件参数数据。
	 * @param pub_ctxt - 发布系统环境，用来计算实际 xml 配置文件地址的。
	 * @return
	 */
	public Map<String, List<String>> getSoftParam(PublishContext pub_ctxt, int channelId) {
		// int channelId = new ParamUtil(web_ctxt.getPageContext()).safeGetIntParam("channelId");
		Map<String, List<String>> map = new HashMap<String, List<String>>();		
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			String fileName = pub_ctxt.getSite().resolvePath("admin/param/admin_soft_params");
			if (channelId > 0) {
				String tempFileName = fileName + "_" + channelId + ".xml";
				
				if (!new File(tempFileName).exists()) {
					fileName += ".xml";
				} else {
					fileName = tempFileName;
				}
			} else {
				fileName += ".xml";
			}
			
			Document document = builder.parse(new File(fileName));
			document.getDocumentElement().normalize();
			
			NodeList list = document.getFirstChild().getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String nodeName = node.getNodeName();
				System.out.println("nodeNode = " + nodeName);
				if ("type".equals(node.getNodeName())) {
					map.put("type", getSoftParam(node.getChildNodes()));
				} else if ("language".equals(node.getNodeName())) {
					map.put("language", getSoftParam(node.getChildNodes()));
				} else if ("copyright".equals(node.getNodeName())) {
					map.put("copyright", getSoftParam(node.getChildNodes()));
				} else if ("os".equals(node.getNodeName())) {
					map.put("os", getSoftParam(node.getChildNodes()));
				}
			}
		} catch (Exception ex) {
			 ex.printStackTrace();			
		}

		return map;
	}
	
	/**
	 * 获取软件的某一个参数
	 * @param nodeList
	 * @return
	 */
	private java.util.List<String> getSoftParam(NodeList nodeList) {
		java.util.List<String> list = new java.util.ArrayList<String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if ("option".equals(node.getNodeName())) {
				list.add(node.getFirstChild().getNodeValue());
			}
		}
		return list;
	}
}
