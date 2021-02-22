package comm.datapf.web.boadmin.domain.dao;


import comm.datapf.web.boadmin.domain.dto.common.ComCdAdmnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginAuthDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDtlDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface CommonDao {

    /**
     * 사용자 로그 INSERT
     * @param loginAuthDto
     * @throws SQLException
     */
    public void insertBoLog(LoginAuthDto loginAuthDto) throws SQLException;


    /**
     *
     * @param srcPath
     * @return
     * @throws SQLException
     */
    public List<MenuPgmDtlDto> selectSrcInfo(String srcPath)throws SQLException;

    /**
     * 공통 코드 조회
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public List<ComCdAdmnDto> doCodeListSelect(HashMap<String, Object> paramMap) throws SQLException;

}
