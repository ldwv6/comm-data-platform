package comm.datapf.web.boadmin.domain.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ComCdAdmnDto {

    @ApiModelProperty(position = 1,required = false, value = "공통유형코드")
    private String comTypeCd;

    @ApiModelProperty(position = 2,required = false, value = "공통유형코드명")
    private String comTypeCdNm;

    @ApiModelProperty(position = 3,required = false, value = "공통코드")
    private String comCd;

    @ApiModelProperty(position = 4,required = false, value = "공통코드명")
    private String comCdNm;

    @ApiModelProperty(position = 5,required = false, value = "공통코드명1")
    private String comCdNm1;

    @ApiModelProperty(position = 6,required = false, value = "공통코드명2")
    private String comCdNm2;

    @ApiModelProperty(position = 7,required = false, value = "상위공통코드")
    private String hgrkComCd;

    @ApiModelProperty(position = 8,required = false, value = "공통코드설명")
    private String comCdDscr;

    @ApiModelProperty(position = 9,required = false, value = "공통코드정렬순서")
    private int comCdSortSqn;

    @ApiModelProperty(position = 10,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 11,required = false, value = "통합 등록자")
    private String erlmNo;

    @ApiModelProperty(position = 12,required = false, value = "통합 등록일시")
    private ZonedDateTime erlmDtime;

    @ApiModelProperty(position = 13,required = false, value = "통합 변경자")
    private String chngNo;

    @ApiModelProperty(position = 14,required = false, value = "통합 변경일시")
    private ZonedDateTime chngDtime;

}
