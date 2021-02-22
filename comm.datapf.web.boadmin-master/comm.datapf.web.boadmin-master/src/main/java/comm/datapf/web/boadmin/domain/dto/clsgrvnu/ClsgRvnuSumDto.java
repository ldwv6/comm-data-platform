package comm.datapf.web.boadmin.domain.dto.clsgrvnu;

import lombok.Getter;
import lombok.Setter;

/**
 * 매출집계표 > 합계DTO
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
public class ClsgRvnuSumDto {

    private String rvnuTypeCd;     /*  매출유형코드    */
    private String rvnuTypeNm;     /*  매출유형명      */
    private String taxTypeCd;      /*  과면세코드      */
    private String taxTypeNm;      /*  과면세명        */
    private Integer ordeCnt;       /*  주문건수        */
    private Long ordePcstAmt;      /*  주문원가금액    */
    private Long ordeAmt;          /*  주문금액        */
    private Integer cnclCnt;       /*  취소수량        */
    private Long cnclPcstAmt;      /*  취소원가금액    */
    private Long cnclAmt;          /*  취소금액        */
    private Integer rvnuCnt;       /*  매출수량        */
    private Long rvnuPcstAmt;      /*  매출원가        */
    private Long ipntDsctAmt;      /*  I-Point할인액   */
    private Long rvnuAmt;          /*  매출금액        */
    private Long prftAmt;          /*  이익액          */

}
