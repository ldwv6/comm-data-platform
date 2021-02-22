package comm.datapf.web.boadmin.domain.dto.trsf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 채권 양도양수 > 채권 양도자 조회 Dto
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-10
 * @version 1.0
 *<pre>
 *2021.02.10 : 최초 작성
 *</pre>
 */
@Alias("trsfGranterVo")
@Getter
@Setter
@ApiModel(value="채권 양도양수 > 채권 양도자 조회 Dto")
public class TrsfGranterVo {
    @ApiModelProperty(required = true, value = "서비스구분")
    private String svcDvsnCd;
    @ApiModelProperty(required = true, value = "양도양수번호")
    private String trsfSeq;
    @ApiModelProperty(required = true, value = "서류접수일")
    private String cvycRcitDt;
    @ApiModelProperty(required = true, value = "적용시작일")
    private String trsfStrtDt;
    @ApiModelProperty(required = true, value = "적용해제일")
    private String trsfEndDt;
    @ApiModelProperty(required = true, value = "양도 거래처번호")
    private String cvycBzepNo;
    @ApiModelProperty(required = true, value = "양도 거래처Seq")
    private Integer cvycCtrtSno;
    @ApiModelProperty(required = true, value = "양도 사업자번호")
    private String cvycBzno;
    @ApiModelProperty(required = true, value = "양도 거래처명")
    private String cvycBzepNm;
    @ApiModelProperty(required = true, value = "양도 대상 상품명")
    private String cvycPrdtNm;
    @ApiModelProperty(required = true, value = "양도 대상 상품수")
    private Integer cvycPrdtCnt;
    @ApiModelProperty(required = true, value = "채권 한도액")
    private long cvycLmtAmt;
    @ApiModelProperty(required = true, value = "등록자")
    private String erlmNo;
    @ApiModelProperty(required = true, value = "양도상품코드")
    private String cvycPrdtCd;
    @ApiModelProperty(required = true, value = "양도공연장코드")
    private String cvycPlacCd;
    @ApiModelProperty(required = true, value = "양수도구분")
    private String trsfDvsnCd;
    @ApiModelProperty(required = true, value = "양수도상태코드")
    private String trsfStatCd;
}
