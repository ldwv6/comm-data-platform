package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClsgSaleTtlzDto {

    @ApiModelProperty(position = 1,required = false, value = "")
    private List<ClsgSaleTtlzMstrDto> clsgSaleTtlzMstrList;

    @ApiModelProperty(position = 2,required = false, value = "")
    private List<ClsgSaleTtlzMstrDto> clsgSaleTtlzClsgList;

    @ApiModelProperty(position = 3,required = false, value = "")
    private List<ClsgSaleTtlzSttlDto> clsgSaleTtlzSttlList;
}
