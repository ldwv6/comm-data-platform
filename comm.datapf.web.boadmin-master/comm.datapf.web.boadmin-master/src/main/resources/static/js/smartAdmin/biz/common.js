// 그리드 생성
function createGrid(gridDivId, fields) {

	var dataProvider = new RealGridJS.LocalDataProvider();
	var gridView = new RealGridJS.GridView(gridDivId);
	gridView.setDataSource(dataProvider);
	
	var gridFields = [];
	var columns = [];
	
	for(var i=0; i<fields.length; i++){
		
		gridFields.push(fields[i]);
		
		var columnStyle;
		
		// 숫자 타입인 경우 스타일 변경
		if(fields[i].dataType == "number"){
			columnStyle = {
				textAlignment: "far"
				, numberFormat: "#,##0"
			}
		}else{
			columnStyle = fields[i].columnStyle;
		}
		
		columns.push({
			name: fields[i].fieldName,
			fieldName : fields[i].fieldName,
			header : {
				text: fields[i].headerText
			},
			width: fields[i].width,
			readOnly: fields[i].readOnly,
			editable: fields[i].editable,
			styles: {
				"background": "#FFF"
			},
			styles: columnStyle,
			visible: fields[i].visible
		});
		
	}
	
	dataProvider.setFields(gridFields);
	
	gridView.setColumns(columns);
	
	// Footer 제거
	gridView.setFooter({
	    visible: false
	});
	
	// 패널 비활성화
	gridView.setOptions({
		panel: {
			visible: false
		},
		stateBar: {
	        visible: false
	    },
	});

	return {
		gridView : gridView,
		dataProvider : dataProvider
	};
	
}

// Form 데이터 처리
$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    var extend = function (i, element) {
    	
    	if(element.name.split(".").length == 1){
    		
    		element.value = element.value.trim();
    		
	        var node = result[element.name];
	        
	        if ('undefined' !== typeof node && node !== null) {
	           if ($.isArray(node)) {
	               node.push(element.value);
	           } else {
	               result[element.name] = [node, element.value];
	           }
	        } else {
	            result[element.name] = element.value;
	        }
	        
    	}else{
    		addChild(result, element.name, element.value);
    	}
    };
 
    $.each(this.serializeArray(), extend);
    
    function addChild(parent, name, value){
    	var sub = name.split(".");
		
		if(parent[sub[0]] == null){
			parent[sub[0]] = {};
		}
		
		if(sub.length == 2){
			parent[sub[0]][sub[1]] = value;
		}else{
			addChild(parent[sub[0]], name.substring(name.indexOf(".") + 1), value);
		}
    }
    
    return result;
};

function isDate(s) {   
  // make sure it is in the expected format
  if (s.search(/^\d{4}[\/|\-|\.|_]\d{1,2}[\/|\-|\.|_]\d{1,2}/g) != 0)
     return false;

  // remove other separators that are not valid with the Date class    
  s = s.replace(/[\-|\.|_]/g, "/"); 

  // convert it into a date instance
  var dt = new Date(Date.parse(s));     

  // check the components of the date
  // since Date instance automatically rolls over each component
  var arrDateParts = s.split("/");
     return (
         dt.getFullYear() == arrDateParts[0] &&
         dt.getMonth() == arrDateParts[1]-1 &&
         dt.getDate() == arrDateParts[2]
     );   
}
 

// dom 로딩 종료 후
$(function(){ 
	
	"use strict";
	
	// Smart Admin 설정
	pageSetUp();
		
	// 달력 설정
	$.datepicker.setDefaults({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년'
	});
	
	RealGridJS.setTrace(false);
	RealGridJS.setRootContext("/static/script");
	
	// TODO 로딩 타이밍 확인 필요
	if($.blockUI != null){
		//override these in your code to change the default behavior and style 
		$.blockUI.defaults = { 
		    // message displayed when blocking (use null for no message) 
		    message:  '<h5>처리중입니다.</h5>', 
		 
		    title: null,        // title string; only used when theme == true 
		    draggable: true,    // only used when theme == true (requires jquery-ui.js to be loaded) 
		 
		    theme: true, // set to true to use with jQuery UI themes 
		 
		    // styles for the message when blocking; if you wish to disable 
		    // these and use an external stylesheet then do this in your code: 
		    // $.blockUI.defaults.css = {}; 
		    css: { 
		        padding:        0, 
		        margin:         0, 
		        width:          '30%', 
		        top:            '40%', 
		        left:           '35%', 
		        textAlign:      'center', 
		        color:          '#000', 
		        border:         '3px solid #aaa', 
		        backgroundColor:'#fff', 
		        cursor:         'wait' 
		    }, 
		 
		    // minimal style set used when themes are used 
		    themedCSS: { 
		        width:  '30%', 
		        top:    '40%', 
		        left:   '35%' 
		    }, 
		 
		    // styles for the overlay 
		    overlayCSS:  { 
		        backgroundColor: '#000', 
		        opacity:         0.6, 
		        cursor:          'wait' 
		    }, 
		 
		    // style to replace wait cursor before unblocking to correct issue 
		    // of lingering wait cursor 
		    cursorReset: 'default', 
		 
		    // styles applied when using $.growlUI 
		    growlCSS: { 
		        width:    '350px', 
		        top:      '10px', 
		        left:     '', 
		        right:    '10px', 
		        border:   'none', 
		        padding:  '5px', 
		        opacity:   0.6, 
		        cursor:    null, 
		        color:    '#fff', 
		        backgroundColor: '#000', 
		        '-webkit-border-radius': '10px', 
		        '-moz-border-radius':    '10px' 
		    }, 
		     
		    // IE issues: 'about:blank' fails on HTTPS and javascript:false is s-l-o-w 
		    // (hat tip to Jorge H. N. de Vasconcelos) 
		    iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank', 
		 
		    // force usage of iframe in non-IE browsers (handy for blocking applets) 
		    forceIframe: false, 
		 
		    // z-index for the blocking overlay 
		    baseZ: 2000, 
		 
		    // set these to true to have the message automatically centered 
		    centerX: true, // <-- only effects element blocking (page block controlled via css above) 
		    centerY: true, 
		 
		    // allow body element to be stetched in ie6; this makes blocking look better 
		    // on "short" pages.  disable if you wish to prevent changes to the body height 
		    allowBodyStretch: true, 
		 
		    // enable if you want key and mouse events to be disabled for content that is blocked 
		    bindEvents: true, 
		 
		    // be default blockUI will supress tab navigation from leaving blocking content 
		    // (if bindEvents is true) 
		    constrainTabKey: true, 
		 
		    // fadeIn time in millis; set to 0 to disable fadeIn on block 
		    fadeIn:  200, 
		 
		    // fadeOut time in millis; set to 0 to disable fadeOut on unblock 
		    fadeOut:  400, 
		 
		    // time in millis to wait before auto-unblocking; set to 0 to disable auto-unblock 
		    timeout: 0, 
		 
		    // disable if you don't want to show the overlay 
		    showOverlay: true, 
		 
		    // if true, focus will be placed in the first available input field when 
		    // page blocking 
		    focusInput: true, 
		 
		    // suppresses the use of overlay styles on FF/Linux (due to performance issues with opacity) 
		    // no longer needed in 2012 
		    // applyPlatformOpacityRules: true, 
		 
		    // callback method invoked when fadeIn has completed and blocking message is visible 
		    onBlock: null, 
		 
		    // callback method invoked when unblocking has completed; the callback is 
		    // passed the element that has been unblocked (which is the window object for page 
		    // blocks) and the options that were passed to the unblock call: 
		    //   onUnblock(element, options) 
		    onUnblock: null, 
		 
		    // don't ask; if you really must know: http://groups.google.com/group/jquery-en/browse_thread/thread/36640a8730503595/2f6a79a77a78e493#2f6a79a77a78e493 
		    quirksmodeOffsetHack: 4, 
		 
		    // class name of the message block 
		    blockMsgClass: 'blockMsg', 
		 
		    // if it is already blocked, then ignore it (don't unblock and reblock) 
		    ignoreIfBlocked: false 
		}; 
	
	}
});

