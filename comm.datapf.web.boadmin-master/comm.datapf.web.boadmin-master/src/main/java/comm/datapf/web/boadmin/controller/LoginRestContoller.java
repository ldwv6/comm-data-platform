package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.PropertyUtils;
import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class LoginRestContoller {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @RequestMapping("/sessionCheck")
    public ApiSuccess<HashMap<String,Object>> sessionCheck(Model model , HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApiSuccess<HashMap<String,Object>> result = new ApiSuccess<>();
        result.setData(loginService.sessionCheck(request));
        return result;
    }

    @RequestMapping(value = "/doUpdatePassword", method = RequestMethod.POST)
    public ResponseEntity<ApiSuccess> doUpdatePassword(HttpServletRequest request, @RequestParam HashMap<String, Object> paramMap) throws Exception {
        ApiSuccess success = loginService.doUpdatePassword(request, paramMap);
        return new ResponseEntity<ApiSuccess>(success, HttpStatus.OK);
    }

    @RequestMapping("/findPwd")
    public ResponseEntity<ApiSuccess> findPwd(Model model, HttpServletRequest request) throws Exception {
        ApiSuccess result = loginService.findPwd(model, request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
