package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.clsgrvnu.ClsgRvnuDeptDto;
import comm.datapf.web.boadmin.domain.dto.clsgrvnu.ClsgRvnuGroupDto;
import comm.datapf.web.boadmin.domain.dto.clsgrvnu.ClsgRvnuSalesCostDto;
import comm.datapf.web.boadmin.domain.dto.clsgrvnu.ClsgRvnuSumDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * 매출집계표 > 매출마감 Dao
 *
 * @project 차세대 마감/정산
 * @author N14235
 * @since 2021-02-05
 * @version 1.0
 *<pre>
 *2021.02.05 : 최초 작성
 *</pre>
 */

@Mapper
public interface ClsgRvnuDao {

    /**
     *
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public List<ClsgRvnuSumDto> selectListClsgRvnuSum(HashMap<String, Object> paramMap)throws SQLException;

    /**
     *
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public List<ClsgRvnuDeptDto> selectListClsgRvnuDept(HashMap<String, Object> paramMap)throws SQLException;

    /**
     *
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public List<ClsgRvnuGroupDto> selectListClsgRvnuGroup(HashMap<String, Object> paramMap)throws SQLException;

    /**
     *
     * @param paramMap
     * @return
     * @throws SQLException
     */
    public List<ClsgRvnuSalesCostDto> selectListClsgRvnuSalesCost(HashMap<String, Object> paramMap)throws SQLException;

}