/**
 * parameter
 * url : call url
 * type : get, post, put, delete 중 하나
 * param : 전송할 데이터
 * sCallBack : 성공 후 호출할 콜백함수
 * eCallBack : 실패 시 호출할 콜백 함수
 */
$.callRestApi = function(url, type, param, sCallBack, eCallBack){
	
	/*
	 * ex 
	 * var params = $("#form").serialize();
	 * get 방식 : $.callRestApi("/bizConnections", "get", params, $.callBack, null);
	 * 
	 * var params = $("#form").serializeObject();
	 * post, put, delete 방식 : $.callRestApi(url, "post", params, $.setSetleCallBack, $.errCallBack);
	 */
	
	type = type.toLowerCase();
	
	if(type == "get"){
		$.ajax({
			type: type,
			url: url,
			data: param,
            beforeSend: function(xhr){
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader('Accept', 'application/json');
				xhr.setRequestHeader('X-ACCESS-MENU-NO', $("#_menuNo").attr("value"));
            },
			success: sCallBack,
			error: function(errorResult) {
				if (errorResult.responseJSON && errorResult.responseJSON.status === 403) {
					alert(errorResult.responseJSON.message);
					location.reload();
				}
				// 호출한 API의 접근 권한이 없을 경우
				else if (errorResult.status === 405) {
					alert(errorResult.responseJSON.error.message);
				} else {
					eCallBack(errorResult);
				}
			}
		});
	}else if(type == "post" || type == "put" || type == "delete"){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			type: type,
			url: url,
			data: JSON.stringify(param),
			beforeSend: function(xhr){
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader('Accept', 'application/json');
				xhr.setRequestHeader('X-ACCESS-MENU-NO', $("#_menuNo").attr("value"));
				xhr.setRequestHeader(header, token);
			},
			dataType: 'json',
			jsonp : false,
			success: sCallBack,
			error: function(errorResult) {
				if (errorResult.responseJSON && errorResult.responseJSON.status === 403) {
					alert(errorResult.responseJSON.message);
					location.reload();
				} 
				// 호출한 API의 접근 권한이 없을 경우
				else if (errorResult.status === 405) {
					alert(errorResult.responseJSON.error.message);
				} else {
					eCallBack(errorResult);
				}
			}
		});
	}
}

$.callRestApiWithFiles = function(url, type, params, sCallBack, eCallBack) {
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: url,
		type: type,
		data: params,
		contentType: false,
		processData: false,
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
			xhr.setRequestHeader('X-ACCESS-MENU-NO', $("#_menuNo").attr("value"));
		},
		success: sCallBack,
		error: eCallBack
	});
}

$.callAsyncRestApi = function(url, type, param, sCallBack, eCallBack){
	
	/*
	 * ex 
	 * var params = $("#form").serialize();
	 * get 방식 : $.callRestApi("/bizConnections", "get", params, $.callBack, null);
	 * 
	 * var params = $("#form").serializeObject();
	 * post, put, delete 방식 : $.callRestApi(url, "post", params, $.setSetleCallBack, $.errCallBack);
	 */
	
	type = type.toLowerCase();
	
	try{
	
		if(type == "get"){
			$.ajax({
				type: type,
				url: url,
				data: param,
				async: false,
	            beforeSend: function(xhr){
	                xhr.setRequestHeader('Content-Type', 'application/json');
	                xhr.setRequestHeader('Accept', 'application/json');
	                xhr.setRequestHeader('X-ACCESS-MENU-NO', $("#_menuNo").attr("value"));
	            },
				success: sCallBack,
				error: eCallBack
			});
		}else if(type == "post" || type == "put" || type == "delete"){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			$.ajax({
				type: type,
				url: url,
				async: false,
				data: JSON.stringify(param),
				beforeSend: function(xhr){
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader('Accept', 'application/json');
					xhr.setRequestHeader('X-ACCESS-MENU-NO', $("#_menuNo").attr("value"));
					xhr.setRequestHeader(header, token);
				},
				dataType: 'json',
				success: sCallBack,
				error: eCallBack
			});
		}
	}catch(e){
		console.log(e);
		$.unblockUI();
	}
}

