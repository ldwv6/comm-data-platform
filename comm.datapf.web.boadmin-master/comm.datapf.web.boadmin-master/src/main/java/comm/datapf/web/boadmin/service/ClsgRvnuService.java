package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.message.ApiSuccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 매출집계표 > 매출마감 Service
 *
 * @project 차세대 마감/정산
 * @author N14235
 * @since 2021-02-05
 * @version 1.0
 *<pre>
 *2021.02.05 : 최초 작성
 *</pre>
 */
public interface ClsgRvnuService {

    ApiSuccess getSearchClsgRvnuList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception;

}
