package comm.datapf.web.boadmin.domain.dto.trsf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 채권 양도양수 > 채권 양수자별 지급내역 조회 Dto
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-15
 * @version 1.0
 *<pre>
 *2021.02.15 : 최초 작성
 *</pre>
 */
@Alias("trsfPymntVo")
@Getter
@Setter
@ApiModel(value="채권 양도양수 > 채권 양수자별 지급내역 조회 Dto")
public class TrsfPymntVo {
    @ApiModelProperty(required = true, value = "서비스구분")
    private String svcDvsnCd;
    @ApiModelProperty(required = true, value = "구분")
    private String dfrmStatNm;
    @ApiModelProperty(required = true, value = "양수도번호")
    private String trsfSeq;
    @ApiModelProperty(required = true, value = "양수우선순위")
    private String acqnRankCd;
    @ApiModelProperty(required = true, value = "양수사업자번호")
    private String acqnBzno;
    @ApiModelProperty(required = true, value = "양수자명(양수거래처명)")
    private String acqnNm;
    @ApiModelProperty(required = true, value = "지급요청액(양수설정액)")
    private long dfrmReqAmt;
    @ApiModelProperty(required = true, value = "지급액")
    private long dfrmAmt;
    @ApiModelProperty(required = true, value = "양도상품코드")
    private String cvycPrdtCd;
    @ApiModelProperty(required = true, value = "상품명")
    private String cvycPrdtNm;
    @ApiModelProperty(required = true, value = "정산번호(리포트번호)")
    private String exccNo;
    @ApiModelProperty(required = true, value = "양수도상세SEQ")
    private String trsfAcqnSeq;
    @ApiModelProperty(required = true, value = "양수도처리SEQ")
    private String trsfPrcsSeq;
    @ApiModelProperty(required = true, value = "양수업체번호")
    private String acqnBzepNo;
    @ApiModelProperty(required = true, value = "양수계약일련번호")
    private String acqnCtrtSno;
    @ApiModelProperty(required = true, value = "지급기준코드")
    private String dfrmBassCd;
    @ApiModelProperty(required = true, value = "지급방법코드")
    private String dfrmMthdCd;
    @ApiModelProperty(required = true, value = "양수도구분")
    private String trsfDvsnCd;
    @ApiModelProperty(required = true, value = "양수도상태코드")
    private String trsfStatCd;
}
