package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.domain.dao.LoginDao;
import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginConnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDao loginDao;
    private final CommonService commonService;

    @Override
    public LoginDto selectAdminMap(HashMap<String, Object> paramMap) throws Exception {
        return loginDao.selectAdminMap(paramMap);
    }

    @Override
    public void updatePwdCnt(HashMap<String, Object> paramMap) throws Exception {
        loginDao.updatePwdCnt(paramMap);
    }

    @Override
    public String getIpAdminMap(LoginDto loginDto) throws Exception{
        return loginDao.getIpAdminMap(loginDto);
    }

    @Override
    public LoginConnDto getTimeAdminMap(String admrId) throws Exception{
        return loginDao.getTimeAdminMap(admrId);
    }

    @Override
    public String getPsnlTrtmInfo(String adminId) throws Exception{
        return loginDao.getPsnlTrtmInfo(adminId);
    }

    @SuppressWarnings("unused")
    @Override
    public List<HashMap<String, Object>> getBlockIpList()throws Exception {

        HashMap<String, Object> sql_map = new HashMap<String, Object>();
        List<String> data_list = new ArrayList<String>();
        List<HashMap<String, Object>> ip_map_list = new ArrayList<HashMap<String,Object>>();

        sql_map.put("sysId", "BO");
        sql_map.put("workCd", "BLK_IP");

        // 관리자 허용 IP대역체크 체크 추가
        String wasType = WasTypeBean.getWasType();
        if(StringUtils.equals(wasType, "local")){
            data_list.add(PropertyUtils.getProperty("net.separation.ip."+wasType));
        }else{
            data_list = loginDao.getBlockIpList(sql_map);
        }

        String _p1 = "";
        String _p2 = "";
        String _p3 = "";
        String _p4 = "";
        String period = "\\.";
        Object [] _pArr = null;
        List<String> _pList = new ArrayList<String>();
        String[] keys = new String[]{"ip1", "ip2", "ip3", "ip4"};

        for (String ip : data_list) {
            HashMap<String, Object> ipMap = new HashMap<String, Object>();
            for (int i = 0; i < 4; i++) {
                ipMap.put(keys[i], null);
            }
            _pArr = ip.split(period);

            if(StringUtils.isNotEmpty(ip)){
                for (int i = 0; i < _pArr.length; i++) {
                    ipMap.put(keys[i], _pArr[i]);
                }
            }
            ip_map_list.add(ipMap);
        }
        return ip_map_list;
    }

    @Override
    public List<MenuPgmDto> getMenuList(HashMap<String, Object> paramMap) throws Exception {
        List<MenuPgmDto> newMenus = menuSetting(loginDao.getMenuList(paramMap));
        return newMenus;
    }

    private List<MenuPgmDto> menuSetting(List<MenuPgmDto> menus) {
        List<MenuPgmDto> tmpMenus = new ArrayList<>();
        MenuPgmDto menu;
        for(int idx = 0; idx < menus.size(); idx++) {
            menu = menus.get(idx);

            idx = recursive(menu, idx+1, menus);

            tmpMenus.add(menu);
        }

        return tmpMenus;
    }

    private int recursive(final MenuPgmDto higherMenu, final int currIdx, List<MenuPgmDto> menus) {
        int idx = currIdx;
        for(; idx < menus.size(); idx++) {
            MenuPgmDto menu = menus.get(idx);

            int nextIdx = idx+1;

            // 메뉴 트리 뎁스 끝
            if(isEndsOfSubMenu(higherMenu, menu)) {
                idx--;
                break;
            }

            if (hasSubMenu(menu, nextIdx, menus))
                idx = recursive(menu, nextIdx, menus);
            higherMenu.addSubMenu(menu);
        }

        return idx;
    }

    private boolean isEndsOfSubMenu(MenuPgmDto higherMenu, MenuPgmDto menu) {
        return menu.getMenuDepth() <= higherMenu.getMenuDepth();
    }

    private boolean hasSubMenu(MenuPgmDto higherMenu, int nextIdx, List<MenuPgmDto> menus) {
        if(!hasNextMenu(menus, nextIdx))        return false;

        MenuPgmDto nextMenu = menus.get(nextIdx);

        return higherMenu.getMenuDepth() < nextMenu.getMenuDepth();
    }

    private boolean hasNextMenu(List<MenuPgmDto> menus, int nextIdx) {
        return nextIdx < menus.size();
    }

    @Override
    public void updateLastLinTime(LoginDto loginDto)throws Exception {
        loginDao.updateLastLinTime(loginDto);
    }

    @Override
    public ApiSuccess findPwd(Model model, HttpServletRequest request) throws Exception{
        ApiSuccess<String> result = new ApiSuccess<String>();

        //HashMap<String, Object> adminMap = new HashMap<String, Object>();
        LoginDto loginDto = new LoginDto();
        HashMap<String, Object> sendMap = new HashMap<String, Object>();
        String resultCode = "0000|성공입니다.";

        String empy_no = request.getParameter("no");
        String admr_emal = request.getParameter("email");

        if(StringUtils.isEmpty(admr_emal)){
            resultCode = "1111|이메일을 입력해주세요.|''|''";
        }else if(StringUtils.isEmpty(empy_no)){
            resultCode = "1111|사번을 입력해주세요.|"+empy_no+"|''";
        }else{

            sendMap.put("admrEmal", SecurityUtils.stringEncrypt(admr_emal));
            sendMap.put("empyNo", empy_no);
            loginDto = loginDao.selectMbpnoAdmr(sendMap);

            if(StringUtils.isEmpty(loginDto.getAdmrId())) resultCode = "1111|미등록 사용자입니다.|"+empy_no+"|"+admr_emal;
        }

        if(resultCode.contains("0000")){

            //관리자 패스워드 초기화
            String pwd = SecurityUtils.getRandomPwd(8);

            String admrNm = loginDto.getAdmrNm();
            String admrEmal = SecurityUtils.stringDecrypt(loginDto.getAdmrEmal());

            Map<String, Object> args = new HashMap<String, Object>();
            args.put("pwd", pwd);
            args.put("name", admrNm);

            HttpSession session = request.getSession();
            String userId = loginDto.getAdmrId();

            loginDto.setPwd(SecurityUtils.convertToHash(pwd));
            loginDto.setChngDtime(DateUtils.getToday("yyyyMMddHHmmss"));
            loginDto.setIp(CommonUtils.getClientIpAddr(request));

            //20161205 비밀번호재설정 여부 추가 N일 경우, 무조건 비밀번호 재설정 페이지 뷰
            loginDto.setPwdReStupYn("N");

            try {
                int updateResult = loginDao.updateAdminUserInfo(loginDto);
                if(updateResult > 0){
                    resultCode = "0000|성공하였습니다."+"|"+admrEmal;
                }else{
                    resultCode = "9999";
                }
            } catch (SQLException e) {
                resultCode = "9999";
            }

            //패스워드찾기 로그
            LoginAuthDto loginAuthDto = new LoginAuthDto();
            loginAuthDto.setAdmrId(loginDto.getAdmrId());
            loginAuthDto.setPgmId("findPwd");
            loginAuthDto.setPgmTypeCd("FINDPW");
            loginAuthDto.setPgmDtlId("");
            loginAuthDto.setSearchItem("사번;이메일;");
            loginAuthDto.setSearchText(empy_no+";"+SecurityUtils.stringEncrypt(admr_emal)+";");
            loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request));
            loginAuthDto.setHistDvsnCd("CONN");

            commonService.insertBoLog(loginAuthDto);

            //TODO
            String mailResult = "9999";
            //mailResult = MailUtils.sendMail(admrNm, admrEmal, PropertyUtils.getProperty("mail.smtp.admin.init.title"), args, PropertyUtils.getProperty("mail.smtp.admin.init.template"));

            if(!mailResult.contains("0000")){
                resultCode = "9999|메일전송 실패하였습니다.";
            }
        }
        result.setData(resultCode);

        return result;
    }

    @Override
    public void logout(HttpServletRequest request) throws Exception {
        //로그인 로그
        LoginAuthDto loginAuthDto = new LoginAuthDto();
        HttpSession session = request.getSession();
        if(session != null){
            String admrId = (String) session.getAttribute("admrId");
            if(StringUtils.isNotEmpty(admrId)){

                loginAuthDto.setAdmrId(admrId);
                loginAuthDto.setPgmId("logout");
                loginAuthDto.setPgmTypeCd("LOGOUT");
                loginAuthDto.setPgmDtlId("");
                loginAuthDto.setAdmrIp(CommonUtils.getClientIpAddr(request));
                loginAuthDto.setHistDvsnCd("CONN");

                commonService.insertBoLog(loginAuthDto);

                log.info("Logout Success ID ["+admrId+"]");
            }
        }
    }

    @Override
    public HashMap<String, Object> sessionCheck(HttpServletRequest request) throws Exception{
        HashMap<String, Object> result = new HashMap<String, Object>();

        HttpSession session = request.getSession();

        if(StringUtils.isNotEmpty(session.getAttribute("closeTime"))){

            String reqTime = request.getParameter("closeTime");
            String sesTime = session.getAttribute("closeTime").toString();

            if(Integer.parseInt(reqTime) > Integer.parseInt(sesTime)){
                session.setAttribute("closeTime", request.getParameter("closeTime"));
            }

        }else{
            session.setAttribute("closeTime", request.getParameter("closeTime"));
        }

        result.put("resultCode",session.getAttribute("closeTime")+"|"+session.getAttribute("admrId"));

        return result;
    }

    @Override
    public ApiSuccess<String> doUpdatePassword(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception {
        ApiSuccess success = new ApiSuccess();
        String resultCode = "0000";
        String mode = StringUtils.nvl(paramMap.get("mode"),"");

        if(StringUtils.equals(mode, "checkPassword")){

            //관리자 수정시 패스워드 규칙
            String pwd = StringUtils.nvl(paramMap.get("password"),"");
            resultCode = SecurityUtils.passwordValidation(pwd);

        }else if(StringUtils.equals(mode, "updatePwd")){

            HttpSession session = request.getSession();

            //로그인 할때 세션에 저장한 ID
            String admrId = StringUtils.nvl(session.getAttribute("admrId"),"");

            //로그인 할때 세션에 저장한 PWD
            String sessionPWD = StringUtils.nvl(session.getAttribute("pwd"),"");

            //화면에서 넘어온 현재 패스워드
            String oldPwd = StringUtils.nvl(paramMap.get("oldPassword"),"");

            //화면에서 넘어온 새 패스워드
            String newPwd = StringUtils.nvl(paramMap.get("newPassword"),"");

            //화면에서 넘어온 새 패스워드 확인
            String confPwd = StringUtils.nvl(paramMap.get("confPassword"),"");


            if(StringUtils.isEmpty(sessionPWD) || StringUtils.isEmpty(admrId)){
                resultCode = "9999";
            }else{
                if(!sessionPWD.equals(SecurityUtils.convertToHash(oldPwd))){

                    resultCode = "1111";
                }else if(sessionPWD.equals(SecurityUtils.convertToHash(newPwd))){
                    resultCode = "2222";
                }else if(!newPwd.equals(confPwd)){
                    resultCode = "3333";
                }else if(!"0000".equals(SecurityUtils.passwordValidation(newPwd))){
                    resultCode = "4444";
                }else{
                    HashMap<String, Object> sendMap = new HashMap<String, Object>();

                    if(StringUtils.equals("N", (String)paramMap.get("pwdReStupYn"))){
                        sendMap.put("pwdReStupYn", "Y");
                    }else{
                        sendMap.put("pwdReStupYn", "");
                    }

                    sendMap.put("admrId", admrId);

                    sendMap.put("pwd", SecurityUtils.convertToHash(newPwd));

                    sendMap.put("chngDtime", DateUtils.getToday("yyyyMMddHHmmss"));
                    sendMap.put("pwdChngDtime", DateUtils.getToday("yyyyMMddHHmmss"));
                    sendMap.put("ip", CommonUtils.getClientIpAddr(request));

                    //20170324 휴면인 경우, 휴면해제처리
                    sendMap.put("admrStatCd", "N0");

                    try {
                        loginDao.doPasswordUpdate(sendMap);
                    } catch (SQLException e) {
                        resultCode = "9999";
                    }
                }
            }
        }
        success.setData(resultCode);
        return success;
    }

    @Override
    public String getPsnlTrtmProgramInfo(String srcPath) throws Exception {
        return loginDao.getPsnlTrtmProgramInfo(srcPath);
    }

    @Override
    public HashMap<String, Object> getRoleYn(HashMap<String, Object> paramMap) throws Exception {
        return loginDao.getRoleYn(paramMap);
    }

    @Override
    public String getPgmTypeCd(HashMap<String, Object> paramMap) throws Exception{
        return loginDao.getPgmTypeCd(paramMap);
    }

    @Override
    public List<HashMap<String, Object>> getMchtAdminInfo(HashMap<String, Object> loginMap)throws Exception {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        dataList = loginDao.getMchtAdminInfo(loginMap);
        return dataList;
    }

    @Override
    public String getLoginToken(String adminId) throws Exception {
        adminId = loginDao.getLoginToken(adminId);
        return adminId;
    }

}