/**
 * 숫자형식 콤마
 * @param service - 공통
 * @param 
 * @param 
 * @returns
 *
 */

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
	

/**
 * 날짜형식 포맷
 * @param service - 공통
 * @param 
 * @param 
 * @returns
 *
 */
function YMDFormatter(num){

    if(!num) return "";
    var formatNum = '';

    // 공백제거
    num=num.replace(/\s/gi, "");
    try{

         if(num.length == 8) {
              formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
         }

    } catch(e) {

         formatNum = num;
         console.log(e);
    }

    return formatNum;

}


/**
 * 휴대폰번호 콤마
 * @param service - 공통
 * @param 
 * @param 
 * @returns
 *
 */

function numberWithHpCommas(x) {
    if(!x) return "";
    x=x.replace(/\s/gi, "");
    return x.toString().replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    
}

/**
 * 공통코드 조회
 * @param service - 서비스(goods/coupon/settlement 중 택1) 
 * @param cd - 컬럼명
 * @param callback - 공통코드 조회 결과 데이터 콜백
 * @returns
 * 
 * ex)
 * 
 * getCommonCode('coupon', 'ROOM_TY', function(data){
 *     console.log(data);
 * });
 */
function getCommonCode(service, cd, callback){
	var data = {
		service: service
		, cd: cd
	};
		
	$.callRestApi("/api/common/code/cd", "get", data, function(data, textStatus, XMLHttpRequest){
		callback(data.data);
	}, function(errorResult){
		alert("코드 조회 실패");
	});
}

function getUrlParams(url){
//	alert("getUrlParams" );
//	var param = new Array();
	var param = {};
  
    var params;
    // url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
    params = url.substring( url.indexOf('?')+1, url.length );
    // 파라미터 구분자("&") 로 분리
    params = params.split("&");
  
// 	alert("params  :  " + params);

    // params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
    
    var size = params.length;
//	alert("size  :  " + size);
    
    var key, value;
    for(var i=0 ; i < size ; i++) {
        key = params[i].split("=")[0];
        value = params[i].split("=")[1];
	    param[key] = value;
	}
    
    return param;
}


// comTypeCd-공통유형코드, id- select박스 id, selectType- (ALL전체추가, ONE선택추가, ''조회데이터만), selectedValue-최초select노출값,filterValue-(삭제 값 또는 허용패턴),sortType-(D-추가, F-패턴만 노출), sortType=ASC,DESC(DEFAULT:ASC), callback-콜백함수
// drawCommonCodeFilter( 'SVC_DVSN_CD', 'svcDvsnCd', '', null, ['0'],'F', 'ASC', null);
// drawCommonCodeFilter( 'SVC_DVSN_CD', 'svcDvsnCd', '', null, ['00','01','05','07','08','09','10'],'D', 'ASC', null);
function drawCommonCodeFilter(comTypeCd, id, selectType, selectedValue, filterValue, tp, sortType, callback) {

	var data = {
		comTypeCd: comTypeCd
	};

	$.ajax({
		url: "/getCodeList",
		type: "GET",
		data: {
			comTypeCd: comTypeCd
			,sortType : sortType
		},
		success: function (data) {
			var options = "";

			if(selectType == "ALL") {
				options += "<option value=''>전체</option>";
			} else if(selectType == "ONE") {
				options += "<option value=''>선택</option>";
			}

			if(tp === "F" ) {
                //DATA Filtering
                data.data = _.filter(data.data, function(v) {
                    return v.comCd.startsWith(filterValue);
                });
            } else if(tp === "D") {
				//DATA DELETE
                data.data = _.filter(data.data, function(v) {
                    //ex XXXX|XXXX
                    return _.indexOf(filterValue, v.comCd) == -1;
                });
            }

			//(list).chain().sortBy('name').reverse().sortBy('totInHouseCnt').reverse().value();
			/*if(!_.isUndefined(sortType) && !_.isNull(sortType)) {
                data.data = _.sortBy(data.data, sortType);
                if(!_.isUndefined(orderBy) && !_.isNull(orderBy) && orderBy == "DESC")
                    data.data= data.data.reverse();
            }*/

			for(var i=0; i<data.data.length; i++) {
				var item = data.data[i];
				options += "<option value='" + item.comCd + "'>" + item.comCdNm + "</option>";
			}

			$("#" + id).html(options);

			if(selectedValue != null) {
				$("#" + id).val(selectedValue);
				$("#" + id).trigger("change");
			}

			if(callback != null) {
				callback();
			}

		},
		complete: function () {

		}
	});
}


/**
 * 대상 selectbox 에 공통코드 내용 추가 후 change 이벤트 발생 
 * @param service - 서비스('goods' or 'coupon' or 'settlement') 
 * @param cd - 컬럼명
 * @param id - id
 * @param selectType - 유형('ALL' or 'ONE' or null or '')
 * @param selectedValue - 선택할 값
 * @returns
 * 
 * ex)
 * 
 * drawCommonCode('coupon', 'ROOM_TY', 'roomTy', 'ALL');
 * drawCommonCode('coupon', 'ROOM_TY', 'roomTy', 'ONE', 'R0002');
 */
