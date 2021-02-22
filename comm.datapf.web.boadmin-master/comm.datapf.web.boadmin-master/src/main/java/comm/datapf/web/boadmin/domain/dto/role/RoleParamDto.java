package comm.datapf.web.boadmin.domain.dto.role;

import comm.datapf.web.boadmin.domain.dto.common.SearchRepository;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleParamDto extends SearchRepository {

    @ApiModelProperty(position = 1,required = false, value = "Role ID")
    private String searchRoleId;

    @ApiModelProperty(position = 2,required = false, value = "사용유무")
    private String searchUseYn;

    @ApiModelProperty(position = 3,required = false, value = "노출갯수")
    private int searchDisplayCnt;
}
