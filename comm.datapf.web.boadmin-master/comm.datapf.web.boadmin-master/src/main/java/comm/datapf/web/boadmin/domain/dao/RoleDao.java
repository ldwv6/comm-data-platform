package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.role.RoleAdmrAuthDto;
import comm.datapf.web.boadmin.domain.dto.role.RoleAuthorDto;
import comm.datapf.web.boadmin.domain.dto.role.RoleParamDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface RoleDao {

    RoleAuthorDto selectSearchRoleList() throws SQLException;

    List<RoleAuthorDto> selectRoleList(RoleParamDto param) throws SQLException;

    int insertRoleAuth(HashMap<String, Object> paramMap) throws SQLException;

    int updateRoleAuth(HashMap<String, Object> paramMap) throws SQLException;

    List<RoleAdmrAuthDto> selectRoleAdmrAuth(HashMap<String, Object> paramMap) throws SQLException;
}