function drawCommonCode(service, cd, id, selectType, selectedValue){
	var data = {
		service: service
		, cd: cd
	};
		
	$.callRestApi("/api/common/code/cd", "get", data, function(data, textStatus, XMLHttpRequest){
		
		var options = "";
		
		if(selectType == "ALL"){
			options += "<option value=''>전체</option>";
		}else if(selectType == "ONE"){
			options += "<option value=''>선택</option>";
		}
		
		for(var i=0; i<data.data.length; i++){
			var item = data.data[i];
			options += "<option value='" + item.cdTy + "'>" + item.nmKr + "</option>";
		}
		
		$("#" + id).html(options);
		
		if(selectedValue != null){
			$("#" + id).val(selectedValue);
			$("#" + id).trigger("change");
		}
		
	}, function(errorResult){
		alert("코드 조회 실패");
	});
}
/**
 * 대상 selectbox 에 공통코드 내용 추가 후 change 이벤트 발생 (동기방식)
 * @param service - 서비스('goods' or 'coupon' or 'settlement')
 * @param cd - 컬럼명
 * @param id - id
 * @param selectType - 유형('ALL' or 'ONE' or null or '')
 * @param selectedValue - 선택할 값
 * @returns
 *
 * ex)
 *
 * drawAsyncCommonCode('coupon', 'ROOM_TY', 'roomTy', 'ALL');
 * drawAsyncCommonCode('coupon', 'ROOM_TY', 'roomTy', 'ONE', 'R0002');
 */
function drawAsyncCommonCode(service, cd, id, selectType, selectedValue){
	var data = {
		service: service
		, cd: cd
	};

	$.callAsyncRestApi("/api/common/code/cd", "get", data, function(data, textStatus, XMLHttpRequest){

		var options = "";

		if(selectType == "ALL"){
			options += "<option value=''>전체</option>";
		}else if(selectType == "ONE"){
			options += "<option value=''>선택</option>";
		}

		for(var i=0; i<data.data.length; i++){
			var item = data.data[i];
			options += "<option value='" + item.cdTy + "'>" + item.nmKr + "</option>";
		}

		$("#" + id).html(options);

		if(selectedValue != null){
			$("#" + id).val(selectedValue);
			$("#" + id).trigger("change");
		}

	}, function(errorResult){
		alert("코드 조회 실패");
	});
}

/**
 * 대상 selectbox 에 필터조건을 걸어 공통코드 내용 추가 후 change 이벤트 발생
 * @param service - 서비스('goods' or 'coupon' or 'settlement')
 * @param cd - 검색할 코드명
 * @param id - 코드값 적용할 element id
 * @param selectType - 유형('ALL' or 'ONE' or null or '') ALL=전체/ONE=선택
 * @param selectedValue - 불러온 코드 중에서 기본 선택할 값
 * @param filterValue - 필터링 또는 제거할 코드값
 * @param tp - 유형('F' or 'D') filterValue 코드를 F=필터링처리/D=제거처리 // 제거는 다중처리 안됨 확인필요
 * @param sortType - 정렬할 컬럼명 (컬럼명 camel표기)
 * @param orderBy - 정렬방식('ASC' or 'DESC')
 * @param callback - callback Function
 * @returns
 *
 * ex)
 *
 * drawCommonCodeFilter('goods', 'GOODS_SE', 'goodsSeM', 'ALL', 'A5001', ['A5007'], 'D', 'cdTy', 'ASC');
 * drawCommonCodeFilter('goods', 'GOODS_SE', 'goodsSeM', 'ONE', null, ['A5007'], 'D', 'cdTy', 'DESC');
 */
/*function drawCommonCodeFilter(service, cd, id, selectType, selectedValue, filterValue, tp, sortType, orderBy, callback) {
	var data = {
		service: service
		, cd: cd
	};
		
	$.callRestApi("/api/common/code/cd", "get", data, function(data, textStatus, XMLHttpRequest) {
		
		var options = "";
		
		if(selectType == "ALL") {
			options += "<option value=''>전체</option>";
		} else if(selectType == "ONE") {
			options += "<option value=''>선택</option>";
		}
		
		if(tp === "F" ) {
			//DATA Filtering
			data.data = _.filter(data.data, function(v) { 
				return v.cdTy.startsWith(filterValue); 
			});
		} else if(tp === "D") {
			//DATA DELETE
			data.data = _.filter(data.data, function(v) {
				//ex XXXX|XXXX
				return _.indexOf(filterValue, v.cdTy) == -1;
			});			
		}
		
		//(list).chain().sortBy('name').reverse().sortBy('totInHouseCnt').reverse().value();
		if(!_.isUndefined(sortType) && !_.isNull(sortType)) {
			data.data = _.sortBy(data.data, sortType);
			if(!_.isUndefined(orderBy) && !_.isNull(orderBy) && orderBy == "DESC")
				data.data= data.data.reverse();
		}
		
		for(var i=0; i<data.data.length; i++) {
			var item = data.data[i];
			options += "<option value='" + item.cdTy + "'>" + item.nmKr + "</option>";
		}
		
		$("#" + id).html(options);
		
		if(selectedValue != null) {
			$("#" + id).val(selectedValue);
			$("#" + id).trigger("change");
		}
		
		if(callback != null) {
			callback();
		}
		
	}, function(errorResult) {
		alert("코드 조회 실패");
	});
}*/


Date.prototype.format = function(f){
	if (!this.valueOf()) return "";
	 
	var d = this;
	 
	return f.replace(/(yyyy|yy|MM|dd|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
			case "yyyy": return d.getFullYear();
			case "yy": return (d.getFullYear() % 1000).zf(2);
			case "MM": return (d.getMonth() + 1).zf(2);
			case "dd": return d.getDate().zf(2);
			case "HH": return d.getHours().zf(2);
			case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
			case "mm": return d.getMinutes().zf(2);
			case "ss": return d.getSeconds().zf(2);
			case "a/p": return d.getHours() < 12 ? "오전" : "오후";
			default: return $1;
		}
	});
}

