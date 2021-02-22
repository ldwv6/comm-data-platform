package comm.datapf.web.boadmin.domain.dto.trsf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 채권 양도양수 > 채권 양도양수 등록 - 양도 대상 상품 ParamVo
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-19
 * @version 1.0
 *<pre>
 *2021.02.19 : 최초 작성
 *</pre>
 */
@Alias("updateTrsfGoodsParamVo")
@Getter
@Setter
@ApiModel(value="채권 양도양수 > 채권 양도양수 등록 - 양도 대상 상품 ParamVo")
public class UpdateTrsfGoodsParamVo {
    @ApiModelProperty(required = true, value = "양수도SEQ")
    private String trsfSeq;
    @ApiModelProperty(required = true, value = "양수도상품SEQ")
    private String trsfPrdtSeq;
    @ApiModelProperty(required = true, value = "양도상품코드")
    private String cvycPrdtCd;
    @ApiModelProperty(required = true, value = "양도공연장코드")
    private String cvycPlacCd;
    @ApiModelProperty(required = true, value = "양도우선순위 (차감순위)")
    private String cvycRankCd;
}
