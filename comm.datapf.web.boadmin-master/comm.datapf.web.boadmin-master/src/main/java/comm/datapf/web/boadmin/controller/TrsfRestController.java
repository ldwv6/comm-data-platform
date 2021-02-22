package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.domain.dto.trsf.UpdateTrsfParamVo;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.TrsfService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class TrsfRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TrsfService trsfService;

    /**
     * 채권 양도양수 - 채권 양도자 조회
     */
    @RequestMapping(value = "trsf/doSelectGranter", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectGranter(@RequestParam HashMap<String, Object> input) throws Exception {
        ApiSuccess success = trsfService.doSelectGranter(input);

        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    /**
     * 채권 양도양수 - 채권 양수자 조회
     */
    @RequestMapping(value = "trsf/doSelectReceiver", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectReceiver(@RequestParam HashMap<String, Object> input) throws Exception {
        ApiSuccess success = trsfService.doSelectReceiver(input);

        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    /**
     * 채권 양도양수 - 채권 양수자별 지급내역 조회
     */
    @RequestMapping(value = "trsf/doSelectPymnt", method = RequestMethod.GET)
    public ResponseEntity<ApiSuccess> doSelectPymnt(@RequestParam HashMap<String, Object> input) throws Exception {
        ApiSuccess success = trsfService.doSelectPymnt(input);

        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    /**
     * 채권 양도양수 - 등록/수정
     */
    @RequestMapping(value = "trsf/doUpdateTrsf", method = RequestMethod.PUT)
    public ResponseEntity<ApiSuccess> doUpdateTrsf(@RequestBody UpdateTrsfParamVo param) throws Exception {
        ApiSuccess success = trsfService.doUpdateTrsf(param);

        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }
}
