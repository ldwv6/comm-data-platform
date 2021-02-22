package comm.datapf.web.boadmin.domain.dto.clsgrvnu;

import lombok.Getter;
import lombok.Setter;

/**
 * 매출집계표 > 판매비용 DTO
 *
 * @project 차세대 마감/정산
 * @author N14235
 * @since 2021-02-15
 * @version 1.0
 *<pre>
 *2021.02.05 : 최초 작성
 *</pre>
 */
@Getter
@Setter
public class ClsgRvnuSalesCostDto {

    private String clsgStatDvsnCd;
    private String clsgStatDvsnNm;
    private Long omTxtnCost;          /*  OM과세-추가비용       */
    private Long omTxexCost;          /*  OM면세-추가비용       */
    private Long mdTxtnCost;          /*  MD과세-추가비용       */
    private Long mdTxexCost;          /*  MD면세-추가비용       */
    private Long drctTxtnCost;        /*  직매입과세-추가비용   */
    private Long drctTxexCost;        /*  직매입면세-추가비용   */
    private Long omBdenCost;          /*  OM-고객부담비용       */
    private Long mdBdenCost;          /*  MD-고객부담비용       */
    private Long drctBdenCost;        /*  직매입-고객부담비     */
    private Long omRtrnDlivCost;      /*  OM-반품택배비         */
    private Long mdRtrnDlivCost;      /*  MD-반품택배비         */
    private Long drctRtrnDlivCost;    /*  직매입-반품택배비     */
    private Long etcImptCost;         /*  기타-수입제반비용     */

}
