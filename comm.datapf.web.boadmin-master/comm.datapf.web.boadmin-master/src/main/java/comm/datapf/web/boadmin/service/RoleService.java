package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.domain.dto.role.RoleAuthorDto;
import comm.datapf.web.boadmin.domain.dto.role.RoleParamDto;
import comm.datapf.web.boadmin.message.ApiSuccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface RoleService {

    RoleAuthorDto getSearchRoleList() throws Exception;

    ApiSuccess getRoleListData(HttpServletRequest request, HttpServletResponse response, RoleParamDto param) throws Exception;

    ApiSuccess doRoleAuthInsert(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception;

    ApiSuccess doRoleAuthUpdate(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception;

}