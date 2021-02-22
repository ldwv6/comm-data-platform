package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Alias("ClsgSaleTtlzMstrDto")
public class ClsgSaleTtlzMstrDto {

    @ApiModelProperty(position = 1,required = false, value = "결제마감일자")
    private int no;

    @ApiModelProperty(position = 2,required = false, value = "결제마감 시작일자")
    private String searchStartDate;

    @ApiModelProperty(position = 2,required = false, value = "결제마감 종료일자")
    private String searchEndDate;

    @ApiModelProperty(position = 3,required = false, value = "사업장구분")
    private String svcDvsnCd;

    @ApiModelProperty(position = 4,required = false, value = "사업장구분명")
    private String svcDvsnNm;

    @ApiModelProperty(position = 5,required = false, value = "매출유형코드(업체구분)")
    private String bzepDvsnCd;

    @ApiModelProperty(position = 6,required = false, value = "매출유형명")
    private String bzepDvsnNm;

    @ApiModelProperty(position = 7,required = false, value = "매출유형코드(업체정산유형)")
    private String bzepExccTypeCd;

    @ApiModelProperty(position = 7,required = false, value = "매출유형명(업체정산유형))")
    private String bzepExccTypeNm;

    @ApiModelProperty(position = 8,required = false, value = "수동통제여부")
    private String mnalCtrlYn;

    @ApiModelProperty(position = 9,required = false, value = "수동통제여부명")
    private String mnalCtrlNm;

    @ApiModelProperty(position = 10,required = false, value = "주문유형코드")
    private String ordeTypeCd;

    @ApiModelProperty(position = 11,required = false, value = "마감상태코드")
    private String clsgStatCd;

    @ApiModelProperty(position = 12,required = false, value = "결제금액")
    private Double sttlAmt;

    @ApiModelProperty(position = 13,required = false, value = "전체수량(판매수량)")
    private Double saleCnt;

    @ApiModelProperty(position = 14,required = false, value = "수량(주문)")
    private Double ordeSaleCnt;

    @ApiModelProperty(position = 15,required = false, value = "수량(취소)")
    private Double cnclSaleCnt;

    @ApiModelProperty(position = 16,required = false, value = "금액(판매)")
    private Double saleAmt;

    @ApiModelProperty(position = 17,required = false, value = "금액(주문)")
    private Double ordeSaleAmt;

    @ApiModelProperty(position = 18,required = false, value = "금액(취소)")
    private Double cnclSaleAmt;

    @ApiModelProperty(position = 19,required = false, value = "주문예매수수료(판매)")
    private Double rsvFeeAmt;

    @ApiModelProperty(position = 20,required = false, value = "주문예매수수료(주문)")
    private Double ordeRsvFeeAmt;

    @ApiModelProperty(position = 21,required = false, value = "주문예매수수료(취소)")
    private Double cnclRsvFeeAmt;

    @ApiModelProperty(position = 22,required = false, value = "주문_배송료(판매)")
    private Double shpgAmt;

    @ApiModelProperty(position = 23,required = false, value = "주문_배송료(주문)")
    private Double ordeShpgAmt;

    @ApiModelProperty(position = 24,required = false, value = "주문_배송료(취소)")
    private Double cnclShpgAmt;

    @ApiModelProperty(position = 25,required = false, value = "취소수수료")
    private Double cnclFeeAmt;

    @ApiModelProperty(position = 26,required = false, value = "배송료>추가비용1")
    private Double shpgAmt10;

    @ApiModelProperty(position = 27,required = false, value = "배송료>추가비용2")
    private Double shpgAmt20;

    @ApiModelProperty(position = 28,required = false, value = "배송료>고객부담비")
    private Double shpgAmt30;

    @ApiModelProperty(position = 29,required = false, value = "배송료>고객부담비-잡손실")
    private Double shpgAmt40;

    @ApiModelProperty(position = 30,required = false, value = "배송료>반품택배비")
    private Double shpgAmt50;

    @ApiModelProperty(position = 31,required = false, value = "배송료>수입제반비용")
    private Double shpgAmt60;

    @ApiModelProperty(position = 32,required = false, value = "전표연동일자")
    private String lnkgDt;

    @ApiModelProperty(position = 33,required = false, value = "전표연동번호")
    private int lnkgNo;

    @ApiModelProperty(position = 34,required = false, value = "통합 등록자")
    private String erlmNo;

    @ApiModelProperty(position = 35,required = false, value = "통합 등록일시")
    private ZonedDateTime erlmDtime;

    @ApiModelProperty(position = 36,required = false, value = "통합 변경자")
    private String chngNo;

    @ApiModelProperty(position = 37,required = false, value = "통합 변경일시")
    private ZonedDateTime chngDtime;



}
