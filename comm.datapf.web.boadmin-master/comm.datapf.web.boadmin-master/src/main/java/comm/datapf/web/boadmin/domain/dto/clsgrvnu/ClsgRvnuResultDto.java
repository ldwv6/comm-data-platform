package comm.datapf.web.boadmin.domain.dto.clsgrvnu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 매출집계표 > 매출마감 검색결과 Dto
 *
 * @project 차세대 마감/정산
 * @author N14235
 * @since 2021-02-05
 * @version 1.0
 *<pre>
 *2021.02.05 : 최초 작성
 *</pre>
 */
@Getter
@Setter
public class ClsgRvnuResultDto {

    List<ClsgRvnuSumDto> clsgRvnuSumDtoList;

    List<ClsgRvnuDeptDto> clsgRvnuDeptDtoList;

    List<ClsgRvnuGroupDto> clsgRvnuGroupDtoList;

    List<ClsgRvnuSalesCostDto> clsgRvnuSalesCostDtoList;

}
