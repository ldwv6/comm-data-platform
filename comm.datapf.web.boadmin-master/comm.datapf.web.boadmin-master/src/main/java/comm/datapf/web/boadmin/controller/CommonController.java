package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.CommonService;
import comm.datapf.web.boadmin.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommonService commonService;

    @RequestMapping("/getCodeList")
    public ResponseEntity<ApiSuccess> getCodeList(HttpServletRequest request, @RequestParam HashMap<String, Object> paramMap) throws Exception {
        ApiSuccess success = commonService.doCodeListSelect(request, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }
}