Date.prototype.addDays = function(days) {
	var d = this;
	d.setDate(d.getDate() + days);
	return d;
}

Date.prototype.addMonths = function(months) {
	var d = this;
	d.setMonth(d.getMonth() + months);
	return d;
}

String.prototype.lpad = function(padLength, padString){
    var s = this;
    while(s.length < padLength)
        s = padString + s;
    return s;
}
 
String.prototype.rpad = function(padLength, padString){
    var s = this;
    while(s.length < padLength)
        s += padString;
    return s;
}

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

String.prototype.toDateFormat = function(){
	if (this == "" || this.length != 8) return "";
	var s = this;
	var year = s.substr(0,4);
	var month = s.substr(4,2) - 1;
	var day = s.substr(6,2);
	
	return new Date(year, month, day).format("yyyy-MM-dd");
};

toTimeStr = function(s) {
	if(s == null || s == "") return "";

	var time = s.split(" ")[0];
	var meridian = s.split(" ")[1];

	var hours = Number(time.split(":")[0]);
	var minutes = Number(time.split(":")[1]);

	if(meridian.toUpperCase() == "AM" && hours == 12){
		hours = 0;
	}
	else if(meridian.toUpperCase() == "PM" && hours != 12){
		hours += 12;
	}

	var result = hours.toString().zf(2) + ":" + minutes.toString().zf(2);

	if (result == "24:00") {
		return "23:59";
	}

	return result;
};

parseTimepicker = function(s) {
	
	var hours;
	var minutes;
	
	if (s == null || s.replace(/\u0000/g, "") == "") {
		var now = new Date();
		hours = now.getHours();
		minutes = now.getMinutes();
	} else {
		hours = s.substr(0, 2);
		minutes = s.substr(3, 2);
	}
	
	var meridian = Number(hours) > 11 ? "PM" : "AM";
	
	// DB: 0시 -> 화면: AM 12시
	if (hours == 0) 
		hours = 12;
	
	if (meridian == "PM")
		hours = Number(hours) - 12 == 0 ? 12 : Number(hours) - 12;
	
	return Number(hours) + ":" + minutes.zf(2) + " " + meridian;
};

$.isEmail = function(s) {
	
	if(s == null || s == "")
		return false;
	
	return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(s);
	
}

$.numberOnly = function(o) {
	var s = $.trim($(o).val());
	if(s == "") {
		return false;
	}
	$(o).val(s.replace(/[^0-9]/gi, ""));
}

function smsSend(phoneNo, bkPhoneNo, msg, userId, resveNo, sendDate, userAlert, chGubun) {
	var baseUrl = "/send/sms";
	
	var params = {};
	params["callback"] = bkPhoneNo;
	params["etc1"] = chGubun;
	params["etc2"] = userId;
	params["etc3"] = resveNo;
	params["etc4"] = "";
//	params["id"] = "";
	params["msg"] = msg;
	params["phone"] = phoneNo;
	params["rsltStat"] = "";
	params["sendDate"] = sendDate;
	params["sendStat"] = "";
//	params["timestamp"] = "";
	
	var result;
	$.callAsyncRestApi(baseUrl, "post", params, function(data, textStatus, XMLHttpRequest){
		result = data.status;
	}, function(errorResult) {
		result = errorResult;
	});
	
	return result;
}

function lmsSend(phoneNo, bkPhoneNo, subject, msg, userId, resveNo, sendDate, userAlert, chGubun) {
	var baseUrl = "/send/lms";
	
	var params = {};
	params["callback"] = bkPhoneNo;
	params["etc1"] = chGubun;
	params["etc2"] = "touradmin";
	params["etc3"] = resveNo;
//	params["id"] = "";
	params["msg"] = msg;
	params["phone"] = phoneNo;
	params["reqdate"] = "";
	params["status"] = "0";
	params["subject"] = subject;
	params["type"] = "0";
//	params["timestamp"] = "";
	
	var result;
	$.callAsyncRestApi(baseUrl, "post", params, function(data, textStatus, XMLHttpRequest){
		result = data.status;
	}, function(errorResult) {
		result = errorResult;
	});
	
	return result;
}

function emailSend(sendTypeCd, receiverEmailAddr, receiverMemberNo, receiverMemberNm, senderEmailAddr, senderMemberNo
		,reservationNo, mailUrl, remark, reqUserId, sendTime, statusCd, regUserId) {
	
	var baseUrl = "/send/email";
	
	var params = {};
	params["sendTypeCD"] = sendTypeCd;
	params["receiverEmailAddr"] = receiverEmailAddr;
	params["receiverMemberNo"] = receiverMemberNo;
	params["receiverMemberNm"] = receiverMemberNm;
	params["senderEmailAddr"] = senderEmailAddr;
	params["senderMemberNo"] = senderMemberNo;
	params["reservationNo"] = reservationNo;
	params["mailUrl"] = mailUrl;
	params["remark"] = remark;
	params["reqUserId"] = reqUserId;
	params["sendTime"] = sendTime;
	params["statusCd"] = statusCd;
	params["regUserId"] = regUserId;
	
	var result;
	$.callAsyncRestApi(baseUrl, "post", params, function(data, textStatus, XMLHttpRequest){
		result = data.status;
	}, function(errorResult) {
		result = errorResult;
	});
	
	return result;
}

function alimTalkSend(phoneNo, userId, chGubun, resveNo, sendMsg, messageType, sendFlag, templateId) {
	var baseUrl = "/send/alimTak";

	var params = {};
	params["reserve7"] = templateId;
	params["receiveMobileNo"] = phoneNo;
	params["contentsType"] = "004";
	//goodsNm
	params["registerBy"] = userId;
	params["etc1"] = chGubun;
	params["etc3"] = resveNo;
	params["sendMessage"] = sendMsg;
	//checkIndate
	//useDay
	params["messageType"] = messageType;
	params["backupProcessCode"] = "003";
	params["sendFlag"] = sendFlag;
	params["jobType"] = "R00";
	
	var result;
	$.callAsyncRestApi(baseUrl, "post", params, function(data, textStatus, XMLHttpRequest){
		result = data.status;
	}, function(errorResult) {
		result = errorResult;
	});
	
	return result;
}

