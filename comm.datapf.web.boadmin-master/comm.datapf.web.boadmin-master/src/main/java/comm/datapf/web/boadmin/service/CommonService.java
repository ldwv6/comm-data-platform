package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.domain.dto.common.ComCdAdmnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.message.ApiSuccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonService {

    void insertBoLog(LoginAuthDto loginAuthDto) throws Exception;

    void insertBoLog(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception;

    void insertRoleBoLog(HttpServletRequest request, HttpServletResponse response, String PGM_TYPE_CD, HashMap<String, Object> paramMap, String mode) throws Exception;

    void insertRoleYnBoLog(HttpServletRequest request, HttpServletResponse response, String pgmTypeCd, HashMap<String, Object> paramMap) throws Exception;

    ApiSuccess<List<ComCdAdmnDto>> doCodeListSelect(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception;
}
