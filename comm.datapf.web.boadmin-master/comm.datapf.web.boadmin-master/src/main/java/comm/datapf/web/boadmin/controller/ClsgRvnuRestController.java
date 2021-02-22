package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.ClsgRvnuService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class ClsgRvnuRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClsgRvnuService clsgRvnuService;

    @RequestMapping("/clsgRvnu/doSearch")
    public ResponseEntity<ApiSuccess> getSearchClsgRvnuList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> paramMap) throws Exception {
        // 매출집계표 조회
        ApiSuccess success =  clsgRvnuService.getSearchClsgRvnuList(request, response, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }
}