function faxSend(faxDestPhone, faxDestName, sendPhone, sendName, subject, coverFlag
		, faxFile, etc1, etc2, etc3, etc4, etc5) {
	var baseUrl = "/send/fax";
	
	var params = {};
	params["destPhone"] = faxDestPhone
	params["destName"] = faxDestName
	params["sendPhone"] = sendPhone
	params["sendName"] = sendName;
	params["subject"] = subject;
	params["coverFlag"] = coverFlag;
	params["faxFile"] = faxFile;
	params["etc1"] = etc1;
	params["etc2"] = etc2;
	params["etc3"] = etc3;
	params["etc4"] = etc4;
	params["etc5"] = etc5;
	
	var result;
	$.callAsyncRestApi(baseUrl, "post", params, function(data, textStatus, XMLHttpRequest){
		result = data.status;
	}, function(errorResult) {
		result = errorResult;
	});
	
	return result;
}

/**
 * 담당자 조회
 * @param service - 서비스(goods/coupon/settlement/display 중 택1) 
 * @param keyword - 검색어
 * @param sCallback - 
 * @param eCallback - 
 * @returns
 */
function searchAdmin(service, keyword, sCallback, eCallback) {
	var data = {
		service: service
		, keyword: keyword
	};
	
	if (eCallback == null) {
		eCallback = function(errorResult) {
			alert("코드 조회 실패");
		};
	}
	
	$.callRestApi("/api/common/admins", "get", data, sCallback, eCallback);
}

var adminSearchPopup = null;

/**
 * 담당자 조회 팝업
 * ex)
 * popAdminSearch();
 */
function popAdminSearch(){
	
	var popUrl = "/common/admin/search";
	var popOption = "width=500, height=400, resizable=yes, scrollbars=yes, status=no;";
	
	if(adminSearchPopup != null){
	    adminSearchPopup.close();
	    adminSearchPopup = null;
	}
	
	adminSearchPopup = window.open(popUrl, "", popOption);
}

function modalAdminSearch(service, keyword, callback, errorCallback) {
	var baseUrl = "/api/common/admins";
	baseUrl += "?service=" + service + "&keyword=" + keyword;
	
	console.log(baseUrl);
	
	/*$.adminSearchCallBack = function (data, textStatus, XMLHttpRequest) {
		console.log(data.data);
		
		//담당자 셋팅 그리드
		mBsnChargerDataProvider.setRows(null);
		mBsnChargerDataProvider.setRows(data.data);
		setTimeout(function() {
			//담당자 그리드View refresh
			mBsnChargerGridView.refresh();
		}, 10);
		
	}
	
	$.adminSearchErrCallBack = function (errorResult) {
		alert(errorResult.responesText);
	}*/
	
	//$.callRestApi(baseUrl, "get", null, $.adminSearchCallBack, $.adminSearchErrCallBack);
	$.callRestApi(baseUrl, "get", null, callback, errorCallback);
}

function modalMemberSearch(memberNo, callback, errorCallback) {
	var baseUrl = "/api/common/members/"+memberNo;
	$.callRestApi(baseUrl, "get", null, callback, errorCallback);
}

/**
 * 회원아이디로 회원 조회
 * @param memberId
 * @param callback
 * @param errorCallback
 * @returns
 */
function searchMemberById(memberId, callback, errorCallback) {
	var baseUrl = "/api/common/members/id";
	var params = {};
	params["memberId"] = memberId;
	// 기존 GET 의 /api/common/members/id/{memberId} 로 보내다가
	// 회원아이디에 이메일 형식이 들어가는 아이디가 있어 pathvariable 로 들어가면 aaa@naver.com 에서 .뒤 com이  짤림.
	// post 방식으로 변경 - PMS ITSERVICE-17952
	$.callRestApi(baseUrl, "get", params, callback, errorCallback);
}

function uploadVideo(id, callback) {
	
	var url = "/api/common/vimeo";
	var method = "post";
	
	var params = new FormData();
	
	params.append("video", $("#" + id)[0].files[0]);
	
	var sCallBack = function(videoId, textStatus, XMLHttpRequest) {
		callback(videoId);
	};
	
	var eCallBack = function(errorResult) {
		alert(errorResult.responseText);
	};
	
	$.callRestApiWithFiles(url, method, params, sCallBack, eCallBack);
	
}

_.isNotEmpty = function(val){
	return !_.isEmpty(val);
};

function openBox(boxId){
	$('#' + boxId).removeClass('jarviswidget-collapsed').children('div').slideDown('fast');
}

function hideBox(boxId){
	$('#' + boxId).addClass('jarviswidget-collapsed').children('div').slideUp('fast');
}

/* 엑셀 업로드 함수 */
function fn_excelUpload(e, excelcallback) {
	
    var files = e.target.files;
    var i, f;
    var rtndata;
    for (i = 0, f = files[i]; i != files.length; ++i) {
        var reader = new FileReader();
        var name = f.name;
        reader.onload = function (e) {
            var data = e.target.result;
            var arr;
 
            var o = "", l = 0, w = 10240;
            for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
            o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
            
            arr = o;
            
            workbook = XLSX.read(btoa(arr), { type: 'base64' });

            var result = {};
            workbook.SheetNames.forEach(function (sheetName) {
                var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], { header: "A" });
                if (roa.length > 0) {
                    result[sheetName] = roa;
                }
            });
            
//             rtndata = JSON.stringify(result.Sheet1);
			rtndata = result.Sheet1;
            excelcallback(rtndata);
            
        }
        reader.readAsArrayBuffer(f);
    }
}


