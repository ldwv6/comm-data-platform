package comm.datapf.web.boadmin.domain.dto.trsf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 채권 양도양수 > 채권 양도양수 등록 - 양수자 ParamVo
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-19
 * @version 1.0
 *<pre>
 *2021.02.19 : 최초 작성
 *</pre>
 */
@Alias("updateTrsfReceiverParamVo")
@Getter
@Setter
@ApiModel(value="채권 양도양수 > 채권 양도양수 등록 - 양수자 ParamVo")
public class UpdateTrsfReceiverParamVo {
    @ApiModelProperty(required = true, value = "양수도SEQ")
    private String trsfSeq;
    @ApiModelProperty(required = true, value = "양수도상세SEQ")
    private String trsfAcqnSeq;
    @ApiModelProperty(required = true, value = "양수우선순위")
    private String acqnRankCd;
    @ApiModelProperty(required = true, value = "양수업체번호 (거래처번호)")
    private String acqnBzepNo;
    @ApiModelProperty(required = true, value = "양수계약일련번호 (거래처SEQ)")
    private String acqnCtrtSno;
    @ApiModelProperty(required = true, value = "양수사업자등록번호 (직접입력-사업자번호/주민번호)")
    private String acqnBzno;
    @ApiModelProperty(required = true, value = "양수자명 (직접입력-양수자)")
    private String acqnNm;
    @ApiModelProperty(required = true, value = "지급기준 (E정산금/S판매금)")
    private String dfrmBassCd;
    @ApiModelProperty(required = true, value = "지급방법 (A정액/R정율)")
    private String dfrmMthdCd;
    @ApiModelProperty(required = true, value = "전체지급여부 (체크 시 'Y')")
    private String dfrmAllYn;
    @ApiModelProperty(required = true, value = "지급요청액/율")
    private long dfrmReqAmt;
    @ApiModelProperty(required = true, value = "지급계산방법 (계산방식코드)")
    private String dfrmCalcMthdCd;
    @ApiModelProperty(required = true, value = "양수업체은행코드")
    private String acqnBankCd;
    @ApiModelProperty(required = true, value = "양수업체계좌번호 (암호화 해서 저장)")
    private String acqnAcntNo;
    @ApiModelProperty(required = true, value = "양수업체예금주명")
    private String acqnDpowNm;
    @ApiModelProperty(required = true, value = "계좌인증일시")
    private String acntCtfcDtime;
}
