package comm.datapf.web.boadmin.domain.dto.clsgSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsgSaleTtlzDelvDto {

    @ApiModelProperty(position = 1,required = false, value = "NO")
    private int no;

    @ApiModelProperty(position = 2,required = false, value = "서비스구분")
    private String svcDvsnCd;

    @ApiModelProperty(position = 3,required = false, value = "마감상태코드")
    private String clsgStatCd;

    @ApiModelProperty(position = 4,required = false, value = "마감상태명")
    private String clsgStatNm;

    @ApiModelProperty(position = 5,required = false, value = "주문유형코드")
    private String ordeTypeCd;

    @ApiModelProperty(position = 6,required = false, value = "배숑료_추가비용(OM 상품)")
    private Double totSttl;

    @ApiModelProperty(position = 7,required = false, value = "배숑료_추가비용(OM 상품)")
    private Double totSale;

    @ApiModelProperty(position = 8,required = false, value = "배숑료_추가비용(OM 상품)")
    private Double omAddShpgAmt;

    @ApiModelProperty(position = 9,required = false, value = "배송료_추가비용(MD 매입)")
    private Double mdBuyAddShpgAmt;

    @ApiModelProperty(position = 10,required = false, value = "배송료_추가비용(MD 수수료)")
    private Double mdFeeAddShpgAmt;

    @ApiModelProperty(position = 11,required = false, value = "배송료_추가비용(직매입)")
    private Double interBuyAddShpgAmt;

    @ApiModelProperty(position = 12,required = false, value = "배송료_추가비용1(MD 상품)")
    private Double mdAddShpgAmt;

    @ApiModelProperty(position = 13,required = false, value = "배송료_고객부담비(OM)")
    private Double omCustShpgAmt;

    @ApiModelProperty(position = 14,required = false, value = "배송료_고객부담비(MD 매입)")
    private Double mdBuyCustShpgAmt;

    @ApiModelProperty(position = 15,required = false, value = "배송료_고객부담비(MD 수수료)")
    private Double mdFeeCustShpgAmt;

    @ApiModelProperty(position = 16,required = false, value = "배송료_고객부담비(직매입)")
    private Double interBuyCustShpgAmt;

    @ApiModelProperty(position = 17,required = false, value = "배송료_고객부담비(MD)")
    private Double mdCustShpgAmt;

    @ApiModelProperty(position = 18,required = false, value = "배송료_잡손실")
    private Double lossShpgAmt;

    @ApiModelProperty(position = 19,required = false, value = "배송료_반품택배비(OM)")
    private Double omRetuShpgAmt;

    @ApiModelProperty(position = 20,required = false, value = "배송료_반품택배비(MD 매입)")
    private Double mdBuyRetuShpgAmt;

    @ApiModelProperty(position = 21,required = false, value = "배송료_반품택배비(MD 수수료)")
    private Double mdFeeRetuShpgAmt;

    @ApiModelProperty(position = 22,required = false, value = "배송료_반품택배비(직매입)")
    private Double interBuyRetuShpgAmt;

    @ApiModelProperty(position = 23,required = false, value = "배송료_반품택배비(MD)")
    private Double mdRetuShpgAmt;

    @ApiModelProperty(position = 24,required = false, value = "배송료_수입제반비용(OM)")
    private Double omInterShpgAmt;

    @ApiModelProperty(position = 25,required = false, value = "배송료_수입제반비용(MD 매입)")
    private Double mdBuyInterShpgAmt;

    @ApiModelProperty(position = 26,required = false, value = "배송료_수입제반비용(MD 수수료)")
    private Double mdFeeInterShpgAmt;

    @ApiModelProperty(position = 27,required = false, value = "배송료_수입제반비용(직매입)")
    private Double interBuyInterShpgAmt;

    @ApiModelProperty(position = 28,required = false, value = "배송료_수입제반비용(MD)")
    private Double mdInterShpgAmt;

    @ApiModelProperty(position = 29,required = false, value = "배송료_수입제반비용(전체)")
    private Double shpgAmt;
}
