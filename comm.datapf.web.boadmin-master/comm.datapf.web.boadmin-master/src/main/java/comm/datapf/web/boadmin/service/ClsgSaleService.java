package comm.datapf.web.boadmin.service;

import comm.datapf.web.boadmin.domain.dto.clsgSale.*;
import comm.datapf.web.boadmin.message.ApiSuccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ClsgSaleService {

    ApiSuccess<ClsgSaleTtlzDto> doSelectTtlz(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception;

    ApiSuccess<List<ClsgSaleTtlzSttlDtlDto>> doSelectTtlzSttlDtl(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception;

    ApiSuccess<List<ClsgSaleTtlzMstrDto>> doSelectTtlzClsgDtl(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception;

    ApiSuccess<List<ClsgSaleTtlzDelvDto>> doSelectTtlzDelv(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception;

}
