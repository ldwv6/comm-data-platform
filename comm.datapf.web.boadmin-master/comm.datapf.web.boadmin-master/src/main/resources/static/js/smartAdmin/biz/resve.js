(function(w) {

	var _resve = {};
	w._resve = _resve;
	
	//예약관리 공통함수
	_resve.fn = function(){
		
		test = function(data, self){
			
//			self.target.html("");
			self.dataProvider.setRows(null);
			
			if(data.data.length > 0){
				console.log(data.data[0]);
				self.dataProvider.setRows(data.data);
				self.drawNavigation(data.links);
			}
			
		}
		
		//hiddenType으로 날짜 생성
		createDate = function (formId) {
			var formTag = 'body';
			if(!_.isUndefined(formId)) formTag = formId;
			
			_.each($(formTag+" ._datePicker"), function(e,i){
				var dateVal = $(e).val().split('-').join('');
				if(_.isElement($(e).parent().find("input[name='"+$(e).attr('id')+"']")[0])){
					//element name 있으면 value값수정
					$($("input[name='"+$(e).attr('id')+"']")).val(dateVal);
				}else{
					//element name 없으면 추가
					$(e).parent().append('<input type="hidden" name="'+$(e).attr('id')+'" value="'+dateVal+'"/>');
				}
			});
		}
		
		search = function(formId){
			
			var formTag = 'body';
			if(!_.isUndefined(formId)) formTag = formId;
			
			_.each($(formTag+" ._datePicker"), function(e,i){
				
				var dateVal = $(e).val().split('-').join('');
				if(_.isElement($(e).parent().find("input[name='"+$(e).attr('id')+"']")[0])){
					//element name 있으면 value값수정
					$($("input[name='"+$(e).attr('id')+"']")).val(dateVal);
				}else{
					//element name 없으면 추가
					$(e).parent().append('<input type="hidden" name="'+$(e).attr('id')+'" value="'+dateVal+'"/>');
				}
				
				//전체기간검색이 있으면, 전체기간검색이 체크되어 있으면 hidden 값을 공백으로 처리함.
				if ($("input[name=allDate]").length > 0) {
					if($("input[name=allDate]:checked")){
						$("input[name="+$(e).attr('id')+"]").val('');
					}
				} 
			});
			
			page.search();
			
		}
		
		monthOfDay = function(el) {
			//default : today
			var dt = new Date();
			var day = dt.getDate();
			var month = dt.getMonth();
			var years = dt.getFullYear();
			var $div = $(el).parent().parent().find('._datePicker');

			if ($div.hasClass('_from')) {
				if ($(el).hasClass('_1d')) {
					day = day - 1;
				} else if ($(el).hasClass('_1m')) {
					month -= 1;
				} else if ($(el).hasClass('_2m')) {
					month -= 2;
				} else if ($(el).hasClass('_3m')) {
					month -= 3;
				}
			} else if ($div.hasClass('_to')) {
				if ($(el).hasClass('_1d')) {
					day = day + 1;
				} else if ($(el).hasClass('_1m')) {
					month += 1;
				} else if ($(el).hasClass('_2m')) {
					month += 2;
				} else if ($(el).hasClass('_3m')) {
					month += 3;
				}
			}
			
			$(el).parent().parent().find('._datePicker').datepicker("setDate", new Date(years, month, day));
			
		}
		
		makeResveDetailNo = function(resveSeq, itemUnitSeq, itemNo){
			if(_.isNull(itemUnitSeq) || _.isUndefined(itemUnitSeq)) itemUnitSeq = "00";
			if(_.isNull(itemNo) || _.isUndefined(itemNo)) itemNo = "00";
			//문자열변환
			resveSeq 	= resveSeq+'';
			itemUnitSeq = itemUnitSeq+'';
			itemNo 		= itemNo+'';
			
			return resveSeq +'-'+ itemUnitSeq.lpad(2, 0) + itemNo.lpad(2,0);
		}
		
		// 구분자 ( 3|4|5 )
		makeChildAgeTxt = function(age){
			
			if(age == null || age == '') return age;
			
			var strArr = age.split('|');
			strArr = _.sortBy(strArr);
			var resultTxt = "";
			if(strArr.length == 1){
				resultTxt = "(만 "+_.first(strArr)+"세)"
			}else if(strArr.length > 1){
				resultTxt = "(만 "+_.first(strArr)+"세-"+_.last(strArr)+"세)"
			}
			return resultTxt;
		}
		
		//초기화
		/* formId, checkeBox Default, check 제외대상 */
		initPage = function (formId, checked, exclude) {
			$.each($("#" + formId).find("input, select"), function(i, val) {
				var tagType = $(this).prop("type");
				
				if (tagType === "hidden" || tagType === "text") {
					$(this).val("");
				} else if (tagType === "select-one") {
					$(this).find("option:first").prop("selected", true);
				} else if (tagType === "radio") {
					$("input[name=" + $(this).attr('name') + "]")[0].checked = true
				} else if (tagType === "checkbox") {
					$(this).prop("checked", checked);
					
					if (!_resve.util.isNull(exclude) && !_resve.util.isEmpty(exclude)) {
						if (exclude.findIndex(x => x === $(this).attr("id")) === 0) {
							$(this).prop("checked", !checked)
						}
					}
				}
			});

			var datePicker = $("#" + formId + " ._datePicker");
			
			if (datePicker.length > 0) {
				$.each(datePicker, function(i, val) {
					if ($(this).attr("id") === "startDate") {
						$(this).datepicker().datepicker("setDate", new Date());
					} else if ($(this).attr("id") === "endDate") {
						$(this).datepicker().datepicker("setDate", 7)
					}
				});
			}
		}
		
		//금액 타입
		priceFormat = function (number) {
			return String(number).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		
		return {
			test:test
			,search:search
			,monthOfDay:monthOfDay
			,makeResveDetailNo:makeResveDetailNo
			,makeChildAgeTxt:makeChildAgeTxt
			,createDate:createDate
			,priceFormat:priceFormat
			,initPage:initPage
		};
	}();
	
	_resve.draw = function(){
		
		//예약경로 셀렉트박스 Draw
		drawBcncResveCours = function(service, id, selectType, selectedValue){
			var data = {
				service: service
			};
			$.callRestApi("/bcncs/resveCours", "get", data, function(data, textStatus, XMLHttpRequest){
				
				var options = "";
				
				if(selectType == "ALL"){
					options += "<option value=''>전체</option>";
				}else if(selectType == "ONE"){
					options += "<option value=''>선택</option>";
				}

				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.searchCd) + "'>" + item.bcncNm + "</option>";
				}
/*				
 				options += "<option value='MB'>모바일 전체(회원)</option>";
				options += "<option value='NOMEM'>모바일 전체(비회원)</option>";
*/
				$("#" + id).html(options);
				
				if(selectedValue != null){
					$("#" + id).val(selectedValue);
					$("#" + id).trigger("change");
				}
				
				
			}, function(errorResult){
				alert("코드 조회 실패");
			});
		}
		
		//예약경로 셀렉트박스 Draw
		drawAfiliatesCours = function(id, selectType, data, selectedValue){
			$.callRestApi("/bcncs/bcncHistories", "get", data, function(data, textStatus, XMLHttpRequest){
				var options = "";
				
				if(selectType == "ALL"){
					options += "<option value=''>전체</option>";
				}else if(selectType == "ONE"){
					options += "<option value=''>선택</option>";
				}

				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.bcncId) + "'>" + item.bcncNm + "</option>";
				}
/*				
 				options += "<option value='MB'>모바일 전체(회원)</option>";
				options += "<option value='NOMEM'>모바일 전체(비회원)</option>";
*/
				$("#" + id).html(options);
				
				if(selectedValue != null){
					$("#" + id).val(selectedValue);
					$("#" + id).trigger("change");
				}
				
				
			}, function(errorResult){
				alert("코드 조회 실패");
			});
		}
		
		//광고거래처 셀렉트박스 Draw
		drawBcncIpps = function(service, id, selectType, selectedValue){
			
			var data = {
				service: service
			};
			$.callRestApi("/IPPs", "get", data, function(data, textStatus, XMLHttpRequest){
				
				var options = "";
				
				if(selectType == "ALL"){
					options += "<option value=''>전체</option>";
				}else if(selectType == "ONE"){
					options += "<option value=''>선택</option>";
				}
				
				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.ippCd) + "'>" + item.ippNm + "</option>";
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
		
		//무통장가능은행목록 조회 Draw
		drawDpstBank = function(service, id, selectType, selectedValue){
			
			var data = {
				service: service
			};
			$.callRestApi("/resves/dpst/bank", "get", data, function(data, textStatus, XMLHttpRequest){
				var options = "";
				if(selectType == "ALL"){
					options += "<option value=''>전체</option>";
				}else if(selectType == "ONE"){
					options += "<option value=''>선택</option>";
				}
				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.globalBankCd) + "'>" + item.bankNm + "</option>";
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
		
		//환불은행목록 조회 Draw
		drawRefndBank = function(service, id, selectType, selectedValue){
			
			var data = {
				service: service
			};
			$.callRestApi("/resves/refnd/bank", "get", data, function(data, textStatus, XMLHttpRequest){
				var options = "";
				if(selectType == "ALL"){
					options += "<option value=''>전체</option>";
				}else if(selectType == "ONE"){
					options += "<option value=''>선택</option>";
				}
				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.bankCd) + "'>" + item.bankNm + "</option>";
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
		
		//예약등록 외부제휴채널 목록 조회 Draw
		drawResveBcncs = function(id, data){
			$.callRestApi("/resve/bcncs", "get", data, function(data, textStatus, XMLHttpRequest){
				var options = "<option value=''>선택</option>";
				for(var i=0; i<data.data.length; i++){
					var item = data.data[i];
					options += "<option value='" + _resve.util.trim(item.bcncId) + "'>" + item.bcncNm + "</option>";
				}
				$("#" + id).html(options);
			}, function(errorResult){
				alert("코드 조회 실패");
			});			
		}	
		
		return {
			drawBcncResveCours:drawBcncResveCours
			,drawAfiliatesCours:drawAfiliatesCours
			,drawBcncIpps:drawBcncIpps
			,drawDpstBank:drawDpstBank
			,drawRefndBank:drawRefndBank
			,drawResveBcncs:drawResveBcncs
		};
	}();
	
	
	_resve.util = function(){
		
		var o = {};
		
		o.isNull = function(val) {
			return _.isUndefined(val) || _.isNull(val);
		};

		//StringUtil
		//주어진 문자열이 null 또는 공백일 경우 참 반환
		o.isEmpty = function(s) {
			if (!_.isString(s)) return false;
			if (s == null || s === '') {
				return true;
			}
			return false;
		};

		//입력된 문자열이 숫자와 알파벳로만 구성되어있는지 체크
		o.isAlphaNumeric = function(s) {
			if (!_.isString(s)) return false;
			return /^[A-Za-z0-9]+$/.test(s);
		};

		//입력된 문자열이 숫자로만 구성되어있는지 체크
		o.isNumeric = function(s) {
			if (!_.isString(s)) return false;
			return /^[0-9]+$/.test(s);
		};

		//입력된 문자열이 정수로만 구성되어있는지 체크
		o.isInteger = function(s) {
			if (!_.isString(s)) return false;
			return /^[-|+]?\d+$/.test(s);
		};

		//입력된 문자열이 알파벳로만 구성되어있는지 체크
		o.isAlpha = function(s) {
			if (!_.isString(s)) return false;
			return /^[A-Za-z]+$/.test(s);
		};

		//입력된 문자열이 한글로만 구성되어 있는지 체크
		o.isHangul = function(s) {
			if (!_.isString(s)) return false;
			return /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+$/.test(s);
		};

		//해당하는 문자열에 대한 길이 반환
		o.getLength = function(s) {
			if (!_.isString(s)) return 0;
			return s.length;
		};

		//해당하는 문자열에 대해서 byte 단위에 대해서 길이 계산해서 총 길이 반환
		//한글은 3Byte
		o.getByteLength = function(s) {
			if (!_.isString(s)) return 0;
			var b, i, c = 0;
			for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
			return b;
		};

		//문자열의 왼쪽의 공백 문자열 제거
		o.leftTrim = function(s) {
			if (!_.isString(s)) return '';
			return s.replace(/^\s+/, "");
		};

		//문자열의 오른쪽의 공백 문자열 제거
		o.rightTrim = function(s) {
			if (!_.isString(s)) return '';
			return s.replace(/\s+$/, "");
		};

		//문자열의 공백 문자열 제거
		o.trim = function(s) {
			if (!_.isString(s)) return '';
			return s.replace(/^\s+|\s+$/g, "");
		};

		//해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 왼쪽부터 공백으로 채워넣는다.
		o.leftPad = function(s, len, c) {
			if (!_.isString(s) || !_.isString(c)) return '';
			if (!_.isNumber(len) || len <= o.getLength(s)) return s;
			if (o.getLength(c) != 1) return s;

			var padLen = len - o.getLength(s);
			for (var i = 0; i < padLen; i++) {
				s = c + s;
			}
			return s;
		};

		//해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 오른쪽부터 지정된 문자로 채워넣는다.
		o.rightPad = function(s, len, c) {
			if (!_.isString(s) || !_.isString(c)) return '';
			if (!_.isNumber(len) || len <= o.getLength(s)) return s;
			if (o.getLength(c) != 1) return s;

			var padLen = len - o.getLength(s);
			for (var i = 0; i < padLen; i++) {
				s += c;
			}
			return s;
		};

		o.addCommas = function(s) {
			if (_.isNumber(s)) s = '' + s;
			if (!_.isString(s)) return '';

			var x, x1, x2 = '';
			x = s.split('.');
			x1 = x[0];
			x2 = x.length > 1 ? '.' + x[1] : '';
			var rgx = /(\d+)(\d{3})/;
			while (rgx.test(x1)) {
				x1 = x1.replace(rgx, '$1' + ',' + '$2');
			}
			return x1 + x2;
		};

		//입력된 문자열이 주어진 문자열과 일치하는 모든 문자열을 바꿔야할 문자열로 변경
		o.replaceAll = function(s, bs, as) {
			if (!_.isString(s) || !_.isString(bs) || !_.isString(as)) return '';
			return s.split(bs).join(as);
		};

		//HTML tag가 들어있는 문자열에 대해 unescape해준다.
		o.replaceHtmlEscape = function(s) {
			if (!_.isString(s)) return '';
			return _.escape(s);
		};

		//unescaped된 문자열에 대해 HTML tag 형태로 바꿔준다.
		o.removeEscapeChar = function(s) {
			if (!_.isString(s)) return '';
			return _.unescape(s);
		};

		//DateUtil
		//입력된 일자가 유효한 일자인지 체크
		o.isDate = function(s) {
			if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 8) return false;

			var year = Number(s.substring(0, 4));
			var month = Number(s.substring(4, 6));
			var day = Number(s.substring(6, 8));

			if (1 > month || 12 < month) {
				return false;
			}

			var lastDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			var lastDay = lastDays[month - 1];

			if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
				lastDay = 29;
			}

			if (1 > day || lastDay < day) {
				return false;
			}

			return true;
		};

		//입력된 시간이 유효한지 체크
		o.isTime = function(s) {
			if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 6) return false;

			var h = Number(s.substring(0, 2));
			var m = Number(s.substring(2, 4));
			var s = Number(s.substring(4, 6));

			if (0 > h || 23 < h) {
				return false;
			}

			if (0 > m || 59 < m) {
				return false;
			}

			if (0 > s || 59 < s) {
				return false;
			}

			return true;
		};
		
		//입력된 시간이 유효한지 체크
		o.isHour = function(s) {
			if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 2) return false;

			var h = Number(s);

			if (0 > h || 23 < h) {
				return false;
			}
			
			return true;
		};
		
		//입력받은 일자를 Date형으로 변환
		o.strToDate = function(s) {
			if (!_.isString(s)) return null;

			var array = s.split(' ');
			var date = array[0];
			var time = '000000';

			if (2 == array.length) {
				time = array[1];
			}

			if (!o.isDate(date)) return null;
			if (!o.isTime(time)) return null;

			var year = date.substring(0, 4);
			var month = Number(date.substring(4, 6)) - 1;
			var day = date.substring(6, 8);
			var hour = time.substring(0, 2);
			var minute = time.substring(2, 4);
			var second = time.substring(4, 6);

			return new Date(year, o.leftPad('' + month, 2, '0'), day, hour, minute, second);
		};

		o.formatDate = function formatDate(d, f) {
			if (!_.isString(f)) return '';

			if (_.isDate(d)) {
				return f.replace(/(yyyy|yy|mm|dd|hh24|hh|mi|ss|fff|a\/p)/gi, function($1) {
					switch ($1) {
						case "yyyy":
							return '' + d.getFullYear();
						case "yy":
							return o.leftPad('' + (d.getFullYear() % 1000), 4, '0').substring(2, 4);
						case "mm":
							return o.leftPad('' + (d.getMonth() + 1), 2, '0');
						case "dd":
							return o.leftPad('' + d.getDate(), 2, '0');
						case "hh24":
							return o.leftPad('' + d.getHours(), 2, '0');
						case "hh":
							return o.leftPad('' + ((h = d.getHours() % 12) ? h : 12), 2, '0');
						case "mi":
							return o.leftPad('' + d.getMinutes(), 2, '0');
						case "ss":
							return o.leftPad('' + d.getSeconds(), 2, '0');
						case "fff":
							return o.leftPad('' + d.getMilliseconds(), 3, '0');
						case "a/p":
							return d.getHours() < 12 ? "오전" : "오후";
						default:
							return $1;
					}
				});
			} else if (_.isString(d)) {
				return formatDate(o.strToDate(d), f);
			}

			return '';
		};

		//입력받은 일자의 요일 반환
		o.getDayOfWeek = function(s) {
			if (!o.isDate(s)) return '';
			var week = ['일', '월', '화', '수', '목', '금', '토'];
			return week[o.strToDate(s).getDay()];
		};

		//입력받은 두 날짜 사이의 일자 계산
		o.getDay = function(sd, ed) {
			if (!o.isDate(sd) || !o.isDate(ed)) return -1;
			if (Number(ed) < Number(sd)) return -2;

			var newSd = o.strToDate(sd);
			var newEd = o.strToDate(ed);
			var diffTime = newEd.getTime() - newSd.getTime();

			return Math.floor(diffTime / (1000 * 60 * 60 * 24));
		};

		//입력받은 일자에 대해서 해당 일만큼 더한 일자 반환. 마이너스 일자는 입력받은 일자보다 이전의 일자로 계산해서 반환
		o.addDays = function(s, d, f) {
			if (!o.isDate(s) || !_.isNumber(d)) return '';
			var newDt = o.strToDate(s);
			newDt.setDate(newDt.getDate() + (d));
			return o.formatDate(newDt, f || 'yyyymmdd');
		};

		//입력받은 일자에 대해서 해당 개월수만큼 더한 일자 반환. 마이너스 개월수는 입력받은 일자보다 이전 일자로 계산해서 반환
		o.addMonths = function(s, m, f) {
			if (!o.isDate(s) || !_.isNumber(m)) return '';
			var newDt = o.strToDate(s);
			newDt.setMonth(newDt.getMonth() + (m));
			return o.formatDate(newDt, f || 'yyyymmdd');
		};

		//입력받은 일자에 대해서 해당 년수만큼 더한 일자 반환. 마이너스 년수는 입력받은 일자보다 이전 일자로 계산해서 반환
		o.addYears = function(s, y, f) {
			if (!o.isDate(s) || !_.isNumber(y)) return '';
			var newDt = o.strToDate(s);
			newDt.setFullYear(newDt.getFullYear() + (y));
			return o.formatDate(newDt, f || 'yyyymmdd');
		};

		//입력받은 일자에 마지막 일 반환
		o.getLastDay = function(s, f) {
			if (!o.isDate(s)) return '';
			var newDt = o.strToDate(s);
			newDt.setMonth(newDt.getMonth() + 1);
			newDt.setDate(0);
			return o.formatDate(newDt, f || 'yyyymmdd');
		};
		
		o.checkDateOver = function(s, f) {
			if (!o.isDate(s)) return '';
			
			if(s > f){
				return false;
			}
			
			return true;
		}; 
		
		//NumberUtil
		o.strToInt = function(s) {
			if (!_.isString(s)) return 0;
			return parseInt(s, 10);
		};

		o.parseInt = function(s) {
			return parseInt(s, 10);
		};

		//ValidationUtil
		//문자열의 길이가 최소, 최대 길이 사이에 존재하는지 체크
		o.isRangeLength = function(s, min, max) {
			if (!_.isString(s) || !_.isNumber(min) || !_.isNumber(max)) return false;

			var len = o.getLength(s);
			if (min <= len && len <= max) {
				return true;
			}

			return false;
		};

		//문자열의 길이가 byte 단위로 계산했을때 최소, 최대 길이 사이에 존재하는지 체크
		o.isRangeByteLength = function(s, min, max) {
			if (!_.isString(s) || !_.isNumber(min) || !_.isNumber(max)) return false;

			var len = o.getByteLength(s);
			if (min <= len && len <= max) {
				return true;
			}

			return false;
		};

		//입력된 이메일주소가 유효한이메일주소인지 검증한다.
		o.isEmail = function(s) {
			if (!_.isString(s)) return false;
			return /^([0-9a-zA-Z]+)([0-9a-zA-Z\._-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,3}$/.test(s);
			//return /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(s);
		};
		
		o.phoneFormat = function(val) {
			if (o.isNull(val)) {
				return "";
			}
			
			val = o.replaceAll(val, "-", "");

			if(val.length == 12){
				var val = val.substring(0,4)+'-'+val.substring(4,8)+'-'+val.substring(8,12);
				return val;
			}

			return val.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
		};		
		
		return o;
		
	}();
	
/*
 	Modal... Center...
 	
    function reposition() {
        var modal = $(this),
            dialog = modal.find('.modal-dialog');
        modal.css('display', 'block');

        // Dividing by two centers the modal exactly, but dividing by three 
        // or four works better for larger screens.
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
    }
    // Reposition when a modal is shown
    $('.modal').on('show.bs.modal', reposition);
    // Reposition when the window is resized
    $(window).on('resize', function() {
        $('.modal:visible').each(reposition);
    });
*/
	
})(window);