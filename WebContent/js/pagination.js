/**
 * @author 		comdeng
 */

/**
 * 用来存取分页信息的结构
 */
function PaginationInfo() {};
PaginationInfo.prototype = {
	totalCount:0,
	totalPage:0,
	pageSize:20,
	currPage:1,
	firstPageUrl:"",
	url:"",
	itemName:"文章",
	itemUnit:"篇",
	isStatic:false
}
/**
 * 分页类
 */
function ShowPagination(info) {
	var str_pagination = '\
		共 <b>' + info.totalCount + '</b> ' + info.itemUnit + info.itemName + '\
		&nbsp;' + (info.currPage > 1 ? "<a href='" + (info.firstPageUrl) + "'>首页</a>" : "首页") + '\
		&nbsp;' + (info.currPage > 1 ? "<a href='" 
			+ info.url.replace("{page}", info.isStatic ? info.totalCount - info.currPage + 2 : parseInt(info.currPage) - 1) + "'>上一页</a>" : "上一页") + '\
		&nbsp;' + (info.currPage < info.totalPage ? "<a href='" 
			+ info.url.replace("{page}", info.isStatic ? info.totalCount - info.currPage : parseInt(info.currPage, 10) + 1) + "'>下一页</a>" : "下一页") + '\
		&nbsp;' + (info.currPage < info.totalPage ? "<a href='" 
			+ info.url.replace("{page}", info.isStatic ? 1 : info.totalPage) + "'>尾页</a>" : "尾页") + '\
		&nbsp;页次：<strong><font color=red>' + info.currPage + '</font>/' + info.totalCount + '</strong>页\
		&nbsp;<b>' + info.pageSize + '</b>' + info.itemUnit + info.itemName + '/页\
		&nbsp;转到：\
		<select name="page" size="1" onchange="window.location=this.value">\
		<option value="' + info.firstPageUrl + '"' + (info.currPage == 1 ? "selected" : "") + '>第1页</option>\
		';
		
	for (var i = 2; i < info.totalPage; i++) {
		var page = i;
		if (info.isStatic) {
			page = parseInt(info.totalPage) - parseInt(info.currPage) + 1;
		}
		var url = info.url.replace("{page}", page);
		str_pagination += '<option value="' + url + '" '
			+ (info.currPage == i ? "selected" : "") + '>第' + i + '页</option>';
	}
	if (info.totalPage > 1) {
		str_pagination += '<option value="' + info.lastPageUrl + '" '
			+ (info.currPage == info.totalPage ? "selected" : "") + '>第' + info.totalPage + '页</option>';
	}
	str_pagination += '</select>';
	document.write(str_pagination);
}