/* #43526 상품정보>부대시설: textarea 엔터키 미작동
//엔터 이벤트 전체 제거
document.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		console.log("[common.js] keydown listener() is called...");
		event.preventDefault();
	}
}, true);
//*/

$(document).ajaxStart($.blockUI).ajaxStop(function(){
	$.unblockUI();
	// toggle 상태 스타일 변경
	$("label.toggle input[type=checkbox]").each(function() {
		changeToggleStyle($(this));
	});
	
});

//toggle 스타일 변경 처리
function changeToggleStyle(toggleObj){
	
	var onText = $(toggleObj.parent().find("i")[0]).attr("data-swchon-text");
	
	if(onText != "선택" && onText != "전체" && onText != "공통" && onText != "개별" &&
	   onText != "수동" && onText != "자동" &&
	   onText != "상시" && onText != "전시" && onText != "기간"){
		var colorOn = "rgba(255,255,255,.12)";
		var colorOff = "lightgray";
		
		if(onText == "N" || onText == "미전시" || onText == "미사용" || onText == "미판매"){
			colorOn = "lightgray";
			colorOff = "rgba(255,255,255,.12)";
		}
		
		if(toggleObj.is(":checked")){
			toggleObj.parent().find("i").css("background", colorOn);
		}else{
			toggleObj.parent().find("i").css("background", colorOff);
		}
	}
	
}

//toggle 이벤트 처리
$(document).on("change", "label.toggle input[type=checkbox]", function() {
	changeToggleStyle($(this));
});

/*
data-search attribute 에 값으로 'Search' 가 포함된 검색 버튼 ID값인 경우
엔터키 이벤트 시 해당 검색 버튼 조회로 연결 처리

ex)
- data-search="mBtnSearch"
- id="mBtnSearch" 
<input type="text" class="form-control" data-search="mBtnSearch" id="goodsNmM" name="goodsNmM" value="" />
<button type="button" class="btn btn-primary btn-sm" id="mBtnSearch"/>
*/
$(document).on("keydown", "input[data-search*='Search']", function() {
	if (event.keyCode === 13) {
		$("#" + $(this).attr("data-search")).trigger("click");
	}
});

window.onerror = function(e) {
	console.log(e);
	
	if($.unblockUI){
		$.unblockUI();
	}
};

function fileDownload(fileName, url){
	var link = document.createElement("a");
    link.download = name;
    link.href = "/api/common/files?fileName=" + fileName + "&url=" + url;
    link.click();
}

// 오늘일자(yyyymmdd)
function getToday() {
    var d = new Date(),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return year + month + day;
}

/**
 * 2019.01.07
 * window.open을 가독성 있게 사용하기 위해 공통 Wrapper 펑션 생성
 * post파라메터의 방식과 Callback펑션의 기능은 추후 재작업
 * usage) :
 * var url = "http://tmtournew.interpark.com/checkinnow/goods/" + goodsId + "?preview=Y";
 * var popup = new OpenPopup();
 * popup.setUrl(url);
 * popup.setTarget("tourPreview");
 * popup.setWidth(1400);
 * popup.setHeight(850);
 * popup.open();
* */
function OpenPopup() {
	this._returnValue = null;
	this._url = null;
	this._target = "popup";
	this._name = null;
	this._width = "800";
	this._height = "600";
	this._scroll = "no";
	this._resize = "no";
	this._toolbar = "no";
	this._titlebar = "no";
	this._menubar = "no";
	this._left = "50";
	this._top = "50";
	OpenPopup.prototype.setUrl = function(url) {
		this._url = url;
	};
	OpenPopup.prototype.setTarget = function(target) {
		this._target = target;
	};
	OpenPopup.prototype.setName = function(name) {
		this._name = name;
	};
	OpenPopup.prototype.setWidth = function(width) {
		this._width = width;
	};
	OpenPopup.prototype.setHeight = function(height) {
		this._height = height;
	};
	OpenPopup.prototype.setLeft = function(left) {
		this._left = left;
	};
	OpenPopup.prototype.setTop = function(top) {
		this._top = top;
	};
	OpenPopup.prototype.setScroll = function(scroll) {
		if (scroll) {
			this._scroll = "yes";
		}
	};
	OpenPopup.prototype.setResize = function(resize) {
		if (resize) {
			this._resize = "yes";
		}
	};
	OpenPopup.prototype.setToolbar = function(toolbar) {
		if(toolbar) {
			this._toolbar = "yes";
		}
	};
	OpenPopup.prototype.setTitlebar = function(titlebar) {
		if(titlebar) {
			this._titlebar = "yes";
		}
	};
	OpenPopup.prototype.setMenubar = function(menubar) {
		if(menubar) {
			this._menubar = "yes";
		}
	};
	OpenPopup.prototype.open = function() {
		this._returnValue = window.open(this._url, this._target ,
			'width=' + this._width + ', height=' + this._height +
			', menubar=' + this._menubar +  ', toolbar=' + this._toolbar +
			', scrollbars=' + this._scroll + ', resizable=' + this._resize +
			', titlebar=' + this._titlebar + ' ,left=' + this._left + ' ,top=' + this._top);
	}
}

/*
	(function($){
		//jquery serialize json으로 변환
		$.fn.serializeObject = function() {
			var obj = null;
			try {
				if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
					var arr = this.serializeArray();
					if (arr) {
						obj = {};
						jQuery.each(arr, function() {
							obj[this.name] = this.value;
						});
					}//if ( arr ) {
				}
			} catch (e) {
				alert(e.message);
			} finally {

			}
			return obj;
		};

	})(jQuery);
*/

String.prototype.replaceAll = function(org, dest) {
		return this.split(org).join(dest);
}

