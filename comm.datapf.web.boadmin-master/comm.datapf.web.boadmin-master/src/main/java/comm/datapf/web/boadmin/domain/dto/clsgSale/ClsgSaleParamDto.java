package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("ClsgSaleParamDto")
public class ClsgSaleParamDto {

    @ApiModelProperty(position = 1,required = false, value = "사업장구분")
    private String svcDvsnCd;

    @ApiModelProperty(position = 2,required = false, value = "결제마감 시작일자")
    private String searchStartDate;

    @ApiModelProperty(position = 3,required = false, value = "결제마감 종료일자")
    private String searchEndDate;

    @ApiModelProperty(position = 4,required = false, value = "상세 여부")
    private String dtlYn;

    @ApiModelProperty(position = 5,required = false, value = "상세 여부")
    private String sttlMthdCd;

    @ApiModelProperty(position = 6,required = false, value = "업체정산유형 코드")
    private String bzepExccTypeCd;

    @ApiModelProperty(position = 7,required = false, value = "업체구분 코드")
    private String bzepDvsnCd;

}
