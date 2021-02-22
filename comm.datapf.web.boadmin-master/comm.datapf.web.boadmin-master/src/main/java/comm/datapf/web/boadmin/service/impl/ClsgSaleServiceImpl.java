package comm.datapf.web.boadmin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import comm.datapf.web.boadmin.domain.dao.ClsgSaleDao;
import comm.datapf.web.boadmin.domain.dto.clsgSale.*;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.ClsgSaleService;
import comm.datapf.web.boadmin.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClsgSaleServiceImpl implements ClsgSaleService {

    private final ClsgSaleDao clsgSaleDao;
    private final CommonService commonService;

    @Override
    public ApiSuccess<ClsgSaleTtlzDto> doSelectTtlz(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception{
        ApiSuccess<ClsgSaleTtlzDto> success = new ApiSuccess<>();
        ClsgSaleTtlzDto result = new ClsgSaleTtlzDto();

        // 결제집계 조회
        paramDto.setDtlYn("N");
        List<ClsgSaleTtlzMstrDto> clsgSaleTtlzMstrList = doSelectTtlz(paramDto);

        // 결제집계 매출유형별 조회
        paramDto.setDtlYn("Y");
        List<ClsgSaleTtlzMstrDto> clsgSaleTtlzClsgList = doSelectTtlz(paramDto);

        // 결제집계 결제수단별 조회
        List<ClsgSaleTtlzSttlDto> clsgSaleTtlzSttlList = doSelectTtlzSttl(paramDto);

        ObjectMapper objectMapper = new ObjectMapper();
        commonService.insertBoLog(request, response, objectMapper.convertValue(paramDto, HashMap.class));

        result.setClsgSaleTtlzMstrList(clsgSaleTtlzMstrList);
        result.setClsgSaleTtlzClsgList(clsgSaleTtlzClsgList);
        result.setClsgSaleTtlzSttlList(clsgSaleTtlzSttlList);

        success.setData(result);
        return success;
    }

    @Override
    public ApiSuccess<List<ClsgSaleTtlzSttlDtlDto>> doSelectTtlzSttlDtl(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception{
        ApiSuccess<List<ClsgSaleTtlzSttlDtlDto>> success = new ApiSuccess<>();

        // 결제집계 결제수단별 상세
        List<ClsgSaleTtlzSttlDtlDto> clsgSaleTtlzList = clsgSaleDao.doSelectTtlzSttlDtl(paramDto);

        ObjectMapper objectMapper = new ObjectMapper();
        commonService.insertBoLog(request, response, objectMapper.convertValue(paramDto, HashMap.class));

        success.setData(clsgSaleTtlzList);

        return success;
    }

    @Override
    public ApiSuccess<List<ClsgSaleTtlzMstrDto>> doSelectTtlzClsgDtl(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto )throws Exception{
        ApiSuccess<List<ClsgSaleTtlzMstrDto>> success = new ApiSuccess<>();

        // 결제집계 매출유형별 상세
        List<ClsgSaleTtlzMstrDto> clsgSaleTtlzClsgDtlList = clsgSaleDao.doSelectTtlzClsgDtl(paramDto);

        ObjectMapper objectMapper = new ObjectMapper();
        commonService.insertBoLog(request, response, objectMapper.convertValue(paramDto, HashMap.class));

        success.setData(clsgSaleTtlzClsgDtlList);

        return success;
    }

    @Override
    public ApiSuccess<List<ClsgSaleTtlzDelvDto>> doSelectTtlzDelv(HttpServletRequest request, HttpServletResponse response, ClsgSaleParamDto paramDto) throws Exception{
        ApiSuccess<List<ClsgSaleTtlzDelvDto>> success = new ApiSuccess<>();

        // 결제집계 배송비 조회
        List<ClsgSaleTtlzDelvDto> clsgSaleTtlzDelvList= clsgSaleDao.doSelectTtlzDelv(paramDto);

        ObjectMapper objectMapper = new ObjectMapper();
        commonService.insertBoLog(request, response, objectMapper.convertValue(paramDto, HashMap.class));

        success.setData(clsgSaleTtlzDelvList);

        return success;
    }


    public List<ClsgSaleTtlzMstrDto> doSelectTtlz(ClsgSaleParamDto paramDto) throws Exception{
        List<ClsgSaleTtlzMstrDto> clsgSaleTtlzList = clsgSaleDao.doSelectTtlz(paramDto);
        return clsgSaleTtlzList;
    }

    public List<ClsgSaleTtlzSttlDto> doSelectTtlzSttl(ClsgSaleParamDto paramDto) throws Exception{
        List<ClsgSaleTtlzSttlDto> clsgSaleTtlzSttlList = clsgSaleDao.doSelectTtlzSttl(paramDto);
        return clsgSaleTtlzSttlList;
    }


}
