/**
 * @author 		dengxiaolong
 */
var BODY_ONLOAD_ARRAY = [];
if (window.attachEvent) {
	window.attachEvent("onload", loadEvent);
} else if (window.addEventListener) {
	window.addEventListener("load", loadEvent, false);
}

function loadEvent() {
	for (var i = 0; i < BODY_ONLOAD_ARRAY.length; i++) {
		if (typeof(BODY_ONLOAD_ARRAY[i]) == 'function') {
			BODY_ONLOAD_ARRAY[i].apply(null);
		} else {
			window[BODY_ONLOAD_ARRAY[i]].apply(null);
		}
	}
}

function addBodyOnloadEvent(event) {
	BODY_ONLOAD_ARRAY.push(event);
}
			
function PhotoInfo(){}
PhotoInfo.prototype = {
	/** 图像的地址 */
	photoUrl:"",
	/** 缩略图的地址 */
	thumbPicUrl:""
};

function StringBuffer(){
	this.buffer = [];
}
StringBuffer.prototype = {
	buffer:null,
	/**
	 * 添加字符串
	 * @param {String} str 字符串
	 */
	append:function(str) {
		this.buffer.push(str);
	},
	/**
	 * 转化为字符串。
	 */
	toString:function() {
		return this.buffer.join("");
	}
}

