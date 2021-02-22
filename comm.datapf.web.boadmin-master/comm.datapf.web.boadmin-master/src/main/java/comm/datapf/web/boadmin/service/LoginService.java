package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.domain.dto.login.LoginConnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LoginService {

    LoginDto selectAdminMap(HashMap<String, Object> paramMap) throws Exception;

    void updatePwdCnt(HashMap<String, Object> paramMap) throws Exception;

    String getIpAdminMap(LoginDto loginDto) throws Exception;

    LoginConnDto getTimeAdminMap(String admrId) throws Exception;

    String getPsnlTrtmInfo(String adminId) throws Exception;

    List<HashMap<String, Object>> getBlockIpList()throws Exception;

    List<MenuPgmDto> getMenuList(HashMap<String, Object> paramMap) throws Exception;

    void updateLastLinTime(LoginDto loginDto)throws Exception;

    ApiSuccess findPwd(Model model, HttpServletRequest request) throws Exception;

    void logout(HttpServletRequest request) throws Exception;

    HashMap<String, Object> sessionCheck(HttpServletRequest request) throws Exception;

    ApiSuccess<String> doUpdatePassword(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception;

    String getPsnlTrtmProgramInfo(String srcPath)throws Exception;

    HashMap<String, Object> getRoleYn(HashMap<String, Object> paramMap) throws Exception;

    String getPgmTypeCd(HashMap<String, Object> paramMap) throws Exception;

    List<HashMap<String, Object>> getMchtAdminInfo(HashMap<String, Object> loginMap) throws Exception;

    String getLoginToken(String adminId) throws Exception;

}
