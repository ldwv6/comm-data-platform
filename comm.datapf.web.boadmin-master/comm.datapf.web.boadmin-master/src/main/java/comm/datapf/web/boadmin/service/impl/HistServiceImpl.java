package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.service.HistService;
import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class HistServiceImpl implements HistService {

    @Override
    public HashMap<String, Object> getSearchData(HashMap<String, Object> paramMap) throws Exception {

        String strItem = "";
        String strText = "";

        if(StringUtils.isNotEmpty(paramMap.get("searchStartDate"))){
            strItem += "조회시작일자; ";
            String value = (String) paramMap.get("searchStartDate");
            if(StringUtils.contains(value, "-")){
                value = value.replaceAll("-", "");
            }
            strText += value+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchEndDate"))){
            strItem += "조회종료일자; ";
            String value = (String) paramMap.get("searchEndDate");
            if(StringUtils.contains(value, "-")){
                value = value.replaceAll("-", "");
            }
            strText += value+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchBassYear"))){
            strItem += "기준연도; ";
            strText += paramMap.get("searchBassYear")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("logTitle"))){
            strItem += paramMap.get("logTitle").toString()+"; ";
            strText += paramMap.get("logText").toString()+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchTableCd"))){
            strItem += "구분; ";
            strText += paramMap.get("searchTableCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchPgmId"))){
            strItem += "프로그램ID; ";
            strText += paramMap.get("searchPgmId")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchPgmGrp"))){
            strItem += "메뉴그룹; ";
            strText += paramMap.get("searchPgmGrp")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRqstDvsnCd"))){
            strItem += "신청구분; ";
            strText += paramMap.get("searchRqstDvsnCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchBulkTblYn"))){
            strItem += "발송형태; ";
            strText += paramMap.get("searchBulkTblYn")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchTrSendstat"))){
            strItem += "발송상태; ";
            strText += paramMap.get("searchTrSendstat")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchTrRsltstat"))){
            strItem += "발송결과; ";
            strText += paramMap.get("searchTrRsltstat")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchOutsideCd"))){
            strItem += "기관; ";
            strText += paramMap.get("searchOutsideCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchEmalSndRqstDt"))){
            strItem += "이메일발송요청일; ";
            strText += paramMap.get("searchEmalSndRqstDt")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchOtbkDrwgTrsfFwdgNo"))){
            strItem += "일괄파일전송번호; ";
            strText += paramMap.get("searchOtbkDrwgTrsfFwdgNo")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchOtbkDrwgTrsfFwdgNo"))){
            strItem += "파일전송번호; ";
            strText += paramMap.get("searchOtbkDrwgTrsfFwdgNo")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchMsgNo"))){
            strItem += "메시지번호; ";
            strText += paramMap.get("searchMsgNo")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchMsg"))){
            strItem += "전송메시지; ";
            strText += paramMap.get("searchMsg")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchCmsRcitFnniNo"))){
            strItem += "CMS접수금융기관번호; ";
            strText += paramMap.get("searchCmsRcitFnniNo")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchReprcsStatCd"))){
            strItem += "처리상태; ";
            strText += paramMap.get("searchReprcsStatCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchMembNm"))){
            strItem += "회원명; ";
            strText += paramMap.get("searchMembNm")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchMembNm2"))){
            strItem += "소유자명; ";
            strText += paramMap.get("searchMembNm2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchMembNm3"))){
            strItem += "성명; ";
            strText += paramMap.get("searchMembNm3")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchDvicOs"))){
            strItem += "장치OS; ";
            strText += paramMap.get("searchDvicOs")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchPnsSndRsltCd"))){
            strItem += "PNS발송결과; ";
            strText += paramMap.get("searchPnsSndRsltCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRespCd"))){
            strItem += "응답코드; ";
            strText += paramMap.get("searchRespCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRespCd2"))){
            strItem += "응답유형코드; ";
            strText += paramMap.get("searchRespCd2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRespMsg"))){
            strItem += "응답메시지; ";
            strText += paramMap.get("searchRespMsg")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRespNm"))){
            strItem += "응답유형; ";
            strText += paramMap.get("searchRespNm")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchRstRespCd"))){
            strItem += "처리결과응답코드; ";
            strText += paramMap.get("searchRstRespCd")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchDtctRsltDtime"))){
            strItem += "감지결과일시; ";
            strText += paramMap.get("searchDtctRsltDtime")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("searchDtctRulNo"))){
            strItem += "감지규칙번호; ";
            strText += paramMap.get("searchDtctRulNo")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DTCT_RUL_NM"))){
            strItem += "감지규칙명; ";
            strText += paramMap.get("SEARCH_DTCT_RUL_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DTCT_IFOM_CD"))){
            strItem += "감지알림; ";
            strText += paramMap.get("SEARCH_DTCT_IFOM_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DTCT_IFOM_NM"))){
            strItem += "감지알림명; ";
            strText += paramMap.get("SEARCH_DTCT_IFOM_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AFT_IFOM_CD"))){
            strItem += "후처리알림; ";
            strText += paramMap.get("SEARCH_AFT_IFOM_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RTUN_CD"))){
            strItem += "검색일자; ";
            strText += paramMap.get("SEARCH_RTUN_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("sdate"))){
            strItem += "시작일; ";
            strText += paramMap.get("sdate")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("edate"))){
            strItem += "종료일; ";
            strText += paramMap.get("edate")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CS_DATE"))){
            strItem += "추심월; ";
            strText += paramMap.get("SEARCH_CS_DATE")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BASE_DT"))){
            strItem += "기준일자; ";
            strText += paramMap.get("SEARCH_BASE_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TTLZ_BASS_CD"))){
            strItem += "조회기준; ";
            strText += paramMap.get("SEARCH_TTLZ_BASS_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COLL_DT"))){
            strItem += "수금일자; ";
            strText += paramMap.get("SEARCH_COLL_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RQST_DT"))){
            strItem += "추심일; ";
            strText += paramMap.get("SEARCH_RQST_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SYS_ACNO"))){
            strItem += "시스템계좌번호; ";
            strText += paramMap.get("SEARCH_SYS_ACNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_NO"))){
            strItem += "신용평가기관; ";
            strText += paramMap.get("SEARCH_FNNI_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SVC_TYPE_CD"))){
            strItem += "서비스유형; ";
            strText += paramMap.get("SEARCH_SVC_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_NO2"))){
            strItem += "금융기관; ";
            strText += paramMap.get("SEARCH_FNNI_NO2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_VACCOUNT_CNT"))){
            strItem += "가상계좌 건수; ";
            strText += paramMap.get("SEARCH_VACCOUNT_CNT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_CD"))){
            strItem += "금융기관코드; ";
            strText += paramMap.get("SEARCH_FNNI_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_NM"))){
            strItem += "금융기관명; ";
            strText += paramMap.get("SEARCH_FNNI_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ENG_FNNI_ABRV"))){
            strItem += "영문약어; ";
            strText += paramMap.get("SEARCH_ENG_FNNI_ABRV")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_TYPE_CD"))){
            strItem += "금융기관유형; ";
            strText += paramMap.get("SEARCH_FNNI_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AFFL_FNNI_YN"))){
            strItem += "제휴금융기관여부; ";
            strText += paramMap.get("SEARCH_AFFL_FNNI_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RLTM_RESP_CD"))){
            strItem += "응답코드; ";
            strText += paramMap.get("SEARCH_RLTM_RESP_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RESP_CD_ONLY"))){
            strItem += "오류코드만; ";
            strText += paramMap.get("SEARCH_RESP_CD_ONLY")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RTUN_CD_ONLY"))){
            strItem += "복원회원만; ";
            strText += paramMap.get("SEARCH_RTUN_CD_ONLY")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DRMC_CD_ONLY"))){
            strItem += "휴면회원만; ";
            strText += paramMap.get("SEARCH_DRMC_CD_ONLY")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AUTH_CD_YN"))){
            strItem += "회원인증; ";
            strText += paramMap.get("SEARCH_AUTH_CD_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_LMT_INS_MBDY_CD"))){
            strItem += "한도저장주체 조회포함; ";
            strText += paramMap.get("SEARCH_LMT_INS_MBDY_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AFFL_FNNI_NO"))){
            strItem += "제휴기관번호; ";
            strText += paramMap.get("SEARCH_AFFL_FNNI_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MBPNO"))){
            strItem += "휴대폰번호; ";
            strText += paramMap.get("SEARCH_MBPNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SYS_ID"))){
            strItem += "시스템ID; ";
            strText += paramMap.get("SEARCH_SYS_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_NO"))){
            strItem += "회원번호; ";
            strText += paramMap.get("SEARCH_MEMB_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_NO"))){
            strItem += "가맹점번호; ";
            strText += paramMap.get("SEARCH_MCHT_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_ID"))){
            strItem += "가맹점회원ID; ";
            strText += paramMap.get("SEARCH_MCHT_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_NO2"))){
            strItem += "가맹점회원번호; ";
            strText += paramMap.get("SEARCH_MCHT_NO2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CRDT_RQST_STAT_CD"))){
            strItem += "후결제청구상태; ";
            strText += paramMap.get("SEARCH_CRDT_RQST_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CNSL_DTIME"))){
            strItem += "상담일시; ";
            strText += paramMap.get("SEARCH_CNSL_DTIME")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CNSL_PLAC_CD"))){
            strItem += "상담처; ";
            strText += paramMap.get("SEARCH_CNSL_PLAC_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CRDT_USE_STOP_YN"))){
            strItem += "이용정지여부; ";
            strText += paramMap.get("SEARCH_CRDT_USE_STOP_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_STTL_YM"))){
            strItem += "결제년월; ";
            strText += paramMap.get("SEARCH_STTL_YM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_DT"))){
            strItem += "거래일; ";
            strText += paramMap.get("SEARCH_TR_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_NO"))){
            strItem += "거래번호; ";
            strText += paramMap.get("SEARCH_TR_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AUTH_MTHD_CD"))){
            strItem += "인증방법; ";
            strText += paramMap.get("SEARCH_AUTH_MTHD_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCTM_DT"))){
            strItem += "입금일자; ";
            strText += paramMap.get("SEARCH_RCTM_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCTM_SNO"))){
            strItem += "입금일련번호; ";
            strText += paramMap.get("SEARCH_RCTM_SNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_DTL_TYPE_CD"))){
            strItem += "거래상세유형; ";
            strText += paramMap.get("SEARCH_TR_DTL_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_DTL_TYPE_NM"))){
            strItem += "거래상세유형명; ";
            strText += paramMap.get("SEARCH_TR_DTL_TYPE_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_OPPN_MEMB_NO"))){
            strItem += "대상회원번호; ";
            strText += paramMap.get("SEARCH_TR_OPPN_MEMB_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_OPPN_MEMB_NO2"))){
            strItem += "거래상대방회원번호; ";
            strText += paramMap.get("SEARCH_TR_OPPN_MEMB_NO2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INQUIRY_RESP_CD"))){
            strItem += "조회응답; ";
            strText += paramMap.get("SEARCH_INQUIRY_RESP_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AUTH_TYPE_CD"))){
            strItem += "인증유형; ";
            strText += paramMap.get("SEARCH_AUTH_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_STAT_CD"))){
            strItem += "거래상태; ";
            strText += paramMap.get("SEARCH_TR_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_TYPE_CD"))){
            strItem += "거래유형; ";
            strText += paramMap.get("SEARCH_TR_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_NTWK_CNCL_STAT_CD"))){
            strItem += "망취소상태; ";
            strText += paramMap.get("SEARCH_NTWK_CNCL_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_TR_NO"))){
            strItem += "가맹점주문번호; ";
            strText += paramMap.get("SEARCH_MCHT_TR_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ORGN_TR_DT"))){
            strItem += "원거래일자; ";
            strText += paramMap.get("SEARCH_ORGN_TR_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ORGN_TR_NO"))){
            strItem += "원거래번호; ";
            strText += paramMap.get("SEARCH_ORGN_TR_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PRDT_NM"))){
            strItem += "상품명; ";
            strText += paramMap.get("SEARCH_PRDT_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVNT_NO"))){
            strItem += "이벤트번호; ";
            strText += paramMap.get("SEARCH_EVNT_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COUPON_SUB_TYPE_CD"))){
            strItem += "쿠폰서브유형; ";
            strText += paramMap.get("SEARCH_COUPON_SUB_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PRCS_STAT_CD"))){
            strItem += "처리상태; ";
            strText += paramMap.get("SEARCH_PRCS_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_EMAL"))){
            strItem += "이메일; ";
            strText += paramMap.get("SEARCH_MEMB_EMAL")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_START_TR_AMT"))){
            strItem += "거래금액(MIN); ";
            strText += paramMap.get("SEARCH_START_TR_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_END_TR_AMT"))){
            strItem += "거래금액(MAX); ";
            strText += paramMap.get("SEARCH_END_TR_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_START_OCCR_PRCA_AMT"))){
            strItem += "발생원금(MIN); ";
            strText += paramMap.get("SEARCH_START_OCCR_PRCA_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_END_OCCR_PRCA_AMT"))){
            strItem += "발생원금(MAX); ";
            strText += paramMap.get("SEARCH_END_OCCR_PRCA_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_START_RCVA_AMT"))){
            strItem += "미수금액(MIN); ";
            strText += paramMap.get("SEARCH_START_RCVA_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_END_RCVA_AMT"))){
            strItem += "미수금액(MAX); ";
            strText += paramMap.get("SEARCH_END_RCVA_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_STAT_CD"))){
            strItem += "회원상태; ";
            strText += paramMap.get("SEARCH_MEMB_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_STTL_MTHD_CD"))){
            strItem += "결제방법; ";
            strText += paramMap.get("SEARCH_STTL_MTHD_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IFOM_WORK_CD"))){
            strItem += "알림업무; ";
            strText += paramMap.get("SEARCH_IFOM_WORK_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IFOM_MTHD_CD"))){
            strItem += "알림방법; ";
            strText += paramMap.get("SEARCH_IFOM_MTHD_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BZPR_NM"))){
            strItem += "가맹점명; ";
            strText += paramMap.get("SEARCH_BZPR_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SNBD_NM"))){
            strItem += "간판명; ";
            strText += paramMap.get("SEARCH_SNBD_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BL_TYPE_CD"))){
            strItem += "BL유형; ";
            strText += paramMap.get("SEARCH_BL_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RNCNO"))){
            strItem += "사업자번호; ";
            strText += paramMap.get("SEARCH_RNCNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RNCNO2"))){
            strItem += "실명확인번호; ";
            strText += paramMap.get("SEARCH_RNCNO2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_RNCNO"))){
            strItem += "주민번호; ";
            strText += paramMap.get("SEARCH_MEM_RNCNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RSVN_TRSF_EXEC_FRQC_CD"))){
            strItem += "정산주기; ";
            strText += paramMap.get("SEARCH_RSVN_TRSF_EXEC_FRQC_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DFRM_BASS_CD"))){
            strItem += "지급기준; ";
            strText += paramMap.get("SEARCH_DFRM_BASS_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_TYPE_CD"))){
            strItem += "탈퇴유형; ";
            strText += paramMap.get("SEARCH_MEMB_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_RSON_CD"))){
            strItem += "탈퇴사유; ";
            strText += paramMap.get("SEARCH_MEMB_RSON_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVNT_NM"))){
            strItem += "이벤트명; ";
            strText += paramMap.get("SEARCH_EVNT_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVNT_MEMB_TYPE_CD"))){
            strItem += "고객유형; ";
            strText += paramMap.get("SEARCH_EVNT_MEMB_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVNT_AMT_TYPE_CD"))){
            strItem += "적용구분; ";
            strText += paramMap.get("SEARCH_EVNT_AMT_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ING_DATE"))){
            strItem += "기간; ";
            String tmp = "";
            if(StringUtils.equals(paramMap.get("SEARCH_ING_DATE").toString(), "1")) tmp = "진행중";
            if(StringUtils.equals(paramMap.get("SEARCH_ING_DATE").toString(), "2")) tmp = "미사용";
            if(StringUtils.equals(paramMap.get("SEARCH_ING_DATE").toString(), "3")) tmp = "종료";
            strText += tmp+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IFOM_GRP_CD"))){
            strItem += "알림그룹; ";
            strText += paramMap.get("SEARCH_IFOM_GRP_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IFOM_GRP_NM"))){
            strItem += "알림그룹명; ";
            strText += paramMap.get("SEARCH_IFOM_GRP_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IFOM_DVSN_CD"))){
            strItem += "알림구분; ";
            strText += paramMap.get("SEARCH_IFOM_DVSN_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_TYPE_CD"))){
            strItem += "공통유형코드; ";
            strText += paramMap.get("SEARCH_COM_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_TYPE_CD_NM"))){
            strItem += "공통유형명; ";
            strText += paramMap.get("SEARCH_COM_TYPE_CD_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_CD"))){
            strItem += "공통코드; ";
            strText += paramMap.get("SEARCH_COM_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_CD_NM"))){
            strItem += "공통코드명; ";
            strText += paramMap.get("SEARCH_COM_CD_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_CD_DTL"))){
            strItem += "상세코드; ";
            strText += paramMap.get("SEARCH_COM_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COM_CD_NM_DTL"))){
            strItem += "상세코드명; ";
            strText += paramMap.get("SEARCH_COM_CD_NM_DTL")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_USE_YN"))){
            strItem += "사용유무; ";
            strText += paramMap.get("SEARCH_USE_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_USE_YN2"))){
            strItem += "사용유무; ";
            strText += paramMap.get("SEARCH_USE_YN2")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BASS_DT"))){
            strItem += "실행일자; ";
            strText += paramMap.get("SEARCH_BASS_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TODAY"))){
            strItem += "실행일시; ";
            strText += paramMap.get("SEARCH_TODAY")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ADMR_ID"))){
            strItem += "관리자ID; ";
            strText += paramMap.get("SEARCH_ADMR_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ADMR_NM"))){
            strItem += "관리자명; ";
            strText += paramMap.get("SEARCH_ADMR_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ADMR_EMAL"))){
            strItem += "이메일; ";
            strText += paramMap.get("SEARCH_ADMR_EMAL")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CS_MEMB_NO"))){
            strItem += "상담원번호; ";
            strText += paramMap.get("SEARCH_CS_MEMB_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CS_MEMB_NM"))){
            strItem += "상담원명; ";
            strText += paramMap.get("SEARCH_CS_MEMB_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CS_MEMB_EMAL"))){
            strItem += "상담원ID; ";
            strText += paramMap.get("SEARCH_CS_MEMB_EMAL")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_NOTICE_ID"))){
            strItem += "공지사항ID; ";
            strText += paramMap.get("SEARCH_NOTICE_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVENT_ID"))){
            strItem += "이벤트ID; ";
            strText += paramMap.get("SEARCH_EVENT_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BLBD_DVSN_CD"))){
            strItem += "분류; ";
            strText += paramMap.get("SEARCH_BLBD_DVSN_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TITLE"))){
            strItem += "제목; ";
            strText += paramMap.get("SEARCH_TITLE")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DESCRIPTION"))){
            strItem += "설명; ";
            strText += paramMap.get("SEARCH_DESCRIPTION")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CONTENT"))){
            strItem += "내용; ";
            strText += paramMap.get("SEARCH_CONTENT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CERT_NUM"))){
            strItem += "인증요청번호; ";
            strText += paramMap.get("SEARCH_CERT_NUM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_KMC_RST_CD"))){
            strItem += "배치상태; ";
            strText += paramMap.get("SEARCH_KMC_RST_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RESP_TYPE_CD"))){
            strItem += "응답유형코드; ";
            strText += paramMap.get("SEARCH_RESP_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INQ_FNNI_RSLT_CD"))){
            strItem += "조회결과코드; ";
            strText += paramMap.get("SEARCH_INQ_FNNI_RSLT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CPSP_EX_CD"))){
            strItem += "통신사코드; ";
            strText += paramMap.get("SEARCH_CPSP_EX_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SERVICE_NO"))){
            strItem += "서비스번호; ";
            strText += paramMap.get("SEARCH_SERVICE_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RESP_VALUE_CD"))){
            strItem += "실명확인결과; ";
            strText += paramMap.get("SEARCH_RESP_VALUE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_WORK_DT"))){
            strItem += "작업일자; ";
            strText += paramMap.get("SEARCH_WORK_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ENC_YN"))){
            strItem += "암호여부; ";
            strText += paramMap.get("SEARCH_ENC_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_WORK_CD"))){
            strItem += "작업코드; ";
            strText += paramMap.get("SEARCH_WORK_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FIELD"))){
            strItem += "필드; ";
            strText += paramMap.get("SEARCH_FIELD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_WORK_NM"))){
            strItem += "작업명; ";
            strText += paramMap.get("SEARCH_WORK_NM")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BTCH_DVSN_CD"))){
            strItem += "배치구분; ";
            strText += paramMap.get("SEARCH_BTCH_DVSN_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BTCH_WORK_TYPE_CD"))){
            strItem += "작업유형; ";
            strText += paramMap.get("SEARCH_BTCH_WORK_TYPE_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BTCH_FRQC_CD"))){
            strItem += "주기; ";
            strText += paramMap.get("SEARCH_BTCH_FRQC_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BATCH_STAT_CD"))){
            strItem += "결과; ";
            strText += paramMap.get("SEARCH_BATCH_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ASMT_STAT_CD"))){
            strItem += "상태; ";
            strText += paramMap.get("SEARCH_ASMT_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ASMT_YN"))){
            strItem += "할당여부; ";
            strText += paramMap.get("SEARCH_ASMT_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCIV_CLNT"))){
            strItem += "의뢰인명; ";
            strText += paramMap.get("SEARCH_RCIV_CLNT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FROM_MEMB_NO"))){
            strItem += "출금회원번호; ";
            strText += paramMap.get("SEARCH_FROM_MEMB_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TO_MEMB_NO"))){
            strItem += "입금회원번호; ";
            strText += paramMap.get("SEARCH_TO_MEMB_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_REPRCS_RSLT_CD"))){
            strItem += "처리결과코드; ";
            strText += paramMap.get("SEARCH_REPRCS_RSLT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_VAN_RESP_CD"))){
            strItem += "응답코드; ";
            strText += paramMap.get("SEARCH_VAN_RESP_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("CPA_FROM_FNNI_NO"))){
            strItem += "출금은행; ";
            strText += paramMap.get("CPA_FROM_FNNI_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("CPA_TO_FNNI_NO"))){
            strItem += "입금은행; ";
            strText += paramMap.get("CPA_TO_FNNI_NO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("CPA_TRSF_AMT"))){
            strItem += "입금금액; ";
            strText += paramMap.get("CPA_TRSF_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RMKS"))){
            strItem += "비고; ";
            strText += paramMap.get("SEARCH_RMKS")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TRSF_RSVN_PGSS_STAT_CD"))){
            strItem += "예약이체진행상태; ";
            strText += paramMap.get("SEARCH_TRSF_RSVN_PGSS_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BF_REJECT_RSON_CD"))){
            strItem += "사전거절사유; ";
            strText += paramMap.get("SEARCH_BF_REJECT_RSON_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BANK_CD"))){
            strItem += "은행; ";
            strText += paramMap.get("SEARCH_BANK_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CTRL_STAT_CD"))){
            strItem += "조정상태; ";
            strText += paramMap.get("SEARCH_CTRL_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CRDT_GRAD_CD"))){
            strItem += "신용등급; ";
            strText += paramMap.get("SEARCH_CRDT_GRAD_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COLL_DVSN_CD"))){
            strItem += "수금구분; ";
            strText += paramMap.get("SEARCH_COLL_DVSN_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COLL_AMT"))){
            strItem += "수금금액; ";
            strText += paramMap.get("SEARCH_COLL_AMT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ROLE_ID"))){
            strItem += "권한ID; ";
            strText += paramMap.get("SEARCH_ROLE_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCVG_BANK_BRCH_CD"))){
            strItem += "수신은행지점코드; ";
            strText += paramMap.get("SEARCH_RCVG_BANK_BRCH_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCVG_DRWG_YN"))){
            strItem += "수신출금여부; ";
            strText += paramMap.get("SEARCH_RCVG_DRWG_YN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DFRM_DT"))){
            strItem += "지급일자; ";
            strText += paramMap.get("SEARCH_DFRM_DT")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DFRM_SNO"))){
            strItem += "지급일련번호; ";
            strText += paramMap.get("SEARCH_DFRM_SNO")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_VAL"))){
            strItem += "값; ";
            strText += paramMap.get("SEARCH_VAL")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DVIC_ID"))){
            strItem += "장치ID; ";
            strText += paramMap.get("SEARCH_DVIC_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PNS_TKN_STAT_CD"))){
            strItem += "PNS토큰상태; ";
            strText += paramMap.get("SEARCH_PNS_TKN_STAT_CD")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MOBILE_STATUS"))){
            strItem += "기기상태; ";
            strText += paramMap.get("SEARCH_MOBILE_STATUS")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INTE"))){
            strItem += "인터페이스; ";
            strText += paramMap.get("SEARCH_INTE")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_GUBUN"))){
            strItem += "회원구분; ";
            strText += paramMap.get("SEARCH_MEMB_GUBUN")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AFFL_USER_ID"))){
            strItem += "제휴기관회원ID; ";
            strText += paramMap.get("SEARCH_AFFL_USER_ID")+"; ";
        }

        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TNO"))){
            strItem += "TNO; ";
            strText += paramMap.get("SEARCH_TNO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RQST_MEMB_NO"))){
            strItem += "청구회원번호; ";
            strText += paramMap.get("SEARCH_RQST_MEMB_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RQST_GRP_SNO"))){
            strItem += "청구그룹일련번호; ";
            strText += paramMap.get("SEARCH_RQST_GRP_SNO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PYMT_MEMB_NO"))){
            strItem += "지불회원번호; ";
            strText += paramMap.get("SEARCH_PYMT_MEMB_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PYMT_GRP_SNO"))){
            strItem += "지불그룹일련번호; ";
            strText += paramMap.get("SEARCH_PYMT_GRP_SNO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_STORE"))){
            strItem += "가맹점명; ";
            strText += paramMap.get("SEARCH_STORE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PD_YEAR"))){
            strItem += "년도; ";
            strText += paramMap.get("SEARCH_PD_YEAR")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PD_MONTH"))){
            strItem += paramMap.get("SEARCH_PD_MONTH")+"; "; //;1월;2월;3월;......
            strText += paramMap.get("SEARCH_PD_TTLZ_AMT")+"; "; //100;200;300;
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DISPLAY_Y"))){
            strItem += "화면표시; ";
            strText += paramMap.get("SEARCH_DISPLAY_Y")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BANK_FNNI_NO"))){
            strItem += "금융기관번호; ";
            strText += paramMap.get("SEARCH_BANK_FNNI_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("FNNI_RESP_TYPE_CD"))){
            strItem += "금융기관응답유형; ";
            strText += paramMap.get("FNNI_RESP_TYPE_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("FNNI_RESP_CD"))){
            strItem += "금융기관응답; ";
            strText += paramMap.get("FNNI_RESP_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("MCSS_RESP_TYPE_CD"))){
            strItem += "MCSS응답유형; ";
            strText += paramMap.get("MCSS_RESP_TYPE_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("MCSS_RESP_CD"))){
            strItem += "MCSS응답; ";
            strText += paramMap.get("MCSS_RESP_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("REJECT_USE_YN"))){
            strItem += "사용유무; ";
            strText += paramMap.get("REJECT_USE_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("APPLY_START_DATE"))){
            strItem += "적용시작일시; ";
            strText += paramMap.get("APPLY_START_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("APPLY_END_DATE"))){
            strItem += "적용종료일시; ";
            strText += paramMap.get("APPLY_END_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_APPLY_DATE"))){
            strItem += "적용일자; ";
            strText += paramMap.get("SEARCH_APPLY_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_USER_RESET_NO"))){
            strItem += "초기화일련번호; ";
            strText += paramMap.get("SEARCH_USER_RESET_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INIT_STAT_CD"))){
            strItem += "초기화상태; ";
            strText += paramMap.get("SEARCH_INIT_STAT_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DRWG_DVSN_CD"))){
            strItem += "환급구분; ";
            strText += paramMap.get("SEARCH_DRWG_DVSN_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DRWG_STAT_CD"))){
            strItem += "환급상태; ";
            strText += paramMap.get("SEARCH_DRWG_STAT_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BIRTH_DATE"))){
            strItem += "생년월일; ";
            strText += paramMap.get("SEARCH_BIRTH_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CONTION_CD"))){
            strItem += "체납상태; ";
            String str = (String) paramMap.get("SEARCH_CONTION_CD");
            if(StringUtils.equals("ALL", str)){
                str = "전체";
            }else if(StringUtils.equals("M", str)){
                str = "미납";
            }else if(StringUtils.equals("P", str)){
                str = "부분납";
            }else if(StringUtils.equals("S", str)){
                str = "완납";
            }
            strText += str+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PGM_NAME_HIS"))){
            String str = "프로그램명";
            strItem += str+"; ";
            if(StringUtils.contains((String)paramMap.get("SEARCH_PGM_NAME_HIS"), str+" 전체")){
                paramMap.put("SEARCH_PGM_NAME_HIS", StringUtils.remove((String)paramMap.get("SEARCH_PGM_NAME_HIS"), str));
            }
            strText += paramMap.get("SEARCH_PGM_NAME_HIS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ADM_ID_HIS"))){
            String str = "관리자";
            strItem += str+"; ";
            if(StringUtils.contains((String)paramMap.get("SEARCH_ADM_ID_HIS"), str+" 전체")){
                paramMap.put("SEARCH_ADM_ID_HIS", StringUtils.remove((String)paramMap.get("SEARCH_ADM_ID_HIS"), str));
            }
            strText += paramMap.get("SEARCH_ADM_ID_HIS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PGM_TYPE_HIS"))){
            String str = "프로그램유형";
            strItem += str+"; ";
            if(StringUtils.contains((String)paramMap.get("SEARCH_PGM_TYPE_HIS"), str+" 전체")){
                paramMap.put("SEARCH_PGM_TYPE_HIS", StringUtils.remove((String)paramMap.get("SEARCH_PGM_TYPE_HIS"), str));
            }
            strText += paramMap.get("SEARCH_PGM_TYPE_HIS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MENU_GRP_HIS"))){
            String str = "메뉴그룹";
            strItem += str+"; ";
            if(StringUtils.contains((String)paramMap.get("SEARCH_MENU_GRP_HIS"), str+" 전체")){
                paramMap.put("SEARCH_MENU_GRP_HIS", StringUtils.remove((String)paramMap.get("SEARCH_MENU_GRP_HIS"), str));
            }
            strText += paramMap.get("SEARCH_MENU_GRP_HIS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FR_COUNT"))){
            strItem += "건수; ";
            if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TO_COUNT"))){
                strText += paramMap.get("SEARCH_FR_COUNT")+"~" + paramMap.get("SEARCH_TO_COUNT")+"; ";
            }else{
                strText += paramMap.get("SEARCH_FR_COUNT")+"이상; ";
            }
        }
        if(StringUtils.isEmpty(paramMap.get("SEARCH_FR_COUNT")) && StringUtils.isNotEmpty(paramMap.get("SEARCH_TO_COUNT"))){
            strItem += "건수; ";
            strText += paramMap.get("SEARCH_TO_COUNT")+"이하; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_WORK_GBN"))){
            strItem += "업무구분; ";
            strText += paramMap.get("SEARCH_WORK_GBN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SETTLE_VAN_RESP_CD"))){
            strItem += "VAN응답코드; ";
            strText += paramMap.get("SEARCH_WORK_GBN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_RESP_TYPE_CD"))){
            strItem += "은행응답유형코드; ";
            strText += paramMap.get("SEARCH_FNNI_RESP_TYPE_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FNNI_RESP_CD"))){
            strItem += "은행응답코드; ";
            strText += paramMap.get("SEARCH_FNNI_RESP_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SESSION_ID"))){
            strItem += "세션ID; ";
            strText += paramMap.get("SEARCH_SESSION_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SERVICE_CD"))){
            strItem += "서비스코드; ";
            strText += paramMap.get("SEARCH_SERVICE_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_AUTH_RSON_CD"))){
            strItem += "인증사유코드; ";
            strText += paramMap.get("SEARCH_AUTH_RSON_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PRCS_CD"))){
            strItem += "처리코드; ";
            strText += paramMap.get("SEARCH_PRCS_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FILE_DVSN_CD"))){
            strItem += "파일구분코드; ";
            strText += paramMap.get("SEARCH_FILE_DVSN_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("mcht_no"))){
            strItem += "가맹점번호; ";
            strText += paramMap.get("mcht_no")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("user_no"))){
            strItem += "인터파크 회원 번호; ";
            strText += paramMap.get("user_no")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("admin_id"))){
            strItem += "IBSS 어드민 ID; ";
            strText += paramMap.get("admin_id")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("rq_dtime"))){
            strItem += "가맹점 요청 일자; ";
            strText += paramMap.get("rq_dtime")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("tr_no_cancle"))){
            strItem += "요청 거래번호; ";
            strText += paramMap.get("tr_no_cancle")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("tr_dt_cancle"))){
            strItem += "요청 거래일자; ";
            strText += paramMap.get("tr_dt_cancle")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("cancle_amt"))){
            strItem += "요청 금액; ";
            strText += paramMap.get("cancle_amt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_ORDR_NO"))){
            strItem += "가맹점주문번호; ";
            strText += paramMap.get("SEARCH_MCHT_ORDR_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RCCL_RSLT_CD"))){
            strItem += "대사결과코드; ";
            strText += paramMap.get("SEARCH_RCCL_RSLT_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_STOR_ID"))){
            strItem += "상점ID; ";
            strText += paramMap.get("SEARCH_MCHT_STOR_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MID"))){
            strItem += "MID; ";
            strText += paramMap.get("SEARCH_MID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TR_ID"))){
            strItem += "TR_ID; ";
            strText += paramMap.get("SEARCH_TR_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DATE_GBN"))){
            strItem += "기간구분; ";
            strText += paramMap.get("SEARCH_DATE_GBN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RESL_TR_NO"))){
            strItem += "재판매거래번호; ";
            strText += paramMap.get("SEARCH_RESL_TR_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CPA_FROM_FNNI_NO"))){
            strItem += "출금은행; ";
            strText += paramMap.get("SEARCH_CPA_FROM_FNNI_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("PRCS_RMKS"))){
            strItem += "처리내용; ";
            strText += paramMap.get("PRCS_RMKS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("EXCC_OBJT_YN"))){
            strItem += "정산대상여부; ";
            strText += paramMap.get("EXCC_OBJT_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("EXCC_OBJT_CHNG_RMKS"))){
            strItem += "정산대상처리내용; ";
            strText += paramMap.get("EXCC_OBJT_CHNG_RMKS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EXCC_OBJT_YN"))){
            strItem += "정산대상; ";
            strText += paramMap.get("SEARCH_EXCC_OBJT_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_MID"))){
            strItem += "가맹점ID; ";
            strText += paramMap.get("SEARCH_EXCC_OBJT_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FIELD_SUM"))){
            strItem += "조회조건합계; ";
            strText += paramMap.get("SEARCH_RESP_CD_ONLY")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PAYMENT"))){
            strItem += "결제수단; ";
            strText += paramMap.get("SEARCH_PAYMENT")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("PSNL_TRTM_YN_CHK"))){
            strItem += "개인정보취급화면; ";
            strText += paramMap.get("PSNL_TRTM_YN_CHK")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DATA_SNO"))){
            strItem += "일련번호; ";
            strText += paramMap.get("SEARCH_DATA_SNO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ADMR_STAT_CD"))){
            strItem += "관리자상태; ";
            strText += paramMap.get("SEARCH_DATA_SNO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PSNL_TRTM_YN"))){
            strItem += "망분리처리여부; ";
            strText += paramMap.get("SEARCH_PSNL_TRTM_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("FILE_NAME"))){
            strItem += "파일명; ";
            strText += paramMap.get("FILE_NAME")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SECU_ITM"))){
            strItem += "보안점검항목; ";
            strText += paramMap.get("SEARCH_SECU_ITM")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_LOG_FIELD"))){
            strItem += "항목; ";
            strText += paramMap.get("SEARCH_LOG_FIELD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MCHT_MEMB_NO"))){
            strItem += "가맹점회원NO; ";
            strText += paramMap.get("SEARCH_MCHT_MEMB_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CARD_CD"))){
            strItem += "카드사코드; ";
            strText += paramMap.get("SEARCH_CARD_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_BANK_CD"))){
            strItem += "은행코드; ";
            strText += paramMap.get("SEARCH_BANK_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RVNU_DVSN_CD"))){
            strItem += "매출구분; ";
            strText += paramMap.get("SEARCH_RVNU_DVSN_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TMNL_SHAP_CD"))){
            strItem += "단말형태; ";
            strText += paramMap.get("SEARCH_TMNL_SHAP_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RESP_ECLS"))){
            strItem += "응답없음제외; ";
            strText += paramMap.get("SEARCH_RESP_ECLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INTERP_MEMB_ID"))){
            strItem += "인터파크 회원 ID; ";
            strText += paramMap.get("SEARCH_INTERP_MEMB_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EMPY_NO"))){
            strItem += "사번; ";
            strText += paramMap.get("SEARCH_EMPY_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_ID"))){
            strItem += "회원ID; ";
            strText += paramMap.get("SEARCH_MEMB_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_COMP_TP"))){
            strItem += "부문; ";
            strText += paramMap.get("SEARCH_COMP_TP")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IP001"))){
            strItem += "대분류; ";
            strText += paramMap.get("SEARCH_IP001")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IP002"))){
            strItem += "중분류; ";
            strText += paramMap.get("SEARCH_IP002")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_IP003"))){
            strItem += "소분류; ";
            strText += paramMap.get("SEARCH_IP003")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_INDE_TYPE"))){
            strItem += "가용/비가용구분; ";
            strText += paramMap.get("SEARCH_INDE_TYPE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SEQ_NO"))){
            strItem += "SEQ NO; ";
            strText += paramMap.get("SEARCH_SEQ_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_REQ_METHOD"))){
            strItem += "요청 METHOD; ";
            strText += paramMap.get("SEARCH_REQ_METHOD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RGS_CMP"))){
            strItem += "발급업체; ";
            strText += paramMap.get("SEARCH_RGS_CMP")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ISSUE_CODE"))){
            strItem += "발행코드; ";
            strText += paramMap.get("SEARCH_ISSUE_CODE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PBL_DPR"))){
            strItem += "발행부서; ";
            strText += paramMap.get("SEARCH_PBL_DPR")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RNDM_ISCD"))){
            strItem += "난수발급코드; ";
            strText += paramMap.get("SEACH_RNDM_ISCD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_EVNT_SEQ"))){
            strItem += "이벤트SEQ; ";
            strText += paramMap.get("SEARCH_EVNT_SEQ")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_STAT_TYPE"))){
            strItem += "유형구분; ";
            strText += paramMap.get("SEARCH_STAT_TYPE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SAVE_TYPE"))){
            strItem += "적립구분; ";
            strText += paramMap.get("SEARCH_STAT_TYPE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TABLE_FIX"))){
            strItem += "테이블고정; ";
            strText += paramMap.get("SEARCH_TABLE_FIX")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_DATE_YEAR"))){
            strItem += "조회년도; ";
            strText += paramMap.get("SEARCH_DATE_YEAR")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_VIEW_TOT"))){
            strItem += "소계포함; ";
            strText += paramMap.get("SEARCH_VIEW_TOT")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RSTRC_RSON"))){
            strItem += "사용자제한사유; ";
            strText += paramMap.get("SEARCH_RSTRC_RSON")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ISSUE_DTL_CODE"))){
            strItem += "난수코드; ";
            strText += paramMap.get("SEARCH_ISSUE_DTL_CODE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ISSUE_CODE_NM"))){
            strItem += "발행코드명; ";
            strText += paramMap.get("SEARCH_ISSUE_DTL_CODE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MY_EVENT"))){
            strItem += "나의이벤트조회; ";
            strText += paramMap.get("SEARCH_MY_EVENT")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RNDM_CODE"))){
            strItem += "난수코드; ";
            strText += paramMap.get("SEARCH_RNDM_CODE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SR_SEQ"))){
            strItem += "이벤트SEQ; ";
            strText += paramMap.get("SR_SEQ")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEACH_CLUS_LCLS"))){
            strItem += "약관유형코드; ";
            strText += paramMap.get("SEACH_CLUS_LCLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CLUS_MCLS"))){
            strItem += "약관제목; ";
            strText += paramMap.get("SEARCH_CLUS_MCLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_TERMS_GRP"))){
            strItem += "약관고지그룹; ";
            strText += paramMap.get("SEARCH_TERMS_GRP")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_TITLE"))){
            strItem += "약관대분류제목; ";
            strText += paramMap.get("CLUS_TITLE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_LCLS"))){
            strItem += "약관대분류코드; ";
            strText += paramMap.get("CLUS_LCLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("re_str"))){
            strItem += "처리여부; ";
            strText += paramMap.get("re_str")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_MCLS"))){
            strItem += "약관중분류코드; ";
            strText += paramMap.get("CLUS_MCLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_CLUS_LCLS"))){
            strItem += "약관대분류코드; ";
            strText += paramMap.get("SEARCH_CLUS_LCLS")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_BASS_YYYY"))){
            strItem += "기준연도; ";
            strText += paramMap.get("CLUS_BASS_YYYY")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_USE_TYPE_CD"))){
            strItem += "약관유형코드; ";
            strText += paramMap.get("CLUS_USE_TYPE_CD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("CLUS_USE_TYPE"))){
            strItem += "약관유형; ";
            strText += paramMap.get("CLUS_USE_TYPE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_NO"))){
            strItem += "회원번호; ";
            strText += paramMap.get("SEARCH_MEM_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_ID"))){
            strItem += "회원ID; ";
            strText += paramMap.get("SEARCH_MEM_ID")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_HP"))){
            strItem += "회원휴대폰; ";
            strText += paramMap.get("SEARCH_MEM_HP")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_MAIL"))){
            strItem += "회원이메일; ";
            strText += paramMap.get("SEARCH_MEM_MAIL")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_DI"))){
            strItem += "회원DI; ";
            strText += paramMap.get("SEARCH_MEM_DI")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_CI"))){
            strItem += "회원CI; ";
            strText += paramMap.get("SEARCH_MEM_CI")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEM_INFO"))){
            strItem += "회원정보; ";
            strText += paramMap.get("SEARCH_MEM_INFO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_OCCR_START_DATE"))){
            strItem += "발생시작일시; ";
            strText += paramMap.get("SEARCH_OCCR_START_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_OCCR_END_DATE"))){
            strItem += "발생종료일시; ";
            strText += paramMap.get("SEARCH_OCCR_END_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FILE_NO"))){
            strItem += "파일번호; ";
            strText += paramMap.get("SEARCH_FILE_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("FILE_TITLE"))){
            strItem += "파일제목; ";
            strText += paramMap.get("FILE_TITLE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PRCS_YN"))){
            strItem += "처리여부; ";
            strText += paramMap.get("SEARCH_PRCS_YN")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_FILE_UPLOAD_STAT"))){
            strItem += "파일업로드 상태; ";
            strText += paramMap.get("SEARCH_FILE_UPLOAD_STAT")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_JOB_STAT"))){
            strItem += "작업 상태; ";
            strText += paramMap.get("SEARCH_JOB_STAT")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MEMB_GRAD"))){
            strItem += "회원 등급; ";
            strText += paramMap.get("SEARCH_MEMB_GRAD")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_ORDR_NO"))){
            strItem += "주문번호; ";
            strText += paramMap.get("SEARCH_ORDR_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_PROD_NO"))){
            strItem += "상품번호; ";
            strText += paramMap.get("SEARCH_PROD_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("mod_start_dt"))){
            strItem += "시작일; ";
            strText += paramMap.get("mod_start_dt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("mod_end_dt"))){
            strItem += "종료일; ";
            strText += paramMap.get("mod_end_dt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_MONTH"))){
            strItem += "등급월; ";
            strText += paramMap.get("SEARCH_MONTH")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_RSTC_TYPE"))){
            strItem += "제한유형; ";
            strText += paramMap.get("SEARCH_RSTC_TYPE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SCES_DATE"))){
            strItem += "제한일자; ";
            strText += paramMap.get("SEARCH_SCES_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SCES_START_DATE"))){
            strItem += "제한시작일자; ";
            strText += paramMap.get("SEARCH_SCES_START_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("SEARCH_SCES_END_DATE"))){
            strItem += "제한종료일자; ";
            strText += paramMap.get("SEARCH_SCES_END_DATE")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("OTBK_DRWG_TRSF_FWDG_NO"))){
            strItem += "파일번호; ";
            strText += paramMap.get("OTBK_DRWG_TRSF_FWDG_NO")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("respStr"))){
            strItem += "결과값; ";
            strText += paramMap.get("respStr")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("respCd"))){
            strItem += "결과값; ";
            strText += paramMap.get("respCd")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchFileName"))){
            strItem += "파일명; ";
            strText += paramMap.get("searchFileName")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchFwdgNo"))){
            strItem += "처리번호; ";
            strText += paramMap.get("searchFwdgNo")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchClusUseTypeCd"))){
            strItem += "약관이용유형코드; ";
            strText += paramMap.get("searchClusUseTypeCd")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchPtclCd"))){
            strItem += "부문코드; ";
            strText += paramMap.get("searchPtclCd")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchRecertCd"))){
            strItem += "BlockID구분코드; ";
            strText += paramMap.get("searchRecertCd")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchDelvTypeCd"))){
            strItem += "배송지 구분; ";
            strText += paramMap.get("searchDelvTypeCd")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("searchDisplayDt"))){
            strItem += "노출일자; ";
            strText += paramMap.get("searchDisplayDt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("displayStartDt"))){
            strItem += "노출시작일자; ";
            strText += paramMap.get("displayStartDt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("displayEndDt"))){
            strItem += "노출종료일자; ";
            strText += paramMap.get("displayEndDt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("targetStartDt"))){
            strItem += "대상시작연도; ";
            strText += paramMap.get("targetStartDt")+"; ";
        }
        if(StringUtils.isNotEmpty(paramMap.get("targetEndDt"))){
            strItem += "대상종료연도; ";
            strText += paramMap.get("targetEndDt")+"; ";
        }

        HashMap<String, Object> searchLog = new HashMap<String, Object>();

        if(StringUtils.isNotEmpty(strItem)) searchLog.put("searchItem", strItem);
        if(StringUtils.isNotEmpty(strText)) searchLog.put("seatchText", strText);

        return searchLog;
    }
}
