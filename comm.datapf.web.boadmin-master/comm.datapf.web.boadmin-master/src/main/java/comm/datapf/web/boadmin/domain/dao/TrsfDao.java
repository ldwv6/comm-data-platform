package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.trsf.TrsfGranterVo;
import comm.datapf.web.boadmin.domain.dto.trsf.TrsfPymntVo;
import comm.datapf.web.boadmin.domain.dto.trsf.TrsfReceiverVo;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TrsfDao {
    List<TrsfGranterVo> getTrsfGranter(HashMap<String, Object> input) throws SQLException;

    List<TrsfReceiverVo> getTrsfReceiver(HashMap<String, Object> input) throws SQLException;

    List<TrsfPymntVo> getTrsfPymnt(HashMap<String, Object> input) throws SQLException;
}
