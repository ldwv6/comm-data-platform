package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsgSaleTtlzSttlDtlDto {

    @ApiModelProperty(position = 1,required = false, value = "NO")
    private int no;

    @ApiModelProperty(position = 2,required = false, value = "결제수단구분")
    private String sttlMthdCd;

    @ApiModelProperty(position = 3,required = false, value = "결제수단명")
    private String sttlMthdNm;

    @ApiModelProperty(position = 4,required = false, value = "결제금융기관PG코드")
    private String sttlFnniPgCd;

    @ApiModelProperty(position = 5,required = false, value = "결제금융기관PG명")
    private String sttlFnniPgNm;

    @ApiModelProperty(position = 6,required = false, value = "결제금융기관코드")
    private String sttlFnniCd;

    @ApiModelProperty(position = 7,required = false, value = "결제금융기관명")
    private String sttlFnniNm;

    @ApiModelProperty(position = 8,required = false, value = "건수(주문)")
    private Double ordeSttlCnt;

    @ApiModelProperty(position = 9,required = false, value = "건수(취소)")
    private Double cnclSttlCnt;

    @ApiModelProperty(position = 10,required = false, value = "건수(판매)")
    private Double sttlCnt;

    @ApiModelProperty(position = 11,required = false, value = "금액(주문)")
    private Double ordeSttlAmt;

    @ApiModelProperty(position = 12,required = false, value = "금액(취소)")
    private Double cnclSttlAmt;

    @ApiModelProperty(position = 13,required = false, value = "금액(판매)")
    private Double sttlAmt;


}
