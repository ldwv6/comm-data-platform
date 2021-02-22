package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.service.LoginService;
import comm.datapf.web.boadmin.utils.PropertyUtils;
import comm.datapf.web.boadmin.utils.StringUtils;
import comm.datapf.web.boadmin.utils.WasTypeBean;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @RequestMapping("/main")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        //service.readyLogin(mav, request, response);
        return mav;
    }

    // @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("wasType ", WasTypeBean.getWasType());
        return "views/login/login";
    }


    @RequestMapping("/loginFail")
    public ModelAndView loginFail(HttpServletRequest request) throws Exception {
        return new ModelAndView("views/login/loginFail");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {

        loginService.logout(request);

        HttpSession session = request.getSession();
        session.invalidate();   // 세션 제거
        return "views/login/login";
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpServletRequest request, @RequestParam HashMap<String, Object> paramMap, Model model) throws Exception {
        if(StringUtils.isNotEmpty(paramMap.get("mode"))){
            if(StringUtils.equals("reStup", (String)paramMap.get("mode"))){

                //PWD_RE_STUP_YN 일때 체크
                model.addAttribute("pwdReStupYn", "N");
                model.addAttribute("pwdDate", PropertyUtils.getProperty("passwd.policyDate"));
                return "views/login/change_password";
            }else{
                //mav.setViewName("common/resultCode");

                String resultCode = "";
                //resultCode = service.ChangePasswordModify(request, response, paramMap);
                //mav.addObject("resultCode", resultCode);
            }
        }else{
            //90일 경과와 PWD_RE_STUP_YN 일때 체크
            HttpSession session = request.getSession();
            String pwdReStupYn =  (String)session.getAttribute("pwdReStupYn");
            model.addAttribute("pwdReStupYn", pwdReStupYn);
            model.addAttribute("pwdDate", PropertyUtils.getProperty("passwd.policyDate"));
            return "views/login/change_password";
        }
        return "views/login/change_password";
    }

}
