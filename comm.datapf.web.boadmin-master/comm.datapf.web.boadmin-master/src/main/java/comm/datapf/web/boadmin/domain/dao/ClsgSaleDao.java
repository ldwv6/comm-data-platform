package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.clsgSale.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ClsgSaleDao {

    List<ClsgSaleTtlzMstrDto> doSelectTtlz(ClsgSaleParamDto paramDto) throws SQLException;

    List<ClsgSaleTtlzSttlDto> doSelectTtlzSttl(ClsgSaleParamDto paramDto) throws SQLException;

    List<ClsgSaleTtlzSttlDtlDto> doSelectTtlzSttlDtl(ClsgSaleParamDto paramDto) throws SQLException;

    List<ClsgSaleTtlzMstrDto> doSelectTtlzClsgDtl(ClsgSaleParamDto paramDto) throws SQLException;

    List<ClsgSaleTtlzDelvDto> doSelectTtlzDelv(ClsgSaleParamDto paramDto) throws SQLException;

}
