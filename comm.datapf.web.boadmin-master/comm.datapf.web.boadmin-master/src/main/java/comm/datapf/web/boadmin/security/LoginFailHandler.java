package comm.datapf.web.boadmin.security;

import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.CommonUtils;
import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

@Slf4j
public class LoginFailHandler implements AuthenticationFailureHandler {

    private String redirectUri = "loginFail";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginService loginService;

    @Autowired
    CommonService commonService;

    /**
     * 인증 실패시 사용 Class
     */

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationexception)throws IOException, ServletException {

        String email = request.getParameter("username");
        String passwd = request.getParameter("password");

//		System.out.print("" + request.getParameterNames());

        logger.info("login Fail Message [" + authenticationexception.getMessage() +"], ID ["+email+"]");

        for(Enumeration<?> keys = request.getParameterNames(); keys.hasMoreElements();){
            String key = (String)keys.nextElement();
            logger.debug(key + "==>" + request.getParameter(key));
        }

        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(passwd)){

            request.setAttribute("msg", "잘못된 접근입니다.<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에 문의하세요.");

        }else{

            HashMap<String, Object> paramMap = null;
            LoginDto loginDto = null;

            try {
                paramMap = new HashMap<String, Object>();

                paramMap.put("admrEmal", email);
                loginDto = loginService.selectAdminMap(paramMap);

                if(StringUtils.isEmpty(loginDto.getAdmrId())){
                    logger.info("login Fail not found id ["+email+"]");
                    request.setAttribute("msg", "존재하지 않는 ID 입니다.");
                }else{

                    paramMap.put("admrId", loginDto.getAdmrId());

                    if(!StringUtils.equals("Y", StringUtils.nvl(loginDto.getUseYn(),"N"))){
                        logger.info("login Fail not use id ["+loginDto.getAdmrId()+"]");
                        request.setAttribute("msg", "사용할 수 없는 ID 입니다.<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에 문의하세요.");

                        //비인가 ID 접근 이력관리 처리 추가
                        LoginAuthDto loginAuthDto = new LoginAuthDto();
                        loginAuthDto.setAdmrId(loginDto.getAdmrId());
                        loginAuthDto.setPgmId("login");
                        loginAuthDto.setPgmTypeCd("LOGIN");
                        loginAuthDto.setPgmDtlId("");
                        loginAuthDto.setSearchItem("실패");
                        loginAuthDto.setSearchText("사용할 수 없는 ID 입니다");
                        loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request).toString());
                        loginAuthDto.setHistDvsnCd("CONN");
                        commonService.insertBoLog(loginAuthDto);

                    }else if(Integer.parseInt(StringUtils.nvl(loginDto.getPwdErrRcnt(),"0")) >= 5){
                        logger.info("login Fail wrong 5 time password id ["+loginDto.getAdmrId()+"]");
                        request.setAttribute("msg", "패스워드 5회 이상 실패.<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에 문의하세요.");

                        //비인가 ID 접근 이력관리 처리 추가
                        LoginAuthDto loginAuthDto = new LoginAuthDto();
                        loginAuthDto.setAdmrId(loginDto.getAdmrId());
                        loginAuthDto.setPgmId("login");
                        loginAuthDto.setPgmTypeCd("LOGIN");
                        loginAuthDto.setPgmDtlId("");
                        loginAuthDto.setSearchItem("실패");
                        loginAuthDto.setSearchText("패스워드 5회 이상 실패");
                        loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request).toString());
                        loginAuthDto.setHistDvsnCd("CONN");
                        commonService.insertBoLog(loginAuthDto);

                    }else if(StringUtils.isEmpty(loginDto.getRoleId())){

                        logger.info("login Fail Empty Role id ["+loginDto.getAdmrId()+"]");
                        request.setAttribute("msg", "사용할 수 없는 ID 입니다(권한미부여).<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에 문의하세요.");

                        //비인가 ID 접근 이력관리 처리 추가
                        LoginAuthDto loginAuthDto = new LoginAuthDto();
                        loginAuthDto.setAdmrId(loginDto.getAdmrId());
                        loginAuthDto.setPgmId("login");
                        loginAuthDto.setPgmTypeCd("LOGIN");
                        loginAuthDto.setPgmDtlId("");
                        loginAuthDto.setSearchItem("실패");
                        loginAuthDto.setSearchText("사용할 수 없는 ID 입니다(권한미부여)");
                        loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request).toString());
                        loginAuthDto.setHistDvsnCd("CONN");
                        commonService.insertBoLog(loginAuthDto);

                    }else{
                        logger.info("login Fail wrong password id ["+loginDto.getAdmrId()+"]");
                        request.setAttribute("msg", "패스워드 오류입니다.");

                        //패스워드 에러 카운트 증가
                        paramMap.put("pwdErrRcnt", Integer.parseInt(StringUtils.nvl(loginDto.getPwdErrRcnt(), "0")) + 1);
                        loginService.updatePwdCnt(paramMap);

                        LoginAuthDto loginAuthDto = new LoginAuthDto();
                        loginAuthDto.setAdmrId(loginDto.getAdmrId());
                        loginAuthDto.setPgmId("login");
                        loginAuthDto.setPgmTypeCd("LOGIN");
                        loginAuthDto.setPgmDtlId("");
                        loginAuthDto.setSearchItem("실패");
                        loginAuthDto.setSearchText("비밀번호 불일치");
                        loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request).toString());
                        loginAuthDto.setHistDvsnCd("CONN");


                        commonService.insertBoLog(loginAuthDto);
                    }
                }

            } catch (Exception e) {

                logger.error(this.getClass() + " onAuthenticationFailure " + e);

            } finally {
                paramMap = null;
                loginDto = null;
            }
        }
        request.getRequestDispatcher(getRedirectUri()).forward(request, response);
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
