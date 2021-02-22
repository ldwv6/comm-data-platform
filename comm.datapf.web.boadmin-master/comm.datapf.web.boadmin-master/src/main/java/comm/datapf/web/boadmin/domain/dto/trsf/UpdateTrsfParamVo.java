package comm.datapf.web.boadmin.domain.dto.trsf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 채권 양도양수 > 채권 양도양수 등록 ParamVo
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-19
 * @version 1.0
 *<pre>
 *2021.02.19 : 최초 작성
 *</pre>
 */
@Alias("updateTrsfParamVo")
@Getter
@Setter
@ApiModel(value="채권 양도양수 > 채권 양도양수 등록 ParamVo")
public class UpdateTrsfParamVo {
    @ApiModelProperty(required = true, value = "양수도SEQ")
    private String trsfSeq;
    @ApiModelProperty(required = true, value = "서비스구분")
    private String svcDvsnCd;
    @ApiModelProperty(required = true, value = "양도 통지서 접수일자")
    private String cvycRcitDt;
    @ApiModelProperty(required = true, value = "양도 통지서 접수방법")
    private String cvycRcitMthdCd;
    @ApiModelProperty(required = true, value = "양도 적용 시작일")
    private String trsfStrtDt;
    @ApiModelProperty(required = true, value = "양도 적용 해제일")
    private String trsfEndDt;
    @ApiModelProperty(required = true, value = "양수도상태코드")
    private String trsfStatCd;
    //TODO: 첨부파일 항목 추가 필요.
    @ApiModelProperty(required = true, value = "양도업체번호 (거래처번호)")
    private String cvycBzepNo;
    @ApiModelProperty(required = true, value = "양도계약일련번호 (거래처SEQ)")
    private String cvycCtrtSno;
    @ApiModelProperty(required = true, value = "양도한도액 (채권한도액)")
    private long cvycLmtAmt;
    @ApiModelProperty(required = true, value = "양도 대상 상품 리스트")
    private List<UpdateTrsfGoodsParamVo> trsfGoodsList;
    @ApiModelProperty(required = true, value = "양수자 리스트")
    private List<UpdateTrsfReceiverParamVo> trsfReceiverList;
    @ApiModelProperty(required = true, value = "양도사유 (메모)")
    private String cvycRson;
}
