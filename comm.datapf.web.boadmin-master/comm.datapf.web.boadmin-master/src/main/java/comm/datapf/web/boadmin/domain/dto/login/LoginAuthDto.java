package comm.datapf.web.boadmin.domain.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAuthDto {

    @ApiModelProperty(position = 1,required = false, value = "ADMR_ID")
    private String admrId;

    @ApiModelProperty(position = 2,required = false, value = "PGM_ID")
    private String pgmId;

    @ApiModelProperty(position = 3,required = false, value = "PGM_TYPE_CD")
    private String pgmTypeCd;

    @ApiModelProperty(position = 4,required = false, value = "PGM_DTL_ID")
    private String pgmDtlId;

    @ApiModelProperty(position = 5,required = false, value = "SEARCH_ITEM")
    private String searchItem;

    @ApiModelProperty(position = 6,required = false, value = "SEARCH_TEXT")
    private String searchText;

    @ApiModelProperty(position = 6,required = false, value = "ADMR_IP")
    private String admrIp;

    @ApiModelProperty(position = 6,required = false, value = "HIST_DVSN_CD")
    private String histDvsnCd;

}
