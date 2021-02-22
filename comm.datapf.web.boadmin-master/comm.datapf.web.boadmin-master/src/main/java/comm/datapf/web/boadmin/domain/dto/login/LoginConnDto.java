package comm.datapf.web.boadmin.domain.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginConnDto {
    // ADMR_CONN_TMST 통합_관리자 접속시간관리
    @ApiModelProperty(position = 1,required = false, value = "관리자ID")
    private String admrId;

    @ApiModelProperty(position = 2,required = false, value = "접속구분코드")
    private String connDvsnCd;

    @ApiModelProperty(position = 3,required = false, value = "적용시작일시")
    private String aplyStrtDtime;

    @ApiModelProperty(position = 4,required = false, value = "적용종료일시")
    private String aplyEndDtime;

    @ApiModelProperty(position = 5,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 6,required = false, value = "등록일시")
    private String erlmDtime;

    @ApiModelProperty(position = 7,required = false, value = "등록관리자ID")
    private String erlmAdmrId;

    @ApiModelProperty(position = 8,required = false, value = "등록관리자IP")
    private String erlmAdmrIp;

    @ApiModelProperty(position = 9,required = false, value = "변경일시")
    private String chngDtime;

    @ApiModelProperty(position = 10,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 11,required = false, value = "변경관리자IP")
    private String chngAdmrIp;

    @ApiModelProperty(position = 12,required = false, value = "접속가능시작시간")
    private String connPsblStrtTime;

    @ApiModelProperty(position = 13,required = false, value = "접속가능종료시간")
    private String connPsblEndTime;
}
