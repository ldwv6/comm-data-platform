jQuery(document).ready(function() {
	$(".btn-navi-left").mouseenter(function(){
		
//		$(".btn-navi-left").click();
	});
	
	$(".btn-navi-left").click(function(){
		
//		$(".btn-navi-left").animate({left:100, duration: 2000});
		
		var left_folder_len = $(".header-left").find('.fa-chevron-right').length;
		if(left_folder_len > 0){
			$(".pull-left").css("margin-left", "-155px");
		}else{
			$(".pull-left").css("margin-left", "0px");
		}
	});
	
	window.alert = function(msg, param, error, goEscapeUrl){
		var alertTitle = "";
		var alertMsg = "";
		
		if(error != null){
			if(error == '0000'){
				alertTitle = "알림";
				alertMsg = "<i class='fa fa-exclamation-circle text-danger fa-2x'></i> <span style='position:relative; top:-4px; padding-left: 7px; line-height: 27px;'>"+msg+"</span>";
			}else if(error == '5555'){
				alertTitle = "작업중";
				alertMsg = "<i class='fa fa-bug text-danger fa-2x'></i> <span style='position:relative; top:-4px; padding-left: 7px; line-height: 27px;'>"+msg+"</span>";
			}else{
				alertTitle = "오류";
				alertMsg = "<i class='fa fa-bug text-danger fa-2x'></i> <span style='position:relative; top:-4px; padding-left: 7px; line-height: 27px;'>"+msg+"</span>";
			}
		}else{
//			if(!msg.indexOf("document")){
				alertTitle = "알림";
				alertMsg = "<i class='fa fa-exclamation-circle text-danger fa-2x'></i> <span style='position:relative; top:-4px; padding-left: 7px; line-height: 27px;'>"+msg+"</span>";
//			}
		}
		
		if(error != '5555'){
			var box = bootbox.dialog({
				message: alertMsg,
				onEscape : function(){
					if(!isNull(goEscapeUrl)){
						location.href = goEscapeUrl;
					}
				},
				animate : false,
				title : alertTitle,
				buttons : {
					success : {
						label:"확인",
						className : "btn-info",
						callback: function(){
							if(!isNull(param)) param();
							$('.bootbox-close-button').click();
						}
					}
				}
			});
		}else if(error == '5555'){
			var dialog = bootbox.dialog({
			    title: '작업중',
			    message: '<p><i class="fa fa-spin fa-spinner"></i> '+msg+'</p>'
			});
//			dialog.init(function(){
//			    setTimeout(function(){
//			        dialog.find('.bootbox-body').html('I was loaded after the dialog was shown!');
//			    }, 3000);
//			});
		}
	};
	
	window.confirm = function(msg, param, goEscapeUrl){
		bootbox.dialog({
			message: "<i class='fa fa-check text-success fa-2x'></i> <span style='position:relative; top:-4px; padding-left: 7px;'>"+msg+"</span>",
			onEscape : function(){
				if(!isNull(goEscapeUrl)){
					location.href = goEscapeUrl;
				}
			},
			animate : false,
			title : "확인",
			buttons : {
				cancel : {
					label:"취소"
				},
				success : {
					label:"확인",
					className : "btn-info",
					callback: function(){
						if(!isNull(param)) param();
					}
				}
			}
		});
	};
	
	//footer position
	//$('.mainwrapper').css('min-height',$(window).height()-76);
	
	$.fn.exists = function () //존재하는지 체크.
	{
		if ($(this).length)
		{
			return true;
		}
		else
		{
			return false;
		}
	};
	
	$.fn.center = function(ani) //jquery오브젝트 센터잡기
	{
	
		var _H = $(window).height();
		var _W = $(window).width();
		$(this).each(function(){
			var h = Math.floor((_H/2) - ($(this).outerHeight(false)/2) + $(window).scrollTop());
			var w = Math.floor((_W/2) - ($(this).outerWidth(false)/2) + $(window).scrollLeft());
			var position = "absolute";
			if ($(this).css("position") == "fixed") position = "fixed";
			if (!ani)
			{
				$(this).css({
					position : position,
					top : h-30,
					left : w
				});
			}
			else
			{
				$(this).css("position", position).animate({
					"top" : h,
					"left" : w
				});
			}
		});
	};
	
	$.fn.posPop = function() //레이어 팝업 위치
	{
	
		var _W = $(window).width();
		$(this).each(function(){
			var w = Math.floor((_W/2) - ($(this).outerWidth(false)/2) + $(window).scrollLeft());
			var position = "absolute";
			if ($(this).css("position") == "fixed") position = "fixed";
			$(this).css({
				position : position,
				top : 100,
				left : w
			});
		});
	};
	
	$.ajaxSetup({
		beforeSend:function(xhr){
			xhr.setRequestHeader("AJAX", true);
		},
		error:function(xhr, status, err){
			if(xhr.status == 401){
				location.href="/logout";
			}else if(xhr.status == 403){
				location.href="/logout";
			}
		}
	});
	
	//input - class ->.chkLenText 추가, span -> <span id="byte_view_pbrlPhrs_1">0</span><span id="input_char_pbrlPhrs_1">/50</span> 추가
	$('body').on('keyup', '.chkLenText', function(){
		//const _chkTextLenMsg = "최대 입력가능한 글자수를 초과하였습니다.";
		var _chkTextLenMsg = "최대 입력가능한 글자수를 초과하였습니다.";
		
		var idNm = $(this).attr('id');
		var _view = Number(pubByteCheckTextarea(idNm));
		$("#byte_view_"+idNm).text(_view);
		
		//글자수 
		var textCnt = $('#'+idNm).siblings().eq(1).text().replace('\/', '');
		if(Number(_view) > Number(textCnt)){
			
			alert(_chkTextLenMsg);
			
			$("#input_char_"+idNm).css('color', 'red');
			$("#byte_view_"+idNm).css('color', 'red');
		}else{
			$("#input_char_"+idNm).css('color', 'black');
			$("#byte_view_"+idNm).css('color', 'black');
		}
	});
	
});


