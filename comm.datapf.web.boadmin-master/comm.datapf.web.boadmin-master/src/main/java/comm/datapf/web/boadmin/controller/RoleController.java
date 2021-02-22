package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.domain.dto.role.RoleParamDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @RequestMapping("/roleList")
    public String getRoleList(Model model) throws Exception{
        // 권한관리 호출 및 조회조건(roleId select박스 조회)
        model.addAttribute("searchRoleList", roleService.getSearchRoleList());
        //return roleList;
        return "";
    }

    @RequestMapping("/roleListData")
    public ResponseEntity<ApiSuccess> getRoleListData(HttpServletRequest request, HttpServletResponse response, RoleParamDto param) throws Exception {
        // 권환관리 데이터 조회
        ApiSuccess success =  roleService.getRoleListData(request, response, param);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    @RequestMapping("/roleAuthInsert")
    public String getRoleInsert(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return "views/role/roleInsert";
    }


    @RequestMapping("/doRoleAuthInsert")
    public ResponseEntity<ApiSuccess> doRoleAuthInsert(HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> paramMap) throws Exception{
        ApiSuccess success = roleService.doRoleAuthInsert(request, response, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    @RequestMapping("/doRoleAuthUpdate")
    public ResponseEntity<ApiSuccess> doRoleAuthUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> paramMap) throws Exception{
        ApiSuccess success = roleService.doRoleAuthUpdate(request, response, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    /*@RequestMapping("/doRoleAdmrSelect")
    public ResponseEntity<ApiSuccess> doRoleAdmrSelect(HttpServletRequest request, HttpServletResponse response, @RequestParam HashMap<String, Object> paramMap) throws Exception{
        // 권한관리 > 관리자 등록 > 조회
        ApiSuccess success = roleService.doRoleAuthInsert(request, response, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }*/
}
