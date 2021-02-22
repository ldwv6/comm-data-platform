package comm.datapf.web.boadmin.domain.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleAuthorDto {
    //ADMR_ATHR_BASE 통합_역할그룹기본 (권한관리)

    @ApiModelProperty(position = 1,required = false, value = "Role ID")
    private String roleId;

    @ApiModelProperty(position = 2,required = false, value = "Role 명")
    private String roleNm;

    @ApiModelProperty(position = 3,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 4,required = false, value = "등록관리자ID")
    private String erlmAdmrId;

    @ApiModelProperty(position = 5,required = false, value = "등록관리자IP")
    private String erlmAdmrIp;

    @ApiModelProperty(position = 6,required = false, value = "등록일시")
    private String erlmDtime;

    @ApiModelProperty(position = 7,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 8,required = false, value = "변경관리자IP")
    private String chngAdmrIp;

    @ApiModelProperty(position = 9,required = false, value = "변경일시")
    private String chngDtime;

}