function layerPop(url, params, func) //레이어창 띄우기
{
	if (!$("#popLayer").exists())
	{
		if($("#modalLayer").exists())
		{
			$("#modalLayer").before("<div id=\"popLayer\"></div>");
		}
		else
		{
			$("body").append("<div id=\"popLayer\"></div>");
		}
	}

	$.ajax({
		url : url,
		type : "post",
		data : params,
		error:function(xhr, status, err){
			closePop();
			alert(xhr.status)
			if(xhr.status == 401){
				location.href="/logout";
			}else if(xhr.status == 403){
				location.href="/logout";
			}
		},
		success : function(data){
			
			var $popLayer = $("#popLayer");
			$popLayer.html(data);
			
			if(data.indexOf('권한이 없습니다') > -1) return;
			
			if(data.indexOf('개인정보취급이 허용된 PC') > -1) return;
			
			if (!$("#dim").exists()) $popLayer.prepend("<div id=\"dim\"></div>").show();
			if ($("#popLayer .close").exists()) {$("#popLayer .close").click(function () { closePop(); });} 
			if ($("#popLayer .closeBtn").exists()) {$("#popLayer .closeBtn").click(function () { closePop(); });} 
			
			$("#popLayer .popup").show().posPop();
			

			//레이어가 창보다 클 경우
			if ($(".popup").offset().top < $(window).scrollTop() + 10) $(".popup").css("top", $(window).scrollTop() + 10);
			if ($(".popup").outerHeight() >= $(window).height())
			{
				var padding = parseInt($(".popup").css("paddingTop")) + parseInt($(".popup").css("paddingBottom"));
				var border = parseInt($(".popup").css("borderTopWidth")) + parseInt($(".popup").css("borderBottomWidth"));
				$(".popup").height($(window).height() - padding - border - 40);
				$(".popup").css("overflow-y", "auto");
			}

			if ($(".popup").width() >= $(window).width())
			{
				var padding = parseInt($(".popup").css("paddingLeft")) + parseInt($(".popup").css("paddingRight"));
				var border = parseInt($(".popup").css("borderTopLeft")) + parseInt($(".popup").css("borderBottomRight"));
				$(".popup").width($(window).width() - padding - border - 40);
				$(".popup").css("overflow-x", "auto");
			}

			if ($(".popup").offset().top < 10) $(".popup").css("top", 10);
			if ($(".popup").offset().left < 10) $(".popup").css("left", 10);

			if (func != null) func.call();
		}
		
	});
}

function isNull(obj){
	return (typeof obj != "undefined" && obj != null && obj !="")?false:true;
}

function closePop()
{
	$(".popup").each(function(){
		$(this).remove();
	});
	$(document).unbind("keydown");
	$(document).unbind("keyup");
	$("#popLayer").hide();
}

