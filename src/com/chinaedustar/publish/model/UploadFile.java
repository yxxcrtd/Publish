package com.chinaedustar.publish.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;

/**
 * 上传文件
 * @author dengxiaolong
 *
 */
public class UploadFile {
	private DirNode curDir;
	private int channelId;			//频道ID
	private String rootPath;		//包括网站所在地址和当前频道下载目录地址
	private String webPath;		//仅包括当前频道下载目录地址
	private String curPath;
	private DirNode tree;
	private StringBuffer strJS;	
	private UpFileCollection upFilelist;		//上传文件集合
	private HashMap<String,Integer> userfulFiles;		//有用的文件列表
	private boolean selectFile;			//是否为选择文件的界面
	//TODO 把有用的文件列表缓存起来，现在的速度太慢了
	private PageContext pageContext;
	private String treeName;		//JSTree的名称
	
	private long fileTotalSize=0;	//文件总大小
	
	public static final int SORT_BY_NAME=0;
	public static final int SORT_BY_SIZE=1;
	public static final int SORT_BY_TYPE=2;
	public static final int SORT_BY_LASTMODIFYDATE=3;
	
	private final float M2BYTE=1048576;
	
	/**
	 * 得到跟目录路径
	 * @return
	 */
	public String getRootPath(){
		return this.rootPath;
	}
	/**
	 * 得到当前目录的web路径
	 * @return
	 */
	public String getWebPath(){
		return this.webPath;
	}

	/**
	 * 频道ID
	 */
	public int getChannelId(){
		return this.channelId;
	}
	/**
	 * 当前路径
	 */
	public String getCurPath(){
		return this.curPath;
	}
	/**
	 * 是否选择文件
	 */
	public boolean getSelectFile(){
		return this.selectFile;
	}
	
	
	/**
	 * 
	 * @param channelId
	 * @param pageContext
	 */	
	public UploadFile(PageContext pageContext){
		this.pageContext=pageContext;
		ParamUtil putil=new ParamUtil(pageContext);
		
		this.channelId=putil.safeGetIntParam("channelId",1);
		
		this.curPath = putil.safeGetChineseParameter("curPath");
		
		Channel channel=putil.getPublishContext().getSite().getChannels().getChannel(this.channelId);
		
		if(channel!=null){
			this.rootPath=pageContext.getServletContext().getRealPath("/")
				+ channel.getChannelDir() + "\\" + channel.getUploadDir();
			//如果上传目录不存在，则予以创建。
			if (!new File(this.rootPath).exists()) {
				new File(this.rootPath).mkdir();
			}
			
			this.webPath=((HttpServletRequest)this.pageContext.getRequest()).getContextPath() + "/" + channel.getChannelDir()
				+ "/" + channel.getUploadDir() + this.curPath.replace("\\", "/");
			this.treeName=channel.getUploadDir();
			this.upFilelist=channel.getUpFileCollection();
		}
		
		this.selectFile=putil.safeGetBooleanParam("selectFile",false);
	}	
	
	/**
	 * 得到数据表格
	 * @param fileList
	 * @return
	 */
	public ArrayList<ArrayList<FileNode>> getFileTable(ArrayList<FileNode> fileList){
		ArrayList<ArrayList<FileNode>> table=new ArrayList<ArrayList<FileNode>>();
		
		for(int i=0;i<fileList.size();i+=4){			
			ArrayList<FileNode> row=new ArrayList<FileNode>();
			for(int j=i;j<i+4 && j<fileList.size();j++){
				row.add(fileList.get(j));
			}
			table.add(row);
		}		
		return table;
	}
	
	public DirNode buildTree(){
		return buildTree(false);
	}
	/**
	 * 创建对应频道上传目录的树结构
	 * @param buildFile 是否创建文件节点
	 * @return
	 */
	public DirNode buildTree(boolean buildFile){
		if(this.tree==null)this.tree=new DirNode();
		
		this.tree=buildTreeNode(null,this.rootPath,0,true,buildFile);
		return this.tree;
	}
		
