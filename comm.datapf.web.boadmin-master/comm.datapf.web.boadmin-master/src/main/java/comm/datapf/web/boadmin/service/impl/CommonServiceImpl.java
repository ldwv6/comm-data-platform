package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.domain.dao.CommonDao;
import comm.datapf.web.boadmin.domain.dao.LoginDao;
import comm.datapf.web.boadmin.domain.dto.common.ComCdAdmnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDtlDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.HistService;
import comm.datapf.web.boadmin.utils.CommonUtils;
import comm.datapf.web.boadmin.utils.DateUtils;
import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;
    private final LoginDao loginDao;
    private final HistService histService;

    public void insertBoLog(LoginAuthDto loginAuthDto) throws Exception{
        commonDao.insertBoLog(loginAuthDto);
    }

    public void insertBoLog(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception {

        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");

        String logInfo = StringUtils.nvl(paramMap.get("logInfo"),"");  //페이징 처리시 사용하는 플래그
        //레이어팝업 또는 등록, 수정 처리시 파라메터 저장 2018-12-17 value = 'Y' , value='U'(수정전후 남기는 플래그)
        String saveParam = StringUtils.nvl(paramMap.get("saveParam"), "N");

        if(!StringUtils.equals(logInfo, "N")){

            if (securityContext != null) {
                String srcPath = request.getRequestURI();
                List<MenuPgmDtlDto> menuPgmDtlList = commonDao.selectSrcInfo(srcPath);

                if(menuPgmDtlList != null && menuPgmDtlList.size() > 0){
                    LoginAuthDto loginAuth = new LoginAuthDto();
                    String adminId = securityContext.getAuthentication().getName();

                    loginAuth.setAdmrId(adminId);
                    loginAuth.setPgmId(menuPgmDtlList.get(0).getPgmId());
                    loginAuth.setPgmTypeCd(menuPgmDtlList.get(0).getPgmTypeCd());
                    loginAuth.setPgmDtlId(menuPgmDtlList.get(0).getPgmDtlId());
                    loginAuth.setAdmrIp(CommonUtils.getClientIpAddr(request));
                    loginAuth.setHistDvsnCd("EXEC");

                    /*if(StringUtils.equals("N", saveParam)) {
                        HashMap<String,Object> map = histService.getSearchData(paramMap);
                        loginAuth.setSearchItem();
                        commonDao.insertBoLog(loginAuth);
                    }else {
                        //saveParam이 Y인 경우
                        HashMap<String,Object> map = histService.getSearchData(paramMap);
                        commonDao.insertBoLog(loginAuth);
                    }*/

                    HashMap<String,Object> map = histService.getSearchData(paramMap);
                    loginAuth.setSearchItem(StringUtils.nvl(map.get("searchItem"),""));
                    loginAuth.setSearchText(StringUtils.nvl(map.get("searchText"),""));
                    commonDao.insertBoLog(loginAuth);


                }else{
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("<html>");
                    out.print("<body>");
                    out.print("<script type='text/javascript'>alert('등록되어 있지 않은 프로그램 입니다.</br>관리자에게 문의하세요');</script>");
                    out.print("</body>");
                    out.print("</html>");
                    out.close();
                    return;
                }

            }else{
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<html>");
                out.print("<body>");
                out.print("<script type='text/javascript'>alert('로그인 후 다시 사용하세요.');location.href='/logout';</script>");
                out.print("</body>");
                out.print("</html>");
                out.close();
                return;
            }
        }
    }

    public void insertRoleBoLog(HttpServletRequest request, HttpServletResponse response, String pgmTypeCd, HashMap<String, Object> paramMap, String mode) throws Exception {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (securityContext != null) {

            String srcPath = request.getRequestURI().replaceAll("\\/", "");

            List<MenuPgmDtlDto> menuPgmDtlList = commonDao.selectSrcInfo(srcPath);

            if(menuPgmDtlList != null && menuPgmDtlList.size() > 0){

                List<String> checkIdList = new ArrayList<String>();
                checkIdList = (List<String>) paramMap.get("checkIdList");

                for(int i = 0 ; i < checkIdList.size() ; i++){

                    if(!checkIdList.get(i).contains("menu")){

                        LoginAuthDto loginAuth = new LoginAuthDto();
                        String adminId = securityContext.getAuthentication().getName();

                        loginAuth.setAdmrIp(adminId);
                        loginAuth.setPgmTypeCd(pgmTypeCd);
                        loginAuth.setPgmId(menuPgmDtlList.get(0).getPgmId());
                        loginAuth.setPgmDtlId(menuPgmDtlList.get(0).getPgmDtlId());
                        loginAuth.setAdmrIp(CommonUtils.getClientIpAddr(request));
                        loginAuth.setHistDvsnCd("ATHR");
                        loginAuth.setSearchItem(paramMap.get("searchItem").toString());

                        HashMap<String, Object> tmpMap = new HashMap<String, Object>();
                        String searchText = checkIdList.get(i);
                        if(StringUtils.equals(mode,"PGM")){
                            tmpMap.put("pgmId", searchText);
                            searchText+=";"+loginDao.selectMenuPgm(tmpMap).getPgmNm()+";";
                        }else if(StringUtils.equals(mode,"ADMR")){
                            tmpMap.put("admrId", searchText);
                            searchText+=";"+loginDao.selectAdminMap(tmpMap).getAdmrNm()+";";
                        }

                        loginAuth.setSearchText(searchText);

                        commonDao.insertBoLog(loginAuth);
                    }
                }

            }else{
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<html>");
                out.print("<body>");
                out.print("<script type='text/javascript'>alert('등록되어 있지 않은 프로그램 입니다.</br>관리자에게 문의하세요');location.href='/main';</script>");
                out.print("</body>");
                out.print("</html>");
                out.close();
                return;
            }

        }else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<html>");
            out.print("<body>");
            out.print("<script type='text/javascript'>alert('로그인 후 다시 사용하세요.');location.href='/logout';</script>");
            out.print("</body>");
            out.print("</html>");
            out.close();
            return;
        }
    }

    public void insertRoleYnBoLog(HttpServletRequest request, HttpServletResponse response, String pgmTypeCd, HashMap<String, Object> paramMap) throws Exception {
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (securityContext != null) {
            String srcPath = request.getRequestURI().replaceAll("\\/", "");
            List<MenuPgmDtlDto> menuPgmDtlList = commonDao.selectSrcInfo(srcPath);

            if(menuPgmDtlList != null && menuPgmDtlList.size() > 0){
                LoginAuthDto loginAuth = new LoginAuthDto();
                String adminId = securityContext.getAuthentication().getName();

                loginAuth.setAdmrId(adminId);
                loginAuth.setPgmTypeCd(pgmTypeCd);
                loginAuth.setPgmId(menuPgmDtlList.get(0).getPgmId());
                loginAuth.setPgmDtlId(menuPgmDtlList.get(0).getPgmDtlId());
                loginAuth.setAdmrIp(CommonUtils.getClientIpAddr(request));
                loginAuth.setHistDvsnCd("ATHR");
                loginAuth.setSearchItem(paramMap.get("searchItem").toString());

                String mode = StringUtils.nvl(paramMap.get("mode"),"");
                String str = "";

                if(StringUtils.equals("selectYn", mode)) str = "조회";
                else if(StringUtils.equals("detailYn", mode)) str = "상세";
                else if(StringUtils.equals("insertYn", mode)) str = "등록";
                else if(StringUtils.equals("updateYn", mode)) str = "변경";
                else if(StringUtils.equals("printYn", mode)) str = "출력";
                else if(StringUtils.equals("useYn", mode)) str = "사용";

                HashMap<String,Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put("pgmId", paramMap.get("ID"));
                loginAuth.setSearchText(paramMap.get("id").toString()+";"+loginDao.selectMenuPgm(tmpMap).getPgmNm()+";"+str+";");

                commonDao.insertBoLog(loginAuth);

            }else{
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<html>");
                out.print("<body>");
                out.print("<script type='text/javascript'>alert('등록되어 있지 않은 프로그램 입니다.</br>관리자에게 문의하세요');location.href='/main';</script>");
                out.print("</body>");
                out.print("</html>");
                out.close();
                return;
            }

        }else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<html>");
            out.print("<body>");
            out.print("<script type='text/javascript'>alert('로그인 후 다시 사용하세요.');location.href='/logout';</script>");
            out.print("</body>");
            out.print("</html>");
            out.close();
            return;
        }
    }

    @Override
    public ApiSuccess<List<ComCdAdmnDto>> doCodeListSelect(HttpServletRequest request, HashMap<String, Object> paramMap) throws Exception{
    // 공통 코드 조회
        ApiSuccess<List<ComCdAdmnDto>> success = new ApiSuccess<>();
        List<ComCdAdmnDto> comCdAdmnList = commonDao.doCodeListSelect(paramMap);
        success.setData(comCdAdmnList);
        return success;
    }
}