function innerPop(url, params) //레이어 팝업이 열려있는 상태에서 안으로 또 다른 레이어 창을 띄울때(이너팝)
{
	if (!$("#innerPopLayer").exists())
	{
		$("#popLayer").append("<div id=\"innerPopLayer\"></div>");
	}
	$.ajax({
		url : url,
		data: params,
		type : "post",
		success : function (data){
			$("#innerPopLayer").html(data).prepend("<div id=\"iDim\"></div>").show();
			if ($("#innerPopLayer .close").exists()) {$("#innerPopLayer .close").click(function () { closeInnerPop(); });} 
			if ($("#innerPopLayer .closeBtn").exists()) {$("#innerPopLayer .closeBtn").click(function () { closeInnerPop(); });}
			$("#innerPopLayer .popup").show().posPop();
		}
	});
}

function closeInnerPop() //이너팝 없애기
{
	$("#innerPopLayer").remove();
}

function tabNav(url, obj, params, prog, func)
{
	var $tab = $();
	
	if (obj instanceof jQuery)
	{
		$tab = obj;
	}
	else
	{
		$tab = $(obj).parents('.tab-pane').eq(0);
	}

	var tmpId = $(obj).attr('id');

	var dataArray = new Array();
	if(get_version_of_IE() == 'ie9'){
		$('[placeholder]').each(function(i) {
			var _input_name = new Object();
			var _ph_name = new Object();
			 
			if($.type($(this).attr('name')) != 'undefined'){
					 var info = new Object();
					 if ($(this).val() == $(this).attr("placeholder")) {
						 info.name = $(this).attr('name');
						 info.ph_name = $(this).attr('placeholder');
						 dataArray.push(info); 
					 }
			}
        });
	};
	
	$.ajax({
		url : url,
		type : "post",
		data : params,
		beforeSend:function(xhr){
			
			xhr.setRequestHeader("AJAX", true);
			
			if (!$("#dim2_"+ tmpId).exists()){
				$(".dimWrap").append("<div id=\"dim2_"+tmpId+"\" style=\"cursor:wait; width:200%; height:200%; position:fixed; top:0px; left:0px; filter: alpha(Opacity:60) !important; opacity: 0.6 !important; -webkit-opacity: 0.7!important; filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=50); z-index:1002;\"></div>").show();
			}else{
				$('#dim2_'+tmpId).show();
			}
			
		},
		success : function (data){
			$tab.css({
				height : ""
			});
			
			$tab.html(data);
			
		},
		complete:function(){
			$('#dim2_'+tmpId).hide();
			$(".tooltip").hide();
			if (func != undefined) func();
			
			
			if(get_version_of_IE() == 'ie9'){
				for (var i = 0; i < dataArray.length; i++) {
					
					var _name = dataArray[i].name; 
					
//		 			alert(_name)
					
					var _ph_name = dataArray[i].ph_name;
					$('[name = '+_name+']').val(_ph_name);
//					$('[name = '+_name+']').click();
					
//		 			alert($('#'+_name).val());
				}
			}
		},
		error:function(xhr, status, err){
			if(xhr.status == 401){
				location.href="/logout";
			}else if(xhr.status == 403){
				location.href="/logout";
			}else if(xhr.status == 400){
				
			}
		}
	});
}


/*********************************************************************
*
* console 관련 함수들
*
*********************************************************************/
var log, warn, error, debug, trace;
if (!window.console)
{
	log = function(str){ return str; };
	warn = function(str){ return str; };
	error = function(str){ return str; };
	debug = function(str){ return str; };
	trace = function(){};
	window.console = { log : log, warn : warn, error : error, debug : debug, trace : trace };
}
else if (window.console.log.bind)
{
	log = console.log.bind(console);
	if (window.console.warn) warn = console.warn.bind(console);
	if (window.console.error) error = console.error.bind(console);
	if (window.console.debug)
	{
		debug = console.debug.bind(console);
	}
	else
	{
		debug = console.log.bind(console);
	}

	if (window.console.trace)
	{
		trace = console.trace.bind(console);
	}
	else
	{
		trace = console.log.bind(console);
	}
}
else
{
	log = function (str) { console.log(str); };
	warn = function (str) { console.warn(str); };
	error = function (str) { console.error(str); };
	debug = function (str)
	{
		if (console.debug)
		{
			console.debug(str);
		}
		else if (console.warn)
		{
			console.warn(str);
		}
		else
		{
			console.log(str);
		}
	};

	trace = function ()
	{
		if (console.trace) console.trace();
	};
};

