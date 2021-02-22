package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsgSaleTtlzSttlDto {

    @ApiModelProperty(position = 1,required = false, value = "NO")
    private int no;

    @ApiModelProperty(position = 2,required = false, value = "결제마감 시작일자")
    private String searchStartDate;

    @ApiModelProperty(position = 3,required = false, value = "결제마감 종료일자")
    private String searchEndDate;

    @ApiModelProperty(position = 4,required = false, value = "결제수단코드")
    private String sttlMthdCd;

    @ApiModelProperty(position = 5,required = false, value = "결제수단명")
    private String sttlMthdNm;

    @ApiModelProperty(position = 6,required = false, value = "총 결제금액")
    private Double sttlAmt;

    @ApiModelProperty(position = 7,required = false, value = "쇼핑 결제금액")
    private Double shopSttlAmt;

    @ApiModelProperty(position = 8,required = false, value = "도서 결제금액")
    private Double bookSttlAmt;

    @ApiModelProperty(position = 9,required = false, value = "티켓 결제금액")
    private Double ticketSttlAmt;

    @ApiModelProperty(position = 10,required = false, value = "영화 결제금액")
    private Double movieSttlAmt;

    @ApiModelProperty(position = 11,required = false, value = "POS 결제금액")
    private Double posSttlAmt;

    @ApiModelProperty(position = 12,required = false, value = "국내숙박 결제금액")
    private Double lodgeSttlAmt;

    @ApiModelProperty(position = 13,required = false, value = "호텔 결제금액")
    private Double hotelSttlAmt;

    @ApiModelProperty(position = 14,required = false, value = "국내항공 결제금액")
    private Double domAirSttlAmt;

    @ApiModelProperty(position = 15,required = false, value = "해외항공 결제금액")
    private Double airSttlAmt;

    @ApiModelProperty(position = 16,required = false, value = "여행 결제금액")
    private Double tourSttlAmt;

}
