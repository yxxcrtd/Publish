/**
 * @author comdeng
 * @version {1.0}
 */

function JTree(treeName){
	this.treeName=treeName;
};
JTree.prototype={
	root:null,
	rootName:"",
	container:null,
	init:function(id){			//初始化
		if(typeof id=="string" && document.getElementById(id)){
			this.container=document.getElementById(id);
		}else if(typeof id=="object"){
			this.container=id;
		}else{
			this.container=document.createElement("div");
			document.body.appendChild(this.container);		
		}
		this.root=new JTreeNode();
		this.root.childContainerObj=this.container;		
		
		//添加根节点
		var div=document.createElement("div");
		var img=document.createElement("img");
		img.src="images/folder.gif";
		div.appendChild(img);
		div.appendChild(document.createTextNode(this.treeName));
		this.root.childContainerObj.appendChild(div);
	},
	format:function(nodes){		//格式化节点
		if(!nodes)nodes=this.root.childs;
		for(var i=0;i<nodes.length;i++){
			var position;
			if(nodes.length==1)position=1;
			else if(i==0) position=-1;
			else if(i==nodes.length-1) position=1;
			else position=0;
			
			this.creat(nodes[i],position);
		}
				
		this.container.style.lineHeight="14px";
		this.container.style.fontFamily="verdana";
		this.container.style.fontSize="12px";
		
		var images=this.container.getElementsByTagName("img");
		for(var i=0;i<images.length;i++){
			images[i].style.verticalAlign="middle";
		}
	},	
	addChild:function(node){
		this.root.addChild(node);
	},
	creat:function(node,position){
		node.level=node.parent.level+1;
		if(position==1)node.isLast=true;
		
		node.containerObj=document.createElement("div");
		node.linkObj=document.createElement("a");
		node.toggleObj=document.createElement("img");
		
		//添加左边的图片
		if(node.level>0){
			var nodeArray=[];
			var curNode=node.parent;
			while(curNode!=null){				
				nodeArray.push(curNode.isLast);
				curNode=curNode.parent;	
				if(curNode.isRoot())break;			
			}
			
			for(var i=nodeArray.length-1;i>=0;i--){
				var img=document.createElement("img");
				if(!nodeArray[i])
					img.src="images/I.gif";
				else
					img.src="images/blank.gif";	
				
				node.containerObj.appendChild(img);			
			}
		}			
		
		node.containerObj.appendChild(node.toggleObj);		
		
		//添加标志图片
		var img=document.createElement("img");
		img.src="images/folder.gif";
		node.containerObj.appendChild(img);
		
				
		//添加链接
		node.linkObj.appendChild(document.createTextNode(node.name));
		if(!node.doAjax)
			node.linkObj.href=node.url;
		else
			node.linkObj.href="javascript:void(0)";
		node.linkObj.target=node.target;
		node.containerObj.appendChild(node.linkObj);
		node.parent.childContainerObj.appendChild(node.containerObj);	
		//没有子节点也不用做Ajax操作时添加的开关图片
		if(!node.hasChild() && !node.doAjax){
			node.toggleObj.src=(position==1)?"images/foot.gif":"images/center.gif";			
			return;
		}
		//有子节点或有Ajax操作时添加开关图片	
		node.toggleObj.src=position==1?"images/footPlus.gif":"images/Plus.gif";	
		var base=this;
		node.toggleObj.onclick=function(){
			if(!node.hasChild()){
				var args=[base,node];
				for(var i=1;i<node.url.length;i++){
					args.push(node.url[i]);
				}
				var img=document.createElement("img");
				img.src="images/loading.gif";
				img.style.verticalAlign="middle";
				node.containerObj.insertBefore(img,node.containerObj.lastChild);
				node.url[0].apply(null,args);
			}
			if(node.isExpand){
				node.childContainerObj.style.display="none";
				node.isExpand=false;
				node.toggleObj.src=node.toggleObj.src.replace("Minus.gif","Plus.gif");
			}else{
				node.childContainerObj.style.display="";
				node.isExpand=true;
				node.toggleObj.src=node.toggleObj.src.replace("Plus.gif","Minus.gif");
			}
		}
		
		node.childContainerObj=document.createElement("div");
		node.childContainerObj.style.display="none";
		for(var i=0;i<node.childs.length;i++){
			var position;
			if(node.childs.length==1)position=1;
			else if(i==0) position=-1;
			else if(i==node.childs.length-1) position=1;
			else position=0;
			
			this.creat(node.childs[i],position);
		}
		node.containerObj.appendChild(node.childContainerObj);
	},
	getInnerHTML:function(){
		return this.root.childContainerObj.innerHTML;
	},
	outerCreate:function(node,data){				
		if(node.hasChild())return;		
		if(typeof data!="object"){
			throw new Error("要处理的数据必须为数组");
		}
		
		for(var i=0;i<data.length;i++){
			node.addChild(this.createTreeNode(data[i]));
		}
		this.format(node.childs);	
		node.containerObj.removeChild(node.containerObj.lastChild.previousSibling);	
	},
	createTreeNode:function(item){
		if(typeof item.childs=="undefined"){
			var node=new JTreeNode(item.name,item.url,item.target);
			if(typeof item.doAjax!="undefinded")node.doAjax=item.doAjax;
			return node;
		}else{
			var node=new JTreeNode(item.name,item.url,item.target);
			if(typeof item.doAjax!="undefinded")node.doAjax=item.doAjax;
			for(var i=0;i<item.childs.length;i++){
				node.addChild(this.createTreeNode(item.childs[i]));				
			}
			return node;
		}
	}
}
function JTreeNode(name,url,target){
	this.name=!name?"":name;
	this.url=!url?"javascript:void(0);":url;
	this.target=!target?"UploadFile_Main":target;
	this.childs=[];
};
JTreeNode.prototype={
	name:"",
	url:"",
	target:"",
	isExpand:false,
	isLast:false,
	linkObj:null,
	toggleObj:null,
	containerObj:null,
	childContainerObj:null,
	doAjax:false,
	parent:null,
	childs:null,
	level:-1,
	hasChild:function(){
		return this.childs.length>0;
	},
	isRoot:function(){
		return this.parent==null;
	},
	addChild:function(node){
		this.childs.push(node);
		node.parent=this;
	}
};