/*
* gridView = 페이지에서 사용중인 그리드뷰
* gridViewRole = 권한 HIDE 일때 사용할 그리드뷰
* dataProvider = 페이지에서 사용중인 데이터프로비져
* dataProviderRole = 권한 HIDE 일때 사용할 데이터프로비져
* exportGridView = 함수 실행 후 리턴 그리드뷰
* inputParams = 마스킹 해야 하는 컬럼
let inputParams = {
	"phoneNum" : "rsvctmHpNo,guestHpNo"		//전화번호
	, "name" : "rsvctmNm,guestNm"			//이름
	, "email" : ""							//이메일
	, "ip" : ""								//아이피
	, "cardNum" : ""						//신용카드번호
	, "accountNum" : ""						//계좌번호
	, "address" : ""						//상세주소
	, "ssn" : ""							//주민등록번호
	, "id" : ""								//아이디
	, "memberNum" : ""						//회원번호
};
ex)
$("#btnExcelExport").click(function () {
	let gridViewRole, dataProviderRole, exportGridView;
	let params = {
		"phoneNum" : "guestHpNo"
		, "name" : "rsvctmNm,guestNm"
	};
	async function exportStart(){
		expotGridView = await exportRoleCheck(gridView1, gridViewRole, dataProvider1, dataProviderRole, exportGridView, params);
		expotGridView.exportGrid({

		...

        });

		$("body>#dataGridRole").remove();
	}
	exportStart().then(null,function(e){alert(e);});
});
로컬서버 데이터 10만건, 이름 2개항목, 전화번호 1개항목 테스트 시간 : 약 2분.
*/ 
function exportRoleCheck(gridView, gridViewRole, dataProvider, dataProviderRole, exportGridView, inputParams){
	return new Promise(function(resolve, reject){
		$("body").append('<div id="dataGridRole" style="display: none;"></div>');

		// 엑셀다운로드용 그리드
		gridViewRole = new RealGridJS.GridView("dataGridRole");
		dataProviderRole = new RealGridJS.LocalDataProvider();
		gridViewRole.setDataSource(dataProviderRole);

		var baseUrl = "/system/rest/user/checkRole";

		function apiRequestPromis() {
			return new Promise(function(resolve, reject){
				$.callRestApi(baseUrl, "get", null, function(data){
					if(data.data.excelRole=="HIDE"){
						gridViewRole.setColumns(gridView.getColumns());
						gridViewRole.setOptions(gridView.getOptions());
						var fieldRole = new Array();
						fieldRole = dataProvider1.getFields();
						gridView.getColumns().forEach(function(v){
							if(!v.visible){
								var deleteIndex = fieldRole.findIndex(function(object){return object.orgFieldName == v.fieldName});
								if (deleteIndex > -1) fieldRole.splice(deleteIndex, 1);
							}
						});
						fieldRole.forEach(function(v){
							v.fieldName = v.orgFieldName;
						});

						function setFieldsPromise() {
							return new Promise(function(resolve, reject){
								dataProviderRole.setFields(fieldRole);
								resolve(true);
							});
						}

						function setRowsPromise(){
							return new Promise(function(resolve, reject){
								dataProviderRole.setRows(dataProvider1.getJsonRows(0,-1));
								resolve(true);
							});
						}

						function nextFunction(){
							setFieldsPromise();

							setRowsPromise();

							var params = {
								"data" : dataProviderRole.getJsonRows(0,-1)
								, "phoneNum" : inputParams.phoneNum		//전화번호
								, "name" : inputParams.name				//이름
								, "email" : inputParams.email			//이메일
								, "ip" : inputParams.ip					//아이피
								, "cardNum" : inputParams.cardNum		//신용카드번호
								, "accountNum" : inputParams.accountNum	//계좌번호
								, "address" : inputParams.address		//상세주소
								, "ssn" : inputParams.ssn				//주민등록번호
								, "id" : inputParams.id					//아이디
								, "memberNum" : inputParams.memberNum	//회원번호
							};
							$.callRestApi("/system/rest/masking", "post", params, function(dataRole){
								dataProviderRole.setRows(dataRole.data);
								exportGridView = gridViewRole;
								resolve(true);
							}, function(e){alert("통신에러. 콘솔창 확인.");console.log(e);});
						}

						/*async function nextFunction(){
							await setFieldsPromise();

							await setRowsPromise();

							var params = {
								"data" : dataProviderRole.getJsonRows(0,-1)
								, "phoneNum" : inputParams.phoneNum		//전화번호
								, "name" : inputParams.name				//이름
								, "email" : inputParams.email			//이메일
								, "ip" : inputParams.ip					//아이피
								, "cardNum" : inputParams.cardNum		//신용카드번호
								, "accountNum" : inputParams.accountNum	//계좌번호
								, "address" : inputParams.address		//상세주소
								, "ssn" : inputParams.ssn				//주민등록번호
								, "id" : inputParams.id					//아이디
								, "memberNum" : inputParams.memberNum	//회원번호
							};
							$.callRestApi("/system/rest/masking", "post", params, function(dataRole){
								dataProviderRole.setRows(dataRole.data);
								exportGridView = gridViewRole;
								resolve(true);
							}, function(e){alert("통신에러. 콘솔창 확인.");console.log(e);});
						}*/

						nextFunction();

					} else if(data.data.excelRole=="STOP"){
						reject("권한을 확인해주세요.");
						return false;
					} else {
						exportGridView = gridView;
						resolve(true);
					}

				}, function(e){alert("통신에러. 콘솔창 확인.");console.log(e);});
			});
		}

		function returnResolveFunction(givenResolve){
			apiRequestPromis();
			givenResolve(exportGridView);
		}

		/*async function returnResolveFunction(givenResolve){
			await apiRequestPromis();
			givenResolve(exportGridView);
		}*/

		returnResolveFunction(resolve).then(null,function (e) {
			reject(e);
		});
	});
}