//----------------------------------------------
//숫자만 입력: 
//@param: dcptYn  소수점허용 여부
//----------------------------------------------
function onlyNumberInput(dcptYn) 
{
	var code = window.event.keyCode;
	if ( (dcptYn == "Y" && code == 110) || (code > 34 && code < 41) || (code > 47 && code < 58) || (code > 95 && code < 106) || code == 8 || code == 9 || code == 13 || code == 46 || code == 59 ) 
	{
		window.event.returnValue = true;
		return;
	}
	window.event.returnValue = false;
}

$.fn.digits = function(){ 
    return this.each(function(){ 
        $(this).val( $(this).val().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") ); 
    });
};


function get_version_of_IE () { 

	 var word; 

	 var agent = navigator.userAgent.toLowerCase(); 
	 
	 var _ver;
	 
	 // IE old version ( IE 10 or Lower ) 
	 if ( navigator.appName == "Microsoft Internet Explorer" ){
		 
		 if(agent.search( "msie 9.0" ) > -1 || agent.search( "msie 8.0" ) > -1 ){
			 _ver = "ie9";
		 }else{
			 _ver = "ie10";
		 }
	 }  
	 // IE 11 
	 else if ( agent.search( "trident" ) > -1 ){
		 _ver = "ie11";
	 }  
	 // Microsoft Edge  
	 else if ( agent.search( "edge/" ) > -1 ){ 
		 _ver = "ie_edge";
	 } 

	 return _ver; 
}

function pubByteCheckTextarea(oid){
	//let $str = $("#"+oid).val();
	var $str = $("#"+oid).val();
	stringByteLength = $str.length;
	return stringByteLength;
}

/* true - only number */
function numberChk(str) {
	//const regexp = /^[0-9]*$/;
	var regexp = /^[0-9]*$/;
	return regexp.test(str);
}

// static/js/smartAdmin/biz/common.js 에 동일 함수 존재하여 주석처리
// // comTypeCd-공통유형코드, id- select박스 id, selectType- (ALL전체추가, ONE선택추가, ''조회데이터만), selectedValue-최초select노출값, sortType=ASC,DESC(DEFAULT:ASC), callback-콜백함수
// function drawCommonCodeFilter(comTypeCd, id, selectType, selectedValue, sortType, callback) {
//
// 	$.ajax({
// 		url: "/getCodeList",
// 		type: "GET",
// 		data: {
// 			comTypeCd: comTypeCd
// 			,sortType : sortType
// 		},
// 		success: function (data) {
// 			var options = "";
//
// 			if(selectType == "ALL") {
// 				options += "<option value=''>전체</option>";
// 			} else if(selectType == "ONE") {
// 				options += "<option value=''>선택</option>";
// 			}
//
// 			/*if(tp === "F" ) {
//                 //DATA Filtering
//                 data.data = _.filter(data.data, function(v) {
//                     return v.cdTy.startsWith(filterValue);
//                 });
//             } else if(tp === "D") {
//                 //DATA DELETE
//                 data.data = _.filter(data.data, function(v) {
//                     //ex XXXX|XXXX
//                     return _.indexOf(filterValue, v.cdTy) == -1;
//                 });
//             }*/
//
// 			//(list).chain().sortBy('name').reverse().sortBy('totInHouseCnt').reverse().value();
// 			/*if(!_.isUndefined(sortType) && !_.isNull(sortType)) {
//                 data.data = _.sortBy(data.data, sortType);
//                 if(!_.isUndefined(orderBy) && !_.isNull(orderBy) && orderBy == "DESC")
//                     data.data= data.data.reverse();
//             }*/
//
// 			for(var i=0; i<data.data.length; i++) {
// 				var item = data.data[i];
// 				options += "<option value='" + item.comCd + "'>" + item.comCdNm + "</option>";
// 			}
//
// 			$("#" + id).html(options);
//
// 			if(selectedValue != null) {
// 				$("#" + id).val(selectedValue);
// 				$("#" + id).trigger("change");
// 			}
//
// 			if(callback != null) {
// 				callback();
// 			}
//
// 		},
// 		complete: function () {
//
// 		}
// 	});
// }