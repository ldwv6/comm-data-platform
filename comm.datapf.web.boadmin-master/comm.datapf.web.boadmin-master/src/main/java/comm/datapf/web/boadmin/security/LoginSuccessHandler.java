package comm.datapf.web.boadmin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginConnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDto;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    LoginService loginService;

    @Autowired
    CommonService commonService;

    private String redirectUri = "/login";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 인증 성공시 사용 Class
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        // 관리자 아이디
        String adminId = authentication.getName();
        paramMap.put("admrId", adminId);

        // 관리자 권한
        Collection<GrantedAuthority> grantedAuthority = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority auth : grantedAuthority) {
            paramMap.put("roleId", auth.getAuthority());
        }

        try {

            // 관리자 정보 불러오기
            LoginDto loginDto = new LoginDto();
            LoginConnDto loginConnDto = new LoginConnDto();

            loginDto = loginService.selectAdminMap(paramMap);
            loginDto.setIp(CommonUtils.getClientIpAddr(request));

            String connPsblIp = loginService.getIpAdminMap(loginDto);
            loginConnDto = loginService.getTimeAdminMap(loginDto.getAdmrId());

            if (StringUtils.isEmpty(connPsblIp)) {
                loginDto.setConnPsblIp("");
            } else {
                loginDto.setConnPsblIp(connPsblIp);
            }

            if (StringUtils.isEmpty(loginConnDto)) {
                loginDto.setConnPsblStrtTime("");
                loginDto.setConnPsblEndTime("");
            } else {
                loginDto.setConnPsblStrtTime(loginConnDto.getConnPsblStrtTime());
                loginDto.setConnPsblEndTime(loginConnDto.getConnPsblEndTime());
            }

            // 접속아이피 저장
//          System.out.println("CommonCode.getClientIpAddr(request) >>>>>>>>>>>>>>>>>>>   :::" + CommonCode.getClientIpAddr(request));
//          System.out.println("request.getHeader(X-Forwarded-For) >>>>>>>>>>>>>> " + request.getHeader("X-Forwarded-For"));

            if (!StringUtils.equals(CommonUtils.getClientIpAddr(request),
                    StringUtils.nvl(loginDto.getConnPsblIp(), ""))) {

                // 접속시간 로그
                LoginAuthDto loginAuth = new LoginAuthDto();
                loginAuth.setAdmrId(loginDto.getAdmrId());
                loginAuth.setPgmId("login");
                loginAuth.setPgmTypeCd("LOGIN");
                loginAuth.setPgmDtlId("");
                loginAuth.setSearchItem("실패");
                loginAuth.setSearchText("접근불가IP, " + CommonUtils.getClientIpAddr(request));
                loginAuth.setAdmrIp(CommonUtils.getClientIpAddr(request));
                loginAuth.setHistDvsnCd("CONN");

                commonService.insertBoLog(loginAuth);

                logger.info("Login IP Block ID [" + authentication.getName() + "], CONN_IP ["+ CommonUtils.getClientIpAddr(request) + "]");

                session.invalidate();

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">\n");
                out.print("<head>\n");
                out.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n");
                out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
                out.print("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
                out.print("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n");
                out.print("<link href=\"/static/css/style.default.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print("<link href=\"/static/css/restyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/jquery-2.1.1.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootstrap.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootbox.min.js\"></script>\n");
                out.print("<script language=\"JavaScript\" src=\"/static/js/common.js\"></script>\n");
                out.print("<script type=\"text/javascript\">\n");
                out.print("$(document).ready(function(){");
                out.print("alert('접속 가능한 IP가 아닙니다."
                        + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에게 문의하세요.',function(){location.href='logout'},'0000', 'logout');\n");
                out.print("});");
                out.print("</script>\n");
                out.print("</head>\n");
                out.print("<body>\n");
                out.print("</body>\n");
                out.print("</html>\n");
                out.close();
                return;
            }

            if (StringUtils.isNotEmpty(loginDto.getConnPsblStrtTime())
                    && StringUtils.isNotEmpty(loginDto.getConnPsblEndTime())) {
                // 접속시간 체크
                int connStartTime = Integer.parseInt(loginDto.getConnPsblStrtTime());
                int connEndTime = Integer.parseInt(loginDto.getConnPsblEndTime());
                int nowTime = Integer.parseInt(DateUtils.getToday("HHmmss"));

                if (!(connStartTime <= nowTime && nowTime <= connEndTime)) {

                    // 접속시간 로그
                    LoginAuthDto loginAuth = new LoginAuthDto();
                    loginAuth.setAdmrId(loginDto.getAdmrId());
                    loginAuth.setPgmId("login");
                    loginAuth.setPgmTypeCd("LOGIN");
                    loginAuth.setPgmDtlId("");
                    loginAuth.setSearchItem("실패");
                    loginAuth.setSearchText("접근불가시간");
                    loginAuth.setHistDvsnCd("CONN");

					commonService.insertBoLog(loginAuth);

                    logger.info("Login TIME Block ID [" + authentication.getName() + "], CONN_TIME ["
                            + DateUtils.timeFormat(loginDto.getConnPsblStrtTime()) + " ~ "
                            + DateUtils.timeFormat(loginDto.getConnPsblEndTime()) + "]");

                    session.invalidate();

                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(
                            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                    out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">\n");
                    out.print("<head>\n");
                    out.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n");
                    out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
                    out.print("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
                    out.print("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n");
                    out.print(
                            "<link href=\"/static/css/style.default.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                    out.print("<link href=\"/static/css/restyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                    out.print(
                            "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/jquery-2.1.1.min.js\"></script>\n");
                    out.print(
                            "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootstrap.min.js\"></script>\n");
                    out.print(
                            "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootbox.min.js\"></script>\n");
                    out.print("<script language=\"JavaScript\" src=\"/static/js/common.js\"></script>\n");
                    out.print("<script type=\"text/javascript\">\n");
                    out.print("$(document).ready(function(){");
                    out.print("alert('접속 가능한 시간이 아닙니다."
                            + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;접속가능시간 : "
                            + DateUtils.timeFormat(loginDto.getConnPsblStrtTime()) + " ~ "
                            + DateUtils.timeFormat(loginDto.getConnPsblEndTime())
                            + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에게 문의하세요.',function(){location.href='logout'},'0000', 'logout');\n");
                    out.print("});");
                    out.print("</script>\n");
                    out.print("</head>\n");
                    out.print("<body>\n");
                    out.print("</body>\n");
                    out.print("</html>\n");
                    out.close();
                    return;
                }

            } else {
                // 접속시간 null
                session.invalidate();

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">\n");
                out.print("<head>\n");
                out.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n");
                out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
                out.print("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
                out.print("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n");
                out.print("<link href=\"/static/css/style.default.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print("<link href=\"/static/css/restyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/jquery-2.1.1.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootstrap.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootbox.min.js\"></script>\n");
                out.print("<script language=\"JavaScript\" src=\"/static/js/common.js\"></script>\n");
                out.print("<script type=\"text/javascript\">\n");
                out.print("$(document).ready(function(){");
                out.print("alert('접속 가능한 시간이 아닙니다."
                        + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에게 문의하세요.',function(){location.href='logout'},'0000', 'logout');\n");
                out.print("});");
                out.print("</script>\n");
                out.print("</head>\n");
                out.print("<body>\n");
                out.print("</body>\n");
                out.print("</html>\n");
                out.close();
                return;
            }

            // 관리자상태 휴면인 경우, 처리
            if (StringUtils.equals("D0", loginDto.getAdmrStatCd())) {
                // 접속시간 null
                session.invalidate();

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">\n");
                out.print("<head>\n");
                out.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n");
                out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
                out.print("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
                out.print("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n");
                out.print("<link href=\"/static/css/style.default.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print("<link href=\"/static/css/restyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/jquery-2.1.1.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootstrap.min.js\"></script>\n");
                out.print(
                        "<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootbox.min.js\"></script>\n");
                out.print("<script language=\"JavaScript\" src=\"/static/js/common.js\"></script>\n");
                out.print("<script type=\"text/javascript\">\n");
                out.print("$(document).ready(function(){");
                out.print("alert('30일 이상, 로그인 내역이 없어 휴면상태입니다."
                        + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;관리자에게 문의하세요.',function(){location.href='logout'},'0000', 'logout');\n");
                out.print("});");
                out.print("</script>\n");
                out.print("</head>\n");
                out.print("<body>\n");
                out.print("</body>\n");
                out.print("</html>\n");
                out.close();
                return;
            }

            // 비밀번호 에러카운트 초기화
            paramMap.put("pwdErrRcnt", "0");
            loginService.updatePwdCnt(paramMap);

            // 관리자 개인정보취급권한여부 체크 추가
            loginDto.setPsnlTrtmYn(loginService.getPsnlTrtmInfo(adminId));
            paramMap.put("psnlTrtmYn", loginDto.getPsnlTrtmYn());

            // 관리자 허용 IP대역체크 체크 추가
            loginDto.setBlockIpList(loginService.getBlockIpList());

            // 권한별 메뉴 불러와 세션의 저장
            List<MenuPgmDto> menuList = loginService.getMenuList(paramMap);
            /*for(int i=0; i<menuList.size();i++){
                // 메뉴 트리의 마지막인 경우 체크
                if( i > 0 && menuList.get(i).getMenuDepth() !=3 && menuList.get(i-1).getMenuDepth()==3 ){
                    menuList.get(i).setMenuTree("Y");
                }else {
                    menuList.get(i).setMenuTree("N");
                }
            }*/
            session.setAttribute("menuList", menuList);

            // profiles 정보 세션 저장
            session.setAttribute("wasType", WasTypeBean.getWasType());
            session.setAttribute("admrNm", loginDto.getAdmrNm());

            // 자동 로그아웃 시간
            loginDto.setLogoutTime(PropertyUtils.getProperty("logout.time"));

            // 로그인타임 시간
            loginDto.setLoginTime(DateUtils.getToday("yyyyMMddHHmmss"));

            // 패스워드 변경 7일전
            int checkDate = DateUtils.getDiffOfDate(
                    StringUtils.nvl(loginDto.getPwdChngDtime(), "20000101").substring(0, 8), DateUtils.getToday());

            int policyDate = Integer.parseInt(PropertyUtils.getProperty("passwd.policyDate"));
            int remainDate = policyDate - checkDate;

            loginDto.setPwdRemainDt(remainDate);

            ObjectMapper objectMapper = new ObjectMapper();
            // 관리자 정보 세션에 저장
            this.setSession(session, objectMapper.convertValue(loginDto, HashMap.class));

            // 로그인 로그
            // 로그인 이력 session token값 추가
            LoginAuthDto loginAuthDto = new LoginAuthDto();

            loginAuthDto.setAdmrId(loginDto.getAdmrId());
            loginAuthDto.setPgmId("login");
            loginAuthDto.setPgmTypeCd("LOGIN");
            loginAuthDto.setPgmDtlId("");
            loginAuthDto.setSearchItem("성공");
            loginAuthDto.setSearchText(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
            loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request).toString());
            loginAuthDto.setHistDvsnCd("CONN");

            session.setAttribute("loginToken", loginAuthDto.getSearchText());

            commonService.insertBoLog(loginAuthDto);

            //관리자관리 최종로그인시간 추가
            loginService.updateLastLinTime(loginDto);

            logger.info("Login Success Message [" + authentication.getDetails() + "], ID [" + authentication.getName() + "]");

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> getRedirectUri() " + getRedirectUri());
        request.getRequestDispatcher(getRedirectUri()).forward(request, response);

    }

    @SuppressWarnings("rawtypes")
    private void setSession(HttpSession session, Map<String, Object> paramMap) {

        if (StringUtils.isNotEmpty(paramMap)) {

            Iterator iterator = paramMap.entrySet().iterator();

            while (iterator.hasNext()) {

                Map.Entry entry = (Map.Entry) iterator.next();
                session.setAttribute(entry.getKey().toString(), entry.getValue());
            }

        } else {
            setRedirectUri("/loginFail");
        }

    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
