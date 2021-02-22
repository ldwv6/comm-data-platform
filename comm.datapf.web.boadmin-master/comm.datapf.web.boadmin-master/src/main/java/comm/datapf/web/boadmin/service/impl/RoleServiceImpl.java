package comm.datapf.web.boadmin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import comm.datapf.web.boadmin.domain.dao.RoleDao;
import comm.datapf.web.boadmin.domain.dto.role.RoleAdmrAuthDto;
import comm.datapf.web.boadmin.domain.dto.role.RoleAuthorDto;
import comm.datapf.web.boadmin.domain.dto.role.RoleParamDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.RoleService;
import comm.datapf.web.boadmin.utils.CommonUtils;
import comm.datapf.web.boadmin.utils.DateUtils;
import comm.datapf.web.boadmin.utils.SecurityUtils;
import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final CommonService commonService;

    @Override
    public RoleAuthorDto getSearchRoleList() throws Exception{
        return roleDao.selectSearchRoleList();
    }

    @Override
    public ApiSuccess<List<RoleAuthorDto>> getRoleListData(HttpServletRequest request, HttpServletResponse response, RoleParamDto param) throws Exception{
        ApiSuccess<List<RoleAuthorDto>> result = new ApiSuccess<>();
        List<RoleAuthorDto> roleAuthorList = roleDao.selectRoleList(param);

        for(int i=0; i<roleAuthorList.size();i++){
            roleAuthorList.get(i).setChngAdmrIp(SecurityUtils.stringFormat("IP_INFO", roleAuthorList.get(i).getChngAdmrIp()));
        }
        result.setData(roleAuthorList);

        ObjectMapper objectMapper = new ObjectMapper();
        commonService.insertBoLog(request, response, objectMapper.convertValue(param, HashMap.class));

        return result;
    }

    @Override
    public ApiSuccess<String> doRoleAuthInsert(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception{
        ApiSuccess success = new ApiSuccess();

        // xss 처리 해야하나?
        // paramMap = StringUtils.getParamXssProc(paramMap);

        HttpSession session = request.getSession();
        String userId = StringUtils.nvl(session.getAttribute("admrId"),"");

        if(StringUtils.isNotEmpty(userId)){

            paramMap.put("userId", userId);
            paramMap.put("ip", CommonUtils.getClientIpAddr(request));
            paramMap.put("today", DateUtils.getToday("yyyyMMddHHmmss"));

            try {
                int count = roleDao.insertRoleAuth(paramMap);
                if(count < 1 ){
                    success.setData("ERROR");
                }else{
                    success.setData("SUCCESS");
                }
            } catch (SQLException e) {
                success.setData("ERROR");
            }

        }else{
            success.setData("ERROR");
        }

        return success;
    }

    @Override
    public ApiSuccess doRoleAuthUpdate(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception{
        ApiSuccess success = new ApiSuccess();

        // xss 처리 해야하나?
        // paramMap = StringUtils.getParamXssProc(paramMap);

        HttpSession session = request.getSession();
        String userId = StringUtils.nvl(session.getAttribute("admrId"),"");

        //20161221 ROLE 사용여부 미사용일 경우, ROLE에 종속된 관리자 있는지 여부 체크
        // 종속된 관리자가 있는 경우, 권한을 가진 관리자가 있어 삭제할 수 없습니다. 문구 출력

        boolean skip = true;
        if(StringUtils.equals("N", (String)paramMap.get("useYn"))){
            List<RoleAdmrAuthDto> roleAdmrAuthDtoList = new ArrayList<RoleAdmrAuthDto>();
            paramMap.put("roleUpate", "Y");
            roleAdmrAuthDtoList = roleDao.selectRoleAdmrAuth(paramMap);
            int chk_cnt = 0;
            chk_cnt = roleAdmrAuthDtoList.size();

            if(chk_cnt > 0){
                skip = false;
            }
        }

        commonService.insertBoLog(request, response, paramMap);

        if(skip){
            if(StringUtils.isNotEmpty(userId)){

                paramMap.put("USER_ID", userId);
                paramMap.put("IP",CommonUtils.getClientIpAddr(request));
                paramMap.put("TODAY", DateUtils.getToday("yyyyMMddHHmmss"));

                try {
                    int count = roleDao.updateRoleAuth(paramMap);
                    if(count < 1 ){
                        success.setData("ERROR");
                    }else{
                        success.setData("SUCCESS");
                    }
                } catch (SQLException e) {
                    success.setData("9999");
                }

            }else{
                success.setData("9999");
            }
        }else{
            success.setData("1111");
        }
        return success;
    }

}