	/**
	 * 创建目录树节点
	 * @param parent 父节点
	 * @param path 文件夹路径
	 * @param index 索引，用来分配ID
	 * @param isRoot 是否根节点
	 * @param buildFile 是否创建文件节点
	 * @return
	 */
	private DirNode buildTreeNode(DirNode parent,String path,int index,boolean isRoot,boolean buildFile){
		File dir=new File(path);
		if(!dir.exists())return null;
		
		DirNode node=new DirNode();
		if(parent!=null)
			// TODO: ??? 这样分配的ID能够使用吗？它和数据库中的ID一致吗？
			node.setId(parent.getId() + index);
		else
			node.setId("tree");
		node.setName(dir.getName());
		node.setPath(path.substring(this.rootPath.length()).replace("\\", "\\\\"));
		node.setUrl("admin_upload_list.jsp" + "?channelId=" + this.channelId
				+ "&curPath=" + node.getPath());
		node.setRoot(isRoot);
		node.setLastModifyDate(new Date(dir.lastModified()));
		node.setParent(parent);		
		
		File[] files=dir.listFiles();
		if(files.length==0)return node;
		
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				node.addChildNode(buildTreeNode(node, files[i].getPath(), i, false,buildFile));
			}else if(buildFile && files[i].isFile()){
				node.addChildNode(buildFileNode(files[i]));
			}
		}
		return node;
	}
	
	/**
	 * 创建文件节点
	 * @param file 文件
	 * @return
	 */
	private FileNode buildFileNode(File file){
		createUserfulFiles();
		
		FileNode node=new FileNode();		
		node.setName(file.getName());
		node.setLastModifyDate(new Date(file.lastModified()));
		node.setSize(file.length());
		node.setFormat(node.getName().substring(node.getName().lastIndexOf(".")+1).toLowerCase());
		node.setType(getFileType(node.getFormat()));
		node.setCanPreview(canPreview(node.getFormat()));
		if(!node.getCanPreview()){
			node.setThumbPic(getThumbPic(node.getFormat()));
		}
		String path=this.webPath + "/" + node.getName();
		Integer id=this.userfulFiles.get(path);		
		if(id!=null){
			node.setUserful(true);
			node.setId(id.intValue());
		}else{
			node.setUserful(false);
			node.setId(-1);
		}		
		return node;
	}
	//TODO 还有些文件类型没有解释
	/**
	 * 得到文件的类型
	 * @param format
	 * @return
	 */
	private String getFileType(String format){
		format=format.toLowerCase();
		if(format.equals("gif")){
			return "GIF 图像";
		}else if(format.equals("jpg")){
			return "JPG 图像";
		}else if(format.equals("bmp")){
			return "BMP 图像";
		}else if(format.equals("png")){
			return "PNG 图像";
		}else if(format.equals("swf")){
			return "FLASH 文件";
		}else if(format.equals("mp3")){
			return "MP3 音频";
		}else if(format.equals("rm")){
			return "RM 媒体文件";
		}
		return "未知";
	}
	/**
	 * 检查文件是否能预览
	 * @param format
	 * @return
	 */
	private boolean canPreview(String format){
		return (format.equals("gif") || format.equals("jpg") || format.equals("png")
				|| format.equals("jpeg") || format.equals("bmp"));			
	}
	/**
	 * 得到缩略图
	 * @param format
	 * @return
	 */
	//TODO 分类有待进一步细化
	private String getThumbPic(String format){		
		if(format.equals("swf"))return "flash";
		if(format.equals("mp3") || format.equals("wmv") || format.equals("mid")
				|| format.equals("asf") || format.equals("asf") || format.equals("avi") || format.equals("mpg")
				|| format.equals("ram") || format.equals("rm") || format.equals("ra") || format.endsWith("rmvb"))
			return "media";
		if(format.equals("zip") || format.equals("rar"))
			return "zip";
		if(format.equals("exe") || format.equals("jar"))
			return "exe";
		if(format.equals("doc") || format.equals("xls") || format.equals("ppt") || format.equals("pdf"))
			return "doc";
		return "other";
	}
	
	/**
	 * 得到JS字符串
	 * @return
	 */
	public String getJSString(){
		if(this.strJS==null){
			this.strJS=new StringBuffer();
			getJSNode(buildTree());			
		}
		return this.strJS.toString();
	}
	
	private void getJSNode(DirNode node){
		if(node.getIsRoot()){
			strJS.append("var " + node.getId() + "=new JTree('" + this.treeName + "');\n");
			strJS.append(node.getId() + ".init('root');\n");
			if(node.getHasDir()){
				for(int i=0;i<node.getDirChildNodes().size();i++){
					getJSNode(node.getDirChildNodes().get(i));
				}
			}
			strJS.append(node.getId() + ".format();\n");
		}else{
			if(node.getHasDir()){
				strJS.append("var " + node.getId() + "=new JTreeNode(");
				strJS.append("'" + node.getName() + "','" + node.getUrl() + "'" );
				strJS.append(");\n");
				if(node.getHasParent()){
					strJS.append(node.parent.getId() + ".addChild(" + node.getId() + ");\n");
				}
				
				for(int i=0;i<node.getDirChildNodes().size();i++){
					getJSNode(node.getDirChildNodes().get(i));
				}
			}else{
				if(node.getHasParent()){
					strJS.append(node.parent.getId() + ".addChild(");
					strJS.append("new JTreeNode(" + "'" + node.getName() + "','" + node.getUrl() + "')");
					strJS.append(");\n");
				}
			}
		}
	}
	
	/**
	 * 找到当前文件夹所有的文件 
	 * @return 
	 */
	public DirNode getCurDir(String fileType){
		if(this.curDir==null){
			this.curDir=new DirNode();
			
			File dir=new File(this.rootPath + this.curPath);
			if(!dir.exists())return null;			
			
			this.curDir.setPath(this.curPath);
			this.curDir.setName(dir.getName());
			this.curDir.setLastModifyDate(new Date(dir.lastModified()));
			
			if(this.curPath.equals(""))this.curDir.setRoot(true);
			
			File[] files=dir.listFiles();
			if(files!=null){
				for(int i=0;i<files.length;i++){
					if(files[i].isDirectory()){
						DirNode node=new DirNode();
						node.setName(files[i].getName());
						node.setLastModifyDate(new Date(files[i].lastModified()));
						try {
							node.setPath(URLEncoder.encode(this.curPath + "\\" + node.getName(), "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							node.setPath(this.curPath + "\\" + node.getName());
						}
						this.curDir.addChildNode(node);
					}else if(files[i].isFile() && matchType(files[i].getName(),fileType)){
						this.curDir.addChildNode(buildFileNode(files[i]));
						this.fileTotalSize+=files[i].length();
					}
				}
			}
			
		}
		return this.curDir;
	}
	public DirNode getCurDir(){
		return getCurDir("");
	}	
	/**是否与文件种类匹配*/
	private boolean matchType(String fileName,String fileType){
		if(fileType.equals(""))return true;
		else return fileName.endsWith(fileType);
	}
	
	
	/**
	 * 得到上一级目录
	 * @return
	 */
	public String getParentDir(){
		if(this.curPath.equals(""))return "";
		else return this.curPath.substring(0,this.curPath.lastIndexOf("\\"));
	}
	public ArrayList<FileNode> sortFile(int sortBy,boolean isPositive){
		return sortFile(sortBy, isPositive,this.curDir.getFileChildNodes());
	}
	
	/**
	 * 对文件排序
	 * @param 按什么排序
	 * @param 是否正序
	 * @param 文件列表
	 */
	public ArrayList<FileNode> sortFile(int sortBy,boolean isPositive,ArrayList<FileNode> fileList){
		getCurDir();
		switch(sortBy){
		case SORT_BY_NAME:
			return sortFileByName(isPositive,fileList);
		case SORT_BY_SIZE:
			return sortFileBySize(isPositive,fileList);
		case SORT_BY_TYPE:
			return sortFileByType(isPositive,fileList);
		case SORT_BY_LASTMODIFYDATE:
			return sortFileByLastModified(isPositive,fileList);
		default:
			return this.curDir.getFileChildNodes();
		}		
	}
	/**
	 * 按文件名排序
	 * @param isPositive
	 * @return
	 */
	private ArrayList<FileNode> sortFileByName(boolean isPositive,ArrayList<FileNode> fileList){		
		if(isPositive)return fileList;	
		ArrayList<FileNode> list=new ArrayList<FileNode>();
		for(int i=fileList.size()-1;i>=0;i--){
			list.add(fileList.get(i));
		}		
		return list;
	}
	/**
	 * 按文件大小排序
	 * @param isPositive
	 * @return
	 */
	private ArrayList<FileNode> sortFileBySize(boolean isPositive,ArrayList<FileNode> fileList){		
		if(isPositive){
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getSize()>fileList.get(j).getSize()){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}else{
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getSize()<fileList.get(j).getSize()){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}
		return fileList;
	}	
	/**
	 * 按文件类型排序
	 * @param isPositive
	 * @return
	 */
	private ArrayList<FileNode> sortFileByType(boolean isPositive,ArrayList<FileNode> fileList){	
		if(isPositive){
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getType().compareTo(fileList.get(j).getType())>0){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}else{
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getType().compareTo(fileList.get(j).getType())<0){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}
		return fileList;
	}	
	
	/**
	 * 按文件类型排序
	 * @param isPositive
	 * @return
	 */
	private ArrayList<FileNode> sortFileByLastModified(boolean isPositive,ArrayList<FileNode> fileList){		
		if(isPositive){
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getLastModifyDate().compareTo(fileList.get(j).getLastModifyDate())>0){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}else{
			for(int i=0;i<fileList.size()-1;i++){
				for(int j=i+1;j<fileList.size();j++){
					if(fileList.get(i).getLastModifyDate().compareTo(fileList.get(j).getLastModifyDate())<0){
						FileNode tmpNode=fileList.get(i);
						fileList.set(i, fileList.get(j));
						fileList.set(j, tmpNode);
					}
				}
			}
		}
		return fileList;
	}
	/**
	 * 删除文件
	 * @param 文件名称
	 */
	private void deleteFile(String fileName){
		File file=new File(this.rootPath + this.curPath + "\\" + fileName);
		if(file.exists() && file.isFile()){
			file.delete();
			this.upFilelist.delete(this.webPath + fileName);
		}
	}
	public void deleteFile(String[] fileName){
		for(int i=0;i<fileName.length;i++){			
			deleteFile(fileName[i]);
		}
	}
	/**
	 * 删除目录及其内部所有文件
	 * @param 目录名称
	 */
	public void deleteDir(String dirName){
		File dir=new File(this.rootPath + this.curPath + "\\" + dirName);
		File[] files=dir.listFiles();
		if(files==null || files.length==0){
			dir.delete();
		}else{
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					deleteDir(dirName + "\\" + files[i].getName());
				}else if(files[i].isFile()){
					deleteFile(dirName + files[i].getName());
				}
			}
		}
	}
	/**
	 * 删除当前目录所有文件
	 *
	 */
	public void deleteCurDirFiles(){
		File[] files=new File(this.rootPath + this.curPath).listFiles();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				if(files[i].isFile())files[i].delete();
			}
		}
	}
	/**
	 * 生成有用的文件列表
	 *
	 */
	@SuppressWarnings("unchecked")
	private void createUserfulFiles(){		
		if(this.userfulFiles!=null)return;
		
		this.userfulFiles=new HashMap<String, Integer>();		
		String hql="Select a.uploadFiles from Article a where a.channelId="
			+ this.channelId + " and a.uploadFiles!=null";
		PublishContext pub_ctxt=PublishUtil.getPublishContext(this.pageContext);
		ArrayList<String> list=(ArrayList<String>)pub_ctxt.getDao().list(hql);
		
		
		Set<Integer> set=new HashSet<Integer>();
		for(int i=0;i<list.size();i++){
			if(list.get(i).trim().equals(""))continue;
			String[] ids=list.get(i).split("\\|");
			for(int j = 0; j < ids.length; j++){
				set.add(Integer.parseInt(ids[j]));
			}
		}		
		List<UpFile> upList=this.upFilelist.loadFiles(this.webPath + "/");
		if(upList==null)return;
		
		Iterator<Integer> itor=set.iterator();
		while(itor.hasNext()){
			Integer id=itor.next();
			for(int i=0;i<upList.size();i++){
				if(upList.get(i).getId()==id.intValue()){
					this.userfulFiles.put(upList.get(i).getFilePath(),id);
					break;
				}
			}
		}
	}
	/**
	 * 清除无用的文件
	 * @return 清楚文件的个数
	 */
	public int  clearUsernessFiles(){
		buildTree(true);
		
		return deleteUsernessNode(this.tree);
	}
	
	private int deleteUsernessNode(DirNode node){
		int count=0;
		if(node.getHasFile()){
			List<FileNode> fileList=node.getFileChildNodes();
			for(int i=0;i<fileList.size();i++){
				if(!fileList.get(i).getIsUseful()){
					count++;
					new File(this.rootPath + node.getPath() + "\\" + fileList.get(i).getName()).delete();
				}
			}
		}
		
		if(node.getHasDir()){
			List<DirNode> dirList=node.getDirChildNodes();
			for(int i=0;i<dirList.size();i++){
				count +=deleteUsernessNode(dirList.get(i));
			}
		}
		
		return count;
	}
	
	/**
	 * 得到某页的文件
	 * @param 页数
	 * @param 每页显示的个数
	 * @return
	 */
	public PageNode getPageFiles(int page,int perPageCount){
		getCurDir();
		if(!this.curDir.getHasFile())return null;
		PageNode node=new PageNode();
		ArrayList<FileNode> fileList=this.curDir.getFileChildNodes();
		node.setFileTotalCount(fileList.size());
		node.setFileTotalSize((float)(this.fileTotalSize/M2BYTE));
		node.setPerPageCount(perPageCount);	
		if(fileList.size() % perPageCount==0){
			node.setPageCount(fileList.size() / perPageCount);
		}else{
			node.setPageCount(((int)fileList.size() / perPageCount)+1);
		}
		
		if(page<1)page=1;
		if(page>node.getPageCount())page=node.getPageCount();		
		node.setCurPage(page);		

		long fileSize=0;
		
		for(int i=(node.getCurPage()-1)*perPageCount;i<node.getFileTotalCount() && i<node.getCurPage()*perPageCount;i++){
			node.addFile(fileList.get(i));
			fileSize+=fileList.get(i).getSize();
		}
		node.setCurFileSize((float)(fileSize/M2BYTE));
		
		return node;
	}
	
	/**
	 * 目录节点
	 *
	 */
	public class DirNode{
		private ArrayList<DirNode> dirChildNodes=new ArrayList<DirNode>();
		private ArrayList<FileNode> fileChildNodes=new ArrayList<FileNode>();
		private DirNode parent=null;
		private String id;		//构建树时的变量名称
		private String url;		//构建树时的链接地址
		private String path;
		private String name;
		private String lastModifyDate;
		private boolean root=false;
		
		public String getId(){
			return this.id;
		}
		public void setId(String id){
			this.id=id;
		}
		
		public String getUrl(){
			return this.url;			
		}
		public void setUrl(String path){
			this.url=path;
		}
		public String getName(){
			return this.name;
		}
		public void setName(String name){
			this.name=name;
		}
		/**是否为根节点*/
		public boolean getIsRoot() {
			return root;
		}
		public void setRoot(boolean isRoot) {
			this.root = isRoot;
		}
		
		/**得到父节点*/
		public DirNode getParent() {
			return this.parent;
		}
		
		public void setParent(DirNode parent){
			this.parent=parent;
		}
		
		public ArrayList<DirNode> getDirChildNodes(){
			return this.dirChildNodes;
		}
		
		public ArrayList<FileNode> getFileChildNodes(){
			return this.fileChildNodes;
		}
		
		public String getLastModifyDate() {
			return lastModifyDate;
		}
		public void setLastModifyDate(Date lastModifyDate) {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");			
			this.lastModifyDate = format.format(lastModifyDate);
		}	
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		/**
		 * 添加文件夹子节点
		 * @param node
		 */
		public void addChildNode(DirNode node){
			this.dirChildNodes.add(node);
		}
		/**
		 * 添加文件子节点
		 * @param node
		 */
		public void addChildNode(FileNode node){
			this.fileChildNodes.add(node);
		}
		
		/**
		 * 是否有子文件夹
		 * @return
		 */
		public boolean getHasDir(){
			return !this.dirChildNodes.isEmpty();
		}	
		
		
		/**
		 * 是否有父节点
		 * @return
		 */
		public boolean getHasParent(){
			return this.parent!=null;
		}
		/**
		 * 是否有文件
		 * @return
		 */
		public boolean getHasFile(){
			return !this.fileChildNodes.isEmpty();			
		}
					
	}
	/**
	 * 文件节点
	 * @author dengxiaolong
	 *
	 */
	public class FileNode{		
		private int id;					//文件ID
		private String name;			//名称
		private String lastModifyDate;	//最后修改时间
		private long size;				//大小
		private String stringSize;		//格式化了的大小
		private String type;			//类型
		private String kSize;			//多少K
		private String format;			//后缀
		private boolean userful;		//是否有用
		private boolean canPreview;		//能否预览
		private String thumbPic;		//缩略图
		
		public String getLastModifyDate() {
			return lastModifyDate;
		}
		public void setLastModifyDate(Date lastModifyDate) {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");			
			this.lastModifyDate = format.format(lastModifyDate);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
			this.stringSize=new DecimalFormat("###,###").format(size);
			this.kSize=new DecimalFormat("0").format(this.size/1024);
		}
		public String getKSize(){
			return kSize;
		}
		public String getType() {
			return type;
		}
		public String getStringSize(){
			return this.stringSize;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {			
			this.format = format;
		}
		public boolean getIsUseful() {
			return userful;
		}
		public void setUserful(boolean userful) {
			this.userful = userful;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public boolean getCanPreview() {
			return canPreview;
		}
		public void setCanPreview(boolean canPreview) {
			this.canPreview = canPreview;
		}
		public String getThumbPic() {
			return thumbPic;
		}
		public void setThumbPic(String thumbPic) {
			this.thumbPic = thumbPic;
		}
	}
	/**
	 * 文件分页类型
	 *
	 */
	public class PageNode{
		private int fileTotalCount;		//文件总个数
		private String fileTotalSize;	//文件总大小
		private int pageCount;			//总页数
		private int curPage;			//当前页数
		private int perPageCount;		//每页显示文件数
		private String curFileSize;		//当前页文件总大小
		private ArrayList<FileNode> fileList=new ArrayList<FileNode>();	//当前页的文件列表
		//文件总数
		public int getCurFileCount() {
			return this.fileList.size();
		}
		public String getCurFileSize() {
			return this.curFileSize;
		}
		public void setCurFileSize(float curFileSize) {
			this.curFileSize =new DecimalFormat("0.00").format(curFileSize);
		}
		public int getCurPage() {
			return curPage;
		}
		public void setCurPage(int curPage) {
			this.curPage = curPage;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
		public int getPerPageCount() {
			return perPageCount;
		}
		public void setPerPageCount(int perPageCount) {
			this.perPageCount = perPageCount;
		}
		
		//是否为第一页
		public boolean getIsFirstPage() {
			return this.curPage==1;
		}
		//是否为最后一页
		public boolean getIsLastPage() {
			return this.curPage==this.pageCount;
		}
		public ArrayList<FileNode> getFileList() {
			return fileList;
		}
		//添加文件
		public void addFile(FileNode node){
			this.fileList.add(node);
		}
		public int getFileTotalCount() {
			return fileTotalCount;
		}
		public void setFileTotalCount(int fileTotalCount) {
			this.fileTotalCount = fileTotalCount;
		}
		public String getFileTotalSize() {
			return fileTotalSize;
		}
		public void setFileTotalSize(float fileTotalSize) {
			this.fileTotalSize = new DecimalFormat("0.00").format(fileTotalSize);
		}
	}
}
