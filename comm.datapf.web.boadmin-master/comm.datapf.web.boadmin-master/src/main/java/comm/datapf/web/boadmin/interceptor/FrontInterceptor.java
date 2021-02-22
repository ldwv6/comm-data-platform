package comm.datapf.web.boadmin.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import comm.datapf.web.boadmin.domain.dto.login.LoginConnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;

public class FrontInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginService loginService;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        logger.debug("Parameter ===================================================");
        logger.debug(request.getRequestURI());

        for(Enumeration<?> keys = request.getParameterNames(); keys.hasMoreElements();){
            String key = (String)keys.nextElement();
            logger.debug(key + "==>" + request.getParameter(key));
        }
        logger.debug("=============================================================");
        logger.debug("Header=======================================================");
        logger.debug(request.getMethod());
        for(Enumeration<String> en = request.getHeaderNames();en.hasMoreElements();){
            String name = en.nextElement();
            logger.debug(name + "==>" + request.getHeader(name));
            if(StringUtils.equals("close", request.getHeader("connnection"))){
                // 20161111 세션무효처리
                HttpSession session = request.getSession();
                session.invalidate();
            }
        }
        logger.debug("=============================================================");

        HttpSession session = request.getSession();
        String reqURI = request.getRequestURI();

        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");

        //log MDC 추가
        request.getHeader("X-Forwarded-For");
        String admrId =  (String)session.getAttribute("empyNo");
        String mdcStr = "["+admrId+"]";
        MDC.put("EMPY_NO", mdcStr);

        String srcPath = reqURI;
        //srcPath = reqURI.substring(1,reqURI.length());

        //인증시에만(로그인 성공)
        if (securityContext != null && (!PropertyUtils.getProperty("pass.url").contains(srcPath) && !PropertyUtils.getProperty("pass.ajax.url").contains(srcPath))) {
            String adminId = securityContext.getAuthentication().getName();

            long loginTimeCheck = Long.parseLong(StringUtils.nvl(PropertyUtils.getProperty("login.check.time"),"0")) * 100;
            long logTime = Long.parseLong(StringUtils.nvl(session.getAttribute("loginTime"),"0"));
            long nowLogTime = Long.parseLong(DateUtils.getToday("yyyyMMddHHmmss"));


            //20170728 중복로그인방지 추가
            String dbMode = WasTypeBean.getWasType();
            if(!StringUtils.equals(dbMode, "LOCAL") && !StringUtils.equals(dbMode, "local")){
                if(!(StringUtils.contains(reqURI, "logout") || StringUtils.contains(reqURI, "sessionCheck"))){
                    String _ssoToken = StringUtils.getString(session.getAttribute("loginToken"));
                    String _dbToken = loginService.getLoginToken(adminId);

                    if(!StringUtils.equals(_ssoToken, _dbToken)){
                        logger.info("Login Token Discord ["+adminId+"], ssoToken ["+_ssoToken+"] dbToken ["+_dbToken+"] ");
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        this.front_msg_script(out);
                        //out.print("alert('동일한 ID로 중복 접속 또는 세션이 만료되어 로그아웃합니다.', function(){location.href='/logout'},'0000');");
                        out.print("alert('동일한 ID로 중복 접속 또는 세션이 만료되어 로그아웃합니다.');");
                        out.print("location.href='/logout'");
                        this.after_msg_script(out);
                        out.close();
                        return false;
                    }
                }
            }

            if(nowLogTime - logTime > loginTimeCheck ){
                logger.debug("Start Login Check Time ["+adminId+"]");
                session.setAttribute("LOGIN_TIME", DateUtils.getToday("yyyyMMddHHmmss"));

                HashMap<String, Object> sessionMap = new HashMap<String, Object>();
                LoginConnDto loginConnDto = new LoginConnDto();
                LoginDto loginDto = new LoginDto();

                loginDto.setAdmrId(adminId);
                loginDto.setIp(CommonUtils.getClientIpAddr(request));

                String connPsblIp = loginService.getIpAdminMap(loginDto);//접근허용 IP
                loginConnDto = loginService.getTimeAdminMap(adminId);//접근허용 시간

                if(StringUtils.isEmpty(connPsblIp))sessionMap.put("connPsblIp", ""); else sessionMap.put("connPsblIp", connPsblIp);

                if(StringUtils.isEmpty(loginConnDto)){
                    sessionMap.put("connPsblStrtTime","");sessionMap.put("connPsblEndTime","");
                }else{
                    sessionMap.put("connPsblStrtTime", loginConnDto.getConnPsblStrtTime());
                    sessionMap.put("connPsblEndTime", loginConnDto.getConnPsblEndTime());
                }

                this.setSession(session, sessionMap);

                logger.debug("END Login Check Time ["+adminId+"]");

                //관리가맹점 세션 처리
                ObjectMapper objectMapper = new ObjectMapper();
                this.setSession(session, objectMapper.convertValue(loginDto, HashMap.class));
//                this.setSessionMchtInfo(session, loginMap);
                logger.debug("Set Session Admin Merchant Info ["+loginDto.getAdmrId()+"]");
            }

            //접속아이피 체크
            if(!StringUtils.equals(CommonUtils.getClientIpAddr(request), StringUtils.nvl(session.getAttribute("connPsblIp"),""))){

                logger.info("Login IP Block ID ["+adminId+"], CONN_IP ["+CommonUtils.getClientIpAddr(request)+"]");

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                this.front_msg_script(out);
                /*out.print("alert('접속 가능한 IP가 아닙니다." +
                        "<br/><div style=\"margin-left:37px;\"> 관리자에게 문의하세요.',function(){location.href='/logout'},'0000', 'logout');\n");*/
                out.print("alert('접속 가능한 IP가 아닙니다. 관리자에게 문의하세요.');");
                out.print("location.href='/logout'");
                this.after_msg_script(out);
                out.close();
                return false;
            }

            //TIME CHECK
            if(StringUtils.isNotEmpty(session.getAttribute("connPsblStrtTime")) && StringUtils.isNotEmpty(session.getAttribute("connPsblEndTime"))){
                //접속시간 체크
                int connStartTime = Integer.parseInt(session.getAttribute("connPsblStrtTime").toString());
                int connEndTime = Integer.parseInt(session.getAttribute("connPsblEndTime").toString());
                int nowTime = Integer.parseInt(DateUtils.getToday("HHmmss"));

                if(!(connStartTime <= nowTime && nowTime <= connEndTime)){

                    logger.info("Login TIME Block ID ["+adminId+"], CONN_TIME ["+DateUtils.timeFormat(session.getAttribute("connPsblStrtTime"))+" ~ "+DateUtils.timeFormat(session.getAttribute("connPsblEndTime"))+"]");

                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    this.front_msg_script(out);
                    /*out.print("alert('접속 가능한 시간이 아닙니다." +
                            "<br/><div style=\"margin-left:37px;\">접속가능시간 : "+DateUtils.timeFormat(session.getAttribute("connPsblStrtTime"))+" ~ "+DateUtils.timeFormat(session.getAttribute("connPsblEndTime"))+
                            "<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.',function(){location.href='/logout'},'0000', 'logout');\n");*/
                    out.print("alert('alert('접속 가능한 시간이 아닙니다. 접속가능시간 :" +DateUtils.timeFormat(session.getAttribute("connPsblStrtTime"))+" ~ "+DateUtils.timeFormat(session.getAttribute("connPsblEndTime"))+ " 관리자에게 문의하세요.');");
                    out.print("location.href='/logout'");
                    this.after_msg_script(out);;
                    out.close();
                    return false;
                }
            }else{
                //접속시간 null
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                this.front_msg_script(out);
                /*out.print("alert('접속 가능한 시간이 아닙니다." +
                        "<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.',function(){location.href='logout'},'0000', 'logout');\n");*/
                out.print("alert('alert('접속 가능한 시간이 아닙니다. 관리자에게 문의하세요.');");
                out.print("location.href='/logout'");
                this.front_msg_script(out);
                out.close();
                return false;
            }

            HashMap<String, Object> paramMap = new HashMap<String, Object>();

            Collection<GrantedAuthority> grantedAuthority = (Collection<GrantedAuthority>) securityContext.getAuthentication().getAuthorities();
            for (GrantedAuthority auth : grantedAuthority) {
                paramMap.put("roleId", auth.getAuthority());
            }
            paramMap.put("admrId", adminId);
            paramMap.put("srcPath", srcPath);

            HashMap<String, Object> roleMap = loginService.getRoleYn(paramMap);
            String pgm_type_cd = loginService.getPgmTypeCd(paramMap);
            if(StringUtils.isNotEmpty(pgm_type_cd)){
                pgm_type_cd = StringUtils.trim(pgm_type_cd);
            }

            //20170515 프로그램이 개인정보 사용화면인지 여부 체크 psnl_trtm_yn = Y 인 경우 개인정보 사용 화면, N인 경우 일반화면
            boolean psnl_trtm_use = true;
            String psnl_trtm_yn = "";
            if(psnl_trtm_use){
                psnl_trtm_yn = loginService.getPsnlTrtmProgramInfo(srcPath);
            }

            if(StringUtils.isNotEmpty(roleMap) ){ //roleMap이 널인경우는 프로그램상세가 아닌 경우 ex) shopList
//            	roleMap.put("INSERT_YN", "N");
//				roleMap.put("UPDATE_YN", "N");
//				roleMap.put("PRINT_YN", "N");

                session.setAttribute("roleMap", roleMap);

                //조회권한이 N 이면 alert
                if(StringUtils.isEmpty(pgm_type_cd)){

                    if(!"Y".equals(StringUtils.nvl(roleMap.get("SELECT_YN"), "N"))){

                        logger.info(this.getClass() + " ROLE SELECT BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        this.front_msg_script(out);
//                        out.print("alert('조회 권한이 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.', function(){history.back();},'9999');");
                        out.print("alert('조회 권한이 없습니다. 관리자에게 문의하세요.');");
                        out.print("history.back();");
                        this.after_msg_script(out);
                        out.close();
                        return false;

                    }else{ //조회권한 OK

                        if(StringUtils.equals("Y", psnl_trtm_yn)){ //화면이 개인정보취급화면인 경우

                            //20170329 관리자 개인정보취급가능 확인 PSNL_TRTM _YN
                            if(StringUtils.equals("N", (String) session.getAttribute("psnlTrtmYn"))){
                                logger.info(this.getClass() + " ROLE SELECT BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");
                                response.setContentType("text/html; charset=UTF-8");
                                PrintWriter out = response.getWriter();
                                this.front_msg_script(out);
                                //out.print("alert('권한이 없어 접근 할 수 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요',function(){history.back();},'9999');");
                                out.print("alert('권한이 없어 접근 할 수 없습니다. 관리자에게 문의하세요.');");
                                out.print("history.back();");
                                this.after_msg_script(out);
                                out.close();
                                return false;
                            }else{

                                //조회시, 개인정보 권한 필요없음.
                                boolean skip = false;
                                boolean _ipPass = false;

                                if(!skip){

                                    List<HashMap<String, Object>> ip_list = new ArrayList<HashMap<String,Object>>();

//                                    		String ip_str = "192.168;193.150;194.155"; //시스템공통정보에서 가져와야함.
                                    ip_list = (List<HashMap<String, Object>>) session.getAttribute("blockIpList"); //미니PC(망분리) IP정보
                                    String conn_ip = CommonUtils.getClientIpAddr(request);
                                    String conn_ip_arr [] = null;
                                    conn_ip_arr = conn_ip.split("\\.");
                                    conn_ip = "";
                                    int okCnt = 0;

                                    if(ip_list.size() < 1){ //리스트가 없을때 패스
                                        _ipPass = true;
                                    }else{
                                        for (int i = 0; i < ip_list.size(); i++) {

                                            if(okCnt > 3){
                                                _ipPass = true;
                                                break;
                                            }

                                            HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                                            tmpMap = ip_list.get(i);


                                            for (int j = 0; j < conn_ip_arr.length; j++) {
                                                conn_ip = conn_ip_arr[j];


                                                for (int k = 1; k < 5; k++) {
                                                    String _p = (String)tmpMap.get("ip"+k);


                                                    if((j+1) == k){

                                                        if(conn_ip.equals(_p)){
                                                            okCnt = okCnt+1;
                                                            break;
                                                        }

                                                        if(StringUtils.isEmpty(_p)){
                                                            okCnt = okCnt+1;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if(ip_list.size() < 2){
                                                if(okCnt > 3){
                                                    _ipPass = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if(!_ipPass){ //ip허용 체크

                                        roleMap.put("INSERT_YN", "N");
                                        roleMap.put("UPDATE_YN", "N");
                                        roleMap.put("PRINT_YN", "N");
                                    }
                                }
                            }
                        }else{

                            //접근화면이 개인정보처리 화면이 아닌 경우, 일반화면인 경우
                            //엑셀다운로드, 등록, 수정처리시 IP체크
                            //개인정보취급여부와 상관없이 엑셀다운로드, 등록, 수정인 경우 IP체크 하려면 skip=false로 해야함.

                            boolean skip = true;
                            boolean _ipPass = false;

                            if(!skip){

                                if(StringUtils.equals("PRINT", pgm_type_cd) || StringUtils.equals("INSERT", pgm_type_cd) || StringUtils.equals("UPDATE", pgm_type_cd)){

                                    List<HashMap<String, Object>> ip_list = new ArrayList<HashMap<String,Object>>();

//                            		String ip_str = "192.168;193.150;194.155"; //시스템공통정보에서 가져와야함.
                                    ip_list = (List<HashMap<String, Object>>) session.getAttribute("blockIpList"); //미니PC(망분리) IP정보
                                    String conn_ip = CommonUtils.getClientIpAddr(request);
                                    String conn_ip_arr [] = null;
                                    conn_ip_arr = conn_ip.split("\\.");
                                    conn_ip = "";
                                    int okCnt = 0;

                                    if(ip_list.size() < 1){ //리스트가 없을때 패스
                                        _ipPass = true;
                                    }else{
                                        for (int i = 0; i < ip_list.size(); i++) {

                                            if(okCnt > 3){
                                                _ipPass = true;
                                                break;
                                            }

                                            HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                                            tmpMap = ip_list.get(i);


                                            for (int j = 0; j < conn_ip_arr.length; j++) {
                                                conn_ip = conn_ip_arr[j];


                                                for (int k = 1; k < 5; k++) {
                                                    String _p = (String)tmpMap.get("ip"+k);


                                                    if((j+1) == k){

                                                        if(conn_ip.equals(_p)){
                                                            okCnt = okCnt+1;
                                                            break;
                                                        }

                                                        if(StringUtils.isEmpty(_p)){
                                                            okCnt = okCnt+1;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if(ip_list.size() < 2){
                                                if(okCnt > 3){
                                                    _ipPass = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if(!_ipPass){ //ip허용 체크
                                        logger.info(this.getClass() + " ROLE "+pgm_type_cd+" BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");
                                        response.setContentType("text/html; charset=UTF-8");
                                        PrintWriter out = response.getWriter();
                                        this.front_msg_script(out);

                                        String evJspStr = PropertyUtils.getProperty("ev.url");

                                        if(StringUtils.equals("PRINT", pgm_type_cd)
                                                && !StringUtils.contains(evJspStr, srcPath)){

//                    						out.print("alert('개인정보취급이 허용된 PC가 아닙니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요',function(){history.back();},'9999');");
                                            //out.print("alert('등록 및 수정이 허용된 PC가 아닙니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요',function(){history.back();},'9999');");
                                            out.print("alert('등록 및 수정이 허용된 PC가 아닙니다. 관리자에게 문의하세요.');");
                                        }else{
                                            //out.print("alert('등록 및 수정이 허용된 PC가 아닙니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요');");
                                            out.print("alert('등록 및 수정이 허용된 PC가 아닙니다. 관리자에게 문의하세요.');");
                                        }
                                        this.after_msg_script(out);
                                        out.close();
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }else{ //프로그램 상세 인 경우

                    if(!"Y".equals(StringUtils.nvl(roleMap.get(pgm_type_cd+"_YN"), "N"))){ //N 인경우, 권한이 없는 경우

                        //20161201 print  권한인 경우,  main으로 보내지 않는 처리 추가
                        logger.info(this.getClass() + " ROLE "+pgm_type_cd+" BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        this.front_msg_script(out);
                        if(StringUtils.equals("PRINT", pgm_type_cd)){
                           // out.print("alert('"+roleMap.get("PGM_NM")+" 권한이 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.',function(){history.back();},'9999');");
                            out.print("alert('"+roleMap.get("PGM_NM")+ " 권한이 없습니다. 관리자에게 문의하세요.');");
                            out.print("history.back();");
                        }else if(StringUtils.equals("DETAIL", pgm_type_cd)){
                            //out.print("alert('"+roleMap.get("PGM_NM")+" 상세권한이 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.');\n");
                            out.print("alert('"+roleMap.get("PGM_NM")+ " 상세권한이 없습니다. 관리자에게 문의하세요.');");
                        }else{
//                        	out.print("alert('"+roleMap.get("PGM_NM")+" 조회권한이 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.',function(){location.href='main'},'9999','main');\n");
                            //out.print("alert('"+roleMap.get("PGM_NM")+" 조회권한이 없습니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.');\n");
                            out.print("alert('"+roleMap.get("PGM_NM")+ " 조회권한이 없습니다. 관리자에게 문의하세요.');");
                        }
                        this.after_msg_script(out);
                        out.close();
                        return false;

                    }else{
                        //20170727 망분리 추가
                        boolean skip = false;
                        boolean _ipPass = false;

                        //리스트안에 상세 데이터 경우에 개인정보 화면인 경우 추가
                        if(StringUtils.equals("Y", psnl_trtm_yn)){
                            if(!skip){

                                List<HashMap<String, Object>> ip_list = new ArrayList<HashMap<String,Object>>();

//                            	String ip_str = "192.168;193.150;194.155"; //시스템공통정보에서 가져와야함.
                                ip_list = (List<HashMap<String, Object>>) session.getAttribute("blockIpList"); //미니PC(망분리) IP정보 //미니PC(망분리) IP정보
                                String conn_ip = CommonUtils.getClientIpAddr(request);
                                String conn_ip_arr [] = null;
                                conn_ip_arr = conn_ip.split("\\.");
                                conn_ip = "";
                                int okCnt = 0;

                                if(ip_list.size() < 1){ //리스트가 없을때 패스
                                    _ipPass = true;
                                }else{
                                    for (int i = 0; i < ip_list.size(); i++) {

                                        if(okCnt > 3){
                                            _ipPass = true;
                                            break;
                                        }

                                        HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                                        tmpMap = ip_list.get(i);

                                        for (int j = 0; j < conn_ip_arr.length; j++) {
                                            conn_ip = conn_ip_arr[j];

                                            for (int k = 1; k < 5; k++) {
                                                String _p = (String)tmpMap.get("ip"+k);


                                                if((j+1) == k){

                                                    if(conn_ip.equals(_p)){
                                                        okCnt = okCnt+1;
                                                        break;
                                                    }

                                                    if(StringUtils.isEmpty(_p)){
                                                        okCnt = okCnt+1;
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        if(ip_list.size() < 2){
                                            if(okCnt > 3){
                                                _ipPass = true;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if(!_ipPass){ //ip허용 체크

                                    roleMap.put("INSERT_YN", "N");
                                    roleMap.put("UPDATE_YN", "N");
                                    roleMap.put("PRINT_YN", "N");
                                }
                            }

                        }

                        //20170329 개인정보취급여부 추가 엑셀다운로드, 등록, 수정인 경우 IP체크
                        skip = false;
                        _ipPass = false;

                        if(!skip){

//                        	System.out.println("pgm_type_cd >>>> " +pgm_type_cd);
                            if(StringUtils.equals("Y", psnl_trtm_yn)){ //개인정보 처리화면인 경우

                                if(StringUtils.equals("PRINT", pgm_type_cd) || StringUtils.equals("INSERT", pgm_type_cd) || StringUtils.equals("UPDATE", pgm_type_cd)){

                                    List<HashMap<String, Object>> ip_list = new ArrayList<HashMap<String,Object>>();

//                    		String ip_str = "192.168;193.150;194.155"; //시스템공통정보에서 가져와야함.
                                    ip_list = (List<HashMap<String, Object>>) session.getAttribute("blockIpList"); //미니PC(망분리) IP정보
                                    String conn_ip = CommonUtils.getClientIpAddr(request);
                                    String conn_ip_arr [] = null;
                                    conn_ip_arr = conn_ip.split("\\.");
                                    conn_ip = "";
                                    int okCnt = 0;

                                    if(ip_list.size() < 1){ //리스트가 없을때 패스
                                        _ipPass = true;
                                    }else{

                                        for (int i = 0; i < ip_list.size(); i++) {
                                            if(okCnt > 3){
                                                _ipPass = true;
                                                break;
                                            }

                                            HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                                            tmpMap = ip_list.get(i);
                                            okCnt = 0;
//                    						System.out.println("tmpMap >>>>>>>>>>>>>>"+ tmpMap);


                                            for (int j = 0; j < conn_ip_arr.length; j++) {
                                                conn_ip = conn_ip_arr[j];

//                            					System.out.println("conn_ip >>>>>>>>>>>>>>>>>" + conn_ip);

                                                for (int k = 1; k < 5; k++) {
                                                    String _p = (String)tmpMap.get("ip"+k);

//	                    							System.out.println("(String)tmpMap.get(ip+k)>>>>>>>>>>>>>>"+ k+"번째" + (String)tmpMap.get("ip"+k));

                                                    if((j+1) == k){
//                    									System.out.println("tmpMap >>>>>>>>>>>>>>" + tmpMap);
//                    									System.out.println("conn_ip >>>>>>>>>>>>>>" + conn_ip);
//                    									System.out.println("_p >>>>>>>>>>>>>>" + _p);

                                                        if(conn_ip.equals(_p)){
//
//                    										System.out.println(">>>>>>>>>>> 1");

                                                            okCnt = okCnt+1;
                                                            break;
                                                        }

                                                        if(StringUtils.isEmpty(_p)){
//                    										System.out.println(">>>>>>>>>>> 2");

                                                            okCnt = okCnt+1;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if(okCnt > 3){
                                                _ipPass = true;
                                                break;
                                            }
                                        }
                                    }

                                    if(!_ipPass){ //ip허용 체크

                                        logger.info(this.getClass() + " ROLE "+pgm_type_cd+" BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");

                                        response.setContentType("text/html; charset=UTF-8");
                                        PrintWriter out = response.getWriter();
                                        this.front_msg_script(out);

                                        String evJspStr = PropertyUtils.getProperty("ev.url");

                                        if(StringUtils.equals("PRINT", pgm_type_cd)
                                                && !StringUtils.contains(evJspStr, srcPath)){
                                            //out.print("alert('등록 및 수정이 허용된 PC가 아닙니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요');");
                                            out.print("alert('등록 및 수정이 허용된 PC가 아닙니다. 관리자에게 문의하세요.');");
                                        }else{
                                            //out.print("alert('등록 및 수정이 허용된 PC가 아닙니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요');");
                                            out.print("alert('등록 및 수정이 허용된 PC가 아닙니다. 관리자에게 문의하세요.');");
                                        }
                                        this.after_msg_script(out);
                                        out.close();
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }



            }else{
                if(!PropertyUtils.getProperty("pass.url").contains(srcPath) && !PropertyUtils.getProperty("pass.ajax.url").contains(srcPath)){

                    logger.info(this.getClass() + " ROLE BLOCK PATH [" + srcPath + "], ID ["+adminId+"]");

                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    this.front_msg_script(out);

                    if(srcPath.equals("login")){
                        out.print("location.href='logout';");
                    }else{
                        out.print("$(document).ready(function(){");
                       // out.print("alert('권한이 없습니다. 관리자에게 문의하세요.',function(){history.back();}
                        out.print("alert('권한이 없습니다. 관리자에게 문의하세요.');");
                        out.print("history.back();");
                        out.print("});");
                    }
                    this.after_msg_script(out);
                    out.close();
                    return false;
                }else{
                    if(session != null){
                        //메인은 제외
                        roleMap = new HashMap<String, Object>();
                        //roleMap.put("PGM_ID", "main");
                        roleMap.put("pgmId", "main");
                        session.setAttribute("roleMap", roleMap);
                    }
                }
            }
        }else {
            //로그인 않하고 비정상 접근인 경우 처리
            if(StringUtils.contains("exception?", srcPath)) {

                if(!StringUtils.isNotEmpty(securityContext)) {
                    session.invalidate();
                }

                logger.info(this.getClass() + " ROLE BLOCK PATH [" + srcPath + "]");

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                this.front_msg_script(out);
                //out.print("alert('비 정상적인 접근입니다.<br/><div style=\"margin-left:37px;\">관리자에게 문의하세요.',function(){history.back();},'9999');");
                out.print("alert('비 정상적인 접근입니다. 관리자에게 문의하세요.');");
                out.print("history.back();");

                this.after_msg_script(out);
                out.close();
                return false;
            }
        }
        if(StringUtils.isNotEmpty(session.getAttribute("admrId"))){
            //패스워드 변경 페이지로 들어오면 체크 안함
            if(request.getRequestURI().contains("logout")){

            }else if(!request.getRequestURI().contains("changePassword")){
                //패스워드 90일 체크
                int checkDate = DateUtils.getDiffOfDate(StringUtils.nvl(session.getAttribute("pwdChngDtime"),"20000101").substring(0,8), DateUtils.getToday());
                int policyDate = Integer.parseInt(PropertyUtils.getProperty("passwd.policyDate"));

                if(checkDate >= policyDate){

                    logger.info(this.getClass() + " ROLE PASSWORD " + policyDate + "DAY ID ["+session.getAttribute("admrId")+"]");

                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("<html>");
                    out.print("<body>");
                    out.print("<script type='text/javascript'>location.href='/changePassword'</script>");
                    out.print("</body>");
                    out.print("</html>");
                    out.close();
                    return false;
                }

                // 공통 ajax url분기
                if(PropertyUtils.getProperty("pass.ajax.url").contains(srcPath)){
                    return true;
                }

                String pwd_re_stup_yn = (String)session.getAttribute("pwdReStupYn");
                //20161128 로그인성공시, 비밀번호재설정여부 PWD_RE_STUP_YN 추가체크 'Y'인 경우, 패스워드 변경페이지로 이동처리
                if(StringUtils.equals("N", pwd_re_stup_yn)){

                    logger.info(this.getClass() + " ROLE PWD_RE_STUP_YN :" + pwd_re_stup_yn + " ID ["+session.getAttribute("admrId")+"]");

                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("<html>");
                    out.print("<body>");
                    out.print("<script type='text/javascript'>location.href='/changePassword?mode=reStup'</script>");
                    out.print("</body>");
                    out.print("</html>");
                    out.close();
                    return false;
                    //return super.preHandle(request, response, handler);
                }

            }

            // 공통 ajax url분기
            if(PropertyUtils.getProperty("pass.ajax.url").contains(srcPath)){
                return true;
            }

        }

        return super.preHandle(request, response, handler);
    }

    private void setSession(HttpSession session, HashMap<String, Object> paramMap) {

        if(StringUtils.isNotEmpty(paramMap)){

            Iterator<?> iterator = paramMap.entrySet().iterator();

            while(iterator.hasNext()){

                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
                session.setAttribute(entry.getKey().toString(), entry.getValue());
            }
        }
    }

    @SuppressWarnings("unused")
    private void setSessionMchtInfo(HttpSession session, HashMap<String, Object> loginMap) {
        List<HashMap<String, Object>> mchtMapList = new ArrayList<HashMap<String, Object>>();

        try {
            mchtMapList = loginService.getMchtAdminInfo(loginMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("MCHT_NO", mchtMapList);
        logger.info("Set Session Admin Merchant Info OK");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    public void front_msg_script(PrintWriter out)throws Exception {
        out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        out.print("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" xml:lang=\"ko\">\n");
        out.print("<head>\n");
        out.print("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n");
        out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
        out.print("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
        out.print("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n");
        out.print("<link href=\"/static/css/style.default.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
        out.print("<link href=\"/static/css/restyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
        out.print("<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/libs/jquery-2.1.1.min.js\"></script>\n");
        out.print("<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootstrap/bootstrap.min.js\"></script>\n");
        out.print("<script type=\"text/javascript\" language=\"javascript\" src=\"/static/js/bootbox.min.js\"></script>\n");
       /* out.print("<script language=\"JavaScript\" src=\"/static/js/common.js\"></script>\n");*/
        out.print("<script type=\"text/javascript\">\n");
        out.print("$(document).ready(function(){");
    }
    //static/js/bootstrap/bootstrap.min.j

    public void after_msg_script(PrintWriter out)throws Exception {
        out.print("});");
        out.print("</script>\n");
        out.print("</head>\n");
        out.print("<body>\n");
        out.print("</body>\n");
        out.print("</html>\n");
    }
}
