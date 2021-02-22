package comm.datapf.web.boadmin.domain.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleAdmrAuthDto {

    @ApiModelProperty(position = 1,required = false, value = "관리자ID")
    private String admrId;

    @ApiModelProperty(position = 2,required = false, value = "관리자명")
    private String admrNm;

    @ApiModelProperty(position = 3,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 4,required = false, value = "관리자EMAIL주소")
    private String admrEmal;

    @ApiModelProperty(position = 5,required = false, value = "사번")
    private String empyNo;

    @ApiModelProperty(position = 6,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 7,required = false, value = "변경일시")
    private String chngDtime;


}