var PhotoUtil = {
	link:null,
	img:null,
	curIndex:0,
	slideInterval:null,
	thumbPhotoCurrPage:1,
	thumbPhotoTotalPage:1,
	thumbPhotoPageSize:5,
	thumbPhotoWidth:130,
	photoWidth:600,
	photoList:[],
	/**
	 * 实现对缩略图列表的缓存。
	 */
	cacheThumbList:{},
	/**
	 * 实现对缩略图分页的缓存。
	 */
	cacheThumbPagination:{},
	/**
	 * 初始化。
	 * @param {String} linkId 链接对象的ID。
	 * @param {String} imgId 图片对象的ID。
	 */
	init:function(linkId, imgId){
		this.link = document.getElementById(linkId);
		this.img = document.getElementById(imgId);
	},
	/**
	 * 添加图片。
	 * @param {String} url 图片的地址。
	 * @param {String} thumb 缩略图的地址。
	 */
	addPhoto:function(url, thumb) {
		var info = new PhotoInfo();
		info.photoUrl = url;
		info.thumbPicUrl = thumb;
		this.photoList.push(info);
	},
	/**
	 * 显示第一副图片
	 */
	showFirstPicture:function() {
		this.showPicture(0);
	},
	/**
	 * 开始幻灯显示
	 * @param {Number} second 每隔多少秒变换图片。
	 */
	startSlide:function(second) {
		try {
			second = parseFloat(second);
		} catch (e) {
			return;
		}
		var base = this;
		if (this.slideInterval) {
			window.clearInterval(this.slideInterval);
		}
		this.slideInterval = window.setInterval(function(){base.showNext.apply(base)}, parseInt(second * 1000));
	},
	/**
	 * 停止幻灯显示。
	 */
	stopSlide:function() {
		window.clearTimeout(this.slideInterval);
	},
	/**
	 * 显示下一副图片。
	 */
	showNext:function() {
		this.curIndex = (this.curIndex + 1) % this.photoList.length;
		this.showPicture(this.curIndex);
	},
	/**
	 * 显示图片
	 * @param {Object} index 图片的下标。
	 */
	showPicture:function(index) {
		if (this.photoList.length > 0 && index < this.photoList.length) {
			var img = new Image();
			img.src = this.photoList[index].photoUrl + "?rdm=" + Math.random();
			var base = this;
			if (img.width > 0) {	// 如果从缓存中获取了，则直接可以使用。
				this.link.href = this.img.src = img.src;
				if (img.width > this.photoWidth) {
					this.img.width = this.photoWidth;
					this.img.height = img.height * (this.photoWidth/img.width);
				} else {
					this.img.width = img.width;
					this.img.height = img.height;
				}
			} else {
				img.onload = function() {	// 如果没有从缓存中获取，则需要等待图片下载完全。
					base.link.href = base.img.src = this.src;
					if (this.width > base.photoWidth) {
						base.img.width = base.photoWidth;
						base.img.height = this.height * (base.photoWidth/this.width);
					} else {
						base.img.width = this.width;
						base.img.height = this.height;
					}
				}
				img.onerror = function() {
					base.link.href = this.src;
					base.img.src = "images/notfound.jpg";
				}
			}
			this.curIndex = index;
		}
	},
	/**
	 * 设置图片的宽度
	 * @param {Number} width 宽度
	 */
	setPhotoWidth:function(width) {
		if (typeof(width) == 'number') {
			width = parseInt(width, 10);
			if (width > 0) this.photoWidth = width;
		}
	},
	/**
	 * 设置缩略图的宽度
	 * @param {Number} width 宽度
	 */
	setThumbPhotoWidth:function(width) {
		if (typeof(width) == 'number') {
			width = parseInt(width, 10);
			if (width > 0) this.thumbPhotoWidth = width;
		}
	},
	/**
	 * 显示缩略图列表
	 * @param {String} 用来显示缩略图列表的容器的ID。
	 * @param {Number} maxPerPage 每页最多显示多少个缩略图
	 */
	showThumbList:function(objId, maxPerPage) {
		var obj = document.getElementById(objId);
		if (!obj) return;
		if (!maxPerPage) {
			maxPerPage = 5;
		} else {
			try {
				maxPerPage = parseInt(maxPerPage, 10);
			} catch(e) {
				maxPerPage = 5;
			}
		}
		if (maxPerPage <= 0) {
			maxPerPage = 5;
		}
		this.thumbPhotoPageSize = maxPerPage;
		if (this.photoList.length > 0) {
			this.thumbPhotoTotalPage = parseInt(this.photoList.length / maxPerPage);
			if (this.photoList.length % maxPerPage > 0) {
				this.thumbPhotoTotalPage++;
			}
			var sb = new StringBuffer();
			sb.append('<table border="0" cellpadding="2" cellspacing="2">');
			sb.append('<tbody id="_Thumb_Photo"></tbody>');			
			
			if (this.photoList.length > maxPerPage) {
				sb.append('<tbody><tr>');
				sb.append('<td id="_Thumb_Pagination" colspan="' + maxPerPage + '" align="center"></td>');
				sb.append('</tr></tbody>');
			}			
			sb.append('</table>')
			obj.innerHTML = sb.toString();
			
			// 显示第一页的数据。
			this.showThumbPage(1);
		}
	},
	/**
	 * 得到显示缩略图的tr。
	 * @param {Number} currPage 页次。
	 */
	getThumbPhoto:function(currPage) {
		if (this.cacheThumbList["THUMB_LIST_" + currPage]) return this.cacheThumbList["THUMB_LIST_" + currPage];
		
		var tr = document.createElement("tr");
		for (var i = (currPage - 1) * this.thumbPhotoPageSize; i < currPage * this.thumbPhotoPageSize && i < this.photoList.length; i++) {
			var td = document.createElement('td');
			
			var a = document.createElement('a');
			a.href = 'javascript:PhotoUtil.showPicture(' + i + ')';
			
			var bufferImg = new Image();
			bufferImg.src = this.photoList[i].photoUrl;
			
			var img = document.createElement('img');
			img.style.border = '0';
			img.src = "images/loading.jpg";
			img.style.width = this.thumbPhotoWidth + 'px';
			
			a.appendChild(img);
			td.appendChild(a);
			tr.appendChild(td);
			
			if (bufferImg.height > 0) {	// 如果数据已经缓存过，则可以直接获取 height 的数据。
				img.src = bufferImg.src;
				var height = bufferImg.height * (this.thumbPhotoWidth/bufferImg.width);
				img.height = height;
			} else {					// 如果数据没有下载完，需要用事件来驱动后面的处理。
				var base = this;
				img.src = "images/notfound.jpg";
				img.style.width = this.thumbPhotoWidth + 'px';
				img.style.height = this.thumbPhotoWidth + 'px';
				img.alt = "没有找到此图片";
				
				bufferImg.onload = function() {
					img.src = this.src;
					var height = this.height * (base.thumbPhotoWidth/this.width);					
					img.height = height;
				}
			}
		}
		
		this.cacheThumbList["THUMB_LIST_" + currPage] = tr;
		return tr;
	},
	/**
	 * 得到显示分页的字符串。
	 * @param {Number} currPage 页次。
	 */
	getThumbPagination:function(currPage) {
		if (this.cacheThumbPagination["THUMB_PAGINATION_" + currPage]) return this.cacheThumbPagination["THUMB_PAGINATION_" + currPage];
		
		this.thumbPhotoCurrPage = currPage;
		var sb = new StringBuffer();
		if (currPage > 1) {
			sb.append('<a href="javascript:PhotoUtil.showThumbPage(1)">首页</a>');
		} else {
			sb.append('<span disabled>首页</span>');
		}
		sb.append('&nbsp;&nbsp;');
		if (currPage > 1) {
			sb.append('<a href="javascript:PhotoUtil.showThumbPrevPage()">上一页</a>');
		} else {
			sb.append('<span disabled>上一页</span>');
		}
		sb.append('&nbsp;&nbsp;');
		if (currPage < this.thumbPhotoTotalPage) {
			sb.append('<a href="javascript:PhotoUtil.showThumbNextPage()">下一页</a>');
		} else {
			sb.append('<span disabled>下一页</span>');
		}
		sb.append('&nbsp;&nbsp;');
		if (currPage < this.thumbPhotoTotalPage) {
			sb.append('<a href="javascript:PhotoUtil.showThumbPage(' + this.thumbPhotoTotalPage + ')">尾页</a>');
		} else {
			sb.append('<span disabled>尾页</span>');
		}
		
		this.cacheThumbPagination["THUMB_PAGINATION_" + currPage] = sb.toString();
		return sb.toString();
	},
	/**
	 * 显示指定页次的缩略图。
	 * @param {Number} currPage 页次。
	 */
	showThumbPage:function(currPage) {
		if (this.photoList.length <= 1) return;
		var photoObj = document.getElementById('_Thumb_Photo');
		if (!photoObj) return;
		
		while(photoObj.hasChildNodes()) {
			photoObj.removeChild(photoObj.firstChild);
		}		
		photoObj.appendChild(this.getThumbPhoto(currPage));
		
		var paginationObj = document.getElementById('_Thumb_Pagination');
		if (!paginationObj) return;
		
		while(paginationObj.hasChildNodes()) {
			paginationObj.removeChild(paginationObj.firstChild);
		}
		
		paginationObj.innerHTML = this.getThumbPagination(currPage);
	},
	/**
	 * 显示下一页的缩略图。
	 */
	showThumbPrevPage: function() {
		if (this.thumbPhotoCurrPage > 1) {
			this.thumbPhotoCurrPage--;
		}
		this.showThumbPage(this.thumbPhotoCurrPage);
	},
	/**
	 * 显示上一页的缩略图。
	 */
	showThumbNextPage: function() {
		if (this.thumbPhotoCurrPage < this.thumbPhotoTotalPage) {
			this.thumbPhotoCurrPage++;
		}
		this.showThumbPage(this.thumbPhotoCurrPage);
	}
};

