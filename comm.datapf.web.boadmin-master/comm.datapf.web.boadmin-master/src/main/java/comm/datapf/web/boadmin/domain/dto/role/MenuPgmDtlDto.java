package comm.datapf.web.boadmin.domain.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuPgmDtlDto {

    @ApiModelProperty(position = 1,required = false, value = "프로그램상세ID")
    private String pgmDtlId;

    @ApiModelProperty(position = 2,required = false, value = "프로그램유형코드")
    private String pgmTypeCd;

    @ApiModelProperty(position = 3,required = false, value = "프로그램ID")
    private String pgmId;

    @ApiModelProperty(position = 4,required = false, value = "소스경로")
    private String srcPath;

    @ApiModelProperty(position = 5,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 6,required = false, value = "비고")
    private String rmks;

    @ApiModelProperty(position = 7,required = false, value = "등록관리자ID")
    private String erlmAdmrId;

    @ApiModelProperty(position = 8,required = false, value = "등록관리자IP")
    private String erlmAdmrIp;

    @ApiModelProperty(position = 9,required = false, value = "등록일시")
    private String erlmDtime;

    @ApiModelProperty(position = 10,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 11,required = false, value = "변경관리자IP")
    private String chngAdmrIp;

    @ApiModelProperty(position = 12,required = false, value = "변경일시")
    private String chngDtime;

}
