package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.domain.dto.clsgSale.ClsgSaleParamDto;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.ClsgSaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class ClsgSaleRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ClsgSaleService clsgSaleService;

    // 결제집계
    @RequestMapping(value = "/clsgSale/doSelectTtlz", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectTtlz(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ClsgSaleParamDto paramDto) throws Exception {
        ApiSuccess success = clsgSaleService.doSelectTtlz(request, response, paramDto);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    // 결제집계 결제유형별 상세
    @RequestMapping(value = "/clsgSale/doSelectTtlzSttlDtl", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectTtlzSttlDtl(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ClsgSaleParamDto paramDto) throws Exception {
        ApiSuccess success = clsgSaleService.doSelectTtlzSttlDtl(request, response, paramDto);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    // 결제집계 매출유형별 상세
    @RequestMapping(value = "/clsgSale/doSelectTtlzClsgDtl", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectTtlzClsgDtl(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ClsgSaleParamDto paramDto) throws Exception {
        ApiSuccess success = clsgSaleService.doSelectTtlzClsgDtl(request, response, paramDto);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    // 결제집계 배송비 조회
    @RequestMapping(value = "/clsgSale/doSelectTtlzDelv", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectTtlzDelv(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ClsgSaleParamDto paramDto) throws Exception {
        ApiSuccess success = clsgSaleService.doSelectTtlzDelv(request, response, paramDto);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

}
