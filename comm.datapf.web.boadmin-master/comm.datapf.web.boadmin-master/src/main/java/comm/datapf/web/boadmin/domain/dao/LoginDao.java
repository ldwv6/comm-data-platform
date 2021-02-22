package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.login.LoginConnDto;
import comm.datapf.web.boadmin.domain.dto.login.LoginDto;
import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface LoginDao {

    /**
     * 로그인 사용자 정보 SELECT
     * @param paramMap
     * @return
     * @throws Exception
     */
    LoginDto selectAdminMap(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 로그인 비밀번호 오류횟수, 계정잠금 UPDATE
     * @param paramMap
     * @throws SQLException
     */
    void updatePwdCnt(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 접속 가능 IP SELECT
     * @param loginDto
     * @return
     * @throws SQLException
     */
    public String getIpAdminMap(LoginDto loginDto) throws SQLException;

    /**
     * 접속 가능 시간 SELECT
     * @param admrId
     * @return
     * @throws SQLException
     */
    LoginConnDto getTimeAdminMap(String admrId) throws SQLException;

    /**
     * 개인정보열람여부 SELECT
     * @param adminId
     * @return String
     * @throws SQLException
     */
    String getPsnlTrtmInfo(String adminId) throws SQLException;

    /**
     * 접속 차단 IP SELECT
     * @param sql_map
     * @return
     * @throws SQLException
     */
    List<String> getBlockIpList(HashMap<String, Object> sql_map) throws SQLException;

    /**
     * 메뉴 정보 SELECT
     * @param paramMap
     * @return
     */
    List<MenuPgmDto> getMenuList(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 최종 로그인 시간 UPDATE
     * @param loginDtop
     * @return
     * @throws SQLException
     */
    int updateLastLinTime(LoginDto loginDtop) throws SQLException;

    /**
     * 정보 조회
     * @param paramMap
     * @return
     * @throws SQLException
     */
    LoginDto selectMbpnoAdmr(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 사용자 정보 수정
     * @param loginDto
     * @return
     * @throws SQLException
     */
    int updateAdminUserInfo(LoginDto loginDto) throws SQLException;

    /**
     * 패스워드 변경
     * @param paramMap
     * @throws SQLException
     */
    void doPasswordUpdate(HashMap<String, Object> paramMap)throws SQLException;

    /**
     * 개인정보활용 여부 조회
     * @param srcPath
     * @return
     * @throws SQLException
     */
    String getPsnlTrtmProgramInfo(String srcPath) throws SQLException;

    /**
     * 권한 조회
     * @param paramMap
     * @return
     * @throws SQLException
     */
    HashMap<String, Object> getRoleYn(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 메뉴 프로그램 조회
     * @param paramMap
     * @return
     * @throws SQLException
     */
    MenuPgmDto selectMenuPgm(HashMap<String, Object> paramMap) throws SQLException;

    /**
     *
     * @param paramMap
     * @return
     * @throws SQLException
     */
    String getPgmTypeCd(HashMap<String, Object> paramMap) throws SQLException;

    /**
     * 계정아이디로 업체코드 조회
     * @param loginMap
     * @return
     * @throws SQLException
     */
    List<HashMap<String, Object>> getMchtAdminInfo(HashMap<String, Object> loginMap)throws SQLException;

    /**
     * 계정 이력 최근 이력 조회
     * @param adminId
     * @return
     * @throws SQLException
     */
    String getLoginToken(String adminId)throws SQLException;

}
