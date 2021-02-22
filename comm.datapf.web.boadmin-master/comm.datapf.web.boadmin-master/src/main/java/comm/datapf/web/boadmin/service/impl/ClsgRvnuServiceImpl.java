package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.domain.dao.ClsgRvnuDao;
import comm.datapf.web.boadmin.domain.dto.clsgrvnu.ClsgRvnuResultDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.ClsgRvnuService;
import comm.datapf.web.boadmin.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 매출집계표 > 매출마감 ServiceImpl
 *
 * @project 차세대 마감/정산
 * @author N14235
 * @since 2021-02-05
 * @version 1.0
 *<pre>
 *2021.02.05 : 최초 작성
 *</pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClsgRvnuServiceImpl implements ClsgRvnuService {

    private final CommonService commonService;

    private final ClsgRvnuDao clsgRvnuDao;

    @Override
    public ApiSuccess<ClsgRvnuResultDto> getSearchClsgRvnuList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception{
        ApiSuccess<ClsgRvnuResultDto> result = new ApiSuccess<>();
        ClsgRvnuResultDto clsgRvnuResultDto = new ClsgRvnuResultDto();

        clsgRvnuResultDto.setClsgRvnuSumDtoList(clsgRvnuDao.selectListClsgRvnuSum(paramMap));
        clsgRvnuResultDto.setClsgRvnuDeptDtoList(clsgRvnuDao.selectListClsgRvnuDept(paramMap));
        clsgRvnuResultDto.setClsgRvnuGroupDtoList(clsgRvnuDao.selectListClsgRvnuGroup(paramMap));
        clsgRvnuResultDto.setClsgRvnuSalesCostDtoList(clsgRvnuDao.selectListClsgRvnuSalesCost(paramMap));

        result.setData(clsgRvnuResultDto);

        //syslog save
        commonService.insertBoLog(request, response, paramMap);

        return result;
    }

}
