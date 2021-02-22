/**
 * 페이징
 * 
 * formId - form 태그 아이디
 * targetId - page navigation ul 태그 아이디
 * url - 연동 URL 
 * dataProvider - realgrid dataProvider 객체
 * pageNo - 요청 페이지 번호
 * pageSize - 페이지당 데이터 건수
 * 
 * ex) 
 * 
 * <div class="form-group">
 *	 <div class="text-center">
 *		<ul class="pagination pagination-alt" id="mynavi"></ul>
 *	 </div>
 * </div>
 *												
 * var page = new Page("frmSearch", "mynavi", "/bizConnections", dataProvider, 1, 10, function(){
 *   return this.currentForm.serialize();
 * });
 * page.search();
 * 			
 */
var Page = function(formId, targetId, url, dataProvider, pageNo, pageSize, getFormData){
	this.targetId = targetId;
	this.currentForm = $("#" + formId);
	this.target =  $("#" + targetId);
	this.url = url;
	this.dataProvider = dataProvider;
	this.pageNo = pageNo;
	this.pageSize = pageSize;
	this.getFormData = getFormData;
};

// 내비게이션 영역 생성
Page.prototype.drawNavigation = function(links){
	var self = this;
	
	var pageNaviStr = "";
	
	for(var i = 0 ; i < links.pages.length ; i++){
		if(i == 0){
			pageNaviStr += "<li>";
			pageNaviStr += 	"<a href='javascript:void(0);' navId='" + this.targetId + "' pageNo='"+links.prev+"'>«</a>";
			pageNaviStr += "</li>";
		}
		
		if(Number(this.pageNo) == Number(links.pages[i])){
			pageNaviStr += "<li class='active'>";
		}else{
			pageNaviStr += "<li>";
		}
		
		pageNaviStr += 	"<a href='javascript:void(0);' navId='" + this.targetId + "' pageNo='"+links.pages[i]+"'>"+links.pages[i]+"</a>";
		pageNaviStr += "</li>";
		
		if(i == links.pages.length - 1){
			pageNaviStr += "<li>";
			pageNaviStr += 	"<a href='javascript:void(0);' navId='" + this.targetId + "' pageNo='"+links.next+"'>»</a>";
			pageNaviStr += "</li>";
		}
	}
	
	this.target.append(pageNaviStr);
	
	$("a[navId='" + this.targetId + "']").click(function(){
		var pageNo = $(this).attr("pageNo");
		if(pageNo != "0"){
			self.pageNo = Number(pageNo);
			self.search();
		}
	});

};

// 조회
Page.prototype.search = function(callback){
	
	var self = this;
	
	$("#pageInfo").remove();
	
	var pageInfo = '<div id="pageInfo">';
	pageInfo += '<input type="hidden" name="pageSize" value="' + this.pageSize + '"/>';
	pageInfo += '<input type="hidden" name="pageNo" value="' + this.pageNo + '"/>';
	pageInfo += '</div>';
	
	this.currentForm.append(pageInfo);
	
	var params = null;
	
	if(this.getFormData == undefined){
		params = self.currentForm.serialize();
	}else{
		params = self.getFormData();
		params.pageSize = self.pageSize;
		params.pageNo = self.pageNo;
	}
	
	console.log(params);
	
	$.callRestApi(this.url, "get", params,
        function(data, textStatus, XMLHttpRequest){
            self.target.html("");
            self.dataProvider.setRows(null);

            if(data.data.length > 0){
                console.log(data.data[0]);
                self.dataProvider.setRows(data.data);
                self.drawNavigation(data.links);
            }
            
            if(callback != null){
            	callback(data);
            }
        },
        function(errorResult) {
            console.debug('errorResult : '+ errorResult.responseText);
            console.debug('errorResult.responseJSON : '+ errorResult.responseJSON);

            if(errorResult.responseJSON) {
                /*switch(errorResult.status) {
                    case 403:
                        alert(errorResult.responseJSON.message);
                        break;
                }*/
                alert(errorResult.responseJSON.message);
                if(errorResult.status == 403) {
                    document.location.reload();
                }
            }
        }
	);
	
